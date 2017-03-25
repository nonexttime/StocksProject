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
 * @Description: ���ݿ����ӳع�����
 * @ClassName: DBConnectionManager  
 * @author: fangqifeng 
 * @date:2013-6-1 ����10:22:53  
 *
 * <p> �޸���ʷ</p>
 * <p>  ���		����		�޸���			�޸�ԭ��</p>
 * <p>   1                                       </p>
 */
public class DBConnectionManager {
	
	// Ψһʵ��
	private static DBConnectionManager instance;
	
	// ���ݿ�������Ϣ
	private Vector<DataSourceInfo> drivers = new Vector<DataSourceInfo>();
	
	// ���ݿ����ӳ�
	private Hashtable<String, DBConnectionPool> pools = new Hashtable<String, DBConnectionPool>();
	
	// ��־��¼
	private final Log log = LogFactory.getLog(getClass());
	

	/**
	 * ʵ�������ݿ����ӳع�����
	 */
	private DBConnectionManager() {
		this.init();
	}
	
	/**
	 * 
	 * @Description: �õ�Ψһʵ�������ݿ����ӳع�����
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
	 * @Description: ���һ�����õ�(���е�)���ӡ�
	 * @Title: getConnection
	 * @param poolName ���ݿ����ӳ���
	 * @return
	 * @throws Exception
	 */
	public Connection getConnection(String poolName) throws Exception {
		Connection conn = null;
		DBConnectionPool pool = (DBConnectionPool) pools.get(poolName);
		if (pool != null) {
			conn = pool.getConnection();
		}else{
			log.error("��ȡһ�������ڵ����ӳ����ӣ����ӳ����ƣ�"+poolName);
		}
		if(conn == null) {
			log.error("��ȡ���ݿ�����ʧ�ܣ����ӳ����ƣ�"+poolName);
		}
		return conn;
	}

	/**
	 * 
	 * @Description: �ڳ�ʱʱ����Чǰ�����һ�����õ�(���е�)���ӡ�
	 * @Title: getConnection
	 * @param poolName ���ݿ����ӳ���
	 * @param timeout ��ʱʱ��
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
			log.info("��ȡ���ݿ�����ʧ�ܣ�");
		}
		return conn;
	}

	/**
	 * 
	 * @Description: �ͷ����ݿ����ӣ����ظ�ָ�������ݿ����ӳ�
	 * @Title: freeConnection
	 * @param poolName ���ݿ����ӳ���
	 * @param conn ���Ӷ���
	 * @throws
	 */
	public void freeConnection(String poolName, Connection conn) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(poolName);
		if (pool != null) {
			try {
				pool.freeConnection(conn);
			} catch (SQLException e) {
				log.error("�ͷ����ݿ�����ʧ�ܣ�");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * Description: �������ݿ����ӳأ��ⲿ�ӿڣ�
	 * @Title: createPool
	 * @param info ���ݿ�������Ϣ
	 * @throws
	 */
	public void createPool(DataSourceInfo info) {
		this.createPools(info);
		log.info("�������ݿ����ӳ�<" + info.getDsId() + ">�ɹ���");
	}
	
	/**
	 * 
	 * @Description: �������ݿ����ӳأ��ⲿ�ӿڣ�
	 * @Title: destroyPool
	 * @param poolName ���ݿ����ӳ���
	 * @throws
	 */
	public void destroyPool(String poolName) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(poolName);
		if (pool != null) {
			pool.release();
			log.info("�ͷ����ݿ�<" + poolName + ">���ӣ�");
			pools.remove(poolName);
			log.info("�������ݿ����ӳ�<" + poolName + ">�ɹ���");
		}
	}

	/**
	 * 
	 * @Description: �ر���������,�������������ע��
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
	 * @Description: ���ݿ����ӳس�ʼ��
	 * @Title: init
	 * @throws
	 */
	private void init() {
		// ��ȡ���е����ݿ�������Ϣ
		this.loadDrivers();
		// �������ӳ�
		Enumeration<DataSourceInfo> allDriver = drivers.elements();
		while(allDriver.hasMoreElements()){
			DataSourceInfo info = allDriver.nextElement();
			createPools(info);
			log.info("�������ݿ����ӳ�<" + info.getDsId() + ">�ɹ���");
		}
		log.info("�������ݿ����ӳ���ϣ�");
	}
	
	/**
	 * 
	 * @Description: װ���������ݿ�������Ϣ
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
		log.info("�ɹ�װ���������ݿ�������Ϣ��");
	}
	
	/**
	 * 
	 * @Description: �������ݿ�������Ϣ�������ӳ�
	 * @Title: createPools
	 * @param info ���ݿ�������Ϣ
	 * @throws
	 */
	private void createPools(DataSourceInfo info) {
		String poolName = info.getDsId();
		//�ж����ӳ������Ƿ���ڣ��������򴴽�
		if(!pools.containsKey(poolName)) {
			DBConnectionPool pool = new DBConnectionPool(poolName, info.getDriverClass(), info.getDbUrl(), info.getUsername(), info.getPassword(), info.getMaxCount());
			pool.setPoolName(poolName);
			pool.setMinConn(10);
			pool.setDbType(info.getDbType());
			pools.put(poolName, pool);
		}
	}

}