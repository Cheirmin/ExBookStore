package com.cheirmin.util;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;

public class HttpUtil {
	//获取响应体
	public String GetHttp(String url,int mark) {
		// 获取一页带音乐id的json数据
		StringBuffer body=new StringBuffer();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet HttpGet = new HttpGet(url);
		if(mark==1) {
			// 设置请求头
			HttpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.132 Safari/537.36");
			HttpGet.setHeader("Host", "book.feelyou.top");
		}
		CloseableHttpResponse response;
		try {
			response = httpClient.execute(HttpGet);
			InputStream inputStream = response.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
			String s = "";
			while ((s = reader.readLine()) != null) {
				body.append(s);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return body.toString();
	}

	//下载音乐
	public void DownLoadMusic(String url, String musicName) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet HttpGet = new HttpGet(url);
		CloseableHttpResponse response;
		InputStream inputStream = null;
		FileOutputStream outputStream = null;
		try {
			response = httpClient.execute(HttpGet);
			inputStream = response.getEntity().getContent();
			// 创建输出流
			File file = new File("C:/" + musicName + ".mp3");
			outputStream = new FileOutputStream(file);
			// 创建缓冲区
			byte[] buffer = new byte[1024*1024];
			int temp;
			while ((temp = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer,0,temp);
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
