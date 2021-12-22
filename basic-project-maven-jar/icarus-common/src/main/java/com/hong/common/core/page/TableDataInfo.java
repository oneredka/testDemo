package com.hong.common.core.page;

import java.io.Serializable;
import java.util.List;


/**
 * 表格分页数据对象
 *
 * @author HongYi@10004580
 * @createTime 2021年05月26日 14:08:00
 */
public class TableDataInfo implements Serializable
{
    private static final long serialVersionUID = 1L;
    /** 总记录数 */
    private long total;
    /** 列表数据 */
    private List<?> rows;
    /** 消息状态码 */
    private int code;
    /**
     * 敏感字段
     */
    private List<String> senseColumn;

    /**
     * 表格数据对象
     */
    public TableDataInfo()
    {
    }

    /**
     * 分页
     * 
     * @param list 列表数据
     * @param total 总记录数
     */
    public TableDataInfo(List<?> list, int total)
    {
        this.rows = list;
        this.total = total;
    }

    public long getTotal()
    {
        return total;
    }

    public void setTotal(long total)
    {
        this.total = total;
    }

    public List<?> getRows()
    {
        return rows;
    }

    public void setRows(List<?> rows)
    {
        this.rows = rows;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public List<String> getSenseColumn() {
        return senseColumn;
    }

    public void setSenseColumn(List<String> senseColumn) {
        this.senseColumn = senseColumn;
    }
}
