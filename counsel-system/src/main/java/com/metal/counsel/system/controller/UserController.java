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

    /**
     * 分页条件查询用户列表
     *
     * @param keyword  搜索关键字（可匹配用户名等）
     * @param roleId   角色 ID，用于筛选
     * @param status   账号状态
     * @param page     当前页码，默认 1
     * @param pageSize 每页条数，默认 10
     * @return 返回分页封装的用户数据列表
     */
    @GetMapping
    public Result<PageResult<UserDTO>> getUsers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long roleId,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return Result.ok(userService.page(keyword, roleId, status, page, pageSize));
    }

    /**
     * 创建新用户
     *
     * @param dto 用户数据传输对象参数
     * @return 返回新创建用户的 ID 对象
     */
    @PostMapping
    public Result<IdVO> createUser(@RequestBody UserDTO dto) {
        Long id = userService.create(dto);
        return Result.ok(new IdVO(id));
    }

    /**
     * 更新用户信息
     *
     * @param id  需要更新的用户 ID
     * @param dto 更新用户的详细数据
     * @return 返回成功状态
     */
    @PutMapping("/{id}")
    public Result<Void> updateUser(@PathVariable Long id, @RequestBody UserDTO dto) {
        userService.update(id, dto);
        return Result.ok();
    }

    /**
     * 删除用户
     *
     * @param id 需要删除的用户 ID
     * @return 返回成功状态
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteUser(@PathVariable Long id) {
        userService.delete(id);
        return Result.ok();
    }

    /**
     * 为用户绑定角色
     *
     * @param id  用户 ID
     * @param dto 角色绑定封装信息，包含一组 RoleId
     * @return 返回成功状态
     */
    @PostMapping("/{id}/roles")
    public Result<Void> bindUserRoles(@PathVariable Long id, @RequestBody BindUserRolesDTO dto) {
        userService.bindRoles(id, dto.getRoleIds());
        return Result.ok();
    }
}
