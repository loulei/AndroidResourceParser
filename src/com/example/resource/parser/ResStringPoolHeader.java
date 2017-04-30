package com.example.resource.parser;

import java.util.ArrayList;
import java.util.List;

import com.example.resource.Utils;

public class ResStringPoolHeader extends ResChunkHeader {
	

	public ResStringPoolHeader(byte[] data, int offset) {
		super(data, offset);
		// TODO Auto-generated constructor stub
	}


	int stringNum;
	int stringStyleNum;
	int stringEncode;
	
	int stringOffset;
	int stringStyleOffset;
	
	String[] stringArray;
	String[] stringStyleArray;
	

	@Override
	public void parse() {
		// TODO Auto-generated method stub
		int rawOffset = offset;
		parseCustomHeader(data);
		byte[] u4 = new byte[4];
		
		System.arraycopy(data, offset, u4, 0, 4);
		offset+=4;
		stringNum = Utils.byteToInt(u4);
		
		System.arraycopy(data, offset, u4, 0, 4);
		offset+=4;
		stringStyleNum = Utils.byteToInt(u4);
		
		System.arraycopy(data, offset, u4, 0, 4);
		offset+=4;
		stringEncode = Utils.byteToInt(u4);
		
		System.arraycopy(data, offset, u4, 0, 4);
		offset+=4;
		stringOffset = Utils.byteToInt(u4);
		
		System.arraycopy(data, offset, u4, 0, 4);
		offset+=4;
		stringStyleOffset = Utils.byteToInt(u4);
		
		
		
		StringBuilder builder = new StringBuilder();
		builder.append("type:").append(ResChunkHeader.ChunkType.getName(type)).append("\n")
			.append("header size:").append(headerSize).append("\n")
			.append("chunk size:").append(chunkSize).append("\n")
			.append("string number:").append(stringNum).append("\n")
			.append("string style number:").append(stringStyleNum).append("\n")
			.append("string encode:").append(stringEncode == 0x00000100 ? "utf-8" : "unknown").append("\n")
			.append("string offset:").append(stringOffset).append("\n")
			.append("string style offset:").append(stringStyleOffset).append("\n");
		System.out.print(builder.toString());
		if(stringNum > 0){
			stringArray = new String[stringNum];
			for(int i=0; i<stringNum; i++){
				System.arraycopy(data, offset, u4, 0, 4);
				offset+=4;
				int itemStringOffset = Utils.byteToInt(u4);
				stringArray[i] = parseStringContent(data, rawOffset+stringOffset+itemStringOffset+2); //add 2 bytes string len
				System.out.println("string content:"+stringArray[i]);
			}
		}
		
		
		if(stringStyleNum > 0){
			stringStyleArray = new String[stringStyleNum];
			for(int i=0; i<stringStyleNum; i++){
				System.arraycopy(data, offset, u4, 0, 4);
				offset+=4;
				int itemStringOffset = Utils.byteToInt(u4);
				stringStyleArray[i] = parseStringContent(data, rawOffset+stringStyleOffset+itemStringOffset+2); //add 2 bytes string len
				System.out.println("string style content:"+stringStyleArray[i]);
			}
		}
		System.out.println();
	}

	private String parseStringContent(byte[] data, int offset){
//		byte[] u2 = new byte[2];
//		System.arraycopy(data, offset, u2, 0, 2);
//		offset+=2;
//		short len = Utils.byteToShort(u2);
//		System.out.println("len:"+len);
//		byte[] stringbytes = new byte[len];
//		System.arraycopy(data, offset, stringbytes, 0, len);
		int i=0;
		List<Byte> array = new ArrayList<Byte>();
		while(data[offset+i] != 0x00){
			array.add(data[offset+i]);
			i++;
		}
		array.add((byte)0x00);
		byte[] stringBytes = new byte[array.size()];
		for(int j=0; j<array.size(); j++){
			stringBytes[j] = array.get(j);
		}
		return new String(stringBytes);
	}
}

