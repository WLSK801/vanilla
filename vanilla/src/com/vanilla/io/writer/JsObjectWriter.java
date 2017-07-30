package com.vanilla.io.writer;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vanilla.data.datacolumn.Series;
import com.vanilla.data.dataframe.DataFrame;

public class JsObjectWriter {
	public String write(DataFrame df) {
		StringBuilder sb = new StringBuilder();
		sb.append('[');
		int i = 0;
		for (Series ser : df.getRowList()) {
			sb.append(write(ser));
			if (i != df.getRowNum() - 1) sb.append(',');
			i++;
			
		}
		sb.append(']');
		return sb.toString();
	}
	
	public String write(Series series) {
		List<String> headers = new ArrayList<String>(Arrays.asList(series.getHeaderArray()));
		List<String> values = new ArrayList<String>(Arrays.asList(series.getValueArray()));
		StringBuilder sb = new StringBuilder();
		sb.append('{');
		for (int i = 0; i < headers.size(); i++) {
			sb.append('"');
			sb.append(headers.get(i));
			sb.append('"');
			sb.append(':');
			sb.append('"');
			sb.append(values.get(i));
			sb.append('"');
			if (i != headers.size() - 1) sb.append(',');
		}
		sb.append('}');
		return sb.toString();
	}
}
