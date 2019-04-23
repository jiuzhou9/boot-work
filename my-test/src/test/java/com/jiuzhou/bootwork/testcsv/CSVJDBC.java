package com.jiuzhou.bootwork.testcsv;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.sql.*;
import java.util.Properties;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2019/04/16
 */
public class CSVJDBC {

    public static void main(String[] args) throws SQLException {
//        readCsv();
        // load the driver into memory
        try {
            Class.forName("org.relique.jdbc.csv.CsvDriver");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        //驱动属性定义
        Properties props = new Properties();
        props.put("separator", ",");
        props.put("suppressHeaders", "true");//false表示第一行为表头而非数据
        props.put("headerline", "id,item,name,Brand_Note,Version_Note,Mark_Note,Sub_Note,set,plant,lead_time,item_lead_time,type,rate,category,912,913,914,915,916,917,918,919,920,921,922,923,924,925,926,927,928,929,930,1001,1002,1003,1004,1005,1006,1007,1008,1009,1010,1011,1012,1013,1014,1015,1022,1029,1105,1112,1119,1126,1203,1210,1217");//设置表头字段名称，也就是每一列的名称
        props.put("fileExtension", ".csv");//文件扩展名
        props.put("charset", "utf-8");//编码
        props.put("ignoreHeaderLineNumber", "0");//跳过行数
        props.put("skipLeadingDataLines", "1");//跳过行数
        // create a connection. The first command line parameter is assumed to
        //  be the directory in which the .csv files are held
        long timeMillis = System.currentTimeMillis();
        Connection conn = null;
        try {
            //创建链接
            conn = DriverManager.getConnection("jdbc:relique:csv:" + "/Users/wangjiuzhou/Desktop/csv/", props);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        // create a Statement object to execute the query with
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        // Select the ID and NAME columns from sample.csv
        ResultSet results = null;
        try {
            //执行sql
//            String sql = "SELECT * FROM test where name=" + "'zhangsan'";
//            String sql = "SELECT * FROM test where name like" + "'%zhang%'";
            String sql = "insert into test_1 values (03023LLL,zhangsan,,,,,,Z00000,2,0,normal,,order_demand,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)";
            stmt.execute(sql, Statement.NO_GENERATED_KEYS);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        JSONArray jsonArray = resultSetToJsonArry(results);
        System.out.println(System.currentTimeMillis() - timeMillis);
        System.out.println(jsonArray.toJSONString());

        // dump out the results
        while (results.next())
        {
            System.out.println("item= " + results.getString("ITEM") + "   NAME= " + results.getString("NAME"));
            JSONObject jsonObject = resultSetToJsonObject(results);
            System.out.println(jsonObject.toJSONString());
//            JSONArray jsonArray = resultSetToJsonArry(results);
//            System.out.println(jsonArray.toJSONString());
        }
//        String s = "%F%";	//模糊查询怎么处理？
//        ResultSet results2 = stmt.executeQuery("SELECT test.item,test.NAME FROM test WHERE NAME =" + s);
//        System.out.println("------------------------------------------" + results2);
//        while (results2.next()){
//            System.out.println(results2.getString("NAME"));
//        }

        // clean up
        results.close();
        stmt.close();
        conn.close();
        return;

    }

    private static void readCsv() throws SQLException {
        // load the driver into memory
        try {
            Class.forName("org.relique.jdbc.csv.CsvDriver");
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        //驱动属性定义
        Properties props = new Properties();
        props.put("separator", ",");
        props.put("suppressHeaders", "true");//false表示第一行为表头而非数据
        props.put("headerline", "id,item,name,Brand_Note,Version_Note,Mark_Note,Sub_Note,set,plant,lead_time,item_lead_time,type,rate,category,912,913,914,915,916,917,918,919,920,921,922,923,924,925,926,927,928,929,930,1001,1002,1003,1004,1005,1006,1007,1008,1009,1010,1011,1012,1013,1014,1015,1022,1029,1105,1112,1119,1126,1203,1210,1217");//设置表头字段名称，也就是每一列的名称
        props.put("fileExtension", ".csv");//文件扩展名
        props.put("charset", "utf-8");//编码
        props.put("ignoreHeaderLineNumber", "0");//跳过行数
        props.put("skipLeadingDataLines", "1");//跳过行数
        // create a connection. The first command line parameter is assumed to
        //  be the directory in which the .csv files are held
        long timeMillis = System.currentTimeMillis();
        Connection conn = null;
        try {
            //创建链接
            conn = DriverManager.getConnection("jdbc:relique:csv:" + "/Users/wangjiuzhou/Desktop/csv/", props);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        // create a Statement object to execute the query with
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        // Select the ID and NAME columns from sample.csv
        ResultSet results = null;
        try {
            //执行sql
//            String sql = "SELECT * FROM test where name=" + "'zhangsan'";
//            String sql = "SELECT * FROM test where name like" + "'%zhang%'";
            String sql = "SELECT * FROM test_1000 where id in" + "('1','2')";
            results = stmt.executeQuery(sql);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        JSONArray jsonArray = resultSetToJsonArry(results);
        System.out.println(System.currentTimeMillis() - timeMillis);
        System.out.println(jsonArray.toJSONString());

        // dump out the results
        while (results.next())
        {
            System.out.println("item= " + results.getString("ITEM") + "   NAME= " + results.getString("NAME"));
            JSONObject jsonObject = resultSetToJsonObject(results);
            System.out.println(jsonObject.toJSONString());
//            JSONArray jsonArray = resultSetToJsonArry(results);
//            System.out.println(jsonArray.toJSONString());
        }
//        String s = "%F%";	//模糊查询怎么处理？
//        ResultSet results2 = stmt.executeQuery("SELECT test.item,test.NAME FROM test WHERE NAME =" + s);
//        System.out.println("------------------------------------------" + results2);
//        while (results2.next()){
//            System.out.println(results2.getString("NAME"));
//        }

        // clean up
        results.close();
        stmt.close();
        conn.close();
    }

    public static JSONObject resultSetToJsonObject(ResultSet rs) throws SQLException,JSONException {

        // json对象

        JSONObject jsonObj = new JSONObject();

        // 获取列数

        ResultSetMetaData metaData = rs.getMetaData();

        int columnCount = metaData.getColumnCount();

        // 遍历ResultSet中的每条数据

        if (rs.next()) {

            // 遍历每一列

            for (int i = 1; i <= columnCount; i++) {

                String columnName =metaData.getColumnLabel(i);

                String value = rs.getString(columnName);

                jsonObj.put(columnName, value);

            }

        }

        return jsonObj;

    }


    /**
     * 将resultSet转化为JSON数组
     * @param rs
     * @return
     * @throws SQLException
     * @throws JSONException
     */
    public static JSONArray resultSetToJsonArry(ResultSet rs) throws SQLException,JSONException
    {
        // json数组
        JSONArray array = new JSONArray();

        // 获取列数
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        // 遍历ResultSet中的每条数据
        while (rs.next()) {
            JSONObject jsonObj = new JSONObject();

            // 遍历每一列
            for (int i = 1; i <= columnCount; i++) {
                String columnName =metaData.getColumnLabel(i);
                String value = rs.getString(columnName);
                jsonObj.put(columnName, value);
            }
            array.add(jsonObj);
        }

        return array;
    }
}

