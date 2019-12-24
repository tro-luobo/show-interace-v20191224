package com.jinke.showinterface.utils;

import java.io.BufferedReader;    
import java.io.IOException;    
import java.io.InputStream;    
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;    
     
/**  
 * 判断网络连接状况.  
 * @author luobo  
 * @date 2019年12月13日
 */   
public class PingIpUtil {    
    
    /**
     * 校验是否可以连通
     * @param ip
     * @return true/false
     */
    public static boolean isConnect(String ip){
        boolean connect = false;    
        Runtime runtime = Runtime.getRuntime();    
        Process process;    
        try {    
            process = runtime.exec("ping " + ip);    
            InputStream is = process.getInputStream();     
            InputStreamReader isr = new InputStreamReader(is);     
            BufferedReader br = new BufferedReader(isr);     
            String line = null;     
            StringBuffer sb = new StringBuffer();     
            while ((line = br.readLine()) != null) {     
                sb.append(line);     
            }        
            is.close();     
            isr.close();     
            br.close();     
     
            if (null != sb && !sb.toString().equals("")) {     
                String logString = "";     
                if (sb.toString().indexOf("TTL") > 0) {     
                    // 网络畅通      
                    connect = true;    
                } else {     
                    // 网络不畅通      
                    connect = false;    
                }     
            }     
        } catch (IOException e) {    
            e.printStackTrace();    
        }     
        return connect;    
    }    
    
    
    /**
         * Purpose:ping ip
         * @author Hermanwang
         * @param ipAddress:ip
         * @throws Exception
         * @return boolean
         */
    public static boolean pingIp(String ipAddress) throws Exception {
	    //此处 3是超时时间,单位是秒
	    return 0==Runtime.getRuntime().exec("ping -w 5 "+ipAddress).waitFor();
    }

    /**
         * Purpose:ping host或者 port
         * @author Hermanwang
         * @param ipAddress:ip
         * @param port:port/host
         * @throws Exception
         * @return boolean
         */
    public static boolean pingHost(String ipAddress,int port) throws Exception {
	    boolean isReachable = false;
	    Socket connect = new Socket();
	    try {
		    InetSocketAddress endpointSocketAddr = new InetSocketAddress(ipAddress, port);
		    //此处3000是超时时间,单位 毫秒
		    connect.connect(endpointSocketAddr,5000);
		    isReachable = connect.isConnected();
	    } catch (Exception e) {
	    	System.out.println(e.getMessage() + ", ip = " + ipAddress + ", port = " +port);
	    } finally {
		    if (connect != null) {
		    try {
		    connect.close();
		    } catch (IOException e) {
		    System.out.println(e.getMessage() + ", ip = " + ipAddress + ", port = " +port);
		    }
		    }
	    }
	    return isReachable;
    }
    
         
    public static void main(String[] args) throws Exception {    
    	System.out.println(pingIp("www.baidu"));
        /*PingIpUtil netState = new PingIpUtil();    
        System.out.println("ping结果:"+netState.isConnect("www.baidu.com"));    */
    }    
}
