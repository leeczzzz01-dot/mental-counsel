package com.metal.counsel.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("role_perm")
public class RolePerm {
    private Long roleId;
    private Long permId;
}
