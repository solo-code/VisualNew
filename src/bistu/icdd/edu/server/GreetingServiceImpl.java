package bistu.icdd.edu.server;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import bistu.icdd.edu.client.GreetingService;
import bistu.icdd.edu.server.data.FunctionEffectiveBean;
import bistu.icdd.edu.server.data.HandleData;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	
	private static final int FUNCTION = 2;
	
	private static final int EFFECTIVE = 1;
	
	static{
		HandleData.initData();
	}
	
	/**
	 * 功能列表
	 */
	public List<FunctionEffectiveBean> functions = HandleData.getFunctionList();//全部实例化为 ArrayList
	
	public List<FunctionEffectiveBean> effective = HandleData.getEffectiveList();//全部实例化为 ArrayList


	@Override
	public String loadData(String function[] ,String effective[]) throws IllegalArgumentException {
		toStr(function,effective);
//		List<FunctionEffectiveBean> list = HandleData.generateResult(function, effective);
//		
//		String file = new File("").getAbsolutePath()+"/data.xml";
//		File xmlFile = new File(file);
//		if(xmlFile.exists()){
//			xmlFile.delete();
//		}
//		XStream xs = new XStream();
//		
//		try {
//			FileOutputStream fos = new FileOutputStream(file,true);
//			
//			BufferedOutputStream out = new BufferedOutputStream(fos);
//			xs.alias("function", String.class);
//			xs.toXML(function, out);
//			xs.alias("effective", String.class);
//			xs.toXML(effective,out);
//			XYV []array = null;
//			if(list!=null&&list.size()>0){
//				int xLablePos[] = findPos(list,function,FUNCTION);
//				int yLablePos[] = findPos(list,effective,EFFECTIVE);
//				array = getAll(xLablePos,yLablePos,list);
//			}
//			xs.toXML(array, out);
//			if(out!=null)
//			{
//				out.flush();
//				out.close();
//			}
//			if(fos!=null){
//				fos.close();
//			}
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
		return "";
	}

//public String toXML(String function[] ,String effective[]) throws IllegalArgumentException {
//		
//		List<FunctionEffectiveBean> list = HandleData.generateResult(function, effective);
//		
//		String file = new File("").getAbsolutePath()+"/data.xml";
//		
//		XStream xs = new XStream();
//		
//		StringBuilder sbl = new StringBuilder();
//			xs.alias("function", String.class);
//			sbl.append(xs.toXML(function));
//			xs.alias("effective", String.class);
//			sbl.append(xs.toXML(effective));
//			XYV []array = null;
//			if(list!=null&&list.size()>0){
//				int xLablePos[] = findPos(list,function,FUNCTION);
//				int yLablePos[] = findPos(list,effective,EFFECTIVE);
//				array = getAll(xLablePos,yLablePos,list);
//			}
//			xs.processAnnotations(GreetingServiceImpl.XYV.class);
//			sbl.append(xs.toXML(array));
//			
//			
//		
//		
//		return sbl.toString();
//	}

public String toStr(String function[] ,String effective[]) throws IllegalArgumentException {
	
		List<FunctionEffectiveBean> list = HandleData.generateResult(function, effective);
	
		String file = new File("").getAbsolutePath()+"/js/data.js";
	System.out.println(file);
		StringBuilder sbl = new StringBuilder();
		sbl.append("var xLabel = new Array('',");
		for(int i=0;i<function.length;i++){
			sbl.append("'"+function[i]+"'");
			if(i<function.length-1)
				sbl.append(",");
		}
		sbl.append(");\nvar yLabel = new Array('',");
		for(int i=0;i<effective.length;i++){
			sbl.append("'"+effective[i]+"'");
			if(i<effective.length-1)
			sbl.append(",");
		}
		sbl.append(");\nvar arrays = new Array(");
		XYV []array = null;
		if(list!=null&&list.size()>0){
			int xLablePos[] = findPos(list,function,FUNCTION);
			int yLablePos[] = findPos(list,effective,EFFECTIVE);
			array = getAll(xLablePos,yLablePos,list);
		}
		
		if(array==null){
			array = new XYV[1];
			array[0] = new XYV(0,0,0);
		}
		
		for(int i=0;i<array.length;i++){
			sbl.append(array[i]);
		}
		sbl.delete(sbl.length()-",".length(), sbl.length());
		sbl.append(");");
		try {
			
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
			out.write(sbl.toString().getBytes());
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
	return sbl.toString();
}
	
	private XYV[] getAll(int[] xLablePos, int[] yLablePos, List<FunctionEffectiveBean> list) {
		XYV []res = new XYV[list.size()];
		for(int index =0;index<list.size();index++){
			FunctionEffectiveBean f = list.get(index);
			int v = f.getValue();
			XYV t = new XYV(xLablePos[index],yLablePos[index],v);
			res[index] = t;
		}
		return res;
	}

	/**
	 * @param list
	 * @param strs
	 * @param type 寻找那种类型的字符串
	 * @return
	 */
	private int[] findPos(List<FunctionEffectiveBean> list, String[] src,int type) {
		if(list==null||list.size()<0)
			return null;
		int res[] = new int[list.size()];
		for(int index=0;index<list.size();index++){
			FunctionEffectiveBean feb = list.get(index);
			String target = null;
			if(type==FUNCTION){//功能
				target = feb.getFunction();
			}else{
				target = feb.getEffective();
			}
			for(int i=0;i<src.length;i++){
				String str = src[i];
				if(str.equals(target)){
					res[index] = i+1;
					break;
				}
			}
		}
		return res;
	}

	@Override
	public String pageChange(int start, int end,int type) {
		List<FunctionEffectiveBean> dest = null;
		int sum = 0;
		if(start<0||start>end)
			return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><res><totalCount>0</totalCount></res>";
		dest = new ArrayList<FunctionEffectiveBean>(end-start+1);
		List<FunctionEffectiveBean> list = null;
		if(type==2){
			list = functions;
		}else
			list = effective;
		
		if(end>list.size())
			end = list.size();
		arrayCopy(list, dest, start, end);
		sum = list.size();
		
		String xml = makeXML(dest, sum);
		return xml;
	}
	
	private void arrayCopy(List<FunctionEffectiveBean> src,List<FunctionEffectiveBean>dst,int fromIndex,int end){
		if(dst==null||src==null||fromIndex<0||fromIndex>src.size()||end<0||fromIndex>end||end>src.size()){
			return;
		}
		for(int i=fromIndex;i<end;i++){
			dst.add(src.get(i));
		}
	}
	
	public String makeXML(List<FunctionEffectiveBean> dest,int sum){
		String xml = null;
		if(dest==null){
			xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><res><totalCount>0</totalCount></res>";
		}else{
			XStream xs = new XStream();
//			xs.alias("data", String.class);
			xs.processAnnotations(FunctionEffectiveBean.class);
			xml = xs.toXML(dest);
			xml ="<?xml version=\"1.0\" encoding=\"UTF-8\"?><res><totalCount>"+sum+"</totalCount>"+xml+"</res>";
		}
		
		return xml;
	}
	
	@XStreamAlias("three")
	class XYV{
		int x = -1;
		int y = -1;
		int v = -1;
		public XYV(int x, int y, int v) {
			super();
			this.x = x;
			this.y = y;
			this.v = v;
		}
		public String toString(){
			return "'"+x+","+y+","+v+"',";
		}
	}
	
}
