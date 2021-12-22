package com.hong.biz.user.domain;


import com.hong.common.core.domain.BaseEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SysUser extends BaseEntity {
    private String loginName;
    private String password;
    private String userName;
    private String phoneNumber;
    private String email;
}
