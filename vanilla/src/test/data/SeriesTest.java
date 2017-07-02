package test.data;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.vanilla.data.datacolumn.Series;
import com.vanilla.data.datanode.DataNode;
import com.vanilla.data.datanode.StringNode;

public class SeriesTest {
	
	Series ser;
	
	public SeriesTest() {
		ArrayList<String> h = new ArrayList<String>();
		h.add("a");
		h.add("b");
		h.add("c");
		ArrayList<String> data = new ArrayList<String>();
		data.add("1");
		data.add("2");
		data.add("3");
		ser = new Series("new", h, data);
	}
	
	@Test
	public void containsHeaderKeyTest() {
		assertTrue(ser.containsHeader("c"));
		assertFalse(ser.containsHeader(""));
	}
	@Test
	public void getPositionByheaderTest() {
		assertEquals(2, ser.getPositionByheader("c"));
		assertEquals(0, ser.getPositionByheader("a"));
		assertEquals(-1, ser.getPositionByheader("aa"));
	}
	@Test
	public void setValueTest() {
		ser.setValue("c", "10");
		assertEquals("10", ser.getValueByHeader("c"));
		ser.setValue("d", "4");
		assertEquals("4", ser.getValueByHeader("d"));
		
	}
	@Test
	public void setValueTest2() {
		ser.setValue(1, "10");
		assertEquals("10", ser.getValueByHeader("b"));
		assertEquals("10", ser.getValueByPosition(1));
		ser.setValue(3, "11");
		assertEquals("11", ser.getValueByPosition(3));
		
	}
	@Test
	public void setHeadersTest() {
		List<String> headerList= new ArrayList<String>();
		headerList.add("aa");
		headerList.add("bb");
		headerList.add("cc");
		ser.setHeaders(headerList);
		assertEquals(1, ser.getPositionByheader("bb"));
		
		
	}
	@Test
	public void  getHeaderArrayTest() {
		String[] heads = ser.getHeaderArray();
		assertEquals("b", heads[1]);
		String[] test = {"a", "b", "c"};
		assertArrayEquals(test, heads);
		
		
	}
	
	

}
