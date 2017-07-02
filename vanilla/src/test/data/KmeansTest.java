package test.data;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.vanilla.data.datacolumn.Series;
import com.vanilla.data.dataframe.DataFrame;
import com.vanilla.io.reader.CsvReader;
import com.vanilla.unsupervised.kmeans.Kmeans;

public class KmeansTest {
	Kmeans kmeans;
	
	CsvReader reader;
	
	DataFrame df;
	
	public KmeansTest() {
		
		reader = new CsvReader();
		df = reader.readLocal("testkmean.csv");
		kmeans = new Kmeans();
	
		
		
	}
	
	@Test
	public void testGetRandomCentroids() {
		int[] cens = kmeans.getRandomCentroids(5, 1000);
		for (int cent : cens) {
			System.out.println(cent);
		}
	}
	@Test
	public void testGetEuclideanDistance() {
		System.out.println(kmeans.getEuclideanDistance(df.getRow(1), df.getRow(0)));
		
	}
	@Test
	public void testAssignCentroid() {
		List<Series> serList = df.getRowList();
		int[] cens = kmeans.getRandomCentroids(5, serList.size());
		Series[] clusters = new Series[cens.length];
		for (int i = 0; i < cens.length; i++) {
			clusters[i] = serList.get(i);
			System.out.println(clusters[i]);
		}
		List<Integer> res = kmeans.assignCentroid(serList, clusters);
		for (Integer ele : res) {
			System.out.println(ele);
		} 
		
	}
	
	@Test
	public void testGetMeans() {
		List<Series> serList = df.getRowList();
		int[] cens = kmeans.getRandomCentroids(5, serList.size());
		Series[] clusters = new Series[cens.length];
		for (int i = 0; i < cens.length; i++) {
			clusters[i] = serList.get(i);
		}
		List<Integer> clustersAssign = kmeans.assignCentroid(serList, clusters);
		Series[] col = kmeans.getMeans(clustersAssign, serList, cens.length);
		for (Series ele : col) {
			System.out.println(ele.toString());
		}
		
	}
	@Test
	public void testProcess() {
		
		List<Integer> clusters = kmeans.process(df, 5);
		for (Integer cluster : clusters) {
			System.out.println(cluster);
		}
		
		
		
	}
	
	
	
}
