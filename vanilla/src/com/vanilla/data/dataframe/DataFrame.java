package com.vanilla.data.dataframe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import com.vanilla.data.datacolumn.Series;

public class DataFrame {
	/*
	 * list to represent row
	 */
	private List<Series> rowList;
	/**
	 * store column name.
	 */
	
	private List<String> columnName;
	/**
	 * row length
	 */
	private int rowNum;
	
	/**
	 * column length
	 */
	private int colNum;
	
	public DataFrame() {
		rowList = new ArrayList<Series>();
		columnName = new ArrayList<String>();
		rowNum = 0;
		colNum = 0;
	}
	
	public DataFrame(List<String> columnName) {
		rowList = new ArrayList<Series>();
		this.columnName = columnName;
		rowNum = 0;
		colNum = columnName.size();
	}
	public DataFrame(String[][] data, List<String> col) {
		this(col);
		
		if (data == null || data.length < 1 || data[0].length < 1) {
			throw new IllegalArgumentException("Data Array invaild");
		}
		if (data[0].length != col.size()) {
			throw new IllegalArgumentException("column length mismatch");
		}
		
		for (int i = 0; i < data.length; i++) {
			Series cur= new Series((i + 1) + "",col, Arrays.asList(data[i]));
			rowList.add(cur);
		}
		rowNum = data.length;
		colNum = data[0].length;
	}
	
	public String getPosition(int r, int c) {
		if (r < 0 || c < 0 || r >= rowNum || c >= colNum) {
			return null;
		}
		else {
			return rowList.get(r).getValueByPosition(c);
		}
	}
	public void writeRow(List<String> row) {
		if (row.size() != colNum) {
			throw new IllegalArgumentException("row length have to equal to data frame column number"); 
		}
		Series cur= new Series(("a" + 1) + "", columnName , row);
		rowList.add(cur);
		rowNum++;
		
	}
	
	public Series getRow(int position) {
		if (position < 0  || position >= rowNum) {
			return null;
		}
		return rowList.get(position);	
	}
	
	public List<String> getColumn(String header) {
		if (!columnName.contains(header)) return null;
		List<String> result = new ArrayList<String>(rowNum);
		int position = rowList.get(0).getPositionByheader(header);
		for (int i = 0; i < rowNum; i++) {
			result.add(rowList.get(i).getValueByPosition(position));
		}
		return result;
		
	}
	public int getRowNum() {
		return rowNum;
	}
	
	public List<Series> getRowList() {
		return rowList;
		
	}
	
	
}
