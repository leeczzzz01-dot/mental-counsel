package com.metal.counsel.system.controller.wx;

import com.metal.counsel.common.result.Result;
import com.metal.counsel.system.pojo.dto.WxLoginDTO;
import com.metal.counsel.system.pojo.vo.LoginVO;
import com.metal.counsel.system.pojo.vo.WxLoginVO;
import com.metal.counsel.system.service.WxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信小程序专属认证 Controller
 */
@RestController
@RequestMapping("/auth")
public class WxAuthController {

    @Autowired
    private WxService wxService;

    /**
     * 微信小程序登录
     *
     * @param request 包含从微信前端获取到的 code 凭证
     * @return 返回带有系统 Token、用户昵称和角色的微信登录响应实体
     */
    @PostMapping("/wx-login")
    public Result<WxLoginVO> wxLogin(@RequestBody WxLoginDTO request) {
        LoginVO loginVO = wxService.login(request.getCode());
        WxLoginVO wxLoginVO = new WxLoginVO();
        wxLoginVO.setToken(loginVO.getToken());
        wxLoginVO.setUserName(loginVO.getUserName());
        wxLoginVO.setRole(loginVO.getRole());
        return Result.ok(wxLoginVO);
    }
}
