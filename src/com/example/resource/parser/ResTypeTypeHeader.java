package com.example.resource.parser;

public class ResTypeTypeHeader extends ResChunkHeader {

	public ResTypeTypeHeader(byte[] data, int offset) {
		super(data, offset);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void parse() {
		// TODO Auto-generated method stub
		parseCustomHeader(data);
		StringBuilder builder = new StringBuilder();
		builder.append("type:").append(ResChunkHeader.ChunkType.getName(type))
				.append("\n").append("header size:").append(headerSize)
				.append("\n").append("chunk size:").append(chunkSize)
				.append("\n");
		System.out.println(builder.toString());
	}

}
