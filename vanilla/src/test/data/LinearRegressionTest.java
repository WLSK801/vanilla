package test.data;

import org.junit.Test;

import com.vanilla.data.dataframe.DataFrame;
import com.vanilla.io.reader.CsvReader;
import com.vanilla.supervised.model.Model;
import com.vanilla.supervised.regression.linear.LinearRegression;

public class LinearRegressionTest {
	Model model;
	
	CsvReader reader;
	
	DataFrame df;
	
	DataFrame dfP;
	
	public LinearRegressionTest() {
		
		reader = new CsvReader();
		df = reader.readLocal("linearre2.csv");
		model = new LinearRegression();
		dfP = reader.readLocal("linearreP2.csv");

		
		
	}
	
	@Test
	public void testTrain() {
		model.train(df, 2);
		System.out.println(model.predict(dfP));
		
		
		
		
	}
	
}
