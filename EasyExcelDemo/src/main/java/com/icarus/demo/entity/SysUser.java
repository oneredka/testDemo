package com.icarus.demo.entity;


import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.icarus.demo.convert.GenderConverter;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
//@Accessors(chain = true) 可以用链式访问，该注解设置为chain=true，生成setter方法返回this（也就是返回的是对象），代替了默认的返回void，会让读取的数据行始终为空。
@TableName("sys_user")
public class SysUser extends Model<SysUser> implements Serializable {
    @ExcelProperty("登录名")
    @ColumnWidth(20)
    private String loginName;
    @ExcelProperty("登录密码")
    @ColumnWidth(20)
    private String password;
    @ExcelProperty("用户名")
    @ColumnWidth(20)
    private String userName;
    @ExcelProperty("手机号")
    @ColumnWidth(40)
    private String phoneNumber;
    @ExcelProperty("邮箱")
    @ColumnWidth(40)
    private String email;
    /**
     * EasyExcel使用：自定义转换器
     */
    @ColumnWidth(10)
    @ExcelProperty(value = "性别", converter = GenderConverter.class)
    private Integer gender;
    /**
     * EasyExcel使用：导出时忽略该字段
     */
    @ExcelIgnore
    private String createBy;
    /**
     * EasyExcel使用：日期的格式化
     */
    @ColumnWidth(20)
    @ExcelProperty("创建日期")
    @DateTimeFormat("yyyy-MM-dd")
    private Date createTime;
    @ExcelIgnore
    private String updateBy;
    @ExcelIgnore
    private Date updateTime;
}
