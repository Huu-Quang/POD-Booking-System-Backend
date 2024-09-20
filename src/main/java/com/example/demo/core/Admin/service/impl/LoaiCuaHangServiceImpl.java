package com.example.demo.core.Admin.service.impl;

import com.example.demo.core.Admin.model.request.AdminLoaiCuaHangRequest;
import com.example.demo.core.Admin.repository.AdLoaiCuaHangReponsitory;
import com.example.demo.core.Admin.service.AdLoaiCuaHangService;
import com.example.demo.entity.LoaiCuaHang;
import com.example.demo.reponsitory.LoaiCuaHangReponsitory;
import com.example.demo.util.DataUltil;
import com.example.demo.util.DatetimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class LoaiCuaHangServiceImpl implements AdLoaiCuaHangService {
    @Autowired
    private AdLoaiCuaHangReponsitory loaiCuaHangReponsitory;

    @Override
    public Page<LoaiCuaHang> getAll(Integer page) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page, 5, sort);
        return loaiCuaHangReponsitory.findAll(pageable);
    }

    @Override
    public List<LoaiCuaHang> findAll() {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return loaiCuaHangReponsitory.findAll(sort);
    }

    @Override
    public List<LoaiCuaHang> getAllByTrangThai(Integer trangThai) {
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        return loaiCuaHangReponsitory.findAllByTrangThai(trangThai, sort);
    }

    @Override
    public LoaiCuaHang getById(Integer id) {
        Optional<LoaiCuaHang> optional = this.loaiCuaHangReponsitory.findById(id);
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public Page<LoaiCuaHang> search(String keyword, Integer page) {
        if (keyword == null) {
            return this.getAll(page);
        } else {
            Pageable pageable = PageRequest.of(page, 5);
            return loaiCuaHangReponsitory.search("%" + keyword + "%", pageable);
        }
    }

    @Override
    public HashMap<String, Object> add(AdminLoaiCuaHangRequest dto) {
        dto.setTrangThai(1);
        dto.setNgayTao(DatetimeUtil.getCurrentDate());
        LoaiCuaHang loaiCuaHang = dto.dtoToEntity(new LoaiCuaHang());
        try {
            LoaiCuaHang loaiCuaHang1 = loaiCuaHangReponsitory.save(loaiCuaHang);
            loaiCuaHang1.setMa("L" + loaiCuaHang1.getId());
            return DataUltil.setData("success", loaiCuaHangReponsitory.save(loaiCuaHang1));
        } catch (Exception e) {
            return DataUltil.setData("error", "error");
        }
    }

    @Override
    public HashMap<String, Object> update(AdminLoaiCuaHangRequest dto, Integer id) {
        Optional<LoaiCuaHang> optional = loaiCuaHangReponsitory.findById(id);
        if (optional.isPresent()) {
            LoaiCuaHang loaiCuaHang = optional.get();
            loaiCuaHang.setMa(loaiCuaHang.getMa());
            loaiCuaHang.setTen(dto.getTen());
            loaiCuaHang.setNgayTao(loaiCuaHang.getNgayTao());
            loaiCuaHang.setNgaySua(DatetimeUtil.getCurrentDate());
            try {
                return DataUltil.setData("success", loaiCuaHangReponsitory.save(loaiCuaHang));
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy loại sản phẩm để sửa");
        }
    }

    @Override
    public HashMap<String, Object> delete(Integer id) {
        Optional<LoaiCuaHang> optional = loaiCuaHangReponsitory.findById(id);
        if (optional.isPresent()) {
            LoaiCuaHang loaiCuaHang = optional.get();
            loaiCuaHang.setTrangThai(0);
            try {
                return DataUltil.setData("success", loaiCuaHangReponsitory.save(loaiCuaHang));
            } catch (Exception e) {
                return DataUltil.setData("error", "error");
            }
        } else {
            return DataUltil.setData("error", "không tìm thấy loại sản phẩm để xóa");
        }
    }
}
