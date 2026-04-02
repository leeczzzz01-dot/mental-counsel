package com.metal.counsel.system.pojo.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String name;
    private String phone;
    private String status;
    private String roleNames;
    private String roleIds;
}
