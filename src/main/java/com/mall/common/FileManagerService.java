package com.mall.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManagerService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// 이미지 경로
//	public static final String FILE_UPLOAD_PATH = "D:\\project\\workspace\\images/";
	public static final String FILE_UPLOAD_PATH = "C:\\Users\\jinyo\\Desktop\\project\\workspace\\images/";
	
	public String saveFile(String userLoginId, MultipartFile file) {
		String directoryName = userLoginId + "_" + System.currentTimeMillis() + "/";
		String filePath = FILE_UPLOAD_PATH + directoryName;
		
		File directory = new File(filePath);
		
		// 디렉토리(폴더) 만들기 실패
		if (directory.mkdir() == false) {
			return null;
		}
		
		// 파일 업로드
		try {
			byte[] bytes = file.getBytes();
			Path path = Paths.get(filePath + file.getOriginalFilename());
			Files.write(path, bytes);
		} catch (IOException e) {
			logger.error("[file upload] 파일 업로드 실패. filePath : {}", filePath);
			return null;
		}
		
		// 이미지 url path 리턴
		return "/images/" + directoryName + file.getOriginalFilename();
	}
	
	public void deleteFile(String imagePath) {
		// 겹치는 /images/ 제거
		Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));
		
		if (Files.exists(path)) {
			try {
				// 이미지 삭제
				Files.delete(path);
			} catch (IOException e) {
				logger.error("[delete image] 이미지 삭제 실패. imagePath : {}", imagePath);
			}
			
			path = path.getParent();
			
			if (Files.exists(path)) {
				try {
					// 디렉토리(폴더) 삭제
					Files.delete(path);
				} catch (IOException e) {
					logger.error("[delete image] 디렉토리 삭제 실패. imagePath : {}", imagePath);
				}
			}
		}
	}

}
