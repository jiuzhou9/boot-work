package com.jiuzhou.bootwork.csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2018/09/19
 */
public class CSVUtils {

    public static void read(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));//换成你的文件名
            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;
            while((line=reader.readLine())!=null){
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分

                String last = item[item.length-1];//这就是你要的数据了
                //int value = Integer.parseInt(last);//如果是数值，可以转化为数值
                System.out.println(last);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void write(String fileName) {
        try {
            File csv = new File(fileName); // CSV数据文件

            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); // 附加
            // 添加新的数据行
            bw.write("\"李四\"" + "," + "\"1988\"" + "," + "\"1992\"");
            bw.newLine();
            bw.close();

        } catch (FileNotFoundException e) {
            // File对象的创建过程中的异常捕获
            e.printStackTrace();
        } catch (IOException e) {
            // BufferedWriter在关闭对象捕捉异常
            e.printStackTrace();
        }
    }


    public static void writeAndRead(){
        try {
            File csv = new File("/Users/wangjiuzhou/Desktop/mar/111.csv"); // CSV数据文件'
            if (!csv.exists()){
                csv.createNewFile();
            }

            BufferedWriter bw = new BufferedWriter(new FileWriter(csv, true)); // 附加

            BufferedReader reader = new BufferedReader(new FileReader("/Users/wangjiuzhou/Desktop/mar/MatirxOutPut_20180918132833_MINI_VAN.csv"));//换成你的文件名
            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;
            while((line=reader.readLine())!=null){
//                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分

//                String last = item[item.length-1];//这就是你要的数据了
                //int value = Integer.parseInt(last);//如果是数值，可以转化为数值
//                System.out.println(last);

                // 添加新的数据行
                bw.write(line);
                bw.newLine();

            }

            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        writeAndRead();
        System.out.println(System.currentTimeMillis() - l);
    }

}
