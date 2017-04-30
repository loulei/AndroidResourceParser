package com.example.resource.parser;

import com.example.resource.Utils;

public class ResTypeSpecHeader extends ResChunkHeader {
	
	byte id;
	String[] typeArray;
	int entryCount;
	
	public ResTypeSpecHeader(byte[] data, int offset, String[] typeArray) {
		super(data, offset);
		// TODO Auto-generated constructor stub
		this.typeArray = typeArray;
	}

	@Override
	public void parse() {
		// TODO Auto-generated method stub
		parseCustomHeader(data);
		id = data[offset];
		offset+=4;
		
		byte[] u4 = new byte[4];
		System.arraycopy(data, offset, u4, 0, 4);
		entryCount = Utils.byteToInt(u4);
		
		if(entryCount > 0){
			
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append("type:").append(ResChunkHeader.ChunkType.getName(type))
				.append("\n").append("header size:").append(headerSize)
				.append("\n").append("chunk size:").append(chunkSize)
				.append("\n").append("type:").append(typeArray[id])
				.append("\n").append("entry count:").append(entryCount)
				.append("\n");
		System.out.println(builder.toString());
	}

}
