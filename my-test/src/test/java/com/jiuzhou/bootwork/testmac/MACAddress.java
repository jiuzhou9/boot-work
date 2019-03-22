package com.jiuzhou.bootwork.testmac;
 
import org.junit.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
 
public class MACAddress {

    @Test
    public void test(){
        String mac = null;
        try {
            mac = getMac("40.73.34.31");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(mac);
    }
 
	public String getMac(String ip) throws IOException {
		String mac = "not found!";
		if (ip != null) {
 
			try {
				Process process = Runtime.getRuntime().exec("arp "+ip);
				InputStreamReader ir = new InputStreamReader(process.getInputStream());
				LineNumberReader input = new LineNumberReader(ir);
				String line;
				StringBuffer s = new StringBuffer();
				while ((line = input.readLine()) != null) {
					s.append(line);
 
				}
				mac = s.toString();
				if (mac != null) {
 
					mac = mac.substring(mac.indexOf(":") - 2, mac.lastIndexOf(":") + 3);
 
				} else {
					mac = "not found!";
				}
				return mac;
 
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return mac;
 
	}
}