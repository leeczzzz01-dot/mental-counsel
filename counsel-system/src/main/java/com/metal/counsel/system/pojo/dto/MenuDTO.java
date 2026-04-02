package com.metal.counsel.system.pojo.dto;

import lombok.Data;
import java.util.List;

@Data
public class MenuDTO {
    private Long id;
    private String name;
    private String path;
    private String icon;
    private Integer sort;
    private Long parentId;
    private List<MenuDTO> children;
}
