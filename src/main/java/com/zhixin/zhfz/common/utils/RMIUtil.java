package com.zhixin.zhfz.common.utils;

import com.ideal.netcare.v5.common.rmi.core.RMIServiceClient;
import com.ideal.netcare.v5.common.rmi.core.RemoteService;
import com.ideal.netcare.v5.common.util.api.dls.DLSReceiverApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RMIUtil {

	private static String ipMaster = "172.16.5.3";
	private static int portMaster = 10000;
	private static String ipBackup = "172.16.5.4";
	private static int portBackup = 10000;

	private static RMIUtil util = null;

	private static final Logger logger = LoggerFactory.getLogger(RMIUtil.class);
	
	private RMIUtil() {
		try {
			ipMaster = PropertyUtil.getContextProperty("master.rmi.service.host").toString();
			portMaster = Integer.parseInt(PropertyUtil.getContextProperty("master.rmi.service.port").toString());
			ipBackup = PropertyUtil.getContextProperty("backup.rmi.service.host").toString();
			portBackup = Integer.parseInt(PropertyUtil.getContextProperty("backup.rmi.service.port").toString());
			logger.info("rmi.service.host=" + ipMaster+" , "+ipBackup);
			logger.info("rmi.service.port=" + portMaster+" , "+portBackup);
		} catch (Exception e) {
			logger.error("load rmi.properties error!", e);
			e.printStackTrace(System.out);
		}
	}

	public static final synchronized RMIUtil getInstance() throws Exception {
		if (util == null) {
			util = new RMIUtil();
		}
		return util;
	}

	@SuppressWarnings("unchecked")
	public <T extends RemoteService> T lookupService(String service) throws Exception {
		try{
			RMIServiceClient client = new RMIServiceClient(ipMaster, portMaster);
			return (T) client.lookup(service);
		}catch (Exception e){
			RMIServiceClient client = new RMIServiceClient(ipBackup, portBackup);
			return (T) client.lookup(service);
		}
	}

	public static void main(String[] args) throws Exception {
		/*try {
			RMIServiceClient client = new RMIServiceClient("172.16.5.3", 10000);
			System.out.println("-----------------1");
			DLSReceiverApiService service = (DLSReceiverApiService) client.lookup("DLSReceiverApiService");
			System.out.println("-----------------2");
			service.isActiveTag(249881, false);
			System.out.println("-----------------3");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		String serviceName = "DLSReceiverApiService";
		DLSReceiverApiService service= null;
		try{
			RMIServiceClient client = new RMIServiceClient(ipMaster, portMaster);
			service =  (DLSReceiverApiService) client.lookup(serviceName);
		}catch (Exception e){
			RMIServiceClient client = new RMIServiceClient(ipBackup, portBackup);
			service =  (DLSReceiverApiService) client.lookup(serviceName);
		}
		System.out.println("-----------------begin");
		service.isActiveTag(249881, false);
		System.out.println("-----------------end");
	}
}
