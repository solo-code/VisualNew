package bistu.icdd.edu.server.data;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("data")
public class FunctionEffectiveBean {
	
	private int posID = 0;
	private String function = null;
	private String effective = null;
	private int value = 0;
	
	public FunctionEffectiveBean() {
		
	}
	
	public FunctionEffectiveBean(String[] temp) {
		if(temp==null||temp.length!=3)
			return;
		function = temp[0].trim();
		effective = temp[1].trim();
		if(temp[2].matches("\\d+"))
			value = Integer.parseInt(temp[2]);
	}
	
	
	
	/**
	 * @return the function
	 */
	public String getFunction() {
		return function;
	}
	/**
	 * @param function the function to set
	 */
	public void setFunction(String function) {
		this.function = function;
	}
	/**
	 * @return the effective
	 */
	public String getEffective() {
		return effective;
	}
	/**
	 * @param effective the effective to set
	 */
	public void setEffective(String effective) {
		this.effective = effective;
	}
	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		this.value = value;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((effective == null) ? 0 : effective.hashCode());
		result = prime * result
				+ ((function == null) ? 0 : function.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FunctionEffectiveBean other = (FunctionEffectiveBean) obj;
		if (effective == null) {
			if (other.effective != null)
				return false;
		} else if (!effective.equals(other.effective))
			return false;
		if (function == null) {
			if (other.function != null)
				return false;
		} else if (!function.equals(other.function))
			return false;
		return true;
	}
	

	/**
	 * @return the posID
	 */
	public int getPosID() {
		return posID;
	}

	/**
	 * @param posID the posID to set
	 */
	public void setPosID(int posID) {
		this.posID = posID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return function+","+effective+","+value;
	}
}
