package backup;

import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class SqlUtils {

//    private static final Log logger = LogFactory.getLog(SqlUtils.class);
    public final static Integer MAX_RECORDS = 20000;


    /**
     * 插入sql
     */
    private static String insert = "INSERT INTO";

    /**
     * values关键字
     */
    private static String values = "VALUES";


    /**
     * 获取insert语句
     * @param statement
     * @param table
     * @param start
     * @param end
     * @return
     */
    public static List<String> getMysqlInsertSqlList(Statement statement, String table, int start, int end){
        ResultSet results = null;
        //计数
        int recordsCount = 0;
        List<String> list = new ArrayList<>();
        StringBuffer insertBatchPre = null;

        String queryStr = "select * from " + table + " limit " + start + "," + end;
        log.info("语句：{}", queryStr);
        try {
            // 获取一个查询结果集为一张表的内容
            results = statement.executeQuery(queryStr);
            ResultSetMetaData rsmd = results.getMetaData();
            int columnCount = rsmd.getColumnCount();

            while (results.next()) {
                StringBuffer columnNameBuffer = new StringBuffer();
                StringBuffer columnValueBuffer = new StringBuffer();

                for (int i = 1; i <= columnCount; i++) {
                    String value = results.getString(i);
                    int columnType = rsmd.getColumnType(i);
                    String columnName = rsmd.getColumnName(i);
                    if (i == columnCount) {
                        columnNameBuffer.append(columnName);

                        if (value == null) {
                            columnValueBuffer.append("null");
                            continue;
                        }else {
                            if (Types.CHAR == columnType
                                    || Types.VARCHAR == columnType
                                    || Types.LONGVARCHAR == columnType
                                    || Types.DATE == columnType
                                    || Types.TIME == columnType
                                    || Types.TIMESTAMP == columnType

                            ) {
                                columnValueBuffer.append("'").append(value).append("'");
                                continue;
                            } else {
                                columnValueBuffer.append(value);
                                continue;
                            }
                        }


                    } else {
                        columnNameBuffer.append(columnName + ",");
                        if (value == null) {
                            columnValueBuffer.append("null,");
                        }

                        if (Types.CHAR == columnType
                                || Types.VARCHAR == columnType
                                || Types.LONGVARCHAR == columnType
                                || Types.DATE == columnType
                                || Types.TIME == columnType
                                || Types.TIMESTAMP == columnType
                        ) {
                            columnValueBuffer.append("'").append(value).append("',");
                            continue;
                        } else {
                            columnValueBuffer.append(value).append(",");
                            continue;
                        }
                    }
                }


                if (insertBatchPre == null){
                    insertBatchPre = new StringBuffer(insert);
                    insertBatchPre.append(" ")
                            .append(table)
                            .append("(")
                            .append(columnNameBuffer.toString())
                            .append(")\n")
                            .append(values + "\n");
                }

                if (recordsCount < MAX_RECORDS){
                    insertBatchPre.append("(")
                            .append(columnValueBuffer)
                            .append("),\n");
                    recordsCount++;
                }else if (recordsCount == MAX_RECORDS){
                    insertBatchPre.append("(")
                            .append(columnValueBuffer)
                            .append(");\n");
                    recordsCount = 0;
                    list.add(insertBatchPre.toString());
                    insertBatchPre = null;
                }else {
                    //
                }
            }

            if (insertBatchPre != null){
                String lastTmp = insertBatchPre.toString();
                String last = lastTmp.substring(0, lastTmp.length() - 2).concat(";\n\n");
                list.add(last);
            }

        } catch (Exception e) {
            System.out.println("three" + e.getMessage());
            return null;
        }
        return list;
    }
}