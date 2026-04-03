package com.metal.counsel.system.controller;

import com.metal.counsel.common.result.Result;
import com.metal.counsel.system.pojo.dto.BindRolePermsDTO;
import com.metal.counsel.system.pojo.entity.Perm;
import com.metal.counsel.system.service.PermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perms")
public class PermController {

    @Autowired
    private PermService permService;

    /**
     * 获取权限列表
     *
     * @param roleId 角色ID（可选），用于查询指定角色的权限
     * @return 返回权限实体列表
     */
    @GetMapping
    public Result<List<Perm>> getPerms(@RequestParam(required = false) Long roleId) {
        return Result.ok(permService.getPerms(roleId));
    }

    /**
     * 为角色绑定权限
     *
     * @param dto 包含角色ID和需要绑定的权限ID列表
     * @return 返回成功状态
     */
    @PostMapping("/bind")
    public Result<Void> bindRolePerms(@RequestBody BindRolePermsDTO dto) {
        permService.bindRolePerms(dto.getRoleId(), dto.getPermIds());
        return Result.ok();
    }
}
