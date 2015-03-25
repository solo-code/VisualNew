package bistu.icdd.edu;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bistu.icdd.edu.server.GreetingServiceImpl;
import bistu.icdd.edu.server.data.HandleData;

public class TestMakeXML {

	@Before
	public void before(){
		HandleData.initData();
	}
	
	@Test
	public void testEffective() {
		
		GreetingServiceImpl greet = new GreetingServiceImpl();
		String xml = greet.pageChange(0, 5, 1);
		System.out.println(xml);
	}
	
	@After
	public void destroyData(){
		HandleData.map.clear();
		HandleData.map = null;
	}


}
