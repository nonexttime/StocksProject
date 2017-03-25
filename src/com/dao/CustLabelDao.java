package com.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTableInterface;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.util.Bytes;

import com.Bean.CustLabelBean;

public class CustLabelDao extends HBaseDao{

    /*TBL_CUST_LABEL
     * 根据label_Name模糊查询TBL_CUST_LABEL
     * @tableName 表明
     * @custBean custBean
     * @
     */
    public static  List<CustLabelBean> queryCustLabel(String tableName,String familyName,String columnName, String label_like_str)
            throws Exception {
    	List<CustLabelBean> custBean_List = new ArrayList<CustLabelBean>();
    	HTablePool pool = getHTablePool();
    	HTableInterface table = null;
    	try{
            table = pool.getTable(tableName);  
    		Scan scan = new Scan();

    		Filter fileter = new SingleColumnValueFilter(Bytes.toBytes(familyName),
    			    Bytes.toBytes(columnName), CompareOp.EQUAL,
    			    new RegexStringComparator(label_like_str));
    		
    		scan.setFilter(fileter);
    		scan.setCaching(200);
    		
    		ResultScanner rScanner = null;
    		//scan.setMaxVersions();
    		rScanner = table.getScanner(scan);
    		
    		for(Result r = rScanner.next();r!=null;r=rScanner.next()){
    	    	CustLabelBean custBean = new CustLabelBean();

    	    	custBean.setRowKey(getRealRowKey(r.getColumnLatest("LABEL_DESC".getBytes(), "LABEL_CNAME".getBytes())));
            	custBean.setLABEL_CNAME(Bytes.toString( r.getValue("LABEL_DESC".getBytes(), "LABEL_CNAME".getBytes()) ));
            	custBean.setLABEL_ENAME(Bytes.toString( r.getValue("LABEL_DESC".getBytes(), "LABEL_ENAME".getBytes()) ));
            	custBean.setLABEL_LEVEL(Bytes.toString( r.getValue("LABEL_DESC".getBytes(), "LABEL_LEVEL".getBytes()) ));
            	custBean.setLEVEL1_LABEL_ID(Bytes.toString( r.getValue("LABEL_SUBJECT".getBytes(), "LEVEL1_LABEL_ID".getBytes()) ));
            	custBean.setLEVEL1_LABEL_NAME(Bytes.toString( r.getValue("LABEL_SUBJECT".getBytes(), "LEVEL1_LABEL_NAME".getBytes()) ));
            	custBean.setLABEL_TYPE(Bytes.toString( r.getValue("LABEL_DESC".getBytes(), "LABEL_TYPE".getBytes()) ));

            	custBean_List.add(custBean);
    		}
    	}catch(IOException e){
    		e.printStackTrace();
    	}finally{
    		pool.putTable(table);
    	}
		return custBean_List;
		
    }
    
    /*TBL_CUST_LABEL
     * 添加新的CustLabel到TBL_CUST_LABEL
     * @tableName 表明
     * @custBean custBean
     * @
     */
    public static  boolean addCustLabel(String tableName,CustLabelBean newcustBean)
            throws Exception {
    	HTablePool pool = getHTablePool();
    	HTableInterface table = null;
    	Boolean result = true;
    	try{
            table = pool.getTable(tableName);  
            Put put = new Put(Bytes.toBytes(newcustBean.getRowKey()));// 设置rowkey

            put.add(Bytes.toBytes("LABEL_DESC"), Bytes.toBytes("LABEL_CNAME"),Bytes.toBytes(newcustBean.getLABEL_CNAME()));
            put.add(Bytes.toBytes("LABEL_DESC"), Bytes.toBytes("LABEL_ENAME"),Bytes.toBytes(newcustBean.getLABEL_CNAME()));
            put.add(Bytes.toBytes("LABEL_DESC"), Bytes.toBytes("LABEL_LEVEL"),Bytes.toBytes(newcustBean.getLABEL_CNAME()));
            put.add(Bytes.toBytes("LABEL_SUBJECT"), Bytes.toBytes("LEVEL1_LABEL_ID"),Bytes.toBytes(newcustBean.getLEVEL1_LABEL_ID()));
            put.add(Bytes.toBytes("LABEL_SUBJECT"), Bytes.toBytes("LEVEL1_LABEL_NAME"),Bytes.toBytes(newcustBean.getLABEL_CNAME()));
            put.add(Bytes.toBytes("LABEL_DESC"), Bytes.toBytes("LABEL_TYPE"),Bytes.toBytes(newcustBean.getLABEL_CNAME()));

           // table.put(put);
    		
    	}finally{
    		pool.putTable(table);
    	}
    	
		return result;
    }
    
