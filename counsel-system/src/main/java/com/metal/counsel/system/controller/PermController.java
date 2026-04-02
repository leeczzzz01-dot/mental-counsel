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

    @GetMapping
    public Result<List<Perm>> getPerms(@RequestParam(required = false) Long roleId) {
        return Result.ok(permService.getPerms(roleId));
    }

    @PostMapping("/bind")
    public Result<Void> bindRolePerms(@RequestBody BindRolePermsDTO dto) {
        permService.bindRolePerms(dto.getRoleId(), dto.getPermIds());
        return Result.ok();
    }
}
