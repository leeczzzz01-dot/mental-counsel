package com.metal.counsel.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.metal.counsel.system.pojo.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 按角色ID查询菜单列表
     */
    List<Menu> selectMenusByRoleId(@Param("roleId") Long roleId);
}
