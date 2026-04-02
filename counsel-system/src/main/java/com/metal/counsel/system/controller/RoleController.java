package com.metal.counsel.system.controller;

import com.metal.counsel.common.result.Result;
import com.metal.counsel.system.pojo.entity.Role;
import com.metal.counsel.system.pojo.vo.IdVO;
import com.metal.counsel.system.service.RoleService;
import com.metal.counsel.system.service.PermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermService permService;

    @GetMapping
    public Result<List<Role>> getRoles() {
        return Result.ok(roleService.list());
    }

    @GetMapping("/{id}/perms")
    public Result<java.util.List<Long>> getRolePermIds(@PathVariable Long id) {
        java.util.List<Long> permIds = permService.getPerms(id).stream()
                .map(com.metal.counsel.system.pojo.entity.Perm::getId)
                .collect(java.util.stream.Collectors.toList());
        return Result.ok(permIds);
    }

    @PostMapping
    public Result<IdVO> createRole(@RequestBody Role role) {
        Long id = roleService.create(role);
        return Result.ok(new IdVO(id));
    }

    @PutMapping("/{id}")
    public Result<Void> updateRole(@PathVariable Long id, @RequestBody Role role) {
        roleService.update(id, role);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteRole(@PathVariable Long id) {
        roleService.delete(id);
        return Result.ok();
    }
}
