package com.example.demo.core.Admin.repository;

import com.example.demo.entity.LoaiCuaHang;
import com.example.demo.reponsitory.LoaiCuaHangReponsitory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdLoaiCuaHangReponsitory extends LoaiCuaHangReponsitory {
    @Query("select  pot from  LoaiCuaHang  pot where pot.ten like :keyword or pot.ma like :keyword")
    Page<LoaiCuaHang> search(String keyword, Pageable pageable);
    List<LoaiCuaHang> findAllByTrangThai(Integer trangThai, Sort sort);
    Optional<LoaiCuaHang> findByTen(String ten);

    @Query("select  pot from  LoaiCuaHang  pot where pot.ten like :ten")
    LoaiCuaHang findByTens(String ten);

}
