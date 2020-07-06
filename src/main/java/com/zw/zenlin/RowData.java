package com.zw.zenlin;


/**
 * @Date 2020/6/4
 * @Author wang zhi
 */
public class RowData {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 版本
     */
    private String version;
    /**
     * 主键值
     */
    private String pkValue;

    /**
     * 字段值数组（数组的下标为列的序号）
     */
    private String[] fieldDatas;


    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPkValue() {
        return pkValue;
    }

    public void setPkValue(String pkValue) {
        this.pkValue = pkValue;
    }

    public String[] getFieldDatas() {
        return fieldDatas;
    }

    public void setFieldDatas(String[] fieldDatas) {
        this.fieldDatas = fieldDatas;
    }


}
