package test.data;

import org.junit.Test;

import com.vanilla.data.dataframe.DataFrame;
import com.vanilla.io.reader.CsvReader;
import com.vanilla.io.reader.JsObjectReader;
import com.vanilla.io.writer.JsObjectWriter;

public class JsObjectWriterTest {
	@Test
	public void testWrite() {
		JsObjectReader rd = new JsObjectReader();
		String row = "[{\"a\":\"1\",\"b\":\"1\",\"c\":\"1\"},{\"a\":\"2\",\"b\":\"2\",\"c\":\"2\"}]";
		DataFrame df = rd.read(row);
		System.out.println(df.getRow(0));
		System.out.println(df.getRow(1));
		JsObjectWriter writer = new JsObjectWriter();
		System.out.println(writer.write(df.getRow(0)));
		System.out.println(writer.write(df.getRow(1)));
		System.out.println(writer.write(df));
		System.out.println(df.getRowNum());

	}
}
