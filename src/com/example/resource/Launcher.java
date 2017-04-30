package com.example.resource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.example.resource.parser.Resource;

public class Launcher {

	public static void main(String[] args) {
		File file = new File("file/resources.arsc");
		try {
			FileInputStream fis = new FileInputStream(file);
			int len = 0;
			byte[] buffer = new byte[1024];
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			while((len=fis.read(buffer)) != -1){
				bos.write(buffer, 0, len);
			}
			bos.flush();
			byte[] rawData = bos.toByteArray();
			bos.close();
			fis.close();
			
			Resource resource = new Resource(rawData);
			resource.parseHeader(0);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
