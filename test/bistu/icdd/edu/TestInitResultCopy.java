package bistu.icdd.edu;

import static org.junit.Assert.*;

import org.junit.Test;

import bistu.icdd.edu.server.data.HandleData;

public class TestInitResultCopy {

	@Test
	public void test() {
		HandleData.initData();
		if(HandleData.map.size()!=1723)
			fail("data is wrong");
	}

}
