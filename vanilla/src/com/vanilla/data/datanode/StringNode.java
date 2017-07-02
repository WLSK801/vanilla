package com.vanilla.data.datanode;

public class StringNode implements DataNode, Comparable<String>{
	
	private String key;
	
	private String value;

	public StringNode(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public int compareTo(String o) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getKey() {
		return key;
	}
	@Override
	public void setKey(String key) {
		this.key = key;
	}
	@Override
	public String getValue() {
		
		return value;
	}
	@Override
	public void setValue(String value) {
		this.value = (String) value;
	}



}
