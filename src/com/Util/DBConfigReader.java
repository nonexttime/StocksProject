package com.Util;

import java.util.Iterator;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.Bean.DataSourceInfo;
import com.Util.Constants;

/**
 * 
 * Description: 数据源文件操作 
 * @ClassName: DBConfigReader  
 * @author: fangqifeng 
 * @date:2013-5-30 下午03:49:22  
 *
 * <p> 修改历史</p>
 * <p>  序号		日期		修改人			修改原因</p>
 * <p>   1                                       </p>
 */
public class DBConfigReader {
	
	//唯一静态实例
	private static DBConfigReader instance = null;
	
	//私有化构造方法
	private DBConfigReader(){};
	
	/**
	 * 
	 * Description: 得到单例
	 * @Title: getInstance
	 * @return
	 * @return DBConfigReader
	 * @throws
	 */
	public static synchronized DBConfigReader getInstance(){
		if (null == instance){
			instance = new DBConfigReader();
		}
		return instance;
	}
	
	/**
	 * 
	 * Description: 得到所有的数据源列表
	 * @Title: getAllInfoList
	 * @return
	 * @throws Exception
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public synchronized Vector<DataSourceInfo> getAllInfoList() throws Exception{
		
		//构建解XML文档析器
	    SAXReader reader = new SAXReader();
	        
	    //构建Document树
	    Document initDoc = null;

	    //读取配置文件
	    initDoc = reader.read(Constants.SOURCEINFO_FILE_NAME);

	    //取得文件节点根
	    Element intRoot = initDoc.getRootElement();
	    
	    Vector<DataSourceInfo> list = new Vector<DataSourceInfo>();
	    
	    for (Iterator<Element> intRootIte = intRoot.elementIterator("info");intRootIte.hasNext();){
	    	Element infoElement = intRootIte.next();

	    	String id = infoElement.attributeValue("id").trim();
	    	String alias = infoElement.attributeValue("alias").trim();
	    	String dbType = infoElement.attributeValue("dbType").trim();
	    	String dbUrl = infoElement.attributeValue("dbUrl").trim();
	    	String driverClass = infoElement.attributeValue("driverClass").trim();
	    	String username = infoElement.attributeValue("username").trim();
	    	String password = infoElement.attributeValue("password").trim();
	    	String maxCountValue = infoElement.attributeValue("maxCount").trim();
	    	Integer maxCount = 30;
	    	if (null != maxCountValue && !"".equals(maxCountValue.trim())){
	    		try{
	    			maxCount = Integer.parseInt(maxCountValue.trim());
	    		}catch(Exception e){
	    			maxCount = 30;
	    		}
	    		
	    	}
	    	
	    	DataSourceInfo info = new DataSourceInfo();
	    	info.setDsId(id);
	    	info.setAlias(alias);
	    	info.setDbType(dbType);
	    	info.setDbUrl(dbUrl);
	    	info.setDriverClass(driverClass);
	    	info.setUsername(username);
	    	info.setPassword(password);
	    	info.setMaxCount(maxCount);
	    	list.add(info);
	    }
		return list;
	}


	/**
	 * 
	 * Description: 得到某个数据源
	 * @Title: getInfo
	 * @param id
	 * @return
	 * @throws Exception
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public synchronized DataSourceInfo getInfo(String id) throws Exception{
		
		//构建解XML文档析器
	    SAXReader reader = new SAXReader();
	        
	    //构建Document树
	    Document initDoc = null;

	    //读取配置文件
	    initDoc = reader.read(Constants.SOURCEINFO_FILE_NAME);

	    //取得文件节点根
	    Element intRoot = initDoc.getRootElement();
	    
	    DataSourceInfo info = null;
	    
	    for (Iterator<Element> intRootIte = intRoot.elementIterator("info");intRootIte.hasNext();){
	    	Element infoElement = intRootIte.next();
	    	
	    	String oldId = infoElement.attributeValue("id").trim();
	    	
	    	if (id.equals(oldId)){
		    	String alias = infoElement.attributeValue("alias").trim();
		    	String dbType = infoElement.attributeValue("dbType").trim();
		    	String dbUrl = infoElement.attributeValue("dbUrl").trim();
		    	String driverClass = infoElement.attributeValue("driverClass").trim();
		    	String username = infoElement.attributeValue("username").trim();
		    	String password = infoElement.attributeValue("password").trim();
		    	String maxCountValue = infoElement.attributeValue("maxCount").trim();
		    	Integer maxCount = 30;
		    	if (null != maxCountValue && !"".equals(maxCountValue.trim())){
		    		try{
		    			maxCount = Integer.parseInt(maxCountValue.trim());
		    		}catch(Exception e){
		    			maxCount = 30;
		    		}
		    		
		    	}

		    	info = new DataSourceInfo();
		    	info.setDsId(id);
		    	info.setAlias(alias);
		    	info.setDbType(dbType);
		    	info.setDbUrl(dbUrl);
		    	info.setDriverClass(driverClass);
		    	info.setUsername(username);
		    	info.setPassword(password);
		    	info.setMaxCount(maxCount);
	    	}
	    }
	    return info;
	}
	
}
