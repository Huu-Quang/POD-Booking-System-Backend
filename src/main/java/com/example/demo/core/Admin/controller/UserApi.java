package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.model.request.AdminUserRequest;
import com.example.demo.core.Admin.model.response.AdminUserResponse;
import com.example.demo.core.Admin.service.AdUserService;
import com.example.demo.entity.User;
import com.example.demo.util.DataUltil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/admin/user")
public class UserApi {
    @Autowired
    AdUserService adUserService;

    @GetMapping("/getAllUser")
    public List<AdminUserResponse> getAllUser() {
        return adUserService.getAllUser();
    }

    @GetMapping("/getAllUserByRole")
    public List<AdminUserResponse> getAllUserByRole(@RequestParam("role") String role) {
        return adUserService.getAllUserByRole(role);
    }

    @GetMapping("/trang-thai")
    public ResponseEntity<?> getAllByTrangThai(@RequestParam("trangThai") Integer trangThai) {
        List<User> page = adUserService.getAllByTrangThai(trangThai);
        HashMap<String, Object> map = DataUltil.setData("ok", page);
        return ResponseEntity.ok(map);
    }

    @PostMapping()
    public ResponseEntity<?> add(@RequestBody AdminUserRequest request) {
        return ResponseEntity.ok(adUserService.add(request));
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@RequestBody AdminUserRequest request, @PathVariable Integer id) {
        return ResponseEntity.ok(adUserService.update(request, id));
    }

    @PutMapping("{id}/delete")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(adUserService.delete(id));
    }

    @PutMapping("{id}/vo-hieu-hoa")
    public ResponseEntity<?> voHieuHoaUser(@PathVariable Integer id) {
        return ResponseEntity.ok(adUserService.VoHieuHoaUser(id));
    }
}