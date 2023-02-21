package com.zhixin.zhfz.common.entity;

import java.io.Serializable;

/*
 * 手环
 * */
public class BarCodePrinterConfigEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String barCodePrinterName;

	private String barCodePrinterPort;

	@Override
	public String toString() {
		return "BarCodePrinterConfigEntity [barCodePrinterName=" + barCodePrinterName + ", barCodePrinterPort="
				+ barCodePrinterPort + "]";
	}

	public String getBarCodePrinterName() {
		return barCodePrinterName;
	}

	public void setBarCodePrinterName(String barCodePrinterName) {
		this.barCodePrinterName = barCodePrinterName;
	}

	public String getBarCodePrinterPort() {
		return barCodePrinterPort;
	}

	public void setBarCodePrinterPort(String barCodePrinterPort) {
		this.barCodePrinterPort = barCodePrinterPort;
	}

	
}