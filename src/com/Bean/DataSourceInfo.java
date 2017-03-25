package com.Bean;

import java.io.Serializable;

/**
 * 
 * @Description: 数据源信息  
 * @ClassName: DataSourceInfo  
 * @author: fangqifeng 
 * @date:2014-4-11 下午03:25:26  
 *
 * <p> 修改历史</p>
 * <p>  序号		日期		修改人			修改原因</p>
 * <p>   1                                       </p>
 */
@SuppressWarnings("serial")
public class DataSourceInfo implements Serializable {
	
	// 数据源编号
	private String dsId;
	
	// 别名
	private String alias;
	
	// 数据库类型
	private String dbType;

	// 数据库URL
	private String dbUrl;	
	
	// 驱动类
	private String driverClass;

	// 用户名 
	private String username;
	
	// 密码
	private String password;

	// 最大连接数
	private Integer maxCount;
	
	// ---------- 非持久化段 ----------
	
	// 数据连接测试语句
	private String sqlTestStr;

	public String getDsId() {
		return dsId;
	}

	public void setDsId(String dsId) {
		this.dsId = dsId;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getMaxCount() {
		return maxCount;
	}

	public void setMaxCount(Integer maxCount) {
		this.maxCount = maxCount;
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getDbUrl() {
		return dbUrl;
	}

	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}

	public String getSqlTestStr() {
		return sqlTestStr;
	}

	public void setSqlTestStr(String sqlTestStr) {
		this.sqlTestStr = sqlTestStr;
	}

}
