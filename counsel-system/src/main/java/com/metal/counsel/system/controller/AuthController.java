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

    /**
     * 管理员登录接口
     *
     * @param request 登录请求参数，包含用户名和密码
     * @return 返回封装的登录信息，即 Token 等
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@RequestBody LoginDTO request) {
        LoginVO response = authService.login(request);
        return Result.ok(response);
    }

    /**
     * 管理员登出接口
     *
     * @return 返回成功状态
     */
    @PostMapping("/logout")
    public Result<Void> logout() {
        return Result.ok();
    }
}
