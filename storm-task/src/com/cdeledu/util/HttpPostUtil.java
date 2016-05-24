package com.cdeledu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.log4j.Logger;

/** 
 * @ClassName: HttpPostUtil 
 * @Description: POST请求工具类
 * @author lxm
 * @date 2014-1-22 上午9:32:42 
 * 
 */
public class HttpPostUtil {

	/**
	 * @Title: httpPost
	 * @description post请求
	 * @version 1.0
	 * @author lxm
	 * @update 2014-1-22 上午9:32:42
	 */
	private static Logger logger = Logger.getLogger(HttpPostUtil.class);

	public static String httpPost(HttpServletRequest request, String serverURL, String param, String encoding, int so_timeout, int connection_timeout,String sessionId) {
		String strBack = null;
		
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		StringEntity stringEntity = null;
		HttpResponse httpResponse = null;
		HttpEntity httpEntity = null;
		BufferedReader br = null;
		
		try{
			httpClient = new DefaultHttpClient();
			httpClient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, so_timeout).setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, connection_timeout);
			httpPost = new HttpPost(serverURL);
			stringEntity = new StringEntity(param);
			stringEntity.setContentType("application/x-www-form-urlencoded");
			
			// 设置X-Forwarded-For头部信息
			if(request != null){ 
//				httpPost.setHeader("X-Forwarded-For", getCustomerIP(request));
				httpPost.setHeader("referer", request.getHeader("referer"));
			}else{
				httpPost.setHeader("referer", "smssendjob");
			}
			if(sessionId != null){
				httpPost.setHeader("Cookie", "JSESSIONID=" + sessionId);
			}
			httpPost.setEntity(stringEntity);
			httpResponse = httpClient.execute(httpPost);
			httpEntity = httpResponse.getEntity();
			br = new BufferedReader(new InputStreamReader(httpEntity.getContent(),encoding));
			StringBuffer backData = new StringBuffer();
			String line = null;
			while((line = br.readLine())!=null){
				backData.append(line);
			} 
			strBack = backData.toString();
			logger.debug("---> post to: "+serverURL);
			logger.debug("---> data is: "+param);
			logger.debug("---> back data is: "+backData);
		}catch(SocketTimeoutException e){
			logger.error(e + " serverURL={ "+serverURL+", "+param+" }");
		}catch(ConnectTimeoutException e){
			logger.error(e + " serverURL={ "+serverURL+", "+param+" }");
		}catch(HttpHostConnectException e){
			logger.error(e + " serverURL={ "+serverURL+", "+param+" }");
		}catch(UnknownHostException e){
			logger.error(e + " serverURL={ "+serverURL+", "+param+" }");
		}catch(SocketException e){
			logger.error(e + " serverURL={ "+serverURL+", "+param+" }");
		}catch(Exception e){
			logger.error(e + " serverURL={ "+serverURL+", "+param+" }");
			e.printStackTrace();
		}finally{
			//1.关闭BufferedReader
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//2.关闭StringEntity
			stringEntity = null;
			//3.关闭HttpEntity
			if(httpEntity != null){
				try {
					httpEntity.consumeContent();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//4.关闭HttpResponse
			httpResponse = null;
			//5.释放HttpPost
			if(httpPost != null)
				httpPost.abort();
			//6.关闭HttpClient
			if(httpClient != null)
				httpClient.getConnectionManager().shutdown();
		}
		return strBack;
	}
	public static String httpPost(HttpServletRequest request, String serverURL, String param, String encoding, int so_timeout, int connection_timeout){
		return httpPost(request,  serverURL,  param,  encoding,  so_timeout,  connection_timeout, null) ;
	}
	public static String httpPost(HttpServletRequest request, String serverURL, String param, String encoding) {
		return httpPost(request, serverURL, param, encoding, 5000, 5000);
	}
	public static String httpPost(HttpServletRequest request, String serverURL, String param, String encoding,String sessionId) {
		return httpPost(request, serverURL, param, encoding, 5000, 5000,sessionId);
	}
	public static String httpPost(String serverURL, String param, String encoding, int so_timeout, int connection_timeout) {
		return httpPost(null, serverURL, param, encoding, so_timeout, connection_timeout);
	}
	
	public static String httpPost(String serverURL, String param, String encoding) {
		return httpPost(serverURL, param, encoding, 5000, 5000);
	}
	
	/**
     * 获取客户端ip
     * @param request
     * @return
     */
    public static String getCustomerIP(HttpServletRequest request) {
        String userip = request.getHeader("forwarded"); //格式 Forwarded: for=192.0.2.43,for=[2001:db8:cafe::17],for=unknown
        if(userip != null && userip.length() != 0 && !"unknown".equalsIgnoreCase(userip)) {           
            if (userip.indexOf(",") > 0) //如果有多个ip，则取第一个ip
            {
                String userips[] = userip.split(",");
                for (int i = 0; i < userips.length; i++) {
                    if(userips[i].split("=")[0].equals("for") && userips[i].split("=")[1].indexOf(".")>0){//判断ip格式
                        userip = userips[i].split("=")[1];
                    }
                }
            } 
            else
            {
                if(userip.split("=")[0].equals("for") && userip.split("=")[1].indexOf(".")>0){//判断ip格式
                    userip = userip.split("=")[1];
                } else {
                    userip = null;
                }
            }
        } 
        //查询代理
        if(userip == null || userip.length() == 0 || "unknown".equalsIgnoreCase(userip)) {           
            String userip_x = request.getHeader("x-forwarded-for");   //格式X-Forwarded-For: client1, proxy1, proxy2
            if(userip_x != null && userip_x.length() != 0 && !"unknown".equalsIgnoreCase(userip_x)) {    
                if (userip_x.indexOf(",") > 0){//如果有多个
                    //暂时去掉下面的方式
                    //userip = request.getHeader("Client-IP");
                    if(userip == null || userip.length() == 0 || "unknown".equalsIgnoreCase(userip)) {
                        try{
                            userip = userip_x.split(",")[0];//取第一个
                        } catch (Exception e){
                        }
                    }
                } else {
                    userip = userip_x;
                }
            }
        }
        if(userip == null || userip.length() == 0 || "unknown".equalsIgnoreCase(userip)) {
            userip = request.getHeader("Client-IP");
        } 
        if(userip == null || userip.length() == 0 || "unknown".equalsIgnoreCase(userip)) {           
            userip = request.getHeader("Proxy-Client-IP");       
        }       
        if(userip == null || userip.length() == 0 || "unknown".equalsIgnoreCase(userip)) {           
            userip = request.getHeader("WL-Proxy-Client-IP");       
        }       
        if(userip == null || userip.length() == 0 || "unknown".equalsIgnoreCase(userip)) {           
            userip = request.getRemoteAddr();  
        }  
        //如果代理IP是局域网IP
        if ((userip.substring(0, 2) == "0.") || (userip.substring(0, 3) == "10.") || (userip.substring(0, 4) == "127.") || (userip.substring(0, 4) == "192.") || (userip.substring(0, 4) == "172."))
        {
            userip = request.getRemoteAddr(); 
        }
        return userip;
    }

	public static void main(String[] args) throws Exception
	{
		// TODO Auto-generated method stub
//		Map<String, String> map = new HashMap<String, String>();
//		long nowTime = new Date().getTime();
//		long time = Long.valueOf(nowTime);
//		System.out.println("=-============"+new Date());
//		String privateKey = "fJ3UjIFyTu";
//		String serverURL = "http://www.chinatet.com/op/api/openCourse/openCourse.shtm";
//		String pkey = pkey(privateKey+time+"chinatet", null);
//		String encoding = "UTF-8";
//		String userID = "26980950";
//		String userName = URLEncoder.encode("wyfcdel6");
//		String realName = URLEncoder.encode("雷雯正保");
//		String mobile = "13811589240";
//		String param = "userID="+userID+"&wareID=414354&userName="+userName+"&realName="+realName+"&sex=0&mobile="+mobile+"&key="+pkey+"&nowTime="+time;
//		httpPost(serverURL, param, encoding);
	}

}
