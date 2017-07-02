package test.data;

import static org.junit.Assert.*;

import org.junit.Test;

import com.vanilla.data.datanode.DataNode;
import com.vanilla.data.datanode.StringNode;

public class DataNodeTest {
	
	DataNode sn;
	public DataNodeTest() {
		sn = new StringNode("a","1");
	}
	
	@Test
	public void testgetKey() {
		assertEquals("a", sn.getKey());
		assertEquals("1", sn.getValue());
		sn.setKey("b");
		assertEquals("b", sn.getKey());
		sn.setValue("2");
		assertEquals("2", sn.getValue());
	}

}
