package com.example.demo.core.Admin.service;


import com.example.demo.core.Admin.model.request.AdminUserRequest;
import com.example.demo.core.Admin.model.response.AdminUserResponse;
import com.example.demo.entity.User;

import java.util.List;

public interface AdUserService {

    List<AdminUserResponse> getKhachHang();

    List<AdminUserResponse> getNhanVien();

    List<AdminUserResponse> getAdmin();

    List<User> getAllByTrangThai(Integer trangThai);

    AdminUserResponse add(AdminUserRequest user);

    AdminUserResponse delete(Integer id);

    AdminUserResponse update(AdminUserRequest user, Integer id);

    AdminUserResponse VoHieuHoaUser(Integer id);

    List<AdminUserResponse> getAllUserByRole(String role);

    List<AdminUserResponse> getAllUser();
}
