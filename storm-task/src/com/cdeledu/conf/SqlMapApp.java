package com.cdeledu.conf;


import java.io.IOException;
import java.io.Reader;
import java.util.Properties;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
/**
 * 
 * <p>初始化数据库配置</p>
 * <ul>
 *	 <li>加载数据源，教务库</li>
 * </ul>
 * @author yangzhenping
 * Create at:2016年4月29日 上午10:48:40
 */
public class SqlMapApp {
	
	public static SqlMapClient accJiaoWuClient = null;
	
	
	static {
		try {
			Properties props = getProperties("main");
			accJiaoWuClient = buildClient("sql-map-storm-accjiaowu.xml", props);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static SqlMapClient buildClient(String fileName, Properties props) throws IOException{
		
		return SqlMapClientBuilder.buildSqlMapClient(getReader(fileName), props);
	}
	
	private static Properties getProperties(String conf) throws IOException{
		
		return Resources.getResourceAsProperties("config_"+ conf +".properties");
	}
	
	private static Reader getReader(String fileName) throws IOException{
		
		return Resources.getResourceAsReader(fileName);
	}
	
	public static SqlMapClient getAccJiaoWuClient() {
	    return accJiaoWuClient;
	}
	
}