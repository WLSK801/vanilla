package com.vanilla.io.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

import com.vanilla.data.dataframe.DataFrame;

/**
 * read csv and output as a data frame object
 * 
 * @author wucha
 * 
 */
public class CsvReader {

	public DataFrame readLocal(String url) {
		String line = "";
		String cvsSplitBy = ",";
		DataFrame result  = null;
		int lineNumber = 1;
		try (BufferedReader br = new BufferedReader(new FileReader(url))) {
			
			while ((line = br.readLine()) != null) {
				String[] values = line.split(cvsSplitBy);
				if (lineNumber == 1) {
					result = new DataFrame(Arrays.asList(values));
				}
				else {
					result.writeRow(Arrays.asList(values));
				} 
				lineNumber++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
