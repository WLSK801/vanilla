package com.vanilla.supervised.regression.linear;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.vanilla.data.datacolumn.Series;
import com.vanilla.data.dataframe.DataFrame;
import com.vanilla.supervised.model.Model;

public class LinearRegression implements Model {
	private boolean isTrain;
	private double learningRate;
	private int outputColumn;
	Series modelParameter;
	
	public LinearRegression() {
		isTrain = false;
		learningRate = 0.0000001;
		outputColumn = 0;
	}
	/**
	 * The size of each step. Largger value will decrease running time,
	 * but may fail to coverage or even coverage
	 * @param alpha learningRate
	 */
	public void setLearningRate(int alpha) {
		learningRate = alpha;
	}
	
	public double getLearningRate() {
		return learningRate;
	}
	@Override
	public List<String> predict(DataFrame frame) {
		if (!isTrain) {
			throw new NullPointerException("Model does not exist");
		}
		List<String> res = new ArrayList<>(frame.getRowNum());
		System.out.println(modelParameter);
		System.out.println(modelParameter.getValueByPosition(0));
		System.out.println(modelParameter.getValueByPosition(1));
		for (Series series : frame.getRowList()) {
			Double sum = Double.parseDouble(modelParameter.getValueByPosition(0));
			for (int i  = 0; i < series.size(); i++) {
				String header = series.getHeader(i);
				sum += Double.parseDouble(series.getValueByPosition(i)) *
						Double.parseDouble(modelParameter.getValueByHeader(header));
			}
			res.add( "" + sum);
		}
		return res;
	}

	@Override
	public void train(DataFrame frame, int outputColumn) {
		this.outputColumn = outputColumn;
		String[][] data = frame.toArray();
		String[] output = new String[data.length];
		String[][] input = new String[data.length][data[0].length - 1];
		for (int i = 0; i < data.length; i++) {
			output[i] = data[i][outputColumn];
			for (int j = 0; j < input[0].length; j++) {
				if (j < outputColumn) {
					input[i][j] = data[i][j];
				}
				else {
					input[i][j] = data[i][j + 1];
				}
			}
			
		}
		// initialize parameter list;
		List<Double> parameters = new ArrayList<Double>(data[0].length);
		for (int k = 0; k < data[0].length; k++) {
			parameters.add(0.0);
		}
		Double oldCost = cost(parameters, input, output);

		update(parameters, input, output);
		Double newCost = cost(parameters, input, output);

		double minCost = Double.MAX_VALUE;
		int it = 0;
		while (it < 100000 && 
				oldCost - newCost > 0.000001) {
			System.out.println(Math.abs(oldCost - newCost));
			System.out.println(it);
			update(parameters, input, output);
			oldCost = newCost;
			newCost = cost(parameters, input, output);

			
			it++;
		}
		List<String> nameList = new ArrayList<String>();
		nameList.add("X0" + data.hashCode());
		int headPos = 0;
		for (String header : frame.getRow(0).getHeaderArray()) {
			if(headPos != outputColumn) 
				nameList.add(header);
			headPos++;
		}
		List<String> valueList = new ArrayList<String>(nameList.size());
		for (Double parameter : parameters) {
			valueList.add(String.valueOf(parameter));
		}
		modelParameter = new Series("parameter", nameList, valueList);
		isTrain = true;
		
	}
	private void updateParameter(List<Double> parameters, int pos, String[][] data, String[] output) {
		Double val = parameters.get(pos);
		Double sum = 0.0;
		int xNum = data.length;

		for (int i = 0; i < xNum; i++) {
			Double hval = 0.0;
			int j = 0;
			for (Double para : parameters) {
				if (j == 0) {
					hval += para;
				} 
				else {

					hval += para * Double.parseDouble(data[i][j - 1]);
				}
				j++;
			}
			if (pos == 0) 
				sum += hval - Double.parseDouble(output[i]);
			else 
				sum += (hval - Double.parseDouble(output[i])) * Double.parseDouble(data[i][pos - 1]);
						
		}
		Double currVal = val - learningRate  * sum / xNum;
		parameters.set(pos, currVal);
		
		
	}
	private void update(List<Double> parameters, String[][] data, String[] output) {
		for (int i = 0; i < parameters.size(); i++) {
			updateParameter(parameters, i, data, output);
		} 
	}
	private Double cost(List<Double> parameters, String[][] data, String[] output) {
		Double cost = 0.0;
		for (int i = 0; i < data.length; i++) {
			Double hx = parameters.get(0);
			for (int j = 0; j < data[0].length; j++) {
				hx += parameters.get(j) * Double.parseDouble(data[i][j]);
			}
			cost += (hx - Double.parseDouble(output[i])) * (hx - Double.parseDouble(output[i]));
		}
		cost = cost / (output.length << 1);
		return cost;
	}

}
