package com.example.demo.core.khachHang.service.impl;

import com.example.demo.core.Admin.repository.AdUserRepository;
import com.example.demo.core.khachHang.repository.KHThongBaoRepository;
import com.example.demo.core.token.service.TokenService;
import com.example.demo.entity.ThongBao;
import com.example.demo.entity.User;
import com.example.demo.infrastructure.status.ThongBaoStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThongBaoServiceImpl {
    @Autowired
    TokenService tokenService;

    @Autowired
    AdUserRepository userRepository;

    @Autowired
    private KHThongBaoRepository khThongBaoRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public List<ThongBao> getAll(String token) {
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);
        return khThongBaoRepository.findAll(user.getId());
    }

    public Integer dem(String token) {
        if (tokenService.getUserNameByToken(token) == null) {
            return null;
        }
        String userName = tokenService.getUserNameByToken(token);
        User user = userRepository.findByUserName(userName);
        return khThongBaoRepository.dem(user.getId());
    }

    public ThongBao daXem(Integer id) {
        ThongBao thongBao = khThongBaoRepository.findById(id).get();
        if (thongBao != null) {
            thongBao.setTrangThai(ThongBaoStatus.DA_XEM);
            return khThongBaoRepository.save(thongBao);
        }
        return null;
    }
}
