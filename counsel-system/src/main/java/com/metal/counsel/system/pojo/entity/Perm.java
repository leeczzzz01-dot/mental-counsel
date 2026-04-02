package com.metal.counsel.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("perms")
public class Perm {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String code;

    private String name;
}
