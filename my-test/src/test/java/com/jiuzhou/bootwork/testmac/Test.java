package com.jiuzhou.bootwork.testmac;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @author wangjiuzhou (835540436@qq.com)
 * @date 2019/02/24
 */
public class Test {
    public static void main(String[] args) throws UnknownHostException, SocketException {
        InetAddress ia = InetAddress.getLocalHost();
        System.out.println("亲测有效"+getMACAddress("40.73.34.31"));
    }


    /**
     * 根据IP地址获取MAC地址
     */
    public static String getMACAddress(String ipAddress) {
        String str = "", strMAC = "", macAddress = "";
        if(ipAddress.equals("本地")||ipAddress.equals("0:0:0:0:0:0:0:1")){
            return macAddress="本地";
        }
        try {
            Process pp = Runtime.getRuntime().exec("nbtstat -a " + ipAddress);
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (int i = 1; i < 100; i++) {
                str = input.readLine();
                if (str != null) {
                    if (str.indexOf("MAC Address") > 1||str.indexOf("MAC 地址") > 1) {
                        strMAC = str.substring(str.indexOf("MAC Address") + 14, str.length());
                        break;
                    }
                }
            }
        } catch (IOException ex) {
            return "Can't Get MAC Address!";
        }
        if (strMAC.length() < 17) {
            return "Error!";
        }
        macAddress = strMAC.substring(0, 2) + ":" + strMAC.substring(3, 5) + ":" + strMAC.substring(6, 8) + ":" + strMAC.substring(9, 11) + ":"
                        + strMAC.substring(12, 14) + ":" + strMAC.substring(15, 17);
        return macAddress;
    }

    public static String what(String ip) throws SocketException, UnknownHostException {
        NetworkInterface ne=NetworkInterface.getByInetAddress(InetAddress.getByName(ip));
        byte[]mac=ne.getHardwareAddress();
        StringBuffer sb = new StringBuffer("");
        for(int i=0; i<mac.length; i++) {
            if(i!=0) {
                sb.append("-");
            }
            //字节转换为整数
            int temp = mac[i]&0xff;
            String str = Integer.toHexString(temp);
            System.out.println("每8位:"+str);
            if(str.length()==1) {
                sb.append("0"+str);
            }else {
                sb.append(str);
            }
        }
        return sb.toString().toUpperCase();
    }
}
