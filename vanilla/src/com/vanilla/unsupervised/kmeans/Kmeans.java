package com.vanilla.unsupervised.kmeans;

import java.util.*;

import com.vanilla.data.datacolumn.Series;
import com.vanilla.data.dataframe.DataFrame;

public class Kmeans {

	public List<Integer> process(DataFrame frame, int clusters) {
		double minCost = Double.MAX_VALUE;
		List<Integer> finalAssign = null;
		for (int test = 0; test < 5; test++) {
			int[] originalPositions = getRandomCentroids(clusters, frame.getRowNum());
			Series[] originalSeries = new Series[clusters];
			List<Series> rowList = frame.getRowList();
			for (int i = 0; i < clusters; i++) {
				originalSeries[i] = rowList.get(originalPositions[i]);
			}
			List<Integer> originalAssign = assignCentroid(rowList, originalSeries);
			Series[] newSeries = getMeans(originalAssign, rowList, clusters);
			int maxite = 100;
			//&& !
			while (maxite > 0 && !testEnd(originalSeries, newSeries)) {
				
				originalAssign = assignCentroid(rowList, newSeries);
				originalSeries = newSeries;
				newSeries = getMeans(originalAssign, rowList, clusters);
				
				maxite--;
			}
			double currCost = cost(newSeries, rowList, originalAssign);
			if (currCost < minCost) {
				finalAssign = originalAssign;
				minCost = currCost; 
			}
			
		}
		System.out.println(minCost);
		
		return finalAssign;
		
	}
	public int[] getRandomCentroids(int clusters, int dataLength) {
		Random random = new Random();
		Set<Integer> resultSet = new HashSet<Integer>();
		int[] result = new int[clusters];
		while (resultSet.size() < clusters) {
			resultSet.add(random.nextInt(dataLength));
		}
		int i = 0;
		for (Integer ele : resultSet) {
			result[i++] = ele;
		}
		
		return result;
		
	}
	
	public double getEuclideanDistance(Series a, Series b) {
		int len = a.size();
		double sum = 0;
		for (int i = 0; i < len; i++) {
			double aValue = Double.parseDouble(a.getValueByPosition(i));
			double bValue = Double.parseDouble(b.getValueByPosition(i));
			sum += (aValue - bValue) * (aValue - bValue);
		}
		return Math.sqrt(sum);
	}
	
	public List<Integer> assignCentroid(List<Series> serList, Series[] clusters) {
		List<Integer> resultList = new ArrayList<Integer>(serList.size());

		for (int i = 0; i < serList.size(); i++) {
			int minPos = -1;
			Double minValue = Double.MAX_VALUE;
			for (int j = 0; j < clusters.length; j++) {
				Double curr = getEuclideanDistance(serList.get(i), clusters[j]);
				if (curr < minValue) {
					minValue = curr;
					minPos = j;
				}
			}
			resultList.add(minPos);
		}
		
		return resultList;
	}
	
	public Series[] getMeans(List<Integer> clustersAssign, List<Series> serList, int clusters) {
		List<List<Series>> clustersList = new ArrayList<List<Series>>(clusters);
		for (int i = 0; i < clusters; i++) {
			clustersList.add(new ArrayList<Series>());
		}
		for (int i = 0; i < clustersAssign.size(); i++) {
			clustersList.get(clustersAssign.get(i)).add(serList.get(i));
			
		}
		Series[] result = new Series[clustersList.size()];
		int serSize = clustersList.get(0).get(0).size();
		for (int i = 0; i < clustersList.size(); i++) {
			List<Series> curList = clustersList.get(i);
			List<String> values = new ArrayList<String>(serSize);
			List<String> headers = Arrays.asList(curList.get(0).getHeaderArray());
			for (int j = 0; j < serSize; j++) {
				double sum = 0;
				for (int k = 0; k < curList.size(); k++) {
					sum += Double.parseDouble(curList.get(k).getValueByPosition(j));
				}
				values.add((sum / curList.size()) + "");
			}
			result[i] = new Series(i +"", headers, values);
		}
		
		return result;
	}
	public boolean testEnd(Series[] originalSeries, Series[] newSeries) {
		System.out.println("print");
		double sum = 0;
		for (int i = 0; i < originalSeries.length; i++) {
			sum += getEuclideanDistance(originalSeries[i], newSeries[i]);
		}
		if (sum < 0.001) {
			return true;
		}
		else {
			return false;
		}
	}
	public double cost(Series[] means, List<Series> serList, List<Integer> assigns) {
		double result = 0.0;
		for (int i = 0; i < serList.size(); i++) {
			result += getEuclideanDistance(serList.get(i), means[assigns.get(i)]);
		}
		return result;
	}
}
