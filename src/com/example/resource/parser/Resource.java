package com.example.resource.parser;

import com.example.resource.Utils;

public class Resource {
	
	private byte[] data;

	public Resource(byte[] rawData){
		this.data = rawData;
	}
	
	public void parseHeader(int offset){
		byte[] data_byte = new byte[2];
		
		ResChunkHeader chunkHeader = null;
		System.arraycopy(data, offset, data_byte, 0, 2);
		short type = Utils.byteToShort(data_byte);
		switch (type) {
		case 0x0002:
			chunkHeader = new ResTableHeader(data, offset);
			break;
		case 0x0001:
			chunkHeader = new ResStringPoolHeader(data, offset);
			break;
		case 0x0000:
			chunkHeader = new ResNullHeader(data, offset);
			break;
		case 0x0200:
			chunkHeader = new ResTablePackageHeader(data, offset);
			break;
		default:
			break;
		}
		
		if(chunkHeader == null)
			return;
		chunkHeader.parse();
		if(chunkHeader.type == 0x0002){
			offset+=chunkHeader.headerSize;
		}else{
			offset+=chunkHeader.chunkSize;
		}
		
		
		if(chunkHeader.headerSize <= 0){
			return;
		}
		if(offset < data.length){
			parseHeader(offset);
		}
	}
}
