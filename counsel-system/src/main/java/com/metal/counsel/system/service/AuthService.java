package com.metal.counsel.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.metal.counsel.common.exception.BusinessException;
import com.metal.counsel.common.utils.JwtUtils;
import com.metal.counsel.system.pojo.dto.LoginDTO;
import com.metal.counsel.system.pojo.vo.LoginVO;
import com.metal.counsel.system.pojo.entity.Role;
import com.metal.counsel.system.pojo.entity.User;
import com.metal.counsel.system.pojo.entity.UserRole;
import com.metal.counsel.system.mapper.RoleMapper;
import com.metal.counsel.system.mapper.UserMapper;
import com.metal.counsel.system.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private JwtUtils jwtUtils;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public LoginVO login(LoginDTO request) {
        // 查找用户
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getUsername, request.getUsername()));
        if (user == null) {
            throw new BusinessException("用户名或密码错误");
        }

        // 验证密码
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new BusinessException("用户名或密码错误");
        }

        // 检查状态
        if ("disabled".equals(user.getStatus())) {
            throw new BusinessException("账号已被禁用");
        }

        // 查询用户第一个角色
        UserRole userRole = userRoleMapper.selectOne(
                new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, user.getId()).last("LIMIT 1"));

        LoginVO response = new LoginVO();
        response.setToken(jwtUtils.generateToken(user.getId(), user.getUsername()));
        response.setUserName(user.getName());

        if (userRole != null) {
            Role role = roleMapper.selectById(userRole.getRoleId());
            if (role != null) {
                response.setRole(role.getCode());
                response.setRoleId(role.getId());
                response.setRoleName(role.getName());
            }
        }

        return response;
    }
}
