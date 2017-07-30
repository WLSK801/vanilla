package com.vanilla.data.datacolumn;
/**
 * series class
 * @author wlsk801
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.vanilla.data.datanode.DataNode;
import com.vanilla.data.datanode.StringNode;

public class Series {
	
	
	private String name;
	
	
	private List<DataNode> orderedData;
	
	/**
	 * creat empty series
	 */
	public Series() {
		name = "";
		
		orderedData = new ArrayList<DataNode>();
	}
	/**
	 * create Series with element
	 * @param serName name
	 * @param headers headers list
	 * @param values value list
	 */
	public Series(String serName, List<String> headers, List<String> values) {
		if (headers.size() != values.size()) {
			throw new IllegalArgumentException("header size must equal to values size");
		}
		if (checkDuplicateHeader(headers)) {
			throw new IllegalArgumentException("header cannot contain duplicate value");
		}
		name = serName;
		orderedData = new ArrayList<DataNode>();
		for (int i = 0; i < headers.size(); i++) {
			DataNode node = new StringNode(headers.get(i), values.get(i));
			orderedData.add(node);
		}
	}
	
	/**
	 * check whether have a given header
	 * @param headers
	 * @return have or not. ture means have
	 */
	private boolean checkDuplicateHeader(List<String> headers) {
		Set<String> set = new HashSet<String>(headers);

		if(set.size() < headers.size()){
		    return true;
		}
		else {
			return false;
		}
		
	}
	/**
	 * check whether contain given header
	 * @param headers
	 * @return result
	 */
	public boolean containsHeader(String header) {
		boolean result = false;
		for (int i = 0; i < orderedData.size(); i++) {
			if(orderedData.get(i).getKey().equals(header)) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	/**
	 * get postion information by a given header
	 * @param header
	 * @return
	 */
	public int getPositionByheader(String header) {
		
		int result = -1;
		for (int i = 0; i < orderedData.size(); i++) {
			if(orderedData.get(i).getKey().equals(header)) {
				result = i;
				break;
			}
		}
		return result;
	}
	
	
	/**
	 * using a header to find its value, return null if it do not exist
	 * @param header
	 * @return result
	 */
	public String getValueByHeader(String header) {
		if (!containsHeader(header)) {
			return null;
		}
		int pos = getPositionByheader(header);
		return orderedData.get(pos).getValue();
	}
	/**
	 * using a header to find its value, return null if it do not exist
	 * @param header
	 * @return result
	 */
	public String getValueByPosition(int position) {
		if (position >= size() || position < 0) {
			throw new IndexOutOfBoundsException("Positon only in: 0 - " + (size() - 1));
		}
		return orderedData.get(position).getValue();
	}
	/**
	 * set value for head, if header exist change it to new value.
	 * if not exist creat new header.
	 * 
	 * @param header
	 * @param value
	 */
	public void setValue(String header, String value) {
		if (containsHeader(header)) {
			int pos = getPositionByheader(header);
			orderedData.get(pos).setValue(value);
		}
		else {
			DataNode node = new StringNode(header, value);
			orderedData.add(node);
		}
	}
	/**
	 * set value in specific postion, only allow 0 to list length
	 * if index equal to list length, add new element in the end of list 
	 * with header: "Head {position}"
	 * @param index
	 * @param value
	 */
	
	public void setValue(int index, String value) {
		if (index > orderedData.size() + 1 || index < 0) {
			throw new IndexOutOfBoundsException("index error only allow 0 to list length");
		}
		if (index == orderedData.size()) {
			
			String defaultHeader = "Head " + (index + 1);
			if (containsHeader(defaultHeader)) {
				throw new IllegalArgumentException("Default header cannot be generated, please use setValue(header, value)");
			}
			else {
				orderedData.add(new StringNode(defaultHeader, value));
				
			}
		}
		else {
			orderedData.get(index).setValue(value);
		}
		
	}
	/**
	 * get the length of the Series
	 * @return
	 */
	public int size() {
		return orderedData.size();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * set headers by a list
	 * 
	 * @param headerList
	 */
	public void setHeaders(List<String> headerList) {
		if (headerList.size() != orderedData.size()) {
			throw new IllegalArgumentException("headers list size must equal to series size");
		}
		if (checkDuplicateHeader(headerList)) {
			throw new IllegalArgumentException("header cannot contain duplicate value");
		}
		for (int i = 0; i < headerList.size(); i++) {
			orderedData.get(i).setKey(headerList.get(i));
		}
	}

	public List<DataNode> getOrderedData() {
		return new ArrayList<DataNode>(orderedData);
	}
	
	public String[] getHeaderArray() {
		String[] result = new String[size()];
		int i = 0;
		for (DataNode node : orderedData) {
			result[i++] = node.getKey();
		}
		
		return result;
	}
	public Object[] toArray() {
		Object[] res = new Object[size()];
		int i = 0;
		for (DataNode node : orderedData) {
			res[i++] = node.getValue();
		}
		return res;
		
	}
	public String[] getValueArray() {
		String[] res = new String[size()];
		int i = 0;
		for (DataNode node : orderedData) {
			res[i++] = node.getValue();
		}
		return res;
		
	}
	/**
	 * set values by a list
	 * @param input
	 */
	public void setOrderedData(List<String> input) {
		if (input.size() != orderedData.size()) {
			throw new IllegalArgumentException("map size must equal to list size");
		}
		for (int i = 0; i < input.size(); i++) {
			orderedData.get(i).setValue(input.get(i));
		}
	}
	
	/**
	 * remove a value by its header
	 * 
	 * @param header
	 * @return that DataNode
	 */
	public DataNode remove(String header) {
		if (!containsHeader(header)) {
			return null;
		}
		int pos = getPositionByheader(header);
		return orderedData.remove(pos);
	}
	public String getHeader(int position) {
		return orderedData.get(position).getKey();
	}
	public DataNode remove(int position) {
		if (position < 0 || position >= size()) {
			return null;
		}
		return orderedData.remove(position);
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (DataNode node : orderedData) {
			sb.append("{");
			sb.append(node.getKey());
			sb.append(":");
			sb.append(node.getValue());
			sb.append("}");
		}
		sb.append("]");
		return sb.toString();
		
	}
}
