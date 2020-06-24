package db.backup;

import com.alibaba.fastjson.JSON;
import db.backup.utils.DataBackUpUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2019/09/26
 */
@Slf4j
public class DataBackUpUtilsTest {

    private String jdbcDriver;

    private String jdbcUrl;

    private String uploadPath;

    private String jdbcUserName;

    private String jdbcPassword;

    private List<String> tables;

    @Test
    public void backupData() {
        jdbcUrl = "jdbc:mysql://10.10.0.70:3306/liuguo_mafia?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true";
        jdbcDriver = "com.mysql.jdbc.Driver";
        jdbcUserName = "dev";
        jdbcPassword = "ShanshuDev2018$";
        uploadPath = "/Users/wangjiuzhou/Desktop/六国库备份";
        getMysqlTables();


        String s = null;
        try {
            s = DataBackUpUtils.backupData(uploadPath, jdbcDriver, jdbcUrl, jdbcUserName, jdbcPassword, tables);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.info(s);
    }

    @Test
    public void backUpDDL() {
        jdbcUrl = "jdbc:mysql://10.10.0.70:3306/liuguo_mafia?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true";
        jdbcDriver = "com.mysql.jdbc.Driver";
        jdbcUserName = "dev";
        jdbcPassword = "ShanshuDev2018$";
        uploadPath = "/Users/wangjiuzhou/Desktop/六国库备份";
        getMysqlTables();

        String s = null;
        try {
            s = DataBackUpUtils.backUpMysqlDDL(uploadPath, jdbcDriver,
                                               jdbcUrl, jdbcUserName,
                                               jdbcPassword, tables);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.info(s);
    }

    @Test
    public void getMysqlTables() {
        jdbcUrl = "jdbc:mysql://10.10.0.70:3306/liuguo_mafia?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true";
        jdbcDriver = "com.mysql.jdbc.Driver";
        jdbcUserName = "dev";
        jdbcPassword = "ShanshuDev2018$";

        try {
            tables = DataBackUpUtils.getMysqlTables(jdbcDriver, jdbcUrl, jdbcUserName, jdbcPassword);
            tables.removeIf(table->table.startsWith("QRTZ")
                            || table.startsWith("mafia")
                            || table.startsWith("sms")
                            || table.startsWith("sap")
                            || table.startsWith("sys_"));
            log.info(JSON.toJSONString(tables));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}