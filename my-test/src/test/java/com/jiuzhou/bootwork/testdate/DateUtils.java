package com.jiuzhou.bootwork.testdate;

import junit.framework.TestCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wangjiuzhou (jiuzhou@shanshu.ai)
 * @date 2019/03/25
 */
public class DateUtils extends TestCase{

    public void test() {
        String s = get("20180912");
        System.out.println(s);
    }

    /**
     * 获取时间格式：由 yyyyMMdd 转换为 yyyy-MM-dd
     * @param dateStr
     * @return
     */
    public static String get(String dateStr){
        //获取的值为"19570323"
        // dateStr = "19570323";

        //1、定义转换格式
        SimpleDateFormat formatter  = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter2  = new SimpleDateFormat("yyyyMMdd");
        //2、调用formatter2.parse(),将"19570323"转化为date类型  输出为：Sat Mar 23 00:00:00 GMT+08:00 1957
        Date date = null;
        try {
            date = formatter2.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //3、将date类型  (Sat Mar 23 00:00:00 GMT+08:00 1957)转化为String类型
        //注意现在用的是formatter来做转换,输出为String类型的："1957-03-23"
        String  dString = formatter.format(date);
//        System.out.println(dString);



        //4、将String转化为date，需要注意java.sql.Date.valueOf()函数只能接受参数类型为yyyy-MM-dd类型的
        //        Date data = java.sql.Date.valueOf(dString);
        //5、将获取的date类型的出生日期赋值给javabean
        //        personAudit.setBirthDate((emp.getHealthCarePrincipalPerson() != null
        //                        && emp.getHealthCarePrincipalPerson().getBirthTime() != null)?data:null);
        return dString;
    }
}
