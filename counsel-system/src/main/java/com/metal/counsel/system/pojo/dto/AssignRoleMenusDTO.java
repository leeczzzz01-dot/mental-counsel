package com.metal.counsel.system.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * 角色绑定全量菜单项入参
 */
@Data
public class AssignRoleMenusDTO {
    private List<Long> menuIds;
}
