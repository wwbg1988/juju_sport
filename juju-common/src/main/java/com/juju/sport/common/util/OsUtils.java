package com.juju.sport.common.util;

public class OsUtils {
	public OsUtils(){
		
	}
	
	public static boolean isWindowsOS(){
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if(osName.toLowerCase().indexOf("windows")>-1){
			isWindowsOS = true;
		}
		return isWindowsOS;
	}
}
