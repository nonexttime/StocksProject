package com.Util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.Bean.DataSourceInfo;


/**
 * 
 * @Description: 数据库连接池管理器
 * @ClassName: DBConnectionManager  
 * @author: fangqifeng 
 * @date:2013-6-1 上午10:22:53  
 *
 * <p> 修改历史</p>
 * <p>  序号		日期		修改人			修改原因</p>
 * <p>   1                                       </p>
 */
public class DBConnectionManager {
	
	// 唯一实例
	private static DBConnectionManager instance;
	
	// 数据库连接信息
	private Vector<DataSourceInfo> drivers = new Vector<DataSourceInfo>();
	
	// 数据库连接池
	private Hashtable<String, DBConnectionPool> pools = new Hashtable<String, DBConnectionPool>();
	
	// 日志记录
	private final Log log = LogFactory.getLog(getClass());
	

	/**
	 * 实例化数据库连接池管理器
	 */
	private DBConnectionManager() {
		this.init();
	}
	
	/**
	 * 
	 * @Description: 得到唯一实例化数据库连接池管理器
	 * @Title: getInstance
	 * @return
	 * @throws
	 */
	public static synchronized DBConnectionManager getInstance() {
		if (instance == null) {
			instance = new DBConnectionManager();
		}
		return instance;
	}

	/**
	 * 
	 * @Description: 获得一个可用的(空闲的)连接。
	 * @Title: getConnection
	 * @param poolName 数据库连接池名
	 * @return
	 * @throws Exception
	 */
	public Connection getConnection(String poolName) throws Exception {
		Connection conn = null;
		DBConnectionPool pool = (DBConnectionPool) pools.get(poolName);
		if (pool != null) {
			conn = pool.getConnection();
		}else{
			log.error("获取一个不存在的连接池连接！连接池名称："+poolName);
		}
		if(conn == null) {
			log.error("获取数据库连接失败！连接池名称："+poolName);
		}
		return conn;
	}

	/**
	 * 
	 * @Description: 在超时时间生效前，获得一个可用的(空闲的)连接。
	 * @Title: getConnection
	 * @param poolName 数据库连接池名
	 * @param timeout 超时时间
	 * @return
	 * @throws
	 */
	public Connection getConnection(String poolName, long timeout) throws Exception {
		Connection conn = null;
		DBConnectionPool pool = (DBConnectionPool) pools.get(poolName);
		if (pool != null) {
			conn = pool.getConnection(timeout);
		}
		if(conn == null) {
			log.info("获取数据库连接失败！");
		}
		return conn;
	}

	/**
	 * 
	 * @Description: 释放数据库连接，发回给指定的数据库连接池
	 * @Title: freeConnection
	 * @param poolName 数据库连接池名
	 * @param conn 连接对象
	 * @throws
	 */
	public void freeConnection(String poolName, Connection conn) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(poolName);
		if (pool != null) {
			try {
				pool.freeConnection(conn);
			} catch (SQLException e) {
				log.error("释放数据库连接失败！");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * Description: 创建数据库连接池（外部接口）
	 * @Title: createPool
	 * @param info 数据库连接信息
	 * @throws
	 */
	public void createPool(DataSourceInfo info) {
		this.createPools(info);
		log.info("创建数据库连接池<" + info.getDsId() + ">成功！");
	}
	
	/**
	 * 
	 * @Description: 销毁数据库连接池（外部接口）
	 * @Title: destroyPool
	 * @param poolName 数据库连接池名
	 * @throws
	 */
	public void destroyPool(String poolName) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(poolName);
		if (pool != null) {
			pool.release();
			log.info("释放数据库<" + poolName + ">连接！");
			pools.remove(poolName);
			log.info("销毁数据库连接池<" + poolName + ">成功！");
		}
	}

	/**
	 * 
	 * @Description: 关闭所有连接,撤销驱动程序的注册
	 * @Title: release
	 * @throws Exception
	 * @throws
	 */
	public synchronized void release() throws Exception {
		Enumeration<DBConnectionPool> allPools = pools.elements();
		while (allPools.hasMoreElements()) {
			DBConnectionPool pool = allPools.nextElement();
			pool.release();
		}
		pools.clear();
	}

	/**
	 * 
	 * @Description: 数据库连接池初始化
	 * @Title: init
	 * @throws
	 */
	private void init() {
		// 获取所有的数据库连接信息
		this.loadDrivers();
		// 创建连接池
		Enumeration<DataSourceInfo> allDriver = drivers.elements();
		while(allDriver.hasMoreElements()){
			DataSourceInfo info = allDriver.nextElement();
			createPools(info);
			log.info("创建数据库连接池<" + info.getDsId() + ">成功！");
		}
		log.info("创建数据库连接池完毕！");
	}
	
	/**
	 * 
	 * @Description: 装载所有数据库连接信息
	 * @Title: loadDrivers
	 * @throws
	 */
	private void loadDrivers() {
		DBConfigReader dbReader = DBConfigReader.getInstance();
		try {
			drivers = dbReader.getAllInfoList();
		} catch (Exception e) {
			log.error(e.toString());
		}
		log.info("成功装载所有数据库连接信息！");
	}
	
	/**
	 * 
	 * @Description: 根据数据库连接信息创建连接池
	 * @Title: createPools
	 * @param info 数据库连接信息
	 * @throws
	 */
	private void createPools(DataSourceInfo info) {
		String poolName = info.getDsId();
		//判断连接池名称是否存在，不存在则创建
		if(!pools.containsKey(poolName)) {
			DBConnectionPool pool = new DBConnectionPool(poolName, info.getDriverClass(), info.getDbUrl(), info.getUsername(), info.getPassword(), info.getMaxCount());
			pool.setPoolName(poolName);
			pool.setMinConn(10);
			pool.setDbType(info.getDbType());
			pools.put(poolName, pool);
		}
	}

}