package com.climber.action;

import java.io.File;
import java.io.FileInputStream;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import com.climber.utils.FileUtils;

public class FileServiceMain {

	public static void main(String[] args) {
		try {
			// 上传
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			Client client = dcf.createClient("http://localhost:8888/fileservice?wsdl");

			File file = new File("F:/1.png");

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
			String ss = client.invoke("uploadpath",
					new Object[] { file.getName(), FileUtils.encodeBase64File(fis), "sys/" })[0].toString();
			System.out.println(ss);
		} catch (java.lang.Exception e) {
			e.printStackTrace();
		}
	}

}
