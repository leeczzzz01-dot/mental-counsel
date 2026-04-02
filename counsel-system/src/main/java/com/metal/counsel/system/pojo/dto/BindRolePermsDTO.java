package com.metal.counsel.system.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * 角色绑定权限 DTO，替代 Map&lt;String, Object&gt; 入参
 */
@Data
public class BindRolePermsDTO {
    private Long roleId;
    private List<Long> permIds;
}
