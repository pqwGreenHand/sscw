package com.zhixin.zhfz.bacs.entity;

public class LED2010Entity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;


	/** LED屏幕IP **/
	private String ip;
	/** LED屏幕端口 **/
	private int port;
	/** 需要显示的文字内容 **/
	private String content;
	/** yanse **/
	private String color;
	
	private int left;
	
	private int top;
	
	private int width;
	
	private int height;
	
	private int fontSize;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	@Override
	public String toString() {
		return "LED2010Entity [ip=" + ip + ", port=" + port + ", content=" + content + ", color=" + color + ", left="
				+ left + ", top=" + top + ", width=" + width + ", height=" + height + ", fontSize=" + fontSize + "]";
	}

	
}
