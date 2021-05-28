package com.hong.web.controller.user;


import com.hong.common.core.controller.BaseController;
import com.hong.common.core.page.TableDataInfo;
import com.hong.biz.user.base.AESBase64;
import com.hong.biz.user.domain.SysUser;
import com.hong.biz.user.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    @Autowired
    private ISysUserService userService;

    @PostMapping("/get_user_by_id")
    public SysUser getUserById(String id) {
        SysUser user = userService.getUserById(id);
        System.out.println(user.toString());
        return user;
    }

    @PostMapping("/get_all_user")
    @ResponseBody
    public TableDataInfo getAllUser() {
        startPage();
        List<SysUser> userList = userService.getAllUser();
        return getDataTable(userList);
    }

    @PostMapping("/login")
    public String login(SysUser user) {
        try {
            String pwd = user.getPassword();
            String decryptPwd = AESBase64.Decrypt(pwd,"1111222233334444");
            SysUser user1 = userService.selectUserByLoginNameAndPwd(user.getLoginName(),decryptPwd);
            if (null != user1) {
                return "登陆成功";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "登陆失败";
    }
}
