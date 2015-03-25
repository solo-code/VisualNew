package bistu.icdd.edu;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bistu.icdd.edu.server.data.FunctionEffectiveBean;
import bistu.icdd.edu.server.data.HandleData;

public class TestGetList {

	@Before
	public void before(){
		HandleData.initData();
	}
	
	@Test
	public void testEffective() {
		
		List<FunctionEffectiveBean> list = HandleData.getEffectiveList();
		org.junit.Assert.assertEquals(list.size(), 1489);//
	}
	
	@After
	public void destoryData(){
		HandleData.map.clear();
		HandleData.map = null;
	}

}
