package bistu.icdd.edu.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.IntegerFieldDef;
import com.gwtext.client.data.Record;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.data.XmlReader;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.grid.BaseColumnConfig;
import com.gwtext.client.widgets.grid.CheckboxColumnConfig;
import com.gwtext.client.widgets.grid.CheckboxSelectionModel;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.layout.VerticalLayout;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class VisualNew implements EntryPoint {
	

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);

	private CheckboxSelectionModel effectiveSelectionModel = null;
	
	private CheckboxSelectionModel functionSelectionModel = null;
	
	private MyPagePanel functionpage = null;
	
	private MyPagePanel effectivepage = null;
	
	private TextArea functionArea = null;
	
	private TextArea effectiveArea = null;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		final Button sendButton = new Button("Send");
		final TextBox nameField = new TextBox();
		nameField.setText("GWT User");
		final Label errorLabel = new Label();

		// We can add style names to widgets
		sendButton.addStyleName("sendButton");

		// Add the nameField and sendButton to the RootPanel
		// Use RootPanel.get() to get the entire body element
		
		Panel functionPanel = createFunctionPanel();
		Panel effectivePanel = createEffectivePanel();
		
//		RootPanel.get("nameFieldContainer").add(nameField);
//		RootPanel.get("sendButtonContainer").add(sendButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);
		functionpage.initData();
		effectivepage.initData();
		
		 // Add a text area
	     functionArea = new TextArea();
	    functionArea.setVisibleLines(18);
	    functionArea.setReadOnly(true);
	   
	    // Add a text area
	    effectiveArea = new TextArea();
	    effectiveArea.setVisibleLines(18);
	    effectiveArea.setReadOnly(true);
	    
	    Button drawButton = new Button("绘图",new ButtonListenerAdapter(){
	    	@Override
			public void onClick(Button button, EventObject e) {
	    		String function[] = null;
	    		String effective[] = null;
	    	
				function = getInnerTexts_(functionArea,"请选择功能");
				effective = getInnerTexts_(effectiveArea,"请选择效果");
	    		
	    		greetingService.loadData(function, effective, new AsyncCallback<String>() {
					
					@Override
					public void onSuccess(String result) {
						// TODO Auto-generated method stub
						
						Window.open("/show.html", "_blank", "");
						
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
					}
				});
	    	}

			
	    });
	    Panel temp = new Panel();
	    
	    temp.setWidth(50);
	    temp.setHeight(320);
	    temp.setBorder(false);
	    temp.setLayout(new VerticalLayout(100));
	    temp.setBottomToolbar(drawButton);
	    
	    Panel functionRes = new Panel();
		Button clearFunctionButton = new Button("清空",new ButtonListenerAdapter(){
			@Override
			public void onClick(Button button, EventObject e) {
				functionArea.setText("");
			}
		});
		functionRes.add(functionArea);
		functionRes.setBottomToolbar(clearFunctionButton);
		
		Panel effectiveRes = new Panel();
			Button clearEffectiveButton = new Button("清空",new ButtonListenerAdapter(){
				@Override
				public void onClick(Button button, EventObject e) {
					effectiveArea.setText("");
				}
			});
			effectiveRes.add(effectiveArea);
			effectiveRes.setBottomToolbar(clearEffectiveButton);
	    
		RootPanel.get("function").add(functionPanel);
		RootPanel.get("effective").add(effectivePanel);
		RootPanel.get("functionResult").add(functionRes);
		RootPanel.get("effectiveResult").add(effectiveRes);
		RootPanel.get("errorLabelContainer").add(temp);
	}
	private String[] getInnerTexts_(TextArea texts,String erroMsg) {
		String[] function;
		String temp = texts.getText();
		if(temp==null||temp.length()==0){
			MessageBox.alert(erroMsg);
		}
		function = temp.split("\n");
		return function;
	}
	private Panel createEffectivePanel() {
		Panel panel = new Panel();  
        panel.setBorder(false);  
        panel.setPaddings(15);  
        panel.setLayout(new VerticalLayout(15));  
        
		effectiveSelectionModel = new CheckboxSelectionModel();
		GridPanel effectivePanel = createGridPanel("效果列表","effective",effectiveSelectionModel);
		panel.add(effectivePanel);
		effectivepage = new MyPagePanel(effectivePanel.getStore(),1);//效果
		effectivepage.setMASK_ID("effectiveID");
		panel.setId("effectiveID");
		
		Button addButton = new Button("添加",new ButtonListenerAdapter(){

			
			@Override
			public void onClick(Button button, EventObject e) {
				Record[] records = effectiveSelectionModel.getSelections();
				StringBuilder sbl = new StringBuilder();
				if(effectiveArea.getText()!=null){
					sbl.append(effectiveArea.getText());
				}
				if(records!=null&&records.length>0){
					for(int i=0;i<records.length;i++){
						
						sbl.append(records[i].getAsString("effective")+"\n");
					}
					effectiveArea.setReadOnly(false);
					effectiveArea.setText(sbl.toString());
					effectiveArea.setReadOnly(true);
				}
			}
			
		});
		
		HorizontalPanel horizon = new HorizontalPanel();
		horizon.add(effectivepage);
		horizon.add(addButton);
		
		panel.add(horizon);
		return panel;
	}

	public Panel createFunctionPanel(){
		
		Panel panel = new Panel();  
        panel.setBorder(false);  
        panel.setPaddings(15);  
        panel.setLayout(new VerticalLayout(15));  
		
		functionSelectionModel = new CheckboxSelectionModel();  
		GridPanel functionPanel = createGridPanel("功能列表","function",functionSelectionModel);//功能
		
		functionpage = new MyPagePanel(functionPanel.getStore(),2);
		functionpage.setMASK_ID("functionID");
		Button addButton = new Button("添加",new ButtonListenerAdapter(){

			
			@Override
			public void onClick(Button button, EventObject e) {
				Record[] records = functionSelectionModel.getSelections();
				StringBuilder sbl = new StringBuilder();
				if(functionArea.getText()!=null){
					sbl.append(functionArea.getText());
				}
				if(records!=null&&records.length>0){
					for(int i=0;i<records.length;i++){
						sbl.append(records[i].getAsString("function")+"\n");
					}
					functionArea.setReadOnly(false);
					functionArea.setText(sbl.toString());
					functionArea.setReadOnly(true);
				}
			}
			
		});
		HorizontalPanel horizon = new HorizontalPanel();
		horizon.add(functionpage);
		horizon.add(addButton);
		panel.add(functionPanel);
		panel.add(horizon);
		panel.setId("functionID");
		
		return panel;
	}
	
	
	/**
	 * 
	 * @param title
	 * @param cbSelectionModel
	 * @return
	 */
	public GridPanel createGridPanel(String title,String field,final CheckboxSelectionModel cbSelectionModel){
		  
	        RecordDef recordDef = new RecordDef(  
	                new FieldDef[]{  
	                        new StringFieldDef(field),
	                        new IntegerFieldDef("posID"),
	                }  
	        );  
	  
	        GridPanel grid = new GridPanel();  
	  
	        XmlReader reader = new XmlReader("data",recordDef);

			reader.setTotalRecords("totalCount");
			reader.setId("posID");
			Store store = new Store(reader);
	        grid.setStore(store);  
	  
	        BaseColumnConfig[] columns = new BaseColumnConfig[]{  
	                new CheckboxColumnConfig(cbSelectionModel),  
	                //column ID is company which is later used in setAutoExpandColumn  
	                new ColumnConfig(field, field,160,true,null,"Data"), 
	        };  
	  
	        ColumnModel columnModel = new ColumnModel(columns);  
	        grid.setColumnModel(columnModel);  
//	        grid.setHeader(false);
	        grid.setFrame(true);  
	        grid.setStripeRows(true);  
	        grid.setAutoExpandColumn("Data");  
	  
	        grid.setSelectionModel(cbSelectionModel);  
	        grid.setWidth(300);  
	        grid.setHeight(300);  
	        grid.setFrame(true);  
	        grid.setTitle(title);  
	        grid.setIconCls("grid-icon");  
	  
	 
		return grid;  
	}
	
}
