package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.entity.Enum.PaymentEnums;
import com.example.demo.entity.Enum.TransactionsEnum;
import com.example.demo.exception.EntityNotFoundException;
import com.example.demo.model.OrderRequest;
import com.example.demo.model.PODOrderDetailRequest;
import com.example.demo.model.PODOrderRequest;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.PODOrderRepository;
import com.example.demo.repository.PODRepository;
import com.example.demo.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PODOrderService {

    @Autowired
    PODOrderRepository podOrderRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PODRepository podRepository;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    PaymentRepository paymentRepository;

    public PODOrder create(PODOrderRequest podOrderRequest) {

        Account customer = authenticationService.getCurrentAccount();

        PODOrder podOrder = new PODOrder();
        List<PODOrderDetail> podOrderDetails = new ArrayList<>();
        float total = 0;

        podOrder.setCustomer(customer);

        podOrder.setDate(new Date());

        for (PODOrderDetailRequest podOrderDetailRequest : podOrderRequest.getDetail()) {

            POD pod = podRepository.findPODById(podOrderDetailRequest.getPodId());

            PODOrderDetail podOrderDetail = new PODOrderDetail();

            podOrderDetail.setQuantity(podOrderDetailRequest.getQuantity());
            podOrderDetail.setPrice(podOrderDetail.getPrice());
            podOrderDetail.setPodOrder(podOrder);

            podOrderDetail.setPod(pod);

            podOrderDetails.add(podOrderDetail);

            total += podOrderDetail.getPrice() * podOrderDetail.getQuantity();
        }
        podOrder.setPodOrderDetails(podOrderDetails);
        podOrder.setTotal(total);


        return podOrderRepository.save(podOrder);
    }

    public List<PODOrder> getAll() {
        Account customer = authenticationService.getCurrentAccount();
        List<PODOrder> podOrders = podOrderRepository.findPODOrderByCustomer(customer);
        if (podOrders == null || podOrders.isEmpty()) {
            throw new EntityNotFoundException("No orders found for the current customer");
        }

    return podOrders;
}
public PODOrder get (UUID id) {
    PODOrder podOrder = podOrderRepository.findPODOrderById(id);
    if (podOrder == null) {
        throw new EntityNotFoundException("Order not found");
    }
    return podOrder;
}
public PODOrder update(UUID id, PODOrderRequest podOrderRequest) {
    PODOrder podOrder = podOrderRepository.findPODOrderById(id);
    List<PODOrderDetail> podOrderDetails = new ArrayList<>();
    float total = 0;
    for (PODOrderDetailRequest podOrderDetailRequest : podOrderRequest.getDetail()) {
        POD pod = podRepository.findPODById(podOrderDetailRequest.getPodId());
        PODOrderDetail podOrderDetail = new PODOrderDetail();
        podOrderDetail.setPodOrder(podOrder);
        podOrderDetail.setQuantity(podOrderDetailRequest.getQuantity());
        podOrderDetail.setPrice(podOrderDetail.getPrice());
        podOrderDetail.setPod(pod);
        podOrderDetails.add(podOrderDetail);
        total += podOrderDetail.getPrice() * podOrderDetail.getQuantity();
    }
    podOrder.setPodOrderDetails(podOrderDetails);
    podOrder.setTotal(total);
    return podOrderRepository.save(podOrder);
}
public void delete(UUID id) {
    PODOrder podOrder = podOrderRepository.findPODOrderById(id);
    podOrderRepository.delete(podOrder);
}
    public String createUrl(PODOrderRequest PODOrderRequest) throws  Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        LocalDateTime createDate = LocalDateTime.now();
        String formattedCreateDate = createDate.format(formatter);


        // create order
        PODOrder podOrder = create(PODOrderRequest);

        float money = podOrder.getTotal() * 100;
        String amount = String.valueOf((int) money);







        String tmnCode = "0I712H9B";
        String secretKey = "ZOPSQ8G5KQFVU2PDYNEA0VB05BQUVSZO";
        String vnpUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
        String returnUrl = "https://www.google.com.vn/?hl=vi" + podOrder.getId(); //frontend
        String currCode = "VND";

        Map<String, String> vnpParams = new TreeMap<>();
        vnpParams.put("vnp_Version", "2.1.0");
        vnpParams.put("vnp_Command", "pay");
        vnpParams.put("vnp_TmnCode", tmnCode);
        vnpParams.put("vnp_Locale", "vn");
        vnpParams.put("vnp_CurrCode", currCode);
        vnpParams.put("vnp_TxnRef", podOrder.getId().toString());
        vnpParams.put("vnp_OrderInfo", "Thanh toan cho ma GD: " + podOrder.getId());
        vnpParams.put("vnp_OrderType", "other");
        vnpParams.put("vnp_Amount",amount);

        vnpParams.put("vnp_ReturnUrl", returnUrl);
        vnpParams.put("vnp_CreateDate", formattedCreateDate);
        vnpParams.put("vnp_IpAddr", "128.199.178.23");

        StringBuilder signDataBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            signDataBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            signDataBuilder.append("=");
            signDataBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            signDataBuilder.append("&");
        }
        signDataBuilder.deleteCharAt(signDataBuilder.length() - 1); // Remove last '&'

        String signData = signDataBuilder.toString();
        String signed = generateHMAC(secretKey, signData);

        vnpParams.put("vnp_SecureHash", signed);

        StringBuilder urlBuilder = new StringBuilder(vnpUrl);
        urlBuilder.append("?");
        for (Map.Entry<String, String> entry : vnpParams.entrySet()) {
            urlBuilder.append(URLEncoder.encode(entry.getKey(), StandardCharsets.UTF_8.toString()));
            urlBuilder.append("=");
            urlBuilder.append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.toString()));
            urlBuilder.append("&");
        }
        urlBuilder.deleteCharAt(urlBuilder.length() - 1); // Remove last '&'

        return urlBuilder.toString();
    }
    private String generateHMAC(String secretKey, String signData) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmacSha512 = Mac.getInstance("HmacSHA512");
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        hmacSha512.init(keySpec);
        byte[] hmacBytes = hmacSha512.doFinal(signData.getBytes(StandardCharsets.UTF_8));

        StringBuilder result = new StringBuilder();
        for (byte b : hmacBytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

    public void createTransaction(UUID uuid) {
        // find order

        PODOrder podOrder = podOrderRepository.findById(uuid).orElseThrow(()->new EntityNotFoundException("Order not found"));
        // create payment
        Payment payment = new Payment();
        payment.setPodOrder(podOrder);
        payment.setCreateAt(new Date());
        payment.setPayment_method(PaymentEnums.BANKING);;


        Set<Transactions> setTransactions = new HashSet<>();


        // create transaction
        Transactions transactions1 = new Transactions();
        //VNPay -> Member
        Account member = authenticationService.getCurrentAccount();
        transactions1.setFrom(null);
        transactions1.setTo(member);
        transactions1.setPayment(payment);
        transactions1.setStatus(TransactionsEnum.SUCCESS);
        transactions1.setDescription("VNPay to Member");

        setTransactions.add(transactions1);

        Transactions transactions2 = new Transactions();
        //VNPay -> ADMIN
        Account admin = accountRepository.findAccountByRole(Role.ADMIN);
        transactions2.setFrom(member);
        transactions2.setTo(admin);
        transactions2.setPayment(payment);
        transactions2.setStatus(TransactionsEnum.SUCCESS);
        transactions2.setDescription("Member to Admin");
        float newBalance = admin.getBalance() + podOrder.getTotal() * 0.10f;
        admin.setBalance(newBalance);


        setTransactions.add(transactions2);


        //ADMIN -> OWNER
        Transactions transactions3 = new Transactions();
        transactions3.setPayment(payment);
        transactions3.setStatus(TransactionsEnum.SUCCESS);
        transactions3.setDescription("Admin to Owner");
        transactions3.setFrom(admin);
        Account owner = podOrder.getPodOrderDetails().get(0).getPod().getAccount();
        transactions3.setTo(owner);
        float newOwnerBalance = owner.getBalance() + podOrder.getTotal() * (1 - 0.10f);
        owner.setBalance(newOwnerBalance);
        setTransactions.add(transactions3);


        accountRepository.save(admin);
        accountRepository.save(owner);
        payment.setTransactions(setTransactions);
        paymentRepository.save(payment);


    }
}



