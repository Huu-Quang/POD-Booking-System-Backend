package com.example.demo.core.Admin.model.request;

import com.example.demo.entity.LoaiCuaHang;
import com.example.demo.infrastructure.adapter.DtoToEntity;
import com.example.demo.util.DatetimeUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminLoaiCuaHangRequest implements DtoToEntity<LoaiCuaHang> {

    private Integer id;

    @NotBlank(message = "Không bỏ trống tên")
    private String ten;

    private String ma;

    private String ngaySua;

    private String ngayTao;

    private Integer trangThai;


    @Override
    public LoaiCuaHang dtoToEntity(LoaiCuaHang loaiCuaHang) {
        loaiCuaHang.setMa(this.ma);
        loaiCuaHang.setNgayTao(DatetimeUtil.getCurrentDate());
        loaiCuaHang.setTen(this.ten);
        loaiCuaHang.setTrangThai(this.trangThai);
        return loaiCuaHang;
    }
}
