package com.hong.common.core.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hong.common.core.domain.Feedback;
import com.hong.common.core.page.PageDomain;
import com.hong.common.core.page.TableDataInfo;
import com.hong.common.core.page.TableSupport;
import com.hong.common.utils.*;
import com.hong.common.utils.sql.SqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.beans.PropertyEditorSupport;
import java.util.*;

/**
 * web层通用数据处理
 *
 * @author HongYi@10004580
 * @createTime 2021年05月26日 14:08:00
 */
public class BaseController
{
    protected final Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 设置请求排序数据
     */
    protected void startOrderBy()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        if (StringUtils.isNotEmpty(pageDomain.getOrderBy()))
        {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.orderBy(orderBy);
        }
    }

    /**
     * 获取request
     */
    public HttpServletRequest getRequest()
    {
        return ServletUtils.getRequest();
    }

    /**
     * 获取response
     */
    public HttpServletResponse getResponse()
    {
        return ServletUtils.getResponse();
    }

    /**
     * 获取session
     */
    public HttpSession getSession()
    {
        return getRequest().getSession();
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTable(List<?> list)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * 响应返回结果
     * 
     * @param rows 影响行数
     * @return 操作结果
     */
    protected Feedback toAjax(int rows)
    {
        return rows > 0 ? success() : error();
    }

    /**
     * 响应返回结果
     * 
     * @param result 结果
     * @return 操作结果
     */
    protected Feedback toAjax(boolean result)
    {
        return result ? success() : error();
    }

    /**
     * 返回成功
     */
    public Feedback success()
    {
        return Feedback.success();
    }

    /**
     * 返回失败消息
     */
    public Feedback error()
    {
        return Feedback.error();
    }

    /**
     * 返回成功消息
     */
    public Feedback success(String message)
    {
        return Feedback.success(message);
    }

    /**
     * 返回失败消息
     */
    public Feedback error(String message)
    {
        return Feedback.error(message);
    }

    /**
     * 返回错误码消息
     */
    public Feedback error(Feedback.Type type, String message)
    {
        return new Feedback(type, message);
    }

    /**
     * 页面跳转
     */
    public String redirect(String url)
    {
        return StringUtils.format("redirect:{}", url);
    }

    /**
     * 获取字符串值
     *
     * @param request HttpServletRequest对象
     * @param param   参数名称
     * @return 返回整型值，没找到返回null
     */
    protected String getString(HttpServletRequest request, String param, String defaultVale) {
        String value = request.getParameter(param);
        if (StringUtil.isBlank(value)) {
            return defaultVale;
        } else {
            return value;
        }
    }

    protected Map<String, Object> assemblyRequestMap() {
        Map<String, Object> params = new HashMap<>();
        Map<String, String[]> map = getRequest().getParameterMap();
        Iterator<String> key = map.keySet().iterator();
        while (key.hasNext()) {
            String k = (String) key.next();
            String[] value = map.get(k);

            if (value.length == 1) {
                String temp = null;
                if (!StringUtil.isBlank(value[0])) {
                    temp = value[0];
                }
                params.put(k, temp);
                getRequest().setAttribute(k, temp);
            } else if (value.length == 0) {
                params.put(k, null);
                getRequest().setAttribute(k, null);
            } else if (value.length > 1) {
                params.put(k, value);
                getRequest().setAttribute(k, value);
            }
        }
        return params;
    }
}
