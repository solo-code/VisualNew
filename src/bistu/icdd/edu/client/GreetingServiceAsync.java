package bistu.icdd.edu.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {

	void loadData(String function[], String effective[],
			AsyncCallback<String> callback);



	void pageChange(int start, int end,int type, AsyncCallback<String> callback);
	
}
