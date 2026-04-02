package com.metal.counsel.common.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    private List<T> list;
    private long total;

    public static <T> PageResult<T> of(List<T> list, long total) {
        return new PageResult<>(list, total);
    }
}
