package db.backup;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
