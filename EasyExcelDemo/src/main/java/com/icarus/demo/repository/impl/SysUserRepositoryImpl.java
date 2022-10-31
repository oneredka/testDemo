package com.icarus.demo.repository.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.icarus.demo.mapper.ISysUserMapper;
import com.icarus.demo.repository.MPSysUserRepository;
import org.springframework.stereotype.Service;
import com.icarus.demo.entity.SysUser;

@Service
public class SysUserRepositoryImpl extends ServiceImpl<ISysUserMapper, SysUser> implements MPSysUserRepository {

}
