package com.Util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;


import org.apache.hive.jdbc.HiveDriver;

public class HiveHelper {
	private static final Logger logger = Logger.getLogger(HiveHelper.class);
	private static Connection connToHive = null;

	// 获得与 Hive 连接,如果连接已经初始化,则直接返回
	public static Connection getHiveConn() throws SQLException {
		if (connToHive == null) {
			try {
				Class.forName("org.apache.hive.jdbc.HiveDriver");
			} catch (ClassNotFoundException err) {
				err.printStackTrace();
				System.exit(1);
			}
			// hadoop3 为集群hive所在节点的IP地址
			connToHive = DriverManager.getConnection(
					"jdbc:hive2://192.168.64.129:10000/default", "hive", "");
		}
		return connToHive;
	}
	
	public static void closeHiveConn() throws SQLException {
		if (connToHive != null) {
			connToHive.close();
		}
	}
	
	public static void main(String[] args) throws SQLException {
		//System.out.println(getMySQLConn());
		//closeMySQLConn();
		Connection conn = HiveHelper.getHiveConn();
		
		//创建的表名
		String tableName = "test2HiveDriverTable";
		
		String sql = "";
		Statement stmt = conn.createStatement();
		ResultSet res = null;

		//执行"describe table"操作
		sql= "describe " + tableName;
		System.out.println("Running :" + sql);
		res = stmt.executeQuery(sql);
		System.out.println("执行'describe table'运行结果");
		while(res.next()){
			System.out.println(res.getString(1) + "\t" + res.getString(2));
		}
		
		/**
		 * Function: PrepareStatement
		 */
		String insertSql = "insert into dzyh_app.student values(?,?)";
		PreparedStatement ps = conn.prepareStatement(insertSql);
		ps.setString(1, "1");
		ps.setString(2, "zacks");
		Boolean return_bool =  ps.execute();
		logger.info("BOOL:" + return_bool);
		
		conn.close();
		
	}
	
}



/**
 * 负责连接Hive及mysql数据库
 * 
 * @author 吖大哥
 * 
 */
/*
public class DBHelper {

	private static Connection connToMySQL = null;

	private DBHelper() {
	}

	// 获得与 Hive 连接,如果连接已经初始化,则直接返回
	public static Connection getHiveConn() throws SQLException {
		if (connToHive == null) {
			try {
				Class.forName("org.apache.hadoop.hive.jdbc.HiveDriver");
			} catch (ClassNotFoundException err) {
				err.printStackTrace();
				System.exit(1);
			}
			// hadoop3 为集群hive所在节点的IP地址
			connToHive = DriverManager.getConnection(
					"jdbc:hive://hadoop3:10000/default", "hive", "mysql");
		}
		return connToHive;
	}

	// 获得与 MySQL 连接
	public static Connection getMySQLConn() throws SQLException {
		if (connToMySQL == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException err) {
				err.printStackTrace();
				System.exit(1);
			}

			// hadoop2为集群mysql安装IP地址
			connToMySQL = DriverManager
					.getConnection(
							"jdbc:mysql://hadoop2:3306/ha?useUnicode=true&characterEncoding=UTF8",
							"root", "hadoop"); // 编码不要写成UTF-8
		}
		return connToMySQL;
	}

	public static void closeHiveConn() throws SQLException {
		if (connToHive != null) {
			connToHive.close();
		}
	}

	public static void closeMySQLConn() throws SQLException {
		if (connToMySQL != null) {
			connToMySQL.close();
		}
	}

	public static void main(String[] args) throws SQLException {
		System.out.println(getMySQLConn());
		closeMySQLConn();
	}
*/