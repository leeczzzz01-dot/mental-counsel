package com.metal.counsel.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.metal.counsel.system.pojo.dto.MenuDTO;
import com.metal.counsel.system.pojo.entity.Menu;
import com.metal.counsel.system.mapper.MenuMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 获取菜单树
     */
    public List<MenuDTO> getMenuTree(Long roleId) {
        List<Menu> menus;
        if (roleId != null) {
            menus = menuMapper.selectMenusByRoleId(roleId);
        } else {
            menus = menuMapper.selectList(new LambdaQueryWrapper<Menu>().orderByAsc(Menu::getSort));
        }
        return buildTree(menus);
    }
    /**
     * 构建菜单树
     */
    private List<MenuDTO> buildTree(List<Menu> menus) {
        List<MenuDTO> dtoList = menus.stream().map(m -> {
            MenuDTO dto = new MenuDTO();
            BeanUtils.copyProperties(m, dto);
            dto.setChildren(new ArrayList<>());
            return dto;
        }).collect(Collectors.toList());

        // 找出根节点并构建树
        List<MenuDTO> tree = new ArrayList<>();
        for (MenuDTO dto : dtoList) {
            if (dto.getParentId() == null) {
                tree.add(dto);
            } else {
                for (MenuDTO parent : dtoList) {
                    if (parent.getId().equals(dto.getParentId())) {
                        parent.getChildren().add(dto);
                        break;
                    }
                }
            }
        }
        return tree;
    }


    public Long create(Menu menu) {
        menuMapper.insert(menu);
        return menu.getId();
    }


    public void update(Long id, Menu menu) {
        menu.setId(id);
        menuMapper.updateById(menu);
    }

    @Transactional
    public void delete(Long id) {
        Menu menu = menuMapper.selectOne(new LambdaQueryWrapper<Menu>().eq(Menu::getParentId, id));
        if (menu != null) {
            throw new RuntimeException("请先删除子菜单");
        }
        menuMapper.deleteById(id);
    }

}
