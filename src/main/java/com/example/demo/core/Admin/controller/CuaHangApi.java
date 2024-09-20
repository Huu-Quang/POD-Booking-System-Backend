package com.example.demo.core.Admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/cua-hang")
public class CuaHangApi {
//    @Autowired
//    private AdCuaHangService cuaHangService;
//    @Autowired
//    private AdUpdateCuaHangService adUpdatecuaHangService;
//
//    @GetMapping("/by-loai")
//    public ResponseEntity<?> getSPByLoai(@RequestParam("idloaicuahang") Integer idloaicuahang) {
//        List<AdminCuaHangResponse> page = cuaHangService.getSanPhamByIdLoai(idloaicuahang);
//        return ResponseEntity.ok(page);
//    }
//
//    @GetMapping("/by-loai")
//    public ResponseEntity<?> getCHByLoaiCH(@RequestParam("idloaicuahang") Integer idloaicuahang) {
//        List<AdminCuaHangResponse> page = cuaHangService.getSanPhamByIdLoai(idloaicuahang);
//        return ResponseEntity.ok(page);
//    }
}
