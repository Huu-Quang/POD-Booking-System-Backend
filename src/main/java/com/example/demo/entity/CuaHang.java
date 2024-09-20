package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "cua_hang")
public class CuaHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Column(name = "ten")
    private String ten;

    @Column(name = "ma")
    private String ma;

    @Column(name = "ngay_sua")
    private String ngaySua;

    @Column(name = "ngay_tao")
    private String ngayTao;

    @Column(name = "trang_thai")
    private Integer trangThai;

    @Column(name = "mo_ta", length = 10000)
    private String moTa;

    @ManyToOne
    @JoinColumn(name = "id_loai_cua_hang")
    private LoaiCuaHang loaiCuaHang;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_dia_chi", referencedColumnName = "id")
    private DiaChi diaChi;
}
