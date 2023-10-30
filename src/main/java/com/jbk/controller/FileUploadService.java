package com.jbk.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadService {
	
	public static String saveFile(String fileName,MultipartFile multipartfile)throws IOException{
		Path uploadPath = Paths.get("Files-Upload");
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		String fileCode=RandomStringUtils.randomAlphanumeric(8);
		
		try (InputStream inputstream=multipartfile.getInputStream()){
			Path filePath = uploadPath.resolve(fileCode+"-"+fileName);
			Files.copy(inputstream, filePath,StandardCopyOption.REPLACE_EXISTING);
			
		} catch (IOException e) {
			throw new IOException("Could not save file:"+fileName,e);
		}
		return fileCode;
		
	}
}
