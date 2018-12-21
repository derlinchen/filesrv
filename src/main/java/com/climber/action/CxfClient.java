package com.climber.action;

import java.io.File;
import java.io.FileInputStream;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import com.climber.utils.FileUtils;


public class CxfClient {

	public static void main(String[] args) {
		sayHello();
	}

	/**
	 * 动态调用方式
	 */
	public static void sayHello() {
		
		try {
//			long start = System.currentTimeMillis();
			// 上传
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//			Client client = dcf.createClient("http://127.0.0.1:8888/fileservice?wsdl");
			Client client = dcf.createClient("http://localhost:8080/filesrv/fileservice?wsdl");
//			Client client = dcf.createClient("http://49.4.149.43:8888/filesrv/fileservice?wsdl");
//			Client client = dcf.createClient("http://121.43.180.239:9000/climber?wsdl");
			
//			HTTPConduit http = (HTTPConduit) client.getConduit();
//			HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();        
//      httpClientPolicy.setConnectionTimeout(120000);  //连接超时      
//      httpClientPolicy.setAllowChunking(false);    //取消块编码   
//      httpClientPolicy.setReceiveTimeout(120000);     //响应超时  
//      http.setClient(httpClientPolicy);  
      
//			Client client = dcf.createClient("http://localhost:8888/fileservice?wsdl");
			
			File file = new File("F:/哈哈.jpg");
			
			if (!file.exists()) {
				// 判断文件上一级是否有文件夹，没有文件夹，先创建文件夹
				if (!file.getParentFile().exists()) {
					// 按照路径创建所有文件夹
					file.getParentFile().mkdirs();
				}
				// 创建文件
				file.createNewFile();
			}
			
			FileInputStream fis = new FileInputStream(file);
//			client.invoke("uploadpath", new Object[]{file.getName(), FileUtils.encodeBase64File(fis),"sys/"});
			String ss = client.invoke("uploadpath", new Object[]{file.getName(), FileUtils.encodeBase64File(fis), "sys/"})[0].toString();
//			String ss = client.invoke("uploadPath", new Object[]{"哈哈.jpg", "sdsds", FileUtils.encodeBase64File(fis)})[0].toString();
//			String ob =  client.invoke("insect_rec", new Object[]{"http://121.43.180.239:8888/climber/dataimage/150826010407120181016035314.jpg"})[0].toString();
//			long end = System.currentTimeMillis();
//			System.out.println(ob);
//			System.out.println("耗时：" +(end - start)/1000);
//			byte[] buffer = new byte[fis.available()];
//			fis.read(buffer);
//			String ss1 = new BASE64Encoder().encode(buffer);
//			String ss = client.invoke("uploadCollect", new Object[]{"1.jpg", "{\"filepath\":\"dataimage/\"," + "\"filecontent\":\"" + ss1 + "\"}"})[0].toString();
			System.out.println(ss);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
//		
//		try {
//			// 上传
//			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//			Client client = dcf.createClient("http://localhost:8888/filesrv/fileservice?wsdl");
//			String ss = client.invoke("upload", new Object[]{"1.png", FileUtils.encodeBase64File("F:/1.png")})[0].toString();
//			System.out.println(ss);
//		} catch (java.lang.Exception e) {
//			e.printStackTrace();
//		}
//		
//		try {
//			// 下载
//			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//			Client client = dcf.createClient("http://localhost:8888/fileservice?wsdl");
//			String ss = client.invoke("download", new Object[]{"哈哈_20180418095229.jpg"})[0].toString();
//			byte[] buffer = new BASE64Decoder().decodeBuffer(ss);
//			FileOutputStream out = new FileOutputStream("E:/1.jpg");
//			out.write(buffer);
//			out.close();
//		} catch (java.lang.Exception e) {
//			e.printStackTrace();
//		}
		
		
//		try {
//			// 下载
//			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
//			Client client = dcf.createClient("http://192.168.99.103:8888/filesrv/fileservice?wsdl");
//			String returnvalue = client.invoke("download", new Object[]{"哈哈_20180418095229.jpg"})[0].toString();
//			JsonObject result = new JsonParser().parse(returnvalue).getAsJsonObject();
//			if (result.get("success").getAsBoolean()) {
//				String filecontent = result.get("filecontent").getAsString();
//				byte[] buffer = new BASE64Decoder().decodeBuffer(filecontent);
//				FileOutputStream out = new FileOutputStream("E:/1.jpg");
//				out.write(buffer);
//				out.close();
//			}
//		} catch (java.lang.Exception e) {
//			e.printStackTrace();
//		}
		
		
	}
}
