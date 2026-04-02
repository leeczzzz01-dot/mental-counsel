package com.metal.counsel.system.controller;

import com.metal.counsel.common.result.Result;
import com.metal.counsel.system.pojo.dto.LoginDTO;
import com.metal.counsel.system.pojo.vo.LoginVO;
import com.metal.counsel.system.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO request) {
        LoginVO response = authService.login(request);
        return Result.ok(response);
    }

    @PostMapping("/logout")
    public Result<Void> logout() {
        return Result.ok();
    }
}
