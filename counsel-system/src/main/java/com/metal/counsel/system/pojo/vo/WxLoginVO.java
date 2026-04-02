package com.metal.counsel.system.pojo.vo;

import lombok.Data;

@Data
public class WxLoginVO {
    private int code;
    private String token;
    private String msg;
    private String userName;
    private String role;
}
