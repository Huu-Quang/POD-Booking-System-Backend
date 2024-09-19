package com.example.demo.core.Admin.service.impl;

import com.example.demo.core.Admin.repository.AdminThongBaoRepository;
import com.example.demo.entity.ThongBao;
import com.example.demo.entity.User;
import com.example.demo.infrastructure.status.ThongBaoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdThongBaoServiceImpl {

    @Autowired
    private AdminThongBaoRepository adminThongBaoRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public List<ThongBao> getAll() {
        List<ThongBao> lst = adminThongBaoRepository.findAll();
        return lst;
    }

    public Integer dem() {
        Integer lst = adminThongBaoRepository.dem();
        return lst;
    }

    public ThongBao daXem(Integer id) {
        ThongBao thongBao = adminThongBaoRepository.findById(id).get();
        if (thongBao != null) {
            thongBao.setTrangThai(ThongBaoStatus.DA_XEM);
            return adminThongBaoRepository.save(thongBao);
        }
        return null;
    }
}
