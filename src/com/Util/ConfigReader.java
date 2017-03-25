package com.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 配置文件
 * @author liwei17
 *
 */
public class ConfigReader {
	private static Log logger = LogFactory.getLog(ConfigReader.class);

	private static ConfigReader instance = new ConfigReader();
	private static Properties props;

	private ConfigReader() {

		InputStream configInputStream = null;
		InputStreamReader reader = null;
		try {
			props = new Properties();
			ClassLoader classLoader = getClass().getClassLoader();
			if(classLoader != null){
				configInputStream = classLoader.getResourceAsStream("config.properties");
				reader = new InputStreamReader(configInputStream, "utf-8");
				props.load(reader);
			}

		} catch (UnsupportedEncodingException e) {
			logger.error("config文件存在不支持的编码类型!");
		} catch (IOException e) {
			logger.error("读取config文件IO异常,请检查!");
		}finally{
			try{
				if(configInputStream != null){
					configInputStream.close();
				}
			}catch (IOException e) {
				logger.error("配置文件流关闭异常!");
			}
			try{
				if(reader != null){
					reader.close();
				}
			}catch (IOException e) {
				logger.error("配置文件流关闭异常!");
			}
		}

	}
	public static ConfigReader getInstance(){
		return instance ;
	}
	
	/**
	 * <pre>
	 * 获取配置文件的属性值
	 * </pre>
	 * @param property 属性
	 * @return
	 */
	public String getProperty(String property){
		return props.getProperty(property);
	}
}