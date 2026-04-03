package com.metal.counsel.system.controller;

import com.metal.counsel.common.result.Result;
import com.metal.counsel.system.pojo.dto.MenuDTO;
import com.metal.counsel.system.pojo.entity.Menu;
import com.metal.counsel.system.pojo.vo.IdVO;
import com.metal.counsel.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menus")
public class MenuController {

    @Autowired
    private MenuService menuService;

    /**
     * 获取菜单树列表
     *
     * @param roleId 角色ID（可选），用于过滤特定角色的菜单
     * @return 返回菜单结构列表
     */
    @GetMapping
    public Result<List<MenuDTO>> getMenus(@RequestParam(required = false) Long roleId) {
        return Result.ok(menuService.getMenuTree(roleId));
    }

    /**
     * 创建新菜单
     *
     * @param menu 菜单实体参数
     * @return 返回新创建菜单的 ID 对象
     */
    @PostMapping
    public Result<IdVO> createMenu(@RequestBody Menu menu) {
        Long id = menuService.create(menu);
        return Result.ok(new IdVO(id));
    }

    /**
     * 更新菜单信息
     *
     * @param id   需要更新的菜单 ID
     * @param menu 菜单新的实体数据
     * @return 返回成功状态
     */
    @PutMapping("/{id}")
    public Result<Void> updateMenu(@PathVariable Long id, @RequestBody Menu menu) {
        menuService.update(id, menu);
        return Result.ok();
    }

    /**
     * 删除菜单
     *
     * @param id 需要删除的菜单 ID
     * @return 返回成功状态
     */
    @DeleteMapping("/{id}")
    public Result<Void> deleteMenu(@PathVariable Long id) {
        menuService.delete(id);
        return Result.ok();
    }
}
