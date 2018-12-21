package com.climber.service.imp;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.climber.service.FileService;
import com.climber.utils.FileUtils;

@WebService(serviceName = "FileService", // 与接口中指定的name一致
targetNamespace = "http://service.climber.com/", // 与接口中的命名空间一致,一般是接口的包名倒
endpointInterface = "com.climber.service.FileService"// 接口地址
)
@Component
public class FileServiceImp implements FileService {
	
	@Value("${file.address}")
	private String address;

	/**
	 * 上传文件, file文件字符串
	 */
	@Override
	public String upload(String filename, String fileinfo) {
		String rtv = FileUtils.decoderBase64File(filename, fileinfo, null);
		return rtv;
	}

	/**
	 * 上传文件, file文件字符串, filepath文件上传路径
	 */
	@Override
	public String uploadpath(String filename, String fileinfo, String filepath) {
		String rtv = FileUtils.decoderBase64File(filename, fileinfo, filepath);
		return rtv;
	}
	
}