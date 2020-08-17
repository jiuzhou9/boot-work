package db.backup.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2019/09/25
 */
@Slf4j
public abstract class DataBackUpUtils {

    private static final String FORMAT_TIME_PATTERN = "yyyyMMddHHmmss";

    /**
     * @param uploadPath 备份路径
     * @param jdbcDriver 驱动
     * @param jdbcUrl 数据库URL
     * @param jdbcUserName 用户名
     * @param jdbcPassword 用户密码
     * @param tables 备份表,如果null 或者 size = 0，将备份当前库的所有表
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static String backupData(String uploadPath, String jdbcDriver,
                                    String jdbcUrl,
                                    String jdbcUserName,
                                    String jdbcPassword,
                                    List<String> tables) throws IOException,
                    ClassNotFoundException, SQLException {
        File file = new File(uploadPath);
        if (!file.exists()) {
            file.mkdir();
        }

        long startGo = System.currentTimeMillis();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMAT_TIME_PATTERN);
        String timeStr = LocalDateTime.now().format(dateTimeFormatter);

        List<String> tableNames = getMysqlTables(jdbcDriver, jdbcUrl, jdbcUserName, jdbcPassword);

        //过滤想要的table
        if (tables != null && tables.size() > 0){
            if (!tableNames.containsAll(tables)){
                throw new SQLException("所选表的集合中，含有不合法的表");
            }
            tableNames = tables;
        }
        String format = LocalDateTime.now().format(dateTimeFormatter);
        String dbName = "";
        Integer index = jdbcUrl.lastIndexOf("/");
        String before = jdbcUrl.substring(0, index);
        String after = jdbcUrl.substring(index + 1);
        if (after.contains("?")) {
            index = after.lastIndexOf("?");
            dbName = after.substring(0, index);
        }
        File dataFile = new File(uploadPath + "/" + format + "-" + dbName + "-data.sql");

        if (!dataFile.exists()) {
            /* 创建文件*/
            dataFile.createNewFile();
        }
        FileWriter dataFw = new FileWriter(dataFile, true);
        for (String table : tableNames) {
            long totalRecord = getRecordCount(jdbcDriver, jdbcUrl, jdbcUserName, jdbcPassword, table);
            long totalPage = totalRecord % SqlUtils.MAX_RECORDS == 0 ? totalRecord / SqlUtils.MAX_RECORDS : totalRecord / SqlUtils.MAX_RECORDS + 1 ;

            for (int page = 0; page < totalPage; page++) {
                Class.forName(jdbcDriver);
                Connection contmp = DriverManager.getConnection(jdbcUrl, jdbcUserName, jdbcPassword);

                long cellStart = System.currentTimeMillis();
                Statement statementTmp = contmp.createStatement();

                List<String> mysqlInsertSqlList =
                                SqlUtils.getMysqlInsertSqlList(statementTmp,
                                                               table,
                                                               page * SqlUtils.MAX_RECORDS,
                                                       SqlUtils.MAX_RECORDS);
                log.info("{}查询{}耗时：{}ms",
                         Thread.currentThread().getName(),
                         table,
                         System.currentTimeMillis() - cellStart);

                statementTmp.close();

                if (mysqlInsertSqlList != null && mysqlInsertSqlList.size() > 0) {
                    for (String insertSqlStr : mysqlInsertSqlList) {
                        dataFw.write(insertSqlStr);
                    }
                }
                contmp.close();
                log.info("{}备份{}耗时：{}ms,connection is closed！",
                         Thread.currentThread().getName(), table, System.currentTimeMillis() - cellStart);
            }
        }
        dataFw.flush();
        dataFw.close();
        log.info("备份数据，总耗时：{}ms", System.currentTimeMillis() - startGo);
        return uploadPath + timeStr + "mysql数据" + ".sql";
    }

    /**
     * @param uploadPath 备份路径
     * @param jdbcDriver 驱动
     * @param jdbcUrl 数据库URL
     * @param jdbcUserName 用户名
     * @param jdbcPassword 用户密码
     * @param tables 备份表,如果null 或者 size = 0，将备份当前库的所有表
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    public static String backUpMysqlFastNew(String uploadPath, String jdbcDriver, String jdbcUrl, String jdbcUserName,
                                         String jdbcPassword, List<String> tables) throws ClassNotFoundException, SQLException, IOException {

        long startGo = System.currentTimeMillis();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMAT_TIME_PATTERN);
        String timeStr = LocalDateTime.now().format(dateTimeFormatter);

        List<String> tableNames = getMysqlTables(jdbcDriver, jdbcUrl, jdbcUserName, jdbcPassword);

        //过滤想要的table
        if (tables != null && tables.size() > 0){
            if (!tableNames.containsAll(tables)){
                throw new SQLException("所选表的集合中，含有不合法的表");
            }
            tableNames = tables;
        }

        // 创建 5 个线程
        ExecutorService service = new ThreadPoolExecutor(5, 5,
                                                         0L, TimeUnit.MILLISECONDS,
                                                         new LinkedBlockingQueue<>(), new ThreadPoolExecutor.DiscardOldestPolicy());
        List<Future<String>> list = new ArrayList<>(5);

        for (String table : tableNames) {
            long totalRecord = getRecordCount(jdbcDriver, jdbcUrl, jdbcUserName, jdbcPassword, table);
            long totalPage = totalRecord % SqlUtils.MAX_RECORDS == 0 ? totalRecord / SqlUtils.MAX_RECORDS : totalRecord / SqlUtils.MAX_RECORDS + 1 ;

            for (int page = 0; page < totalPage; page++) {
                int finalPage = page;
                Future<String> future = service.submit(new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        File dataFile = null;
                        dataFile = new File(uploadPath + "/" + table + "-" + finalPage + ".sql");

                        if (!dataFile.exists()) {
                            /* 创建文件*/
                            dataFile.createNewFile();
                        }
                        FileWriter dataFw = new FileWriter(dataFile, true);

                        Class.forName(jdbcDriver);
                        Connection contmp = DriverManager.getConnection(jdbcUrl, jdbcUserName, jdbcPassword);

                        long cellStart = System.currentTimeMillis();
                        Statement statementTmp = contmp.createStatement();

                        try {
                            List<String> mysqlInsertSqlList = SqlUtils
                                            .getMysqlInsertSqlList(statementTmp, table, finalPage * SqlUtils.MAX_RECORDS,
                                                                   SqlUtils.MAX_RECORDS);
                            log.info(Thread.currentThread().getName() + "查询"+ table +"耗时：{}ms", System.currentTimeMillis() - cellStart);
                            statementTmp.close();

                            if (mysqlInsertSqlList != null && mysqlInsertSqlList.size() > 0) {
                                for (String insertSqlStr : mysqlInsertSqlList) {
                                    dataFw.write(insertSqlStr);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        contmp.close();
                        dataFw.flush();
                        dataFw.close();
                        log.info("{}备份{}耗时：{}ms,connection is closed！", Thread.currentThread().getName(), table, System.currentTimeMillis() - cellStart);
                        return dataFile.getName();
                    }
                });
                list.add(future);
            }
        }
        List<File> fileList = new ArrayList<>();

        // 打印结果
        for (Future<String> future : list) {
            try {
                log.info(future.get());
                if (future.get() != null) {
                    fileList.add(new File(future.get()));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        // 关闭线程池
        service.shutdown();

        /** 压缩方法2  */
        FileOutputStream fos2 = new FileOutputStream(new File(uploadPath + "/" + timeStr + "mysql数据" + ".zip"));
        try {
            FileUtils.toZip(fileList, fos2);

            fos2.close();
        } catch (Exception ex) {
            log.error("压缩mysql脚本失败" + ex.toString());
            return "";
        }
        log.info("备份数据，总耗时：" + (System.currentTimeMillis() - startGo) + "ms");
        return uploadPath + timeStr + "mysql数据" + ".zip";
    }

    /**
     * 获取table记录数
     * @param jdbcDriver
     * @param jdbcUrl
     * @param jdbcUserName
     * @param jdbcPassword
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static long getRecordCount(String jdbcDriver, String jdbcUrl, String jdbcUserName, String jdbcPassword, String table) throws ClassNotFoundException, SQLException {
        Class.forName(jdbcDriver);
        Connection con = DriverManager.getConnection(jdbcUrl, jdbcUserName, jdbcPassword);
        Statement statement = con.createStatement();

        long count = 0;
        ResultSet resultSet = statement.executeQuery("select count(1) from " + table);
        while(resultSet.next())
        {
            //打印的就是总记录数。把检索结果看成只有一跳记录一个字段的表
            count = resultSet.getInt(1);
        }
        resultSet.close();
        statement.close();
        con.close();
        return count;
    }

    /**
     * 谨慎使用
     * 执行指定的SQL语句
     * 为了便于操作数据处理，例如需要每天定时清理垃圾数据等操作。
     *
     * @param jdbcDriver
     * @param jdbcUrl
     * @param jdbcUserName
     * @param jdbcPassword
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static long execute(String jdbcDriver, String jdbcUrl, String jdbcUserName, String jdbcPassword, String sql) throws ClassNotFoundException, SQLException {
        Class.forName(jdbcDriver);
        Connection con = DriverManager.getConnection(jdbcUrl, jdbcUserName, jdbcPassword);
        Statement statement = con.createStatement();

        long count = 0;
        count = statement.executeUpdate(sql);
        statement.close();
        con.close();
        log.info("sql 执行:{}, 受影响行数:{}", sql, count);
        return count;
    }


    /**
     * 获取指定数据库中的tables列表
     * @param jdbcDriver
     * @param jdbcUrl
     * @param jdbcUserName
     * @param jdbcPassword
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static List<String> getMysqlTables(String jdbcDriver, String jdbcUrl, String jdbcUserName, String jdbcPassword) throws ClassNotFoundException, SQLException {
        Class.forName(jdbcDriver);
        Connection con = DriverManager.getConnection(jdbcUrl, jdbcUserName, jdbcPassword);

        Statement statement = con.createStatement();

        DatabaseMetaData dbmd = con.getMetaData();

        String dbName = "";
        Integer index = jdbcUrl.lastIndexOf("/");
        String before = jdbcUrl.substring(0, index);
        String after = jdbcUrl.substring(index + 1);
        if (after.contains("?")) {
            index = after.lastIndexOf("?");
            dbName = after.substring(0, index);
        }
        con.getMetaData().getDatabaseProductName();
        ResultSet rs = dbmd.getTables(dbName, null, null, new String[] { "TABLE" });
        List<String> tableNames = new ArrayList<>();
        while (rs.next()) {
            String tableName = rs.getString("TABLE_NAME");
            tableNames.add(tableName);
        }

        rs.close();
        statement.close();
        con.close();
        return tableNames;
    }

    /**
     * 备份ddl
     * @param uploadPath
     * @param jdbcDriver
     * @param jdbcUrl
     * @param jdbcUserName
     * @param jdbcPassword
     * @throws SQLException
     */
    public static String backUpMysqlDDL(String uploadPath,
                                        String jdbcDriver,
                                        String jdbcUrl, String jdbcUserName,
                                        String jdbcPassword, List<String> tableNames) throws SQLException, ClassNotFoundException, IOException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(FORMAT_TIME_PATTERN);
        String timeStr = LocalDateTime.now().format(dateTimeFormatter);

        File dataFile = null;
        Class.forName(jdbcDriver);
        Connection con = DriverManager.getConnection(jdbcUrl, jdbcUserName, jdbcPassword);

        Statement statement = con.createStatement();

        DatabaseMetaData dbmd = con.getMetaData();

        String dbName = "";
        Integer index = jdbcUrl.lastIndexOf("/");
        String before = jdbcUrl.substring(0, index);
        String after = jdbcUrl.substring(index + 1);
        if (after.contains("?")) {
            index = after.lastIndexOf("?");
            dbName = after.substring(0, index);
        }
        con.getMetaData().getDatabaseProductName();
        ResultSet rs = dbmd.getTables(dbName, null, null, new String[] { "TABLE" });
        if (tableNames == null || tableNames.size() == 0){
            tableNames = new ArrayList<>();
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                tableNames.add(tableName);
            }
        }

        String fileName = uploadPath + "/" + timeStr + "mysql-" +dbName+ "-ddl" + ".sql";
        if (tableNames != null && tableNames.size() > 0) {
            dataFile = new File(fileName);

            if (!dataFile.exists()) {
                /* 创建文件*/
                dataFile.createNewFile();
            }
            FileWriter dataFw = new FileWriter(dataFile, true);
            for (String table : tableNames) {
                try {
                    //查询sql
                    String sql = String.format("SHOW CREATE TABLE %s", table);
                    ResultSet tableInfo = statement.executeQuery(sql);
                    List<String> ddls = new ArrayList<>();
                    ddls.add(String.format("drop table if exists %s;\n", table));
                    try {
                        while (tableInfo.next()) {
                            //第一个参数获取的是tableName
                            System.out.println(tableInfo.getString(1));
                            //第二个参数获取的是表的ddl语句
                            System.out.println(tableInfo.getString(2));
                            ddls.add(tableInfo.getString(2) + ";\n");
                        }
                        for (String ddl : ddls) {
                            dataFw.write(ddl);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            dataFw.flush();
            dataFw.close();
        }
        rs.close();
        statement.close();
        con.close();
//        return toZip(uploadPath, timeStr, dataFile);

        return fileName;
    }

    private static String toZip(String uploadPath, String timeStr, File dataFile) throws FileNotFoundException {
        /** 压缩方法2  */
        List<File> fileList = new ArrayList<>();
        if (dataFile != null) {
            fileList.add(dataFile);
        }
        FileOutputStream fos2 = new FileOutputStream(new File(uploadPath + "/" + timeStr + "mysql-ddl" + ".zip"));
        try {
            FileUtils.toZip(fileList, fos2);
            dataFile.delete();
            fos2.close();
        } catch (Exception ex) {
            log.error("压缩mysql脚本失败" + ex.toString());
            return "";
        }
        return uploadPath + timeStr + "mysql数据" + ".zip";
    }

}
