package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "dia_chi")
public class DiaChi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "dia_chi", length = 10000)
    private String diaChi;

    @Column(name = "id_tinh_thanh")
    private Integer idTinhThanh;

    @Column(name = "ten_tinh_thanh")
    private String tenTinhThanh;

    @Column(name = "id_quan_huyen")
    private Integer idQuanHuyen;

    @Column(name = "ten_quan_huyen")
    private String tenQuanHuyen;

    @Column(name = "id_phuong_xa")
    private String idphuongXa;

    @Column(name = "ten_phuong_xa")
    private String tenphuongXa;

    @Column(name = "loai_dia_chi")
    private String loaiDiaChi;

    @Column(name = "ngay_sua")
    private String ngaySua;

    @Column(name = "ngay_tao")
    private String ngayTao;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @OneToOne(mappedBy = "diaChi")
    private CuaHang cuaHang;
}
