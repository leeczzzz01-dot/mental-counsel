package com.metal.counsel.system.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.metal.counsel.common.exception.BusinessException;
import com.metal.counsel.common.utils.JwtUtils;
import com.metal.counsel.system.pojo.vo.LoginVO;
import com.metal.counsel.system.pojo.entity.Role;
import com.metal.counsel.system.pojo.entity.User;
import com.metal.counsel.system.pojo.entity.UserRole;
import com.metal.counsel.system.mapper.RoleMapper;
import com.metal.counsel.system.mapper.UserMapper;
import com.metal.counsel.system.mapper.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class WxService{

    @Value("${wx.miniapp.appid}")
    private String appId;

    @Value("${wx.miniapp.secret}")
    private String appSecret;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private JwtUtils jwtUtils;

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional
    public LoginVO login(String code) {
        // 1. 调用微信 API 获取 openid
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId +
                "&secret=" + appSecret +
                "&js_code=" + code +
                "&grant_type=authorization_code";

        String response = restTemplate.getForObject(url, String.class);
        log.debug("微信登录返回结果: {}", response);

        try {
            JsonNode jsonNode = objectMapper.readTree(response);
            if (jsonNode.has("errcode") && jsonNode.get("errcode").asInt() != 0) {
                throw new BusinessException("微信登录失败: " + jsonNode.get("errmsg").asText());
            }

            String openid = jsonNode.get("openid").asText();

            // 2. 查找或创建用户
            User user = userMapper.selectOne(
                    new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<User>()
                            .eq(User::getOpenid, openid));

            if (user == null) {
                user = createWxUser(openid);
            }

            // 3. 封装返回结果 (逻辑复用 AuthService)
            LoginVO loginResponse = new LoginVO();
            loginResponse.setToken(jwtUtils.generateToken(user.getId(), user.getUsername()));
            loginResponse.setUserName(user.getName());

            // 查询角色信息
            UserRole userRole = userRoleMapper.selectOne(
                    new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<UserRole>()
                            .eq(UserRole::getUserId, user.getId())
                            .last("LIMIT 1"));

            if (userRole != null) {
                Role role = roleMapper.selectById(userRole.getRoleId());
                if (role != null) {
                    loginResponse.setRole(role.getCode());
                    loginResponse.setRoleId(role.getId());
                    loginResponse.setRoleName(role.getName());
                }
            }

            return loginResponse;

        } catch (Exception e) {
            log.error("微信登录解析失败", e);
            throw new BusinessException("登录系统异常");
        }
    }

    private User createWxUser(String openid) {
        User user = new User();
        user.setOpenid(openid);
        user.setUsername("wx_" + openid.substring(0, 8)); // 默认用户名
        user.setName("微信用户");
        user.setUserType("client"); // 默认类型为用户端
        user.setStatus("active");
        user.setPasswordHash(""); // 微信用户无初始密码
        userMapper.insert(user);

        Role clientRole = roleMapper.selectOne(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<Role>()
                        .eq(Role::getCode, "CLIENT"));
        if (clientRole != null) {
            UserRole ur = new UserRole();
            ur.setUserId(user.getId());
            ur.setRoleId(clientRole.getId());
            userRoleMapper.insert(ur);
        }

        return user;
    }
}
