package backup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/backup")
public class BackUpController {

    /*@Value("${download.file-path}")
    private String FILE_PATH;

    @Value("${spring.datasource.url}")
    private String URL;

    @Value("${spring.datasource.driver-class-name}")
    private String DRIVER_NAME;

    @Value("${spring.datasource.username}")
    private String USERNAME;

    @Value("${spring.datasource.password}")
    private String PASSWORD;

    @GetMapping("/data")
    public BaseResponse<String> backUpData(HttpServletRequest request, HttpServletResponse response, String tables)
                    throws SQLException, IOException, ClassNotFoundException {
        BaseResponse<String> baseResponse = new BaseResponse();

        List<String> tableList = new ArrayList<>();
        if (StringUtils.isNotBlank(tables)) {
            String[] strings = tables.split(",");
            tableList.addAll(Arrays.asList(strings));
        }
        String filePath = DataBackUpUtils
                        .backupData(FILE_PATH, DRIVER_NAME, URL, USERNAME, PASSWORD, tableList);
        baseResponse.setData(filePath);
        ExcelExportUtil.outputBigExcel(request,response, filePath, "数据备份.zip");
        return baseResponse;
    }

    @GetMapping("/structure")
    public BaseResponse<String> backUpStructure(HttpServletRequest request, HttpServletResponse response)
                    throws SQLException, IOException, ClassNotFoundException {
        BaseResponse<String> baseResponse = new BaseResponse();

        String filePath = DataBackUpUtils.backUpMysqlDDL(FILE_PATH, DRIVER_NAME, URL, USERNAME, PASSWORD);
        baseResponse.setData(filePath);
        ExcelExportUtil.outputBigExcel(request,response, filePath, "表结构备份.zip");
        return baseResponse;
    }*/
}
