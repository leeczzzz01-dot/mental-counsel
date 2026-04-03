package com.metal.counsel.system.controller;

import com.metal.counsel.common.result.Result;
import com.metal.counsel.system.pojo.entity.Menu;
import com.metal.counsel.system.pojo.entity.Role;
import com.metal.counsel.system.pojo.vo.IdVO;
import com.metal.counsel.system.service.MenuService;
import com.metal.counsel.system.service.RoleService;
import com.metal.counsel.system.service.PermService;
import com.metal.counsel.system.pojo.dto.AssignRoleMenusDTO;
import com.metal.counsel.system.pojo.dto.AssignRolePermsDTO;
import com.metal.counsel.system.pojo.entity.Perm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermService permService;

    @Autowired
    private MenuService menuService;

    /**
     * 获取所有角色列表
     *
     * @return 返回角色实体列表
     */
    @GetMapping
    public Result<List<Role>> getRoles() {
        return Result.ok(roleService.list());
    }

    /**
     * 获取角色的权限 ID 列表
     *
     * @param id 角色 ID
     * @return 返回该角色绑定的所有权限 ID 集合
     */
    @GetMapping("/{id}/permsIds")
    public Result<List<Long>> getRolePermIds(@PathVariable("id") Long id) {
        List<Long> permIds = permService.getPerms(id).stream()
                .map(Perm::getId)
                .collect(Collectors.toList());
        return Result.ok(permIds);
    }

    @GetMapping("/{id}/menusIds")
    public Result<List<Long>> getRoleMenuIds(@PathVariable("id") Long id) {
        List<Long> menuIds = menuService.getMenuList(id).stream()
                .map(Menu::getId)
                .collect(Collectors.toList());
        return Result.ok(menuIds);
    }

    /**
     * 创建新角色
     *
     * @param role 包含角色信息的实体参数
     * @return 返回新创建角色的 ID 对象
     */
    @PostMapping
    public Result<IdVO> createRole(@RequestBody Role role) {
        Long id = roleService.create(role);
        return Result.ok(new IdVO(id));
    }

    /**
     * 更新角色信息
     * 
     * @param id   需要更新的角色 ID
     * @param role 待更新的角色详细信息
     * @return 返回更新成功的状态
     */
    @PutMapping("/{id}")
    public Result<Void> updateRole(@PathVariable Long id, @RequestBody Role role) {
        roleService.update(id, role);
        return Result.ok();
    }

    /**
     * 删除角色
     *
     * @param id 需要删除的角色 ID
     * @return 返回删除成功的状态
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteRole(@PathVariable Long id) {
        roleService.delete(id);
        return Result.ok();
    }

    /**
     * 为角色分配全量权限点
     *
     * @param id  角色 ID
     * @param dto 角色绑定全量权限点入参
     * @return 返回绑定成功状态
     */
    @PostMapping("/{id}/perms")
    public Result<Void> bindRolePerms(@PathVariable("id") Long id, @RequestBody AssignRolePermsDTO dto) {
        permService.bindRolePerms(id, dto.getPerms());
        return Result.ok();
    }

    /**
     * 为角色分配全量菜单项
     *
     * @param id  角色 ID
     * @param dto 角色绑定全量菜单项入参
     * @return 返回绑定成功状态
     */
    @PostMapping("/{id}/menus")
    public Result<Void> bindRoleMenus(@PathVariable("id") Long id, @RequestBody AssignRoleMenusDTO dto) {
        roleService.bindRoleMenus(id, dto.getMenuIds());
        return Result.ok();
    }
}
