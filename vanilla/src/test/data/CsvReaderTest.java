package test.data;

import org.junit.Test;

import com.vanilla.data.dataframe.DataFrame;
import com.vanilla.io.reader.CsvReader;

public class CsvReaderTest {
	@Test
	public void testReadLocal() {
		CsvReader rd = new CsvReader();
		DataFrame df = rd.readLocal("MyData.csv");
		System.out.println(df.getPosition(2, 2));
		System.out.println(df.getRowNum());
		System.out.println(5 << 1);
	}
}
