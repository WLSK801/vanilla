package test.data;

import org.junit.Test;

import com.vanilla.data.dataframe.DataFrame;
import com.vanilla.io.reader.CsvReader;
import com.vanilla.io.reader.JsObjectReader;

public class JsObjectReaderTest {
	@Test
	public void testParseRow() {
		JsObjectReader rd = new JsObjectReader();
		System.out.println(rd.parseRow("{\"a\":\"1\"}"));
		System.out.println(rd.parseRow("{\"a\":\"1\",\"b\":\"1\",\"c\":\"1\"}"));
		

	}
	@Test
	public void testRead() {
		JsObjectReader rd = new JsObjectReader();
		String row = "[{\"a\":\"1\",\"b\":\"1\",\"c\":\"1\"},{\"a\":\"2\",\"b\":\"2\",\"c\":\"2\"}]";
		DataFrame df = rd.read(row);
		System.out.println(df.getRow(0));
		System.out.println(df.getRow(1));

	}
}
