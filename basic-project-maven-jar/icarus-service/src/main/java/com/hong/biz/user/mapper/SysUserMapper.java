package com.hong.biz.user.mapper;

import com.hong.biz.user.domain.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//@Mapper
public interface SysUserMapper {

    List<SysUser> selectAllUser();

    SysUser selectUserById(String id);

    SysUser selectUserByLoginNameAndPwd(@Param("loginName") String loginName, @Param("pwd") String pwd);

    int deleteUserById(Integer userId);
}
