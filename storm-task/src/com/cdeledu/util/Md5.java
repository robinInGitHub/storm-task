package com.cdeledu.util;

import java.security.MessageDigest;

public class Md5 {
	
	public static String MD5Encode(String digit, String charset)
	{
		String resultString = null;
		StringBuffer buf = new StringBuffer();
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(charset.getBytes());
			byte b[] = md.digest();
			int i;
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			
			if(digit!=null && "16".equals(digit)){
				resultString =  buf.toString().substring(8, 24);
			}else{
				resultString = buf.toString();
			}
			
			md = null;
			buf = null;
		
		} catch (Exception exception) {	
		}
		
		return resultString;
	}
	
}
