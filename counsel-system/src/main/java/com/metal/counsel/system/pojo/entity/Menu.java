package com.metal.counsel.system.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("menus")
public class Menu {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    private String path;

    private String icon;

    private Integer sort;

    private Long parentId;

    private LocalDateTime createdAt;
}
