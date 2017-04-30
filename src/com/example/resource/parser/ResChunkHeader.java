package com.example.resource.parser;

import com.example.resource.Utils;



public abstract class ResChunkHeader {
	
	public short type;
	public short headerSize;
	public int chunkSize;
	
	public byte[] data;
	public int offset;
	
	public ResChunkHeader(byte[] data, int offset){
		this.data = data;
		this.offset = offset;
	}
	
	protected void parseCustomHeader(byte[] data) {
		byte[] u2 = new byte[2];
		byte[] u4 = new byte[4];
		
		System.arraycopy(data, offset, u2, 0, 2);
		type = Utils.byteToShort(u2);
		
		System.arraycopy(data, offset+2, u2, 0, 2);
		headerSize = Utils.byteToShort(u2);
		
		System.arraycopy(data, offset+4, u4, 0, 4);
		chunkSize = Utils.byteToInt(u4);
		offset+=8;
	}
	
	public abstract void parse();
	
	public static enum ChunkType{
		RES_TYPE_NULL("RES_TYPE_NULL", 0x0000),
		RES_STRING_POOL_TYPE("RES_STRING_POOL_TYPE", 0x0001),
		RES_TABLE_TYPE("RES_TABLE_TYPE", 0x0002),
		RES_XML_TYPE("RES_XML_TYPE", 0x0003),
		
		// Chunk types in RES_XML_TYPE
	    RES_XML_FIRST_CHUNK_TYPE("RES_XML_FIRST_CHUNK_TYPE", 0x0100),
	    RES_XML_START_NAMESPACE_TYPE("RES_XML_START_NAMESPACE_TYPE", 0x0100),
	    RES_XML_END_NAMESPACE_TYPE("RES_XML_END_NAMESPACE_TYPE", 0x0101),
	    RES_XML_START_ELEMENT_TYPE("RES_XML_START_ELEMENT_TYPE", 0x0102),
	    RES_XML_END_ELEMENT_TYPE("RES_XML_END_ELEMENT_TYPE", 0x0103),
	    RES_XML_CDATA_TYPE("RES_XML_CDATA_TYPE", 0x0104),
	    RES_XML_LAST_CHUNK_TYPE("RES_XML_LAST_CHUNK_TYPE", 0x017f),
	    // This contains a uint32_t array mapping strings in the string
	    // pool back to resource identifiers.  It is optional.
	    RES_XML_RESOURCE_MAP_TYPE("RES_XML_RESOURCE_MAP_TYPE", 0x0180),

	    // Chunk types in RES_TABLE_TYPE
	    RES_TABLE_PACKAGE_TYPE("RES_TABLE_PACKAGE_TYPE", 0x0200),
	    RES_TABLE_TYPE_TYPE("RES_TABLE_TYPE_TYPE", 0x0201),
	    RES_TABLE_TYPE_SPEC_TYPE("RES_TABLE_TYPE_SPEC_TYPE", 0x0202);
		
		String name;
		int id;
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		private ChunkType(String name, int id){
			this.name = name;
			this.id = id;
		}
		
		public static String getName(int id){
			for (ChunkType chunkType : ChunkType.values()) {
				if(chunkType.getId() == id){
					return chunkType.getName();
				}
			}
			return null;
		}
	}
}
