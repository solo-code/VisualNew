package bistu.icdd.edu.server.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


/**
 * 这个类用来处理数据
 * **/
public class HandleData {
	
	public static HashMap<Integer,FunctionEffectiveBean> map = null;
	
	public static void initData() {
		String fileName = "bistu/icdd/edu/server/data/resultcopy.txt";
		InputStream in = HandleData.class.getClassLoader().getResourceAsStream(fileName);
		InputStreamReader insr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(insr);
		String line = null;
		map = new HashMap<Integer,FunctionEffectiveBean>();
		int count = 0;
		try {
			while((line=br.readLine())!=null){
				String temp[] = line.split(",");
				FunctionEffectiveBean feb = new FunctionEffectiveBean(temp);
//				if(map.containsKey(feb.hashCode())){
//					System.out.println(feb.getEffective()+"\t"+count++);
//				}
				feb.setPosID(count++);
				map.put(feb.hashCode(), feb);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 获取功能结果列表
	 * @return
	 */
	public static List<FunctionEffectiveBean> getFunctionList(){
		
		List<FunctionEffectiveBean> functions = new ArrayList<FunctionEffectiveBean>();
		HashMap<Integer,FunctionEffectiveBean> filter = new HashMap<Integer,FunctionEffectiveBean>();  
		Iterator<FunctionEffectiveBean> it = map.values().iterator();
		while(it.hasNext()){
			FunctionEffectiveBean feb = it.next();
			int code = feb.getFunction().hashCode();
			if(filter.containsKey(code)){
				continue;
			}else
				filter.put(code, feb);
		}
		functions.addAll(filter.values());
		return functions;
	}
	/**
	 * 获取功能结果列表
	 * @return
	 */
	public static List<FunctionEffectiveBean> getEffectiveList(){
		
		List<FunctionEffectiveBean> effective = new ArrayList<FunctionEffectiveBean>();
		HashMap<Integer,FunctionEffectiveBean> filter = new HashMap<Integer,FunctionEffectiveBean>();  
		Iterator<FunctionEffectiveBean> it = map.values().iterator();
		while(it.hasNext()){
			FunctionEffectiveBean feb = it.next();
			int code = feb.getEffective().hashCode();
			if(filter.containsKey(code)){
				continue;
			}else
				filter.put(code, feb);
		}
		effective.addAll(filter.values());
		System.out.println(filter.values().size());
		return effective;
	}
	
	/**
	 * 
	 * @param functions 功能列表
	 * @param effective 效果列表
	 */
	public static List<FunctionEffectiveBean> generateResult(String functions[],String []effective){
		List<FunctionEffectiveBean> res = new ArrayList<FunctionEffectiveBean>();
		for(String f:functions){
			for(String e:effective){
				int key = getCode(f,e);
				FunctionEffectiveBean feb = map.get(key);
				if(feb==null)
					continue;
				else
					res.add(feb);
			}
		}
		return res;
	}

	/**
	 * 计算哈希值
	 * @param f 
	 * @param e
	 * @return 计算的哈希值
	 */
	private static int getCode(String f, String e) {
		final int prime = 31;
		int result = 1;
		result = prime * result+ ((e == null) ? 0 : e.hashCode());
		
		result = prime * result+ ((f == null) ? 0 : f.hashCode());
		
		return result;
	}
}
