package com.zhixin.zhfz.common.common;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class CabinetUtilZhiXin {

	private static Logger logger = Logger.getLogger(CabinetUtilZhiXin.class);

	public static void main(String[] args) throws Exception {
		CabinetUtilZhiXin util = new CabinetUtilZhiXin();
		// 10.11.229.228 1002 1号
		// 10.11.229.228 1002 2号
		util.openBoxOne("01", "01", "10.58.224.34", 1002);
		// util.openBoxAll("01", "10.11.229.228", 1002);

	}

	/*


	/**
	 * 强制单门
	 * M888888K01，点发送01门应该会直接弹开，M888888K00开全部门（注：M后面的888888为之前修改设置的密码）
	 * @param group
	 *            组号 10组
	 * @param no
	 *            柜号 15号
	 */
	public String openBoxOne(String group, String no, String host, int port) throws Exception{
		if (group == null || no == null)
		{
			logger.info("group or no is null");
			return null;
		}
		String returnMsg = "";
		logger.info("group:"+group+",no:"+no+",host:"+host+",port:"+port);
		String order = getOrder(group, no);
		System.err.println("强制单门指令  zhixin：" + order);
		RequestSocket request = null;
		try {
			request = new RequestSocket(host, port);
			request.sendOrder(order);
			//returnMsg = request.getReturnMsg();
			request.close();
		} catch (Exception e) {
			if (request != null) {
				request.close();
				request=null;
			}
			e.printStackTrace(System.out);
			logger.error(e.getMessage(), e);
			throw e;
		}
		
		if (request != null) {
				request.close();
		}
			
		
		System.out.println("### returnMsg ### " + returnMsg);
		if (returnMsg != null && returnMsg.length() >= 9) {
			return returnMsg.substring(7, 9);
		}
		return null;
	}

	/*
	 M888888K01，点发送01门应该会直接弹开，M888888K00开全部门（注：M后面的888888为之前修改设置的密码）
	 * 
	 */

	/**
	 * 强制打开所有柜
	 * 
	 * @param group
	 *            组号 10组
	 * @param no
	 *            柜号 15号
	 */
	public String openBoxAll(String group, String host, int port) {
		if (group == null)
			return null;
		String returnMsg = null;
		String order = getOrder(group, "00");
		System.err.println("强制打开所有柜指令：" + order);
		RequestSocket request = null;
		try {
			request = new RequestSocket(host, port);
			request.sendOrder(order);
			//returnMsg = request.getReturnMsg();
			request.close();
		} catch (Exception e) {
			if (request != null) {
				request.close();
			}
			e.printStackTrace(System.out);
			logger.error("", e);
		}
		System.out.println("### returnMsg ### " + returnMsg);
		if (returnMsg != null && returnMsg.length() >= 9) {
			return returnMsg.substring(7, 9);
		}
		return null;
	}

	// 获取指令
	private String getOrder(String group, String no) {
		//String password=PropertyUtil.getContextProperty("zhixin.box.admin.password").toString();
		String password="888888";
		String order="M"+password+"K"+no;
		return order.toUpperCase();
	}

	private class RequestSocket {

		private Socket socket = null;
		private BufferedReader reader = null;
		private PrintWriter writer = null;

		public RequestSocket(String host, int port) throws Exception {
			socket = new Socket(host, port);
			// socket.setSoTimeout(10000);
			// socket.setKeepAlive(true);
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

			// socket.connect(new SocketAddress(host), port);
		}

		public void sendOrder(String order) {
			logger.info("------------order start-----------" + order);
			System.err.println("------------order start-----------" + order);
			writer.println(order);
			writer.flush();
			System.err.println("------------order end-----------" + order);
		}

		public String getReturnMsg() throws Exception {
			System.err.println("------------read start-----------");
			String ret = reader.readLine();
			// while ((ret = reader.readLine()) != null) {
			// }
			System.out.println("===get message==" + ret);
			return ret;
		}

		public void close() {
			try {
				if (socket != null) {
					socket.close();
					socket = null;
				}
			} catch (Exception e) {
				e.printStackTrace(System.out);
				logger.error("", e);
			}
			try {
				if (reader != null) {
					reader.close();
					reader = null;
				}
			} catch (Exception e) {
				e.printStackTrace(System.out);
				logger.error("", e);
			}
			try {
				if (writer != null) {
					writer.close();
					writer = null;
				}
			} catch (Exception e) {
				e.printStackTrace(System.out);
				logger.error("", e);
			}
		}
	}

}
