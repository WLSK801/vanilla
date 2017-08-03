package test.data;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.vanilla.data.datacolumn.Series;
import com.vanilla.data.dataframe.DataFrame;
import com.vanilla.io.reader.CsvReader;
import com.vanilla.supervised.classification.decisiontree.DecisionTree;
import com.vanilla.supervised.classification.logistic.LogisticRegression;
import com.vanilla.supervised.model.Model;
import com.vanilla.supervised.regression.linear.LinearRegression;
import com.vanilla.unsupervised.kmeans.Kmeans;

public class DecisionTreeTest {
	Model model;
	
	CsvReader reader;
	
	DataFrame df;
	
	DataFrame dfP;
	
	public DecisionTreeTest() {
		
		reader = new CsvReader();
		df = reader.readLocal("decision.csv");
		model = new DecisionTree();
		dfP = reader.readLocal("decisionP.csv");

		
		
	}
	
	@Test
	public void testTrain() {
		model.train(df, 2);	
		System.out.println(model.toString());
		System.out.println(model.predict(dfP));
		
		
		
	}
	
}
