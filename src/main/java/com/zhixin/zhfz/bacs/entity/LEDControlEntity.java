package com.zhixin.zhfz.bacs.entity;

public class LEDControlEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	public static final String COLOR_RED = "red";
	public static final String COLOR_GREEN = "green";
	public static final String COLOR_YELLOW = "yellow";
	public static final int SHOWTYPE_STATIC = 1;
	public static final int SHOWTYPE_RUN = 2;
	public static final int POWER_ON=1;
	public static final int POWER_OFF=0;
	public static final int POWER_PLAY=17;
	public static final int POWER_DOWNLOAD=18;

	private int power;
	/** LED屏幕IP **/
	private String ip;
	/** LED屏幕端口 **/
	private int port;
	/** 需要显示的文字内容 使用中 空闲中 已预订等 **/
	private String content;
	/** 需要显示的文字颜色 red green yellow **/
	private String color;
	/** 文字显示方式 0静止显示，1滚动显示 **/
	private int showType;
	/**保存方式，临时保存值为17，永久保存值为18*/
	private int saveMethod;

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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getShowType() {
		return showType;
	}

	public void setShowType(int showType) {
		this.showType = showType;
	}

	public int getSaveMethod() {
		return saveMethod;
	}

	public void setSaveMethod(int saveMethod) {
		this.saveMethod = saveMethod;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	@Override
	public String toString() {
		return "{\"power\":\"" + power + "\", \"ip\":\"" + ip + "\", \"port\":\"" + port + "\", \"content\":\""
				+ content + "\", \"color\":\"" + color + "\", \"showType\":\"" + showType + "\", \"saveMethod\":\"" + saveMethod + "\"}";
	}

}
