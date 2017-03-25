package com.Test;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Properties;


public class Character_EncodingTest {
	public static void main(String[] args) throws Exception{
		String str = "测试字符转换 hello word"; //默认环境，已是UTF-8编码
		System.out.println(str);
		//str="%EF%BF%BD%EF%BF%BD%CD%A8%D6%A4%C8%AF";
		
		String str2 = "海通证券";
		System.out.println(str2);
		
		Properties pro = System.getProperties();
		System.out.println(pro.getProperty("file.encoding"));
		
		
		try {
			String strGBK = URLEncoder.encode(str, "GBK");
			System.out.println(strGBK);
			String strUTF8 = URLDecoder.decode(str, "UTF-8");
			System.out.println(strUTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
}