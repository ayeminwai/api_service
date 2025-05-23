package com.maybank.apiservice.controller;

import com.maybank.apiservice.dto.KycUserResponse;
import com.maybank.apiservice.dto.ResponseDTO;
import com.maybank.apiservice.dto.UpdateUserRequest;
import com.maybank.apiservice.dto.UserRequest;
import com.maybank.apiservice.model.User;
import com.maybank.apiservice.service.UserService;
import com.maybank.apiservice.util.ResponseUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<ResponseDTO<User>> createUser(@Valid @RequestBody UserRequest request) {
        return ResponseUtil.success(userService.createUser(request));
    }

    @PutMapping
    public ResponseEntity<ResponseDTO<User>> updateUser(@Valid @RequestBody UpdateUserRequest request) {
        return ResponseUtil.success(userService.updateUser(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<User>> getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        return ResponseUtil.success(user);
    }

    @GetMapping("/{id}/external-kyc")
    public ResponseEntity<ResponseDTO<KycUserResponse>> callExternalApi(@PathVariable Long id) {
        KycUserResponse kycData = userService.callExternalKyc(id);
        return ResponseUtil.success(kycData);
    }
}
