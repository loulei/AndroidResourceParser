package com.example.resource.parser;

import com.example.resource.Utils;

public class ResTablePackageHeader extends ResChunkHeader {
	
	int packageId;
	String packageName;
	int typeStringOffset;
	int typeStringNum;
	int keyStringOffset;
	int keyStringNum;

	public ResTablePackageHeader(byte[] data, int offset) {
		super(data, offset);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void parse() {
		// TODO Auto-generated method stub
		final int rawOffset = offset;
		parseCustomHeader(data);
		byte[] u4 = new byte[4];
		
		System.arraycopy(data, offset, u4, 0, 4);
		offset+=4;
		packageId = Utils.byteToInt(u4);
		
		byte[] u256 = new byte[256];
		System.arraycopy(data, offset, u256, 0, 256);
		offset+=256;
		StringBuilder pkgBuilder = new StringBuilder();
		for(int i=0; i<u256.length; i+=2){
			pkgBuilder.append((char)u256[i]);
		}
		packageName = pkgBuilder.toString();
		
		System.arraycopy(data, offset, u4, 0, 4);
		offset+=4;
		typeStringOffset = Utils.byteToInt(u4);
		
		System.arraycopy(data, offset, u4, 0, 4);
		offset+=4;
		typeStringNum = Utils.byteToInt(u4);
		
		System.arraycopy(data, offset, u4, 0, 4);
		offset+=4;
		keyStringOffset = Utils.byteToInt(u4);
		
		System.arraycopy(data, offset, u4, 0, 4);
		offset+=4;
		keyStringNum = Utils.byteToInt(u4);
		
		
		StringBuilder builder = new StringBuilder();
		builder.append("type:").append(ResChunkHeader.ChunkType.getName(type))
			.append("\n").append("header size:").append(headerSize)
			.append("\n").append("chunk size:").append(chunkSize)
			.append("\n").append("package id:").append("0x").append(Integer.toHexString(packageId))
			.append("\n").append("package:").append(packageName)
			.append("\n").append("type Strings offset:").append(typeStringOffset)
			.append("\n").append("type Strings number:").append(typeStringNum)
			.append("\n").append("key Strings offset:").append(keyStringOffset)
			.append("\n").append("key Strings number:").append(keyStringNum)
			.append("\n");
		System.out.println(builder.toString());
		System.out.println("type string");
		ResStringPoolHeader typeStringHeader = new ResStringPoolHeader(data, rawOffset+typeStringOffset);
		typeStringHeader.parse();
		System.out.println("key string");
		ResStringPoolHeader keyStringHeader = new ResStringPoolHeader(data, rawOffset+keyStringOffset);
		keyStringHeader.parse();
		offset = rawOffset+keyStringOffset+keyStringHeader.chunkSize;
		
		byte[] data_type = new byte[2];
		while(offset < rawOffset+chunkSize){
			ResChunkHeader chunkHeader = null;
			System.arraycopy(data, offset, data_type, 0, 2);
			short type = Utils.byteToShort(data_type);
			switch (type) {
			case 0x0202:
				chunkHeader = new ResTypeSpecHeader(data, offset, typeStringHeader.stringArray);
				break;
	
			default:
				break;
			}
			
			if(chunkHeader == null)
				return;
			chunkHeader.parse();
			offset+=chunkHeader.chunkSize;
		}
	}

}
