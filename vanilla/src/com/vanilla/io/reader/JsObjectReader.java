package com.vanilla.io.reader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vanilla.data.datacolumn.Series;
import com.vanilla.data.dataframe.DataFrame;

public class JsObjectReader {
	
	/**
	 * read String version of JS object
	 * @param ObjString
	 * @return dataframe
	 */
	public DataFrame read(String ObjString) {
		if (ObjString == null || ObjString.length() == 1) {
			return null;
		}
		int start = 0;
		int end = 0;
		List<Series> serList= new ArrayList<Series>();
		for (int i = 0; i < ObjString.length(); i++) {
			if (ObjString.charAt(i) == '{') start = i;
			if (ObjString.charAt(i) == '}') {
				end = i;
				serList.add(parseRow(ObjString.substring(start, end + 1)));
				
			}
		}
		DataFrame df= new DataFrame(new ArrayList<String>(Arrays.asList(serList.get(0).getHeaderArray())));
		for (Series row: serList) {
			df.writeRow(row);
		}
		return df;
		
		
		
		
	}
	public Series parseRow(String s) {
		int hStart = -1;
		int hEnd = -1;
		int vStart = -1;
		int vEnd = -1;
		List<String> heads = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		for (int i = 0; i < s.length(); i++) {
			char curr = s.charAt(i);
			if (curr == '{' ) {
				hStart = i + 2;
				vEnd = i - 2;
			}
			else if (curr == ':') {
				hEnd = i - 2;
				vStart = i + 2;
				heads.add(s.substring(hStart, hEnd + 1));
			}
			else if (curr == ',' || curr == '}') {
				hStart = i + 2;
				vEnd = i - 2;
				values.add(s.substring(vStart, vEnd + 1));
			}
		}
		return new Series("", heads, values);
	} 

}
