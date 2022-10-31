package com.icarus.demo.handler.importListender;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.icarus.demo.entity.SysUser;

/**
 * 基于监听器的形式来实现。这种形式可以达到边解析边处理业务逻辑的效果。
 */
public class SysUserExcelListener extends AnalysisEventListener<SysUser> {

 @Override
 public void invoke(SysUser sysUser, AnalysisContext analysisContext) {
  // do something
  System.out.println("读取SysUser=" + sysUser);
  // do something
 }

 @Override
 public void doAfterAllAnalysed(AnalysisContext analysisContext) {
  // do something
  System.out.println("读取Excel完毕");
  // do something
 }
}