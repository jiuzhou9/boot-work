package com.jiuzhou.bootwork.testmac;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2019/02/24
 */
public class TestMac {

    @Test
    public void test(){
        String macByIp = getMacByIp("40.73.34.31");
        System.out.println(macByIp);
    }
    /**
     * 通过访问的Ip地址得到mac地址
     * @param ip
     * @return mac
     */
    public String getMacByIp(String ip){
        String macAddress = "";
        try {
            java.lang.Process process = Runtime.getRuntime().exec("nbtstat -A "+ip);
            InputStreamReader ir = new InputStreamReader(process.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            String str = "";
            while ((str=input.readLine())!=null){
                str = str.toUpperCase();
                if(str.indexOf("MAC ADDRESS")>1){
                    int start = str.indexOf("=");
                    macAddress = str.substring(start+1,str.length()).trim();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  macAddress;

    }
}
