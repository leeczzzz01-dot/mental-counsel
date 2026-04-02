package com.metal.counsel.system.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * 用户绑定角色 DTO，替代 Map&lt;String, List&lt;Long&gt;&gt; 入参
 */
@Data
public class BindUserRolesDTO {
    private List<Long> roleIds;
}
