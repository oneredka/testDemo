package com.hong.biz.user.service.impl;


import com.hong.common.core.domain.Feedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hong.biz.user.domain.SysUser;
import com.hong.biz.user.mapper.SysUserMapper;
import com.hong.biz.user.service.ISysUserService;

import java.util.List;

@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    SysUserMapper userMapper;

    @Override
    public SysUser getUserById(String id) {
        SysUser user = userMapper.selectUserById(id);
        System.out.println(user.toString());
        return user;
    }



    @Override
    public SysUser selectUserByLoginNameAndPwd(String loginName, String pwd) {
        return userMapper.selectUserByLoginNameAndPwd(loginName, pwd);
    }

    @Override
    public List<SysUser> getAllUser() {
        return userMapper.selectAllUser();
    }

    @Override
    public Feedback deleteUserById(Integer userId) {
        int affectCount = userMapper.deleteUserById(userId);
        return affectCount > 0 ?  Feedback.success("操作成功！") : Feedback.error("操作失败！");
    }
}
