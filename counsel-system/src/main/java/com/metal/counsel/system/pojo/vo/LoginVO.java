package com.metal.counsel.system.pojo.vo;

import lombok.Data;

@Data
public class LoginVO {
    private String token;
    private String userName;
    private String role;
    private Long roleId;
    private String roleName;
}