    /*TBL_CUST_LABEL
     * 更新CustLabel到TBL_CUST_LABEL
     * @tableName 表明
     * @custBean custBean
     * @
     */
    public static  boolean updateCustLabel(String tableName,CustLabelBean custBean)
            throws Exception {
    	HTablePool pool = getHTablePool();
    	HTableInterface table = null;
    	Boolean result = true;
    	try{
            table = pool.getTable(tableName);  
            Put put = new Put(Bytes.toBytes(custBean.getRowKey()));// 设置rowkey
    		
            put.add(Bytes.toBytes("LABEL_DESC"), Bytes.toBytes("LABEL_CNAME"),Bytes.toBytes(custBean.getLABEL_CNAME()));
            put.add(Bytes.toBytes("LABEL_DESC"), Bytes.toBytes("LABEL_ENAME"),Bytes.toBytes(custBean.getLABEL_CNAME()));
            put.add(Bytes.toBytes("LABEL_DESC"), Bytes.toBytes("LABEL_LEVEL"),Bytes.toBytes(custBean.getLABEL_CNAME()));
            put.add(Bytes.toBytes("LABEL_SUBJECT"), Bytes.toBytes("LEVEL1_LABEL_ID"),Bytes.toBytes(custBean.getLEVEL1_LABEL_ID()));
            put.add(Bytes.toBytes("LABEL_SUBJECT"), Bytes.toBytes("LEVEL1_LABEL_NAME"),Bytes.toBytes(custBean.getLABEL_CNAME()));
            put.add(Bytes.toBytes("LABEL_DESC"), Bytes.toBytes("LABEL_TYPE"),Bytes.toBytes(custBean.getLABEL_CNAME()));

           table.put(put);
    	}catch(IOException e){
    		e.printStackTrace();
    	}finally{
    		pool.putTable(table);
    	}
    	
		return result;

    }
    
    /* TBL_CUST_LABEL
     * 删除CustLabel到TBL_CUST_LABEL
     * @tableName 表明
     * @custBean custBean
     */
    public static  boolean deleteCustLabel(String tableName,CustLabelBean custBean)
            throws Exception {
    	HTablePool pool = getHTablePool();
    	HTableInterface table = null;
    	Boolean result = true;
    	try{
        	String rowKey = custBean.getRowKey();
            table = pool.getTable(tableName);  

            Delete deleteAll = new Delete(Bytes.toBytes(rowKey));
            table.delete(deleteAll);
    	}catch(IOException e){
    		e.printStackTrace();
    	}finally{
    		pool.putTable(table);
    	}
		return result;    	
    }
    
    /*TBL_CUST_VIEW
     * 根据label_Name模糊查询TBL_CUST_LABEL
     * @tableName 表明
     * @custBean custBean
     * @
     */
    public static  List<CustLabelBean> queryCustViewByCustID(String tableName, String custID_str)
            throws Exception {
    	List<CustLabelBean> custBean_List = new ArrayList<CustLabelBean>();

    	HTablePool pool = getHTablePool();
    	HTableInterface table = null;
    	Boolean result = true;
    	try{
            table = pool.getTable(tableName);  

            Get get = new Get(custID_str.getBytes());
            Result r = table.get(get);
            System.out.println("key:" + r.getRow().toString());
            
            KeyValue[] kv = r.raw();
            for(int i = 0;i < kv.length;i ++){
            	String qualifier = new String(kv[i].getQualifier());
            	String val = new String(kv[i].getValue());
            	System.out.println("Q:" + qualifier + "V:" + val);
            }

    	}catch(IOException e){
    		e.printStackTrace();
    	}finally{
    		pool.putTable(table);
    	}
		return custBean_List;

    }
    
    /*TBL_CUST_VIEW
     * 删除TBL_CUST_VIEW中指定行的某列
     * @tableName 表明
     * @custBean custBean
     */
    public static  boolean deleteCustViewColumn(String tableName,CustLabelBean custBean)
            throws Exception {
    	String rowKey = custBean.getRowKey();
    	
    	HTablePool pool = getHTablePool();
    	HTableInterface table = null;
    	Boolean result = true;
    	try{
            table = pool.getTable(tableName);  

            Delete deleteColumn = new Delete(Bytes.toBytes(rowKey));
            deleteColumn.deleteColumns(Bytes.toBytes(""),Bytes.toBytes(""));
            table.delete(deleteColumn);
    	}catch(IOException e){
    		e.printStackTrace();
    	}finally{
    		pool.putTable(table);
    	}
		return result;    	
    }
    
    
    public static void ScanData(String tableName) throws Exception{    	
    	HTablePool pool = getHTablePool();
    	HTableInterface table = null;
    	Boolean result = true;
    	
	    try {
	    	/**Test:测试 根据CNAME 模糊查询
			 * 
			 */
			System.out.println("queryLabel");

			List<Put> puts = new ArrayList<Put>();
			Put put = null;

            table = pool.getTable(tableName);  
			Scan scan = new Scan();
			//打印查询返回的数据
			ResultScanner rScanner = null;
			rScanner = table.getScanner(scan);
			scan.setMaxVersions();
			System.out.println("Scanner Started");
			List<CustLabelBean> rLabelBean_List = queryCustLabel(tableName,"LABEL_DESC","LABEL_CNAME","信用卡");
			for(CustLabelBean custBean : rLabelBean_List){
				System.out.println("###New Line###");
				System.out.println("[rowKey]" + custBean.getRowKey());
				System.out.println("[CNAME]" +custBean.getLABEL_CNAME());
				System.out.println("[ENAME]" + custBean.getLABEL_ENAME());
				System.out.println("[LEVEL]" + custBean.getLABEL_LEVEL());
				System.out.println("[TYPE]" + custBean.getLABEL_TYPE());
				System.out.println("[LABEL_ID]" + custBean.getLEVEL1_LABEL_ID());
				System.out.println(custBean.getLEVEL1_LABEL_NAME());

			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    
    /*
     * Get The Real rowKey
     * 
     */
    public static String getRealRowKey(KeyValue kv) {
		int rowlength = Bytes.toShort(kv.getBuffer(), kv.getOffset()+KeyValue.ROW_OFFSET);
		String rowKey = Bytes.toStringBinary(kv.getBuffer(), kv.getOffset()+KeyValue.ROW_OFFSET + Bytes.SIZEOF_SHORT, rowlength);
	    return rowKey;
	}
	

}
