package com.metal.counsel.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.metal.counsel.system.pojo.entity.Perm;
import com.metal.counsel.system.pojo.entity.RolePerm;
import com.metal.counsel.system.mapper.PermMapper;
import com.metal.counsel.system.mapper.RolePermMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermService {

    @Autowired
    private PermMapper permMapper;

    @Autowired
    private RolePermMapper rolePermMapper;

    /**
     * 获取权限点列表（可按角色过滤）
     */
    public List<Perm> getPerms(Long roleId) {
        if (roleId != null) {
            return permMapper.selectPermsByRoleId(roleId);
        }
        return permMapper.selectList(null);
    }

    @Transactional
    public void bindRolePerms(Long roleId, List<Long> permIds) {
        // 先删除旧关联
        rolePermMapper.delete(new LambdaQueryWrapper<RolePerm>().eq(RolePerm::getRoleId, roleId));
        // 插入新关联
        if (permIds != null) {
            for (Long permId : permIds) {
                RolePerm rp = new RolePerm();
                rp.setRoleId(roleId);
                rp.setPermId(permId);
                rolePermMapper.insert(rp);
            }
        }
    }
}
