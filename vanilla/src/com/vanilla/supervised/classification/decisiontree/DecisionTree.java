package com.vanilla.supervised.classification.decisiontree;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.vanilla.data.datacolumn.Series;
import com.vanilla.data.dataframe.DataFrame;
import com.vanilla.supervised.model.Model;

public class DecisionTree implements Model {
	
	
	private int maxDepth;
	private int minSize;
	private TreeNode root;
	private boolean isTrain;
	Map<Integer, String> headerMap;
	public double getMaxDepth() {
		return maxDepth;
	}


	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}


	public double getMinSize() {
		return minSize;
	}


	public void setMinSize(int minSize) {
		this.minSize = minSize;
	}


	public DecisionTree() {
		maxDepth = 5;
		minSize = 2;
		isTrain = false;
	}


	@Override
	public List<String> predict(DataFrame frame) {
		List<String> res = new ArrayList<>(frame.getRowNum());
		for (Series row : frame.getRowList()) {
			res.add(getPredict(root,row));
		}
		return res;
	}
	private String getPredict(TreeNode node, Series row) {
		if (!isTrain) {
			throw new NullPointerException("Model does not exist");
		}
		Double value =
				Double.parseDouble(row.getValueByHeader(headerMap.get(root.index)));
		if (value < node.value) {
			if (!node.isTerminal) {
				return getPredict(node.left, row);
			}
			else {
				return node.leftLeaf;
			}
		}
		else {
			if (!node.isTerminal) {
				return getPredict(node.right, row);
			}
			else {
				return node.rightLeaf;
			}
		}
	}

	@Override
	public void train(DataFrame frame, int outputColumn) {
		List<String> output = frame.remove(outputColumn);
		headerMap = new HashMap<>();
		Series one = frame.getRow(0);
		for (int i = 0; i < frame.getColumnNum(); i++) {
			headerMap.put(i, one.getHeader(i));
		}
		root = buildTree(frame, output, maxDepth, minSize);
		isTrain = true;
		

	}
	
	private double giniIndex(List<List<Integer>> groups, List<String> output, Set<String> classValues) {
		double gini = 0.0;
		for (String value : classValues) {
			for (List<Integer> group : groups) {
				int size = group.size();
				if (size == 0) continue;
				
				int count = 0;
				for (String ele : output) {
					if (ele.equals(value)) {
						count++;
					}
				}
				double proportion = (float)count / (float)size;
				gini += (proportion * (1.0 - proportion));
			}
		}
		return gini;
		
	}
	private List<List<Integer>> testSplit(DataFrame frame, double value, int index) {
		List<Integer> left = new ArrayList<>();
		List<Integer> right = new ArrayList<>();
		int i = 0;
		for (Series row : frame.getRowList()) {
			double ele = Double.parseDouble(row.getValueByPosition(index));
			if (ele < value) left.add(i);
			else right.add(i);
			i++;
		} 
		List<List<Integer>> res = new ArrayList<>();
		res.add(left);
		res.add(right);
		return res;
	}
	private TreeNode getSplit(DataFrame frame, List<String> output) {

		Set<String> classValues = new HashSet<>(output);
		double minGini = 999.0;
		List<List<Integer>> resGroups = null;
		int index = 0;
		double value = 0.0;
		for (int i = 0; i < frame.getRowNum(); i++) {
			for (Series row : frame.getRowList()) {
				List<List<Integer>> groups = testSplit(frame,
						Double.parseDouble(row.getValueByPosition(i)), i);
				double gini = giniIndex(groups, output, classValues);
				if (minGini > gini) {
					resGroups = groups;
					minGini = gini;
					index = i;
					value = Double.parseDouble(row.getValueByPosition(i));
				}
			}
		}
		return new TreeNode(index, value, resGroups);
	}
	private String getTerminal(List<Integer> group, List<String> output) {
		Map<String, Integer> map = new HashMap<>();
		for (String ele: output) {
			if (map.containsKey(ele)) {
				map.put(ele, map.get(ele) + 1);
			}
			else {
				map.put(ele, 0);
			}
			
		}
		int max = -1;
		String res = "";
		for (Entry<String, Integer> ele : map.entrySet()) {
			int curr = ele.getValue();
			if(max < curr) {
				max = curr;
				res = ele.getKey();
			}
		}
		return res;
	}
	private void split(TreeNode node, int maxDepth, int minSize, int depth, DataFrame frame, List<String> output) {
		List<List<Integer>> groups = node.groups;
		node.groups = null;
		List<Integer> leftGroup = groups.get(0);
		List<Integer> rightGroup = groups.get(1);
		if (leftGroup.size() == 0 || rightGroup.size() == 0) {
			node.isTerminal = true;
			leftGroup.addAll(rightGroup);
			node.leftLeaf = getTerminal(leftGroup, output);
			node.rightLeaf = node.leftLeaf;
			return;
		}  
		if (depth >= maxDepth) {
			node.isTerminal = true;
			node.leftLeaf = getTerminal(leftGroup, output);
			node.rightLeaf = getTerminal(rightGroup, output);
			return;
		} 
		if (leftGroup.size() <= minSize) {
			node.isTerminal = true;
			node.leftLeaf = getTerminal(leftGroup, output);
		}
		else {
			node.left = getSplit(frame,  output);
			split(node.left, maxDepth, minSize, depth + 1, frame, output);
		}
		if (rightGroup.size() <= minSize) {
			node.isTerminal = true;
			node.rightLeaf = getTerminal(rightGroup, output);
		}
		else {
			node.right = getSplit(frame,  output);
			split(node.right, maxDepth, minSize, depth + 1, frame, output);
		}
	}
	private TreeNode buildTree(DataFrame frame, List<String> output, int maxDepth, int minSize) {
		TreeNode root = getSplit(frame, output);
		split(root,maxDepth, minSize, 1, frame, output);
		return root;
	}
	class TreeNode {
		public List<List<Integer>> groups;
		public int index;
		public double value;
		public TreeNode left;
		public TreeNode right;
		public boolean isTerminal;
		public String leftLeaf;
		public String rightLeaf;
		public TreeNode(int index, double value, List<List<Integer>> groups) {
			this.index = index;
			this.value = value;
			this.groups = groups;
			isTerminal = false;
		}

	}



}
