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

    @GetMapping
    public Result<List<MenuDTO>> getMenus(@RequestParam(required = false) Long roleId) {
        return Result.ok(menuService.getMenuTree(roleId));
    }

    @PostMapping
    public Result<IdVO> createMenu(@RequestBody Menu menu) {
        Long id = menuService.create(menu);
        return Result.ok(new IdVO(id));
    }

    @PutMapping("/{id}")
    public Result<Void> updateMenu(@PathVariable Long id, @RequestBody Menu menu) {
        menuService.update(id, menu);
        return Result.ok();
    }

    @DeleteMapping("/{id}")
    public Result<Void> deleteMenu(@PathVariable Long id) {
        menuService.delete(id);
        return Result.ok();
    }
}
