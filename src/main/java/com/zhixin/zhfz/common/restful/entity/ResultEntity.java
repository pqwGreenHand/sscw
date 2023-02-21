package com.zhixin.zhfz.common.restful.entity;

public class ResultEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String message;
	private boolean status;
	private String jsonString;
	private Object anything;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getJsonString() {
		return jsonString;
	}
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	
	public Object getAnything() {
		return anything;
	}
	public void setAnything(Object anything) {
		this.anything = anything;
	}
	@Override
	public String toString() {
		return "ResultEntity [message=" + message + ", status=" + status + ", jsonString=" + jsonString + ", anything="
				+ anything + "]";
	}
	
}
