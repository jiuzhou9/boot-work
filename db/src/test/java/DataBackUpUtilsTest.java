import backup.DataBackUpUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private List<String> tables = Stream.of("base_base_location","base_category","base_plant","base_sale_area","base_sales","base_series","base_sku","base_spu","base_stock","example_data","oauth_access_token","oauth_approvals","oauth_client_details","oauth_client_token","oauth_code","oauth_refresh_token","spp_alg_production_fcst","spp_alg_sale_area_fcst","spp_alg_sale_fcst","spp_alg_sale_fcst_by_plan","spp_alg_sku_stockout","spp_alg_sku_unconstrained_fcst","spp_inventory","spp_plan_category_priority","spp_product_plan_sku_plant_customize","spp_production_month_count","spp_production_plan","spp_production_plan_detail","spp_production_plan_detail_data","spp_sale_base_data","spp_sale_base_key","spp_sale_plan","spp_sale_plan_data","spp_sale_plan_key","spp_sku_area_month_count","spp_sku_area_month_report","spp_urgent_insertion_order_detail","spp_urgent_insertion_order_plan","sys_menu","sys_menu_permission","sys_permission","sys_role","sys_role_permission","sys_user","sys_user_role").collect(
                    Collectors.toList());

    @Test
    public void backupData() {
        jdbcUrl = "jdbc:mysql://10.10.0.70:3306/liuguo_mafia?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true";
        jdbcDriver = "com.mysql.jdbc.Driver";
        jdbcUserName = "dev";
        jdbcPassword = "ShanshuDev2018$";
        uploadPath = "/Users/wangjiuzhou/Desktop/六国库备份";


        String s = null;
        try {
            s = DataBackUpUtils.backUpMysqlFastNew(uploadPath, jdbcDriver, jdbcUrl, jdbcUserName, jdbcPassword, tables);
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
        jdbcUrl = "jdbc:mysql://10.10.0.70:3306/mafia_aps?useUnicode=true&characterEncoding=utf8&autoReconnect=true&allowMultiQueries=true";
        uploadPath = "/Users/wangjiuzhou/Desktop/tmp";
        jdbcDriver = "com.mysql.jdbc.Driver";
        jdbcUserName = "mafiaAdmin";
        jdbcPassword = "mafiaAps2019";

        String s = null;
        try {
            s = DataBackUpUtils.backUpMysqlDDL(uploadPath, jdbcDriver, jdbcUrl, jdbcUserName, jdbcPassword);
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
            List<String> mysqlTables =
                            DataBackUpUtils.getMysqlTables(jdbcDriver, jdbcUrl, jdbcUserName, jdbcPassword);
            mysqlTables.removeIf(table->table.startsWith("QRTZ")
                            || table.startsWith("mafia")
                            || table.startsWith("sms")
                            || table.startsWith("sap"));
            log.info(JSON.toJSONString(mysqlTables));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}