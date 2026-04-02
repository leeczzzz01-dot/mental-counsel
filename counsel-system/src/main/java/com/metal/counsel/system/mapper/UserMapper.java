package com.metal.counsel.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.metal.counsel.system.pojo.entity.User;
import com.metal.counsel.system.pojo.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

        /**
         * 分页查询用户列表（带角色名）
         */
        List<UserDTO> selectUserPage(@Param("keyword") String keyword,
                        @Param("roleId") Long roleId,
                        @Param("status") String status,
                        @Param("offset") long offset,
                        @Param("size") int size);

        /**
         * 统计用户总数
         */
        long countUsers(@Param("keyword") String keyword,
                        @Param("roleId") Long roleId,
                        @Param("status") String status);
}
