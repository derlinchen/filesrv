package com.climber.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService(name = "FileService", // 暴露服务名称
	targetNamespace = "http://service.climber.com/"// 命名空间,一般是接口的包名倒序
)
public interface FileService {
	
	@WebMethod
	public String upload(@WebParam(name="filename") String filename, @WebParam(name="fileinfo") String fileinfo);
	
	@WebMethod
	public String uploadpath(@WebParam(name="filename") String filename, @WebParam(name="fileinfo") String fileinfo, @WebParam(name="filepath") String filepath);
	
}