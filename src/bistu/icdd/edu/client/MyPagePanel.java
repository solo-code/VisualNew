package bistu.icdd.edu.client;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.core.Ext;
import com.gwtext.client.core.ExtElement;
import com.gwtext.client.data.Store;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.Label;

public class MyPagePanel extends HorizontalPanel {
	private GreetingServiceAsync greetingService;
	private TextBox pageNum;
	public Store store;
	protected int showlenth = 20;
	protected int currentPage = 1;
	int totalPage = -1;
	private Label totalCountLabel = null;
	Button nextPage = null;
	Button prePage = null;
	private String MASK_ID = null;
	private int type;
	private Label currentLabel = null;
	
	public MyPagePanel(Store store,int type){
		totalCountLabel = new Label();
		createPagePanel();
		this.store = store;
		this.type = type;
	}
	
	public MyPagePanel(Store store, int showlenth,int type) {
		this.store = store;
		this.showlenth = showlenth;
		totalCountLabel = new Label();
		createPagePanel();
	}

	public void createPagePanel() {

		
		nextPage = new Button(createLinkFont("下一页", false));
		prePage = new Button(createLinkFont("上一页", false));
		prePage.setDisabled(true);
		prePage.addListener(new ButtonListenerAdapter() {
			@Override
			public void onClick(Button button, EventObject e) {
				if (currentPage >= 1) {
					
					int start = (currentPage-2)*showlenth;
					int end = start + showlenth-1;
					ExtElement ele = Ext.get(MASK_ID);
					if (ele != null) {
						ele.mask("Searching...");
					}
					getXMLData(start, end);
					currentPage--;
					nextPage.enable();
					currentLabel.setText("第 "+currentPage+"页");
					if (currentPage == 1)
						prePage.disable();
				} else {
					prePage.disable();
				}
			}
		});

		nextPage.addListener(new ButtonListenerAdapter() {
			@Override
			public void onClick(Button button, EventObject e) {
				if (currentPage <= totalPage) {
					
					int start = currentPage*showlenth;
					int end = start + showlenth-1;
					ExtElement ele = Ext.get(MASK_ID);
					if (ele != null) {
						ele.mask("Searching...");
					}
					getXMLData(start, end);
					currentPage++;
					prePage.enable();
					currentLabel.setText("第 "+currentPage+"页");
					if (currentPage >= totalPage)
						nextPage.disable();
				} else {
					nextPage.disable();
				}
			}

		});

		totalCountLabel = new Label("/共"+String.valueOf(totalPage)+"页");
		currentLabel = new Label("第 "+currentPage+"页");
		
		this.add(prePage);
		this.add(nextPage);
		this.add(currentLabel);
		this.add(totalCountLabel);
		this.setWidth("260px");
		this.setHeight("22px");
		this.setVisible(true);
	}

	private String createLinkFont(String text, boolean alert) {
		final String blue_link = "#1542bb";
		final String red_link = "#bb4215";

		return "<font color='" + (alert ? red_link : blue_link) + "'><b><u>"
				+ text + "</u></b></font>";
	}

	public void getXMLData(int start, int end) {
		if(greetingService==null){
			greetingService = GWT.create(GreetingService.class);
		}
		greetingService.pageChange(start, end,type, new AsyncCallback<String>() {

			public void onFailure(Throwable caught) {
				ExtElement ele = Ext.get(MASK_ID);
				if (ele != null) {
					ele.unmask();
				}
			}

			public void onSuccess(String result) {
				store.removeAll();
				store.loadXmlData(result, false);
				totalPage = store.getTotalCount();
				if (totalPage % showlenth == 0)
					totalPage = totalPage / showlenth;
				else
					totalPage = totalPage / showlenth + 1;
				totalCountLabel.setText("/共"+String.valueOf(totalPage)+"页");
				ExtElement ele = Ext.get(MASK_ID);
				if (ele != null) {
					ele.unmask();
				}
			}
		});
	}

	public TextBox getPageNum() {
		return pageNum;
	}

	public void setPageNum(TextBox pageNum) {
		this.pageNum = pageNum;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public int getShowlenth() {
		return showlenth;
	}

	public void setShowlenth(int showlenth) {
		this.showlenth = showlenth;
	}

	public void initData() {
		getXMLData(0,this.showlenth);
		this.currentPage = 1;
		if(store!=null&&store.getCount()>0)
			this.nextPage.enable();
	}

	/**
	 * @return the mASK_ID
	 */
	public String getMASK_ID() {
		return MASK_ID;
	}

	/**
	 * @param mASK_ID the mASK_ID to set
	 */
	public void setMASK_ID(String mASK_ID) {
		MASK_ID = mASK_ID;
	}
}