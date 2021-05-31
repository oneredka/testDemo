package com.hong.biz.user.service;


import com.hong.common.core.domain.Feedback;
import com.hong.biz.user.domain.SysUser;

import java.util.List;

public interface ISysUserService {

    SysUser getUserById(String id);

    void  login(SysUser user);

    SysUser selectUserByLoginNameAndPwd(String loginName, String pwd);

    List<SysUser> getAllUser();

    Feedback deleteUserById(Integer userId);
}
