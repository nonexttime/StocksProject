package com.Util;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.Util.DBConnectionManager;

public class ConnectionDB {
	
	private static Log log = LogFactory.getLog(ConnectionDB.class);

	private DBConnectionManager connMgr = null;
	
	private Connection conn;
	
	private String poolName;

	public ConnectionDB() {
		try {
			connMgr = DBConnectionManager.getInstance();		
		} catch (Exception e) {
			log.error("初始化数据库连接池异常！");
		}
	}
	
	/*
	 * 返回数据库连接
	 * */
	public Connection getConnection(String poolName) throws Exception{
		this.poolName = poolName;
		if (null == conn){
			this.conn = connMgr.getConnection(poolName);
		}
		return conn;
	}

	/**
	 * 释放连接
	 * 
	 */
	public void free() {
		try {
			if (null != this.conn && !this.conn.isClosed()){
				this.conn.setAutoCommit(true);
			}
		} catch (SQLException e) {
			log.error(e.toString());
		}
		// 将用过的连接再回收到池中。
		connMgr.freeConnection(poolName, this.conn);
	}
	
}
