package com.zhixin.zhfz.common.common.led;
import com.zhixin.zhfz.bacs.controller.led.LedController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Color;
import java.io.IOException;

/**
 * led直连
 * @author Admin
 *
 */
public class LedUtil {
    private static final Logger logger = LoggerFactory.getLogger(LedUtil.class);

	public static void main(String[] args) throws InterruptedException {
		/*for(int i=0;i<10;i++) {
			Thread.sleep(1000);
			ledPlay("你好"+i, "192.168.201.29", 6666, 0);
		}*/
		
		ledPlay("辨认室", "192.168.201.29", 6666, 0);
		
	}
    public static MyUdpSocket my_udp;
    public static LEDSender2010 ledsender = new LEDSender2010();
    
//    static {
//    	try {
//			 my_udp = new MyUdpSocket(8818);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }

    public static void ledPlay(String text,String ledHost,int ledPort,int addr){
        try {
            //生成节目数据
            logger.info("进入led设置");
            my_udp = new MyUdpSocket(8818);
            ledsender.MakeRoot(LEDSender2010.ROOT_DOWNLOAD, LEDSender2010.COLOR_TYPE_DOUBLE);
            ledsender.AddChapter(1, 1000);
//            ledsender.AddChapterEx(1, 1000, 1, 1, 0x7F, 2016, 4, 28, 10, 10, 0, 2016, 12, 27, 23, 15, 0);
            ledsender.AddRegion(0, 0, 128, 64);

            //点阵文字（单行，支持对齐方式）
            ledsender.AddLeaf(1, 2000);
            //朝阳单独设置一下，辨认室
            if (text.equals("传染病讯问室")) {
                text = "辨认室";
            }
            if(text.indexOf("未成年讯询问")>-1){
                ledsender.AddTextEx(0, 0, 128, 32, text, LEDSender2010.ALIGN_CENTER, LEDSender2010.ALIGN_CENTER, "宋体", 16, Color.BLACK, Color.RED, 1, 1, 0, 2, 0, 0, 0, 0);

            }else{
                ledsender.AddTextEx(0, 0, 128, 32, text, LEDSender2010.ALIGN_CENTER, LEDSender2010.ALIGN_CENTER, "宋体", 24, Color.BLACK, Color.RED, 1, 1, 0, 2, 0, 0, 0, 0);
            }

            send_data(ledHost,ledPort,addr);
            logger.info("led设置成功");
//            my_udp.close();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                my_udp.close();
                logger.info("led销毁成功");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    

    public static void send_data(String ledHost,int ledPort,int addr){
    	int size=0;
        byte[] packet = new byte[1280];
        int i, j, k, x, r;
        int tx, tx_repeat=5;
        boolean ok;
        //下面为通讯代码，包括数据的拆分打包、发送
        //    由于是UDP通讯，增加

        //起始包
        ok=false;
        System.out.println("发送起始包...，序列号=0");
        for (tx=0; tx<tx_repeat; tx++){
            size=ledsender.pkg_begin(packet, addr);
            udp_send(packet, size,ledHost,ledPort);
            for (x=0; x<50; x++){
            	size=udp_receive(packet);
            	if (size>0 && ledsender.parse_trans_respond(packet, size, LEDSender2010.PKC_BEGIN, 0)>0){
                    System.out.println("起始包发送完成，序列号=0");
                    ok=true;
                    break;
            	}
            }
            if (ok) break;
        }
        if (!ok){
            System.out.println("超时");
            return;
        }

        //数据包
        k=ledsender.get_pkg_count(512);
        i=1;
        while (i<=k){
            ok=false;
            System.out.println("发送数据包...，序列号="+i);
            for (tx=0; tx<tx_repeat; tx++){
            	j=i;
                size=ledsender.pkg_data(packet, addr, i, 512);
                udp_send(packet, size,ledHost,ledPort);
                for (x=0; x<50; x++){
                	size=udp_receive(packet);
                	if (size>0){
                		r=ledsender.parse_trans_respond(packet, size, LEDSender2010.PKC_DATA, i);
                		switch(r){
                		case 0x1:
                        	System.out.println("数据包发送完成，序列号="+i);
                            ok=true;
                            j=i+1;
                			break;
                		case 0x2:
                        	System.out.println("数据包序列号校对错误，应收="+i+"，实收="+ledsender.fix_serialno);
                			j=ledsender.fix_serialno;
                			break;
                		}
                        //ok=true;
                        //break;
                	}
                    if (ok) break;
                }
                i=j;
                if (ok) break;
            }
            if (!ok){
                System.out.println("超时");
                return;
            }
        }

        //结束包
        ok=false;
        System.out.println("发送结束包...，序列号="+i);
        for (tx=0; tx<tx_repeat; tx++){
            size=ledsender.pkg_end(packet, addr, i);
            udp_send(packet, size,ledHost,ledPort);
            for (x=0; x<50; x++){
            	size=udp_receive(packet);
            	if (size>0 && ledsender.parse_trans_respond(packet, size, LEDSender2010.PKC_END, i)>0){
                    System.out.println("结束包发送完成，序列号="+i);
                    ok=true;
                    break;
            	}
            }
            if (ok) break;
        }
        if (!ok){
            System.out.println("超时");
            return;
        }
    }
    
    //发送UDP包，buffer为发送数据，size为发送数据长度
    public static void udp_send(byte[] buffer, int size,String ledhost,int ledport){
        byte[] packet = new byte[size];
        ledsender.blockCopy(packet, 0, buffer, 0, size);
        ledsender.print_stream(packet, size);
        try {    
            my_udp.send(ledhost, ledport, packet, size);
        } catch (Exception ex) {    
            ex.printStackTrace();    
        }
    }
    
    //接收UDP包，buffer为接收数据，size为接收数据长度
    public static int udp_receive(byte[] buffer){
        int size=0;
        size=my_udp.receive();
        if(size>0){ 
        	my_udp.get_receive_packet(buffer, size);
        }
        return size;
    }


}
