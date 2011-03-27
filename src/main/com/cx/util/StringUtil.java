package com.cx.util;

public class StringUtil {

	public static boolean isEmail(String email) {
		if (email == null || email.length() < 6) {
			return false;
		}
		
		if (!email.contains("@")) {
			return false;
		}
		
		if (!email.contains(".")) {
			return false;
		}
		
		return true;
	}
	
	public static final String utf8Convert(String utf8String)  {
		if (null == utf8String)
			return null;
		byte[] bytes = new byte[utf8String.length()];
		for (int i = 0; i < utf8String.length(); i++) {
			bytes[i] = (byte) utf8String.charAt(i);
		}
		String result = null;
		try {
			result = new String(bytes, "UTF-8");
		}
		catch (Exception e) {}
		return result;
	}
}
