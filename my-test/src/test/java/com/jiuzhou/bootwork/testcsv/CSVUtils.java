package com.jiuzhou.bootwork.testcsv;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/09/19
 */
public class CSVUtils {

    /**
     * CSV 算法文件 跳过表头，直接读取数据
     *
     * @param path 文件路径
     *
     * @return
     */
    public static List<String> read(String path, boolean skipHeader) {
        List<String> lineList = null;
        if (StringUtils.isEmpty(path)) {
            return null;
        }
        try {
            lineList = new ArrayList<>();
            CsvReader csvReader = new CsvReader(path, ',', Charset.forName("UTF-8"));
            if (skipHeader) {
                // 跳过表头
                csvReader.readHeaders();
            }
            while (csvReader.readRecord()) {
                lineList.add(csvReader.getRawRecord());
            }
            csvReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lineList;
    }


    /**
     * 写CSV文件
     *
     * @param filePath
     * @param headers
     * @param list
     */
    public static void write(String filePath, String[] headers, List<String[]> list) {
        try {
            // 创建CSV写对象
            CsvWriter csvWriter = new CsvWriter(filePath, ',', Charset.forName("utf-8"));
            csvWriter.writeRecord(headers);
            for (String[] strings : list) {
                csvWriter.writeRecord(strings);
            }
            csvWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
