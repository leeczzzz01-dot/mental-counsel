package com.metal.counsel.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.metal.counsel.system.mapper.RoleMapper;
import com.metal.counsel.system.mapper.RoleMenuMapper;
import com.metal.counsel.system.pojo.entity.Role;
import com.metal.counsel.system.pojo.entity.RoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

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

    @Transactional
    public void bindRoleMenus(Long roleId, List<Long> menuIds) {
        // 全量覆盖：先物理删除
        roleMenuMapper.delete(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, roleId));

        // 再批量插入新的关联记录
        if (menuIds != null && !menuIds.isEmpty()) {
            for (Long menuId : menuIds) {
                RoleMenu rm = new RoleMenu();
                rm.setRoleId(roleId);
                rm.setMenuId(menuId);
                roleMenuMapper.insert(rm);
            }
        }
    }
}
