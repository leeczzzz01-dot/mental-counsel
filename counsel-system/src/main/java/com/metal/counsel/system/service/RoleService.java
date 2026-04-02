package com.metal.counsel.system.service;

import com.metal.counsel.system.pojo.entity.Role;
import com.metal.counsel.system.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    public List<Role> list() {
        return roleMapper.selectList(null);
    }

    public Long create(Role role) {
        roleMapper.insert(role);
        return role.getId();
    }

    public void update(Long id, Role role) {
        role.setId(id);
        roleMapper.updateById(role);
    }

    public void delete(Long id) {
        roleMapper.deleteById(id);
    }
}
