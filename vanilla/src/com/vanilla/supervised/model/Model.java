package com.vanilla.supervised.model;

import java.util.List;

import com.vanilla.data.dataframe.DataFrame;

public interface Model {
	/**
	 * use input data to get output
	 * @param frame input data
	 * @return list of numeric result 
	 */
	public List<String> predict(DataFrame frame);
	
	/**
	 * Training data to get model
	 *  
	 * @param frame data source
	 * @param outputColumn the column where outcolumn data located
	 */
	public void train(DataFrame frame, int outputColumn);
	/**
	 * check model is sucessfully trained?
	 * @return trained?
	 */
	public boolean isTrain();
}
