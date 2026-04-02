package com.metal.counsel.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.metal.counsel.common.exception.BusinessException;
import com.metal.counsel.common.pojo.PageResult;
import com.metal.counsel.system.pojo.dto.UserDTO;
import com.metal.counsel.system.pojo.entity.User;
import com.metal.counsel.system.pojo.entity.UserRole;
import com.metal.counsel.system.mapper.UserMapper;
import com.metal.counsel.system.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public PageResult<UserDTO> page(String keyword, Long roleId, String status, int page, int pageSize) {
        long offset = (long) (page - 1) * pageSize;
        List<UserDTO> list = userMapper.selectUserPage(keyword, roleId, status, offset, pageSize);
        long total = userMapper.countUsers(keyword, roleId, status);
        return PageResult.of(list, total);
    }

    @Transactional
    public Long create(UserDTO dto) {
        // 检查用户名唯一
        User existing = userMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getUsername, dto.getUsername()));
        if (existing != null) {
            throw new BusinessException("用户名已存在");
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setName(dto.getName());
        user.setPhone(dto.getPhone());
        user.setStatus(dto.getStatus() != null ? dto.getStatus() : "active");
        user.setPasswordHash(passwordEncoder.encode(
                dto.getPassword() != null ? dto.getPassword() : "123456"));
        userMapper.insert(user);
        return user.getId();
    }

    public void update(Long id, UserDTO dto) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setName(dto.getName());
        user.setPhone(dto.getPhone());
        user.setStatus(dto.getStatus());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        }
        userMapper.updateById(user);
    }

    @Transactional
    public void delete(Long id) {
        userMapper.deleteById(id);
        // 删除用户角色关联
        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, id));
    }

    @Transactional
    public void bindRoles(Long userId, List<Long> roleIds) {
        // 先删除旧的关联
        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, userId));
        // 插入新的关联
        if (roleIds != null) {
            for (Long roleId : roleIds) {
                UserRole ur = new UserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                userRoleMapper.insert(ur);
            }
        }
    }
}
