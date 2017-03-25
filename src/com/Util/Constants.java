package com.Util;

/**
 * 常量类
 */
public final class Constants {
	private static final ConfigReader reader = ConfigReader.getInstance();
	//===================================================================================
	//=                                    读取配置文件常量
	//===================================================================================
	/**
	 * 数据库schema
	 */
//	public static final String SCHEMA = reader.getProperty("schema");
	public static final String SCHEMA = "dzyh_app";
	/**
	 * 数据库用户名
	 */
//	public static final String DBUSER = reader.getProperty("user");
	/**
	 * 数据库密码
	 */
//	public static final String DBPASSWD = reader.getProperty("password");
	/**
	 * 数据库JDBC URL
	 */
//	public static final String JDBC = reader.getProperty("jdbcUrl");
	/**
	 * 数据库驱动文件
	 */
//	public static final String DRIVER = reader.getProperty("driverClass");

	/** 
	 * 数据源文件路径
	 */
	public final static String SOURCEINFO_FILE_NAME = Constants.class.getResource("/db.xml").getPath();
	
	public final static String DB_POOL_P9 = "P9";
	
	public final static String DB_POOL_P10 = "P10";

	/** 
	 * PostgreSQL数据库
	 */
	public final static String POSTGRE_DB = "PostgreSQL"; 
	
	/** 
	 * Oracle数据库
	 */
	public final static String ORACLE_DB = "Oracle"; 

	/** 
	 * 起始页参数名 
	 */
	public final static String PARAM_START = "start";
	
	/** 
	 * 每页条数参数名
	 */
	public final static String PARAM_LIMIT = "limit";
	
	/** 
	 * 分页：每页条数
	 */
	public final static String PAGE_SIZE_STR = "10";
	
	//===================================================================================
	//=                                    场景类 除指标外 常量
	//===================================================================================
	/**
	 * P9文件接收目录
	 */
	public static final String DIR_INFO = reader.getProperty("filedir");
	/**
	 *  login_flow登录流水表文件格式
	 */
	public static final String ECTIP_LOGINFLOW = "ECTIP_LOGIN_FLOW";
	/**
	 *  login_flow登录流水表文件格式
	 */
	public static final String READ_FILE_OVER_TIME = reader.getProperty("READ_FILE_OVER_TIME");
	
	//=====================================================================================
	//=								外部表相关参数
	//=====================================================================================
	public static final String gpfdist = reader.getProperty("gpfdist");
	
	//=====================================================================================
	//=								线程池相关参数
	//=====================================================================================
	/**
	 *  ThreadPoolSize 线程池大小
	 */
	public static final String POOLSIZE_MAX = reader.getProperty("PoolSize_max");
	
	
	//=====================================================================================
	//=								作业参数配置
	//=====================================================================================

	//=====================================================================================
	//=								存取参数配置
	//=====================================================================================
	public static final String CURRENT_PROJECTPATH  = System.getProperty("user.dir");

	public static final String FETCH_DATA_SAVE  = reader.getProperty("FetchDataSave");

}
