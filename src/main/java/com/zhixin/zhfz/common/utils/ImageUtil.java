package com.zhixin.zhfz.common.utils;

import com.zhixin.zhfz.common.common.FileUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.*;

public class ImageUtil {

    private static Logger logger = Logger.getLogger(ImageUtil.class);

    static BASE64Encoder encoder = new BASE64Encoder();

    public static BufferedImage resize(BufferedImage source, int targetW, int targetH) {
        // targetW，targetH分别表示目标长和宽
        int type = source.getType();
        BufferedImage target = null;
        double sx = (double) targetW / source.getWidth();
        double sy = (double) targetH / source.getHeight();
        // 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放
        // 则将下面的if else语句注释即可
        if (sx > sy) {
            sx = sy;
            targetW = (int) (sx * source.getWidth());
        } else {
            sy = sx;
            targetH = (int) (sy * source.getHeight());
        }
        if (type == BufferedImage.TYPE_CUSTOM) { // handmade
            ColorModel cm = source.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(targetW, targetH);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();
            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
        } else {
            target = new BufferedImage(targetW, targetH, type);
        }
        Graphics2D g = target.createGraphics();
        // smoother than exlax:
        g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
        g.dispose();
        return target;
    }

    public static void saveImageAsJpg(String fromFileStr, String saveToFileStr, int width, int hight) throws Exception {
        BufferedImage srcImage;
        // String ex =
        // fromFileStr.substring(fromFileStr.indexOf("."),fromFileStr.length());
        String imgType = "JPEG";
        if (fromFileStr.toLowerCase().endsWith(".png")) {
            imgType = "PNG";
        }
        // System.out.println(ex);
        File saveFile = new File(saveToFileStr);
        File fromFile = new File(fromFileStr);
        srcImage = ImageIO.read(fromFile);
        if (width > 0 || hight > 0) {
            srcImage = resize(srcImage, width, hight);
        }
        ImageIO.write(srcImage, imgType, saveFile);
    }

    public static String getImageBinary(String picPath, String picType) {
        File f = null;
        BufferedImage bi = null;
        ByteArrayOutputStream baos = null;
        try {
            f = new File(picPath);
            bi = ImageIO.read(f);
            baos = new ByteArrayOutputStream();
            ImageIO.write(bi, picType, baos);
            byte[] bytes = baos.toByteArray();
            return encoder.encodeBuffer(bytes).trim();
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            try {
                baos.close();
            } catch (Exception e) {
                logger.error("", e);
            }
        }
        return null;
    }

    public static String yituGetImageStr(String imagepath) {
        InputStream in = null;
        byte[] data = null;

        String cardBase64 = "";
        Base64 decoder = new Base64();
        try {
            in = new FileInputStream(imagepath);
            data = new byte[in.available()];
            in.read(data);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                logger.error("", e);
            }
        }
        cardBase64 = Base64.encodeBase64String(data);
        return cardBase64;
    }


    public static String getImageStr(String imagepath) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imagepath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

    /**
     * base64转image图片
     *
     * @param base64String
     * @param imgPath      生成文件路径名
     */
    public static void base64StringToImage(String base64String, String imgPath) {
        ByteArrayInputStream bais = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes1 = decoder.decodeBuffer(base64String);

            bais = new ByteArrayInputStream(bytes1);
            BufferedImage bi1 = ImageIO.read(bais);
            File w2 = new File(imgPath);// 可以是jpg,png,gif格式
            ImageIO.write(bi1, "jpg", w2);// 不管输出什么格式图片，此处不需改动
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            try {
                bais.close();
            } catch (IOException e1) {
                logger.error("", e1);
            }
        }
    }

    public static void main(String[] args) {
        // String bast =
        // getImageBinary("D:\\faceImageUpload\\20151016\\rq-XYR20151016173030982-yt.jpg",
        // "jpg");
        // base64StringToImage(bast,
        // "D:\\faceImageUpload\\20151016\\rq-test-yt.jpg");
//		String strImg = GetImageStr();
//		System.out.println(strImg);
//		GenerateImage(strImg);
        // System.out.println(getImageStr("C:\\Users\\java_wolf\\Desktop\\test1.jpg"));
        boolean flag = FileUtil.isExist("D:/faceImageUpload/20160420/rq-XYR20160419130531349-yt.jpg");
        System.out.println(flag);
        System.out.println(ImageUtil.getImageStr("D:/faceImageUpload/20160420/rq-XYR20160419130531349-yt.jpg"));
    }

    // 图片转化成base64字符串
    public static String GetImageStr() {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        String imgFile = "D:\\faceImageUpload\\20151016\\rq-XYR20151016173030982-yt.jpg";// 待处理的图片
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

    // base64字符串转化成图片
    public static boolean GenerateImage(String imgStr) { // 对字节数组字符串进行Base64解码并生成图片
        if (imgStr == null) // 图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {// 调整异常数据
                    b[i] += 256;
                }
            }
            // 生成jpeg图片
            String imgFilePath = "D:\\faceImageUpload\\20151016\\rq-test-yt.jpg";// 新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
