package com.example.demo.entity;

import com.example.demo.entity.Enum.PaymentEnums;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    Date createAt;

    @Enumerated(EnumType.STRING)
    PaymentEnums payment_method;

    @OneToOne
    @JoinColumn(name = "order_id")
    Orders orders;

    @OneToOne
    @JoinColumn(name = "pod_order_id")
    PODOrder podOrder;

    @OneToMany(mappedBy = "payment",cascade = CascadeType.ALL)
    Set<Transactions> transactions;

    @ManyToOne
    @JoinColumn(name = "pod_id")
    POD pod;
}
