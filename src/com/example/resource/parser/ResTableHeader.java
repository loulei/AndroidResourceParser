package com.example.resource.parser;

import com.example.resource.Utils;

public class ResTableHeader extends ResChunkHeader {
	
	public ResTableHeader(byte[] data, int offset) {
		super(data, offset);
		// TODO Auto-generated constructor stub
	}
	int packageCount;
	public void parse(){
		parseCustomHeader(data);
		byte[] u4 = new byte[4];
		System.arraycopy(data, offset, u4, 0, 4);
		packageCount = Utils.byteToInt(u4);
		StringBuilder builder = new StringBuilder();
		builder.append("type:").append(ResChunkHeader.ChunkType.getName(type)).append("\n")
			.append("header size:").append(headerSize).append("\n")
			.append("chunk size:").append(chunkSize).append("\n")
			.append("package number:").append(packageCount).append("\n");
		System.out.println(builder.toString());
	}

}
