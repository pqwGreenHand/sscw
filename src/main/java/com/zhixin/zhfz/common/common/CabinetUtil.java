package com.zhixin.zhfz.common.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.log4j.Logger;

public class CabinetUtil {

	private static Logger logger = Logger.getLogger(CabinetUtil.class);

	public static void main(String[] args) throws Exception {
		CabinetUtil util = new CabinetUtil();
		// 10.11.229.228 1002 1号
		// 10.11.229.228 1002 2号
		// util.openBoxOne("2", "01", "10.11.229.226", 1002);

		// util.openBoxOne("01", "01", "10.11.229.228", 1002);
		// util.openBoxOne("01", "04", "10.11.229.228", 1002);
		// util.openBoxOne("01", "09", "10.11.229.228", 1002);
		// util.openBoxOne("01", "15", "10.11.229.228", 1002);
		// util.openBoxOne("01", "12", "10.11.229.228", 1002);
		util.openBoxOne("01", "99", "10.11.229.228", 1002);

		// util.openBoxAll("01", "10.11.229.228", 1002);

		// util.readBoxStatus("02", "10.11.229.228", 1002);
	}

	/*
	 * 计算机“强制单门”操作
	 * 
	 * ⑴ 寄存柜上传“强制单门”数据命令： ！ F4 01 02 0 #
	 * 
	 * 1）2）3） 4） 5） 6） 说明： 1）为前导码 2）为命令 F4为“强制单门”变化上传命令 3）为组号 4）为柜号 5）为校验
	 * 为2）3）4）每位数据的ASC码XOR后，取低4位， 为校验 6）为后导码
	 * 
	 * 
	 * ⑵计算机应答“强制单门”操作 ！ F4 01 01 AA 00 00 #
	 * 
	 * 1） 2） 3） 4） 5） 6） 7） 说明： 1）为前导码 2）为命令 F4 为“强制单门”变化上传命令 3）为组号 4）柜号
	 * 5）应答状态，AA表示OK ，BB 表示NO 6）为校验 为2）3）4）5）XOR 求校验 2） 为后导码
	 */

	/**
	 * 强制单门
	 * 
	 * @param group
	 *            组号 10组
	 * @param no
	 *            柜号 15号
	 */
	public String openBoxOne(String group, String no, String host, int port) throws Exception{
		if (group == null || no == null)
			return null;
		String returnMsg = null;
		String order = getOrder("F4", group, no);
		// String order = "!F4010FAA500000000000#";
		System.err.println("强制单门指令：" + order);
		RequestSocket request = null;
		try {
			request = new RequestSocket(host, port);
			request.sendOrder(order);
			returnMsg = request.getReturnMsg();
			request.close();
		} catch (Exception e) {
			if (request != null) {
				request.close();
			}
			e.printStackTrace(System.out);
			logger.error("", e);
			throw e;
		}
		System.out.println("### returnMsg ### " + returnMsg);
		if (returnMsg != null && returnMsg.length() >= 9) {
			return returnMsg.substring(7, 9);
		}
		return null;
	}

	/*
	 * ⑴ 寄存柜上传“强制打开所有柜门”数据命令： ！ F5 01 00 0 #
	 * 
	 * 1）2） 3） 4） 5） 6） 说明： 1）为前导码 2）为命令 F5 为“强制所有柜门”变化上传命令 3）为组号
	 * 4）为校验为2）3）4）每位数据的ASC码XOR后，取低4位， 为校验 6）为后导码 ⑵ 计算机应答“强制打开所有柜门”操作 ！ F5 01 00
	 * AA 00 #
	 * 
	 * 1） 2） 3） 4） 5） 6） 说明： 1）为前导码 2）为命令 F5 为“强制打开所有柜门”变化上传命令 3）为组号 4) 位补位00
	 * 5）应答状态，AA表示OK ，BB 表示NO 6）为校验 为2）3）4）5）XOR 求校验 7）为后导码
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
		String order = getOrder("F5", group, "00");
		System.err.println("强制打开所有柜指令：" + order);
		RequestSocket request = null;
		try {
			request = new RequestSocket(host, port);
			request.sendOrder(order);
			returnMsg = request.getReturnMsg();
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

	/*
	 * ⑴ 计算机读取组内所有柜门有无物命令： ！ FB 01 00 0 #
	 * 
	 * 1）2）3） 4） 5） 6） 说明： 1）为前导码 2）为命令 FB为读有无物命令 3）为组号 4）为补位00 5）为校验
	 * 为2）3）4）每位数据的ASC码XOR后，取低4位， 为校验 6）为后导码 ⑵ 寄存柜应答有无物操作 ！ FB 01 00 00 00 00 00
	 * 00------00 #
	 * 
	 * 1） 2） 3） 4） 5） 说明： 1）为前导码 2）为命令 FB 为有无物上传命令 3）为组号
	 * 4）有无物位2位1组，从01柜至48柜，00表示无物，01表示有物 3） 为后导码
	 * 
	 */
	/**
	 * 读取有无物
	 * 
	 * @param group
	 *            组号 10组
	 * @param no
	 *            柜号 15号
	 */
	public String[] readBoxStatus(String group, String host, int port) {
		if (group == null)
			return null;
		String order = getOrder("FB", group, "00");
		System.err.println("读取有无物指令：" + order);
		RequestSocket request = null;
		String returnMsg = null;
		try {
			request = new RequestSocket(host, port);
			request.sendOrder(order);
			returnMsg = request.getReturnMsg();
			request.close();
		} catch (Exception e) {
			if (request != null) {
				request.close();
			}
			e.printStackTrace(System.out);
			logger.error("", e);
		}
		System.out.println("### returnMsg ### " + returnMsg);
		String tempStr = null;
		if (returnMsg != null) {
			tempStr = returnMsg.substring(5, returnMsg.length() - 1);
		}
		String[] resultArr = null;
		if (tempStr != null) {
			resultArr = new String[tempStr.length() / 2];
			int i = 0;
			int j = 0;
			for (; i < tempStr.length(); i += 2) {
				resultArr[j] = tempStr.substring(i, i + 1);
				j++;
			}
			return resultArr;
		}
		return null;
	}

	// 获取指令
	private String getOrder(String precode, String group, String no) {
		String xorstr = precode + group + no;
		byte[] bts = xorstr.getBytes();
		byte i = 0;
		for (byte b : bts) {
			i ^= b;
		}
		String xor = Integer.toHexString((i & 0xF)).toString(); // xor校验码
		StringBuffer order = new StringBuffer(); // 指令
		order.append("!").append(precode).append(group).append(no).append(xor).append("#");
		return order.toString().toUpperCase();
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
