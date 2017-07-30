package test.data;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.vanilla.data.dataframe.DataFrame;

public class DataFrameTest {
	@Test
	public void testCon1() {
		DataFrame df = new DataFrame();
	}
	@Test
	public void testCon2() {
		List<String> col = new ArrayList<String>();
		col.add("c1");
		col.add("c2");
		col.add("c3");
		DataFrame df = new DataFrame(col);
	}
	@Test
	public void testCon3() {
		List<String> col = new ArrayList<String>();
		col.add("c1");
		col.add("c2");
		col.add("c3");
		String[][] data = {{"1",  "2", "3"},{"1",  "2", "3"},{"1",  "2", "3"}};
		DataFrame df = new DataFrame(data, col);
	}
	@Test
	public void testGetPosition() {
		List<String> col = new ArrayList<String>();
		col.add("c1");
		col.add("c2");
		col.add("c3");
		String[][] data = {{"1",  "2", "3"}, {"1",  "2", "3"}, {"1",  "2", "3"}};
		DataFrame df = new DataFrame( data, col);
		System.out.println(df.getPosition(0, 0));
		System.out.println(df.getPosition(3, 3));
	}
	@Test
	public void testWriteColumn() {
		List<String> col = new ArrayList<String>();
		col.add("c1");
		col.add("c2");
		col.add("c3");
		String[][] data = {{"1",  "2", "3"}, {"1",  "2", "3"}, {"1",  "2", "3"}};
		DataFrame df = new DataFrame( data, col);
		List<String> c1 = new ArrayList<>();
		c1.add("0");
		c1.add("0");
		c1.add("0");
		df.writeColumn("c1", c1);
		System.out.println(df.getRow(0));
		df.writeColumn("c4", c1);
		System.out.println(df.getRow(0));
	}
	@Test
	public void testGetHeader() {
		List<String> col = new ArrayList<String>();
		col.add("c1");
		col.add("c2");
		col.add("c3");
		String[][] data = {{"1",  "2", "3"}, {"1",  "2", "3"}, {"1",  "2", "3"}};
		DataFrame df = new DataFrame( data, col);
		List<String> c1 = new ArrayList<>();
		c1.add("0");
		c1.add("0");
		c1.add("0");
		df.writeColumn("c1", c1);
		System.out.println(df.getHeader("c4"));
		df.writeColumn("c4", c1);
		System.out.println(df.getHeader("c4"));
	}

}
