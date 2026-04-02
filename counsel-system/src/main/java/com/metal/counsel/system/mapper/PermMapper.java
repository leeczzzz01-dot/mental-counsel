package com.metal.counsel.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.metal.counsel.system.pojo.entity.Perm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PermMapper extends BaseMapper<Perm> {

    /**
     * 按角色ID查询权限点列表
     */
    List<Perm> selectPermsByRoleId(@Param("roleId") Long roleId);
}
