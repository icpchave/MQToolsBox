/**
 * 
 */
package cl.continuum.mqtoolkit.model;

/**
 * @author israel
 *
 */
public class QueueData {
	private String name;
	private String type;
	private long deep;
	private long maxDeep;
	private boolean persitence;
	private long maxMsgLength;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the deep
	 */
	public long getDeep() {
		return deep;
	}
	/**
	 * @param deep the deep to set
	 */
	public void setDeep(long deep) {
		this.deep = deep;
	}
	/**
	 * @return the maxDeep
	 */
	public long getMaxDeep() {
		return maxDeep;
	}
	/**
	 * @param maxDeep the maxDeep to set
	 */
	public void setMaxDeep(long maxDeep) {
		this.maxDeep = maxDeep;
	}
	/**
	 * @return the persitence
	 */
	public boolean isPersitence() {
		return persitence;
	}
	/**
	 * @param persitence the persitence to set
	 */
	public void setPersitence(boolean persitence) {
		this.persitence = persitence;
	}
	/**
	 * @return the maxMsgLength
	 */
	public long getMaxMsgLength() {
		return maxMsgLength;
	}
	/**
	 * @param maxMsgLength the maxMsgLength to set
	 */
	public void setMaxMsgLength(long maxMsgLength) {
		this.maxMsgLength = maxMsgLength;
	}
	
	public String[] toArray() {
		String[] arreglo = {name, type, String.valueOf(deep), String.valueOf(maxDeep), String.valueOf(maxMsgLength), String.valueOf(persitence)};
		return arreglo;
	}
	

}
