package com.dao;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.HTablePool;
import org.apache.hadoop.hbase.client.HTableFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.FilterList.Operator;
import org.apache.hadoop.hbase.filter.RegexStringComparator;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.PoolMap.PoolType;

public class HBaseDao {
	private static Configuration conf = null;

	private final static int poolSize = 1000;
	private static HTablePool hTablePool = null;
	private static HTableFactory factory = null;
	
	
	/**
	 * 初始化配置
	 */
	static {
		conf = HBaseConfiguration.create();
		factory = new HTableFactory();
	}
	
	/**
	 * 获取数据库配置
	 */
	public static Configuration getHBaseConf(){
		return conf;
	}
	
	public static HTableFactory getFactory(){
		return factory;
	}
	
	/**
	 * 获取数据库配置
	 */
	public static HTablePool getHTablePool(){
		if(hTablePool == null){
			HTablePool pool = new HTablePool(getHBaseConf(),poolSize,getFactory(),PoolType.ThreadLocal);
			return pool;
		}else{
			return hTablePool;
		}
		
	}
	

}
