package com.metal.counsel.system.pojo.dto;

import lombok.Data;

import java.util.List;

/**
 * 角色绑定全量权限点入参
 */
@Data
public class AssignRolePermsDTO {
    private List<Long> perms;
}
