package bistu.icdd.edu.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String loadData(String function[], String effective[]) throws IllegalArgumentException;

	/**
	 * @return
	 * @throws IllegalArgumentException
	 */
	
	String pageChange(int start,int end,int type);
}
