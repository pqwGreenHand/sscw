package com.zhixin.zhfz.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;

public class MonitorTool {

	private static Logger logger = Logger.getLogger(MonitorTool.class);

	
	public static final boolean isHostOnline(String ip) {
		boolean f = false;
		try {
//			int timeOut = 2000; // 超时
//			f = InetAddress.getByName(ip).isReachable(timeOut); // 当返回值是true在线
			f = ping(ip, 3, 2000);
			logger.info(ip + " ping status= " + f);
		} catch (Exception e) {
			logger.error("", e);
		}
		return f;
	}

	public static final boolean isHttpOnline(String urlStr) {
		boolean f = false;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setConnectTimeout(20 * 1000);
			httpConnection.setRequestMethod("GET");
			httpConnection.connect();
			int code = httpConnection.getResponseCode();
			logger.info(urlStr + " http code= " + code);
			if (code == 200) {
				f = true;
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return f;
	}

	public static final boolean isFTPOnline(String ip, String ftpUser, String ftpPwd) {
		boolean f = false;
		FTPClient ftpClient = null;
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(ip);// 连接FTP服务器
			ftpClient.login(ftpUser, ftpPwd);
			f = ftpClient.isConnected();
			logger.info(ip + " ftp isConnected= " + f);
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			try {
				ftpClient.disconnect();
			} catch (Exception e) {
			}
		}
		return f;
	}

	public static final boolean isMysqlOnline(String ip, String name, String pwd) {
		boolean f = false;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnect("mysql", "jdbc:mysql://" + ip + ":3306/interrogate_center_db", name, pwd);
			String sql = "select 1";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				logger.info("mysql rs= " + rs.getInt(1));
				f = true;
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			closeConnection(conn, stmt, rs);
		}
		return f;
	}

	public static final boolean isOracleOnline(String ip, String name, String pwd) {
		boolean f = false;
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnect("mysql", "jdbc:oracle:thin:@" + ip + ":1521:orcl", name, pwd);
			String sql = "select 1 from dual";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				logger.info("mysql rs= " + rs.getInt(1));
				f = true;
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			closeConnection(conn, stmt, rs);
		}
		return f;
	}

	private static Connection getConnect(String type, String url, String name, String pwd) {
		try {
			if ("mysql".equalsIgnoreCase(type)) {
				DriverManager.registerDriver(new org.gjt.mm.mysql.Driver());
			} else if ("oracle".equalsIgnoreCase(type)) {
				DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			}
			Connection conn = DriverManager.getConnection(url, name, pwd);
			return conn;
		} catch (Exception e) {
			logger.error("database config error" + "address=" + url + "  name=" + name + " pwd=" + pwd, e);
			return null;
		}
	}

	private static void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
		try {
			rs.close();
		} catch (Exception e) {
		}
		try {
			stmt.close();
		} catch (Exception e) {
		}
		try {
			conn.close();
		} catch (Exception e) {
		}
	}
	
	
	public static boolean ping(String ipAddress, int pingTimes, int timeOut) {  
        BufferedReader in = null;  
        Runtime r = Runtime.getRuntime();  // 将要执行的ping命令,此命令是windows格式的命令  
        String pingCommand = "ping " + ipAddress + " -n " + pingTimes    + " -w " + timeOut;  
        try {   // 执行命令并获取输出  
            Process p = r.exec(pingCommand);   
            if (p == null) {    
                return false;   
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));   // 逐行检查输出,计算类似出现=23ms TTL=62字样的次数  
            int connectedCount = 0;   
            String line = null;   
            while ((line = in.readLine()) != null) {    
                connectedCount += getCheckResult(line);   
            }   // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真  
           return connectedCount == pingTimes;  
        } catch (Exception ex) {   
            ex.printStackTrace();   // 出现异常则返回假  
           return false;  
        } finally {   
            try {    
                in.close();   
            } catch (IOException e) {    
                e.printStackTrace();   
            }  
        }
    }
	
    //若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.
   private static int getCheckResult(String line) {  // System.out.println("控制台输出的结果为:"+line);  
        Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",Pattern.CASE_INSENSITIVE);  
        Matcher matcher = pattern.matcher(line);  
        while (matcher.find()) {
            return 1;
        }
        return 0; 
    }

	public static void main(String[] args) {
		System.out.println(isHostOnline("192.168.201.10"));
		System.out.println(isHostOnline("192.168.201.13"));
		System.out.println(isHttpOnline("http://www.baidu.com/"));
		// System.out.println(isHttpOnline("http://127.0.0.1/"));
		System.out.println(isMysqlOnline("192.168.201.10", "interrogate", "xbw204"));
	}

}
