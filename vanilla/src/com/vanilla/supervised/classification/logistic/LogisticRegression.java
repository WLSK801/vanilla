package com.vanilla.supervised.classification.logistic;

import java.util.ArrayList;
import java.util.List;

import com.vanilla.data.datacolumn.Series;
import com.vanilla.data.dataframe.DataFrame;
import com.vanilla.supervised.model.Model;

public class LogisticRegression implements Model {
	private double rate;
	private Series parameters;
	private boolean isTrain;

	public LogisticRegression() {
		rate = 0.01;
		isTrain = false;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	@Override
	public List<String> predict(DataFrame frame) {
		if (!isTrain) {
			throw new NullPointerException("Model does not exist");
		}
		System.out.println(parameters);
		List<String> res = new ArrayList<>(frame.getRowNum());
		for (Series series : frame.getRowList()) {
			String value = "" + hypothesis(series);
			res.add(value);
		}
		return res;
	}

	@Override
	public void train(DataFrame frame, int outputColumn) {
		List<String> output = frame.remove(outputColumn);
		List<String> headers = new ArrayList<>();
		List<String> values = new ArrayList<>();
		headers.add("x0" + output.hashCode());
		values.add("0.0");
		for (String header : frame.getRow(0).getHeaderArray()) {
			headers.add(header);
			values.add("0.0");
		}
		parameters = new Series("logistic", headers, values);
		//double pastCost = cost(frame, output);
		update(frame, output);
		//double currCost = cost(frame, output);
		int it = 0;
		while (it <= 40000) {
			//System.out.println(currCost);
			//pastCost = currCost;
			update(frame, output);
			//currCost = cost(frame, output);
			//if (it % 1000 == 0) System.out.println(cost(frame, output));
			it++;
		}
		System.out.println(cost(frame, output));
		isTrain = true;

	}

	private void update(DataFrame frame, List<String> output) {		

		
		for (int i = 0; i < parameters.size(); i++) {
			double currParam = Double.parseDouble(parameters
					.getValueByPosition(i));
			double sum = 0.0;
			int j = 0;
			for (Series ser : frame.getRowList()) {
				double currY = Double.parseDouble(output.get(j));
				if (i == 0) {
					//x0
					sum += (hypothesis(ser) - currY);
				}
				else {
					//x1.....xn
					sum += (hypothesis(ser) - currY)
							* Double.parseDouble(ser.getValueByPosition(i - 1));
				}
				
				j++;
			}
			double newParam = currParam - sum * rate / output.size();
			//System.out.println(newParam);
			parameters.setValue(i, "" + newParam);
		}
	}

	private double sigmoid(double z) {
		return 1.0 / (1.0 + Math.exp(-z));
	}

	private double hypothesis(Series values) {
		double res = Double.parseDouble(parameters.getValueByPosition(0));
		for (String head : values.getHeaderArray()) {
			res += Double.parseDouble(values.getValueByHeader(head))
					* Double.parseDouble(parameters.getValueByHeader(head));
		}
		return sigmoid(res);
	}

	private double cost(DataFrame frame, List<String> output) {
		double cost = 0.0;
		int i = 0;
		for (Series ser : frame.getRowList()) {
			double currY = Double.parseDouble(output.get(i));
			cost += currY * Math.log(hypothesis(ser)) + (1 - currY)
					* Math.log(1 - hypothesis(ser));
			i++;
		}
		return -1 * cost / (i + 1);

	}

	@Override
	public boolean isTrain() {

		return isTrain;
	}

}
