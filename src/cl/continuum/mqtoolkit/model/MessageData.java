package cl.continuum.mqtoolkit.model;

public class MessageData {
	
	private String fechaHoraTransferencia = "";
	private String formato = "";
	private String datos = "";
	private String messID = "";
	private String corrID = "";
	private String groupID = "";
	private String isPercistente;
	private long caducidad;
	private String replyToQ = "";
	private String replyToQManager = "";
	private int charSetID;
	private int codeCharSet;

	/**
	 * @return the fechaHoraTransferencia
	 */
	public String getFechaHoraTransferencia() {
		return fechaHoraTransferencia;
	}
	/**
	 * @param fechaHoraTransferencia the fechaHoraTransferencia to set
	 */
	public void setFechaHoraTransferencia(String fechaHoraTransferencia) {
		this.fechaHoraTransferencia = fechaHoraTransferencia;
	}
	/**
	 * @return the formato
	 */
	public String getFormato() {
		return formato;
	}
	/**
	 * @param formato the formato to set
	 */
	public void setFormato(String formato) {
		this.formato = formato;
	}
	/**
	 * @return the datos
	 */
	public String getDatos() {
		return datos;
	}
	/**
	 * @param datos the datos to set
	 */
	public void setDatos(String datos) {
		this.datos = datos;
	}
	/**
	 * @return the messID
	 */
	public String getMessID() {
		return messID;
	}
	/**
	 * @param messID the messID to set
	 */
	public void setMessID(String messID) {
		this.messID = messID;
	}
	/**
	 * @return the corrID
	 */
	public String getCorrID() {
		return corrID;
	}
	/**
	 * @param corrID the corrID to set
	 */
	public void setCorrID(String corrID) {
		this.corrID = corrID;
	}
	/**
	 * @return the groupID
	 */
	public String getGroupID() {
		return groupID;
	}
	/**
	 * @param groupID the groupID to set
	 */
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	/**
	 * @return the isPercistente
	 */
	public String isPercistente() {
		return isPercistente;
	}
	/**
	 * @param isPercistente the isPercistente to set
	 */
	public void setPercistente(String isPercistente) {
		this.isPercistente = isPercistente;
	}
	/**
	 * @return the caducidad
	 */
	public long getCaducidad() {
		return caducidad;
	}
	/**
	 * @param caducidad the caducidad to set
	 */
	public void setCaducidad(long caducidad) {
		this.caducidad = caducidad;
	}
	/**
	 * @return the replyToQ
	 */
	public String getReplyToQ() {
		return replyToQ;
	}
	/**
	 * @param replyToQ the replyToQ to set
	 */
	public void setReplyToQ(String replyToQ) {
		this.replyToQ = replyToQ;
	}
	/**
	 * @return the replyToQManager
	 */
	public String getReplyToQManager() {
		return replyToQManager;
	}
	/**
	 * @param replyToQManager the replyToQManager to set
	 */
	public void setReplyToQManager(String replyToQManager) {
		this.replyToQManager = replyToQManager;
	}
	/**
	 * @return the charSetID
	 */
	public int getCharSetID() {
		return charSetID;
	}
	/**
	 * @param charSetID the charSetID to set
	 */
	public void setCharSetID(int charSetID) {
		this.charSetID = charSetID;
	}
	/**
	 * @return the codeCharSet
	 */
	public int getCodeCharSet() {
		return codeCharSet;
	}
	/**
	 * @param codeCharSet the codeCharSet to set
	 */
	public void setCodeCharSet(int codeCharSet) {
		this.codeCharSet = codeCharSet;
	}
	public String[] toArray() {
		return new String[] { datos, messID, corrID, replyToQ, replyToQManager, String.valueOf(isPercistente) ,groupID,formato, String.valueOf(codeCharSet),String.valueOf(charSetID)};
	}
	
	
	
	

}
