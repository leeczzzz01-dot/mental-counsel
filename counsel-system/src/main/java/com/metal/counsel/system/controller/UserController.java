package com.metal.counsel.system.controller;

import com.metal.counsel.common.pojo.PageResult;
import com.metal.counsel.common.result.Result;
import com.metal.counsel.system.pojo.dto.BindUserRolesDTO;
import com.metal.counsel.system.pojo.dto.UserDTO;
import com.metal.counsel.system.pojo.vo.IdVO;
import com.metal.counsel.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Result<PageResult<UserDTO>> getUsers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long roleId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return Result.ok(userService.page(keyword, roleId, status, page, pageSize));
    }

    @PostMapping
    public Result<IdVO> createUser(@RequestBody UserDTO dto) {
        Long id = userService.create(dto);
        return Result.ok(new IdVO(id));
    }

    @PutMapping("/{id}")
    public Result<Void> updateUser(@PathVariable Long id, @RequestBody UserDTO dto) {
        userService.update(id, dto);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return Result.ok();
    }

    @PostMapping("/{id}/roles")
    public Result<Void> bindUserRoles(@PathVariable Long id, @RequestBody BindUserRolesDTO dto) {
        userService.bindRoles(id, dto.getRoleIds());
        return Result.ok();
    }
}
