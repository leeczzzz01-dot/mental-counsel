package com.metal.counsel.system.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一 ID 响应 VO，替代 Map&lt;String, Long&gt; 作为创建接口返回值
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdVO {
    private Long id;
}
