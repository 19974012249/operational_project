package com.zw.zenlin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhouwei
 * @date
 */
public class Demo {
    public static void main(String[] args) {
        String originalData = "{\n" +
                " \"id\": \"salesOrder20191119143218870\",\n" +
                "\n" +
                " \"rows\":\n" +
                "\n" +
                "  [{\n" +
                "   \"fieldDatas\":[\"201908275024110001\", \"2\",\"01\", \"5024\", \"1000001\", \"110307160\"],\n" +
                "   \"pkValue\": \"9110ED1C1D3FE52BE053D3030201F3F5\",\n" +
                "   \"type\": \"I\",\n" +
                "   \"version\": \"20191029\",\n" +
                "   \"tableName\": \"sales_order.sales_orderdetails\"\n" +
                "  }, {\n" +
                "   \"fieldDatas\": [\"201908275024110001\", \"3\", \"01\", \"5024\", \"1000001\", \"110307160\"],\n" +
                "   \"pkValue\": \"9110ED1C1D40E52BE053D3030201F3F5\",\n" +
                "   \"type\": \"I\",\n" +
                "   \"version\": \"20191029\",\n" +
                "   \"tableName\": \"sales_order.sales_orderdetails\"\n" +
                "  }, {\n" +
                "   \"fieldDatas\": [\"201908275024110001\", \"4\", \"01\", \"5024\", \"1000553\", \"A48078\"],\n" +
                "   \"pkValue\": \"9110ED1C1D41E52BE053D3030201F3F5\",\n" +
                "   \"type\": \"I\",\n" +
                "   \"version\": \"20191029\",\n" +
                "   \"tableName\": \"sales_order.sales_orderdetails\"\n" +
                "  }, {\n" +
                "   \"fieldDatas\": [\"201908275024110001\", \"5\", \"01\", \"5024\", \"1000001\", \"110307160\"],\n" +
                "   \"pkValue\": \"9110ED1C1D42E52BE053D3030201F3F5\",\n" +
                "   \"type\": \"I\",\n" +
                "   \"version\": \"20191029\",\n" +
                "   \"tableName\": \"sales_order.sales_orderdetails\"\n" +
                "  }, {\n" +
                "   \"fieldDatas\": [\"201908275024110001\", \"1\", \"02\", \"5024\", \"1008197\", \"FHG1710008\"],\n" +
                "   \"pkValue\": \"9110ED1C1D3EE52BE053D3030201F3F5\",\n" +
                "   \"type\": \"I\",\n" +
                "   \"version\": \"20191029\",\n" +
                "   \"tableName\": \"sales_order.sales_orderdetails\"\n" +
                "  }],\n" +
                " \"tableId\": \"5ef1e5b56963935c1838270c\",\n" +
                " \"timestamp\": 1574\n" +
                " }\n" +
                " \n" +
                " ";
        Package<Object> objectPackage = new Package<>();
        String[] a = new String[]{"1", "2", "3", "4", "5"};
        ArrayList<String> strings = new ArrayList<>();
        Demo.update("", originalData, 0, a);
//        System.out.println(Arrays.toString(a));
    }

    /*
     * 曾林
     * */
    public static String update(String fixId, String originalData, int index, String[] dataList) {
        Package aPackage = JSON.parseObject(originalData, Package.class);

        List<JSONObject> rows = aPackage.getRows();
        for (int i = 0; i < rows.size(); i++) {
            if (index == i) {
                JSONObject jsonObject = rows.get(i);
                RowData rowData = JSON.parseObject(jsonObject.toJSONString(), RowData.class);
                String[] fieldDatas = rowData.getFieldDatas();
                // 如果两个数组的大小是相等的
                rowData.setFieldDatas(dataList);
                JSONObject json1 = (JSONObject) JSON.toJSON(rowData);
                rows.set(i, json1);
                aPackage.setRows(rows);
            }
        }

        String s = JSON.toJSON(aPackage).toString();
        return s;
    }

    /*
     * 曾林第二种方法
     * */
    public static String update1(String fixId, String originalData, int index, String[] dataList) {
        Package aPackage = JSON.parseObject(originalData, Package.class);
        // dataList是否为null省略
        for (int i = 0; i < dataList.length; i++) {

        }
        return null;
    }
}
