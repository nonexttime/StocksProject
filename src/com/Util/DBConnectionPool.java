package com.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.Util.Constants;

/**
 * 
 * @Description: 数据库连接池类  
 * @ClassName: DBConnectionPool  
 * @author: fangqifeng 
 * @date:2013-5-31 下午04:14:15  
 *
 * <p> 修改历史</p>
 * <p>  序号		日期		修改人			修改原因</p>
 * <p>   1                                       </p>
 */
public class DBConnectionPool {
	
	private Connection conn = null;

	// 连接池名称
	private String poolName;
	
	// 当前数据库连接数
	private int inUsed = 0;
	
	// 最小连接数
	private int minConn;
	
	// 最大连接数
	private int maxConn;
	
	// 数据库驱动
	private String driver;
	
	// 数据库连接地址
	private String url;
	
	// 用户名
	private String user;
	
	// 密码
	private String password;
	
	//数据库类型
	private String dbType;
	
	//最大重试次数
	private int maxRetry = 10;
	
	// 连接容器
	private Vector<Connection> freeConnections = new Vector<Connection>();
	
	// 日志记录
	private final  Log log = LogFactory.getLog(getClass());

	/**
	 * 创建连接池
	 * @param poolName 连接池名称 （DriverInfo 的 id）
	 * @param url 数据库连接地址
	 * @param user 用户名
	 * @param password 密码
	 * @param maxConn 最大连接数
	 */
	public DBConnectionPool(String poolName, String driver, String url, String user,String password, int maxConn) {
		this.poolName = poolName;
		this.driver = driver;
		this.url = url;
		this.user = user;
		this.password = password;
		this.maxConn = maxConn;
	}
	
	/**
	 * 
	 * @Description: 从连接池中获取连接
	 * 					1、如果没有可用连接，且已有连接数小于最大连接数 maxConn限制，则创建并返回新连接。
	 * 					2、如果连接已经失效，则重新获取，直至获取到可用的链接。
	 * 					3、连接池使用数加1。
	 * @Title: getConnection
	 * @return
	 * @throws Exception
	 */
	public synchronized Connection getConnection() throws Exception {
		Connection conn = null;
		if(freeConnections.size() > 0) {
			conn = (Connection) freeConnections.firstElement();
			freeConnections.removeElementAt(0);
		} else if (maxConn == 0 || inUsed < maxConn) {
			conn = newConnection();
		}
		
		// 判读获取的连接是否有效，无效则递归调用
		try {
			if(conn == null || conn.isClosed()) {
				if(maxRetry < 0) {
					throw new Exception("获取数据库连接失败！");
				} else {
					maxRetry--;
					conn = getConnection();
				}
			}else{
				maxRetry = 10;
				inUsed++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	/**
	 * 
	 * @Description: 根据超时时间，从连接池中获取连接
	 * 					1、如果没有可用连接，则等待。
	 * 					2、如果时间超出所设置的超时时间，则返回null。
	 * @Title: getConnection
	 * @param timeout
	 * @return
	 * @throws Exception
	 */
	public synchronized Connection getConnection(long timeout) throws Exception {
		Connection conn = null;
		long startTime = new Date().getTime();
		while ((conn = getConnection()) == null) {
			try {
				wait(timeout);
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.error("等待获取连接异常", e);
			}
			if ((new Date().getTime() - startTime) >= timeout) {
				log.info("等待超时！");
				return null;
			}
		}
		return conn;
	}
	
	/**
	 * 
	 * @Description: 释放数据库连接
	 * @Title: freeConnection
	 * @param con
	 * @throws SQLException
	 * @throws
	 */
	public synchronized void freeConnection(Connection conn) throws SQLException {
		// 将指定连接加入到向量末尾
		if(conn!=null && !conn.isClosed()){
			conn.setAutoCommit(true);
			freeConnections.addElement(conn);
		}
		inUsed--;
		notifyAll();
		log.info("释放数据库<" + poolName + ">连接成功！");
	}
	
	/**
	 * 
	 * @Description: 关闭所有连接 
	 * @Title: release
	 * @throws
	 */
	public synchronized void release() {
		Enumeration<Connection> allConnections = freeConnections.elements();
		while (allConnections.hasMoreElements()) {
			Connection con = (Connection) allConnections.nextElement();
			try {
				con.close();
				log.info("关闭数据库<" + poolName + ">连接成功！");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		freeConnections.removeAllElements();
	}
	
	/**
	 * 
	 * @Description: 创建数据库连接
	 * @Title: newConnection
	 * @return
	 * @throws
	 */
	private Connection newConnection() {
		try {
			Class.forName(driver);
			Properties prop = new Properties();
			prop.put("user", user);
			prop.put("password", password);
			if (this.dbType.equalsIgnoreCase(Constants.ORACLE_DB)){
				//实现oracle数据库的字段注释读取
				prop.put("remarksReporting", "true");
				//设置超时时间
				prop.put("socketTimeout",60000);
				prop.put("readTimeout", 60000);
			}
			//设置驱动程序试图连接到某一数据库时将等待的最长时间，以秒为单位。
			DriverManager.setLoginTimeout(60);
			
			conn = DriverManager.getConnection(url, prop);
			log.info("创建数据库<" + poolName + ">连接成功！");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return conn;
	}

	////////////////////////////////getter setter////////////////////////////////////
	
	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}

	public int getMinConn() {
		return minConn;
	}

	public void setMinConn(int minConn) {
		this.minConn = minConn;
	}

	public int getMaxConn() {
		return maxConn;
	}

	public void setMaxConn(int maxConn) {
		this.maxConn = maxConn;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	
}
