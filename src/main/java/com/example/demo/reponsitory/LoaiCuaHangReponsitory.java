package com.example.demo.reponsitory;

import com.example.demo.entity.LoaiCuaHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoaiCuaHangReponsitory extends JpaRepository<LoaiCuaHang, Integer> {
}
