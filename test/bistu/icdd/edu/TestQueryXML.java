package bistu.icdd.edu;

import org.junit.Test;

import bistu.icdd.edu.server.GreetingServiceImpl;

public class TestQueryXML {

	String f[] = {"车后桥","部件","薄膜","换方法","隔膜","部件"};
	String e[] = {"串味现象","避免相互","抗腐蚀性","串烟","提高排气效率","组装工序数","保证高","外形美观"};
	
	
	@Test
	public void test() {
		GreetingServiceImpl gsi = new GreetingServiceImpl();
		String xml = gsi.toStr(f, e);
		System.out.println(xml);
	}

}
