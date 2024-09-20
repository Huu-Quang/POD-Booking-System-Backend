package com.example.demo.core.Admin.service;

import com.example.demo.core.Admin.model.request.AdminLoaiCuaHangRequest;
import com.example.demo.entity.LoaiCuaHang;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

public interface AdLoaiCuaHangService {
    Page<LoaiCuaHang> getAll(Integer page);

    LoaiCuaHang getById(Integer id);

    List<LoaiCuaHang> findAll();

    List<LoaiCuaHang> getAllByTrangThai(Integer trangThai);

    Page<LoaiCuaHang> search(String keyword, Integer page);

    HashMap<String, Object> add(AdminLoaiCuaHangRequest loaiCuaHang);

    HashMap<String,Object> update(AdminLoaiCuaHangRequest loaiCuaHang, Integer id);

    HashMap<String,Object> delete(Integer id);

}
