package test.data;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.vanilla.data.datacolumn.Series;
import com.vanilla.data.dataframe.DataFrame;
import com.vanilla.io.reader.CsvReader;
import com.vanilla.supervised.model.Model;
import com.vanilla.supervised.regression.linear.LinearRegression;
import com.vanilla.unsupervised.kmeans.Kmeans;

public class LinearRegressionTest {
	Model model;
	
	CsvReader reader;
	
	DataFrame df;
	
	DataFrame dfP;
	
	public LinearRegressionTest() {
		
		reader = new CsvReader();
		df = reader.readLocal("linearre.csv");
		model = new LinearRegression();
		dfP = reader.readLocal("linearreP.csv");

		
		
	}
	
	@Test
	public void testTrain() {
		model.train(df, 1);
		System.out.println(model.predict(dfP));
		
		
		
		
	}
	
}
