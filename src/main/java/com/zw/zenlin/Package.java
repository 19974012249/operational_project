package com.zw.zenlin;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2020/6/4
 * @Author wang zhi
 */
public class Package<T> {
    /**
     * 包数据id（发送批次号）
     */
    private String id;

    /**
     * 源表id
     */
    private String tableId;
    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * 表数据列表(默认需要初始化)
     */
    private List<T> rows = new ArrayList<T>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
