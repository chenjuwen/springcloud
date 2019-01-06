package com.seasy.springcloud.serviceapi.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;


public class EnvUtil {
	public static String getLocalIp(){
		String hostAddress = "";
		String osName = System.getProperty("os.name").toLowerCase();
		if(osName.indexOf("windows") >= 0){
			try {     
				hostAddress = InetAddress.getLocalHost().getHostAddress();
			} catch (Exception ex) {  
			   ex.printStackTrace();
			} 
			
		}else{
			try {
				for (Enumeration<NetworkInterface> enum1 = NetworkInterface.getNetworkInterfaces(); enum1.hasMoreElements();) {
			    	NetworkInterface networkInterface = enum1.nextElement();
			    	for (Enumeration<InetAddress> enum2 = networkInterface.getInetAddresses(); enum2.hasMoreElements();) {
			    		InetAddress inetAddress = enum2.nextElement();
			    		if(inetAddress.getHostAddress().split("\\.").length == 4){ //IPv4
			    			if (!inetAddress.isAnyLocalAddress() && !inetAddress.isLoopbackAddress()
			            		   && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
			    				hostAddress = inetAddress.getHostAddress();
			            	   	break;
			    			}
			    		}
			    	}
		           
			    	if(StringUtil.isNotEmpty(hostAddress)){
			    		break;
		           	}
				}
			} catch (Exception ex) {
			   ex.printStackTrace();
			}
		}
		
		return hostAddress;
	}

}
