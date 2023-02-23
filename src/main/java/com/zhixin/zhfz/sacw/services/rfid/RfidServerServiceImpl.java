package com.zhixin.zhfz.sacw.services.rfid;

import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import com.zhixin.zhfz.glpt.entity.AlarmEntity;
import com.zhixin.zhfz.sacw.entity.*;
import com.zhixin.zhfz.sacw.services.label.ILabelService;
import com.zhixin.zhfz.sacw.services.sapersonalconfig.ISaPersonalConfigDetailService;
import com.zhixin.zhfz.sacw.services.sapositionalarm.ISaPositionAlarmService;
import com.zhixin.zhfz.sacw.services.sapositiondata.ISaPositionDataService;
import com.zhixin.zhfz.sacw.services.sapositiondevice.ISaPositionDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.DataInputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RfidServerServiceImpl implements IRfidServerService {


    private ServerSocket server = null;
    private static String svrhost = "127.0.0.1";
    private static int svrport = 9000;
    private static int intervalSecond = 1;//默认接收数据的间隔时间，秒
    private static int repeatDataIntervalSecond = 1;//插入同一位置正常数据的时间间隔(单位秒)
    private static int repeatDataAlarmIntervalSecond = 1;//插入同一位置报警数据的时间间隔(单位秒)
    private static String alarmList = "";
    private boolean isActive = true;
    public static Map<Integer, LabelEntity> activeLabelMap = new HashMap<>();//已激活的RFID标签
    private static Map<Integer, SaPositionDeviceEntity> positionDeviceMap = new HashMap<>();//所有激活器与房间的对应关系
    private static Map<Integer, Map<Integer, Long>> positionDataMap = new HashMap<>();//每个标签最新的定位数据

    private static final Logger logger = LoggerFactory.getLogger(RfidServerServiceImpl.class);
    @Autowired
    private ILabelService labelService;

    @Autowired
    private ISaPositionDeviceService positionDeviceService;


    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private ISaPersonalConfigDetailService detailService;


    @Autowired
    private ISaPositionAlarmService positionAlarmService;

    @Autowired
    private ISaPositionDataService positionDataService;


    public RfidServerServiceImpl() {


    }

    //构造函数执行之后再执行该方法
    @PostConstruct
    public void init() {


    }

    private class Accepter extends Thread {

        public Accepter() {
            super("SocketServer.Accepter");
        }

        public void run() {
            try {
                while (isActive) {
                    try {
                        Socket socket = server.accept();
                        logger.info("Server accept a socket!");

                        // socket.setReceiveBufferSize(1048576);
                        // socket.setSendBufferSize(1048576);
                        new Connection(socket).start();
                    } catch (Exception e) {
                        e.printStackTrace(System.out);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
        }

    }

    private class Connection extends Thread {
        private Socket socket = null;
        private boolean isDisconnect = false;
        /**
         * socket inputStream
         */
        private DataInputStream income = null;
        /**
         * socket outputStream
         */
        //private PrintWriter outgo = null;
        private String connectionName = null;

        public Connection(Socket socket) {
            // set thread name
            super("Connection.Connection." + System.currentTimeMillis());

            try {
                this.socket = socket;
                this.socket.setSoTimeout(100000);
                //this.socket.set
                this.socket.setKeepAlive(true);
                this.income = new DataInputStream(this.socket.getInputStream());
                //this.outgo = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream()));
                this.connectionName = this.socket.getInetAddress().getHostName() + ":" + this.socket.getPort();
                logger.info(connectionName + " is connecting!");
            } catch (Exception e) {
                logger.info(connectionName + " connecting exception!!!");
                e.printStackTrace();
                logger.info(connectionName + " closed");
                this.close();
            }
        }

        private byte[] bytes = new byte[10000];

        public void run() {
            try {
                while (true) {
                    Thread.sleep(intervalSecond * 1000);//暂停一段时间接受数据
                    int result = income.read(bytes);
                    if (result != -1) {
                        parser(bytes);//解析数据
                    }
                }
            } catch (Exception e) {
                logger.info(connectionName + " connecting exception!!!");
                e.printStackTrace(System.out);
                logger.info(connectionName + " closed");
                this.close();
            }
        }

        private void close() {
            try {
                if (this.income != null) {
                    this.income.close();
                    this.income = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
			/*try {
				if (this.outgo != null) {
					this.outgo.close();
					this.outgo = null;
				}
			} catch (Exception e) {
				e.printStackTrace(System.out);
			}*/
            try {
                if (this.socket != null) {
                    this.socket.close();
                    this.socket = null;
                }
            } catch (Exception e) {
                e.printStackTrace(System.out);
            }
            this.connectionName = null;
        }// close

    }// Connection

    //解析数据-----------------------------------start
    // 通过TCP接收到的数据传入此方法，可以解析数据
    public void parser(byte[] bytes) throws Exception {

        //printBytes(bytes);

        // 报文开头是否正确
        if (!"0x55".equalsIgnoreCase(byteToHexString(bytes[0]))
                && !"0xAA".equalsIgnoreCase(byteToHexString(bytes[1]))) {
            logger.info("报文错误[" + new String(bytes) + "]");
            return;
        }
        // 检查长度是否正确
        int datalength = byteToUnSignedInt(bytes[2]) + byteToUnSignedInt(bytes[3]) * 256;
        if (datalength > bytes.length) {
            logger.info("报文长度错误");
            return;
        }

        // 处理数据
        if ("0x01".equalsIgnoreCase(byteToHexString(bytes[6]))) {
            // 标签数据长度,Data[]域第一个字节，返回此条数据中共计有多张标签
            int targetCount = byteToUnSignedInt(bytes[8]);
            // 阅读器（基站）编号
            int dno = byteToUnSignedInt(bytes[4]) + byteToUnSignedInt(bytes[5]) * 256;
            // 发送的顺序0-255，满255后从0开始重新计数
            int sn = byteToUnSignedInt(bytes[7]);
            // 循环解析每个标签的数据
            for (int i = 0; i < targetCount; i++) {
                int start = 9 + i * 9;
                // 标签编号
                int targetNo = byteToUnSignedInt(bytes[start + 2]) + byteToUnSignedInt(bytes[start + 3]) * 256
                        + byteToUnSignedInt(bytes[start + 4]) * 256 * 256;
                // 标签状态位
                String status = Integer.toBinaryString(bytes[start + 5]);
                if (status.length() < 8) {
                    int count = 8 - status.length();
                    for (int j = 0; j < count; j++) {
                        status = "0" + status;
                    }
                } else {
                    status = status.substring(status.length() - 8);
                }
                // 标签类型
                int targetType = byteToUnSignedInt(bytes[start + 1]);
                // 2.4g场强（阅读器、基站）
                int g24 = byteToUnSignedInt(bytes[start]);
                // 125k场强（激活器、触发器）
                int k125 = byteToUnSignedInt(bytes[start + 8]);
                // 激活器天线ID
                int aid = byteToUnSignedInt(bytes[start + 6]) + byteToUnSignedInt(bytes[start + 7]) * 256;
                // 0：表示不激活 1：表示激活
                int isActivate = Integer.valueOf(status.substring(0, 1));
                // 0：表示电压正常 1：表示电压低
                int isLowPower = Integer.valueOf(status.substring(1, 2));

                handlerLogic(aid, targetNo, isActivate, isLowPower);

//				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//				String date = df.format(new java.util.Date());// new Date()为获取当前系统时间，也可使用当前时间戳
//				System.out.println(date+"; 激活器ID="+aid+"; 是否激活="+(isActivate==1?"已激活":"未激活")+"; 标签ID="+targetNo);

            }
        }
    }

    public String byteToHexString(byte b) {
        StringBuilder stringBuilder = new StringBuilder("");
        String str = Integer.toHexString(0xff & b);
        if (str.length() < 2) {
            stringBuilder.append("0x0").append(str);
        } else {
            stringBuilder.append("0x").append(str);
        }
        return stringBuilder.toString();
    }

    public int byteToUnSignedInt(byte by) {
        return 0xff & by;
    }

    public void printBytes(byte[] bytes) {
        if (bytes == null) {
            System.out.println("=== bytes is null ===");
        } else {
            StringBuilder sb = new StringBuilder("[");
            for (byte b : bytes) {
                sb.append(byteToHexString(b)).append(",");
            }
            sb.append("]");
            System.out.println("=== bytes is ===" + sb.toString());
        }
    }

    public static void main(String[] args) {
//		try {
//			new RfidServerServiceImpl();
//		} catch (Exception e) {
//			System.out.println(" Server connecting exception!!!");
//			e.printStackTrace();
//		}
    }

    public void handlerLogic(int deviceNo, int labelNo, int isActive, int isLowPower) {
        try {
            if (isActive == 1 && activeLabelMap.containsKey(labelNo)) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String date = df.format(new java.util.Date());// new Date()为获取当前系统时间，也可使用当前时间戳
                logger.info(date + "; deviceNo=" + deviceNo + "; labelNo=" + labelNo);

                Map<Integer, Long> deviceNoMap = new HashMap<>();

                SaPositionDataEntity positionDataEntity = new SaPositionDataEntity();
                Integer labelId = activeLabelMap.get(labelNo).getId();
                positionDataEntity.setLabelId(labelId);
                Integer deviceId = positionDeviceMap.get(deviceNo).getId();
                positionDataEntity.setDeviceId(deviceId);
                Integer locationId = positionDeviceMap.get(deviceNo).getLocationId();
                Integer warehouseId = positionDeviceMap.get(deviceNo).getWarehouseId();
                positionDataEntity.setLocationId(locationId);
                positionDataEntity.setWarehouseId(warehouseId);
                positionDataEntity.setLowPower(isLowPower);

                if (!positionDataMap.containsKey(labelNo)) {
                    //第一次收到某个RFID标签的数据
                    //写插入数据库定位数据代码 todo

                    if (alarmList.indexOf(deviceNo + "") != -1) {
                        //属于是告警编辑器编号，编写告警数据插入逻辑
                        logger.info("111111111111111111111111111");
                        //写插入数据库报警数据代码 todo
                       BoundAlarmEntity alarmEntity = new BoundAlarmEntity();
                        alarmEntity.setLabelId(positionDataEntity.getLabelId());
                        alarmEntity.setDeviceId(positionDataEntity.getDeviceId());
                        alarmEntity.setLocationId(positionDataEntity.getLocationId());
                        alarmEntity.setWarehouseId(positionDataEntity.getWarehouseId());


                        alarmEntity.setAlarmType(3);
                        alarmEntity.setAlarmName("非法出库告警");
                        positionAlarmService.insert(alarmEntity);

                        deviceNoMap.put(deviceNo, System.currentTimeMillis());
                        positionDataMap.put(labelNo, deviceNoMap);
                        logger.info("PositionAlarmEntity=" + alarmEntity);
                    } else {
                        logger.info("22222222222222222222222");
                        //写插入数据库定位数据代码 todo
                        positionDataService.insert(positionDataEntity);
                        deviceNoMap.put(deviceNo, System.currentTimeMillis());
                        positionDataMap.put(labelNo, deviceNoMap);
                    }

                } else {

                    deviceNoMap = positionDataMap.get(labelNo);//获取标签

                    Long currentTime = System.currentTimeMillis();
                    Long lastTime = Long.parseLong(deviceNoMap.get(deviceNo).toString());
                    Long intervalTime = currentTime - lastTime;//间隔的时间戳

                    if (alarmList.indexOf(deviceNo + "") != -1) {
                        //属于是告警编辑器编号，编写告警数据插入逻辑
                        if (intervalTime >= repeatDataAlarmIntervalSecond * 1000) {
                            logger.info("333333333333333333333333");
                            //写插入数据库报警数据代码 todo
                            BoundAlarmEntity positionAlarmEntity = new BoundAlarmEntity();
                            positionAlarmEntity.setLabelId(positionDataEntity.getLabelId());
                            positionAlarmEntity.setDeviceId(positionDataEntity.getDeviceId());
                            positionAlarmEntity.setLocationId(positionDataEntity.getLocationId());
                            positionAlarmEntity.setWarehouseId(positionDataEntity.getWarehouseId());
                            positionAlarmEntity.setAlarmType(3);
                            positionAlarmEntity.setAlarmName("非法出库告警");
                            positionAlarmService.insert(positionAlarmEntity);

                            deviceNoMap.put(deviceNo, System.currentTimeMillis());
                            positionDataMap.put(labelNo, deviceNoMap);
                            logger.info("PositionAlarmEntity=" + positionAlarmEntity);
                        }

                    } else {

                        if (intervalTime >= repeatDataIntervalSecond * 1000) {
                            logger.info("44444444444444444444444");
                            //写插入数据库定位数据代码 todo
                            positionDataService.insert(positionDataEntity);
                            deviceNoMap.put(deviceNo, System.currentTimeMillis());
                            positionDataMap.put(labelNo, deviceNoMap);
                        }
                    }


                }

            }
        } catch (Exception ex) {
            logger.error("数据处理异常=" + ex.getMessage());
            ex.printStackTrace();
        }
    }

}
