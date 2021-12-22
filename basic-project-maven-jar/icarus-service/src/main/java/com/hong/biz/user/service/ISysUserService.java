package com.hong.biz.user.service;



import com.hong.biz.user.domain.SysUser;
import com.hong.common.core.domain.Feedback;

import java.util.List;

public interface ISysUserService {

    SysUser getUserById(String id);


    SysUser selectUserByLoginNameAndPwd(String loginName, String pwd);

    List<SysUser> getAllUser();

    Feedback deleteUserById(Integer userId);
}
