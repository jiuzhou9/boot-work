package db.backup;

import com.alibaba.fastjson.JSON;
import db.backup.utils.DataBackUpUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2019/09/26
 */
@Slf4j
public class DataBackUpUtilsTest {

    private String jdbcDriver;

    private String jdbcUrl;

    private String jdbcUserName;

    private String jdbcPassword;

    private List<String> tables;

    String[] jdbcArr = {
                    "jdbc:mysql://10.10.0.70:3306/foxconn_data?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true",
                    "jdbc:mysql://10.10.0.70:3306/aw?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true",
                    "jdbc:mysql://10.10.0.70:3306/mac?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true",
                    "jdbc:mysql://10.10.0.70:3306/foxconn_quartz?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true"
    };

    private static String uploadPath;

    static {
        uploadPath = "/Users/wangjiuzhou/工作包/富士康/数据库脚本/" + LocalDate.now();
    }

    @Test
    public void backupData() {
//        jdbcUrl = "jdbc:mysql://10.10.0.70:3306/liuguo_mafia?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true";
        jdbcDriver = "com.mysql.jdbc.Driver";
        jdbcUserName = "dev";
        jdbcPassword = "---";
//        uploadPath = "/Users/wangjiuzhou/工作包/富士康/数据库脚本";

        String s = null;
        try {
            for (int i = 0; i < jdbcArr.length; i++) {
                jdbcUrl = jdbcArr[i];
                getMysqlTables();
                s = DataBackUpUtils.backupData(uploadPath, jdbcDriver, jdbcUrl, jdbcUserName, jdbcPassword, tables);
            }

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
//        jdbcUrl = "jdbc:mysql://10.10.0.70:3306/liuguo_mafia?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true";
        jdbcDriver = "com.mysql.jdbc.Driver";
        jdbcUserName = "dev";
        jdbcPassword = "---";
//        uploadPath = "/Users/wangjiuzhou/工作包/富士康/数据库脚本";
//        getMysqlTables();

        String s = null;
        try {
            for (int i = 0; i < jdbcArr.length; i++) {
                jdbcUrl = jdbcArr[i];
                getMysqlTables();
                s = DataBackUpUtils.backUpMysqlDDL(uploadPath, jdbcDriver,
                                                   jdbcUrl, jdbcUserName,
                                                   jdbcPassword, tables);
            }
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

        jdbcDriver = "com.mysql.jdbc.Driver";
        jdbcUserName = "dev";
        jdbcPassword = "---";

        try {
            tables = DataBackUpUtils.getMysqlTables(jdbcDriver, jdbcUrl, jdbcUserName, jdbcPassword);
            log.info(JSON.toJSONString(tables));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}