package com.zhixin.zhfz.common.utils;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class OfficeUtil {

    private static final Logger logger = LoggerFactory.getLogger(OfficeUtil.class);

    /**
     * word文档转换html函数
     *
     * @param docfile  word文档的绝对路径加文件名(包含扩展名)
     * @param htmlfile 转换后的html文件绝对路径和文件名(不含扩展名)
     */
    public static void wordTohtml(String docfile, String htmlfile) {
        if (docfile.substring(docfile.lastIndexOf('.') + 1).indexOf("doc") < 0) {
            logger.info("OfficetoHtml.wordTohtml : 不是word文档");
            return;
        }
        ActiveXComponent app = null;
        try {
            app = new ActiveXComponent("Word.Application"); // 启动word
            app.setProperty("Visible", new Variant(false));
            // 设置word不可见
            Dispatch docs = app.getProperty("Documents").toDispatch();
            Dispatch doc = Dispatch.invoke(
                    docs,
                    "Open",
                    Dispatch.Method,
                    new Object[]{docfile, new Variant(false),
                            new Variant(true)}, new int[1]).toDispatch();
            // 打开word文件
            Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[]{
                    htmlfile, new Variant(8)}, new int[1]);
            // 作为html格式保存到临时文件
            Variant f = new Variant(false);
            Dispatch.call(doc, "Close", f);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (app != null) {
                app.invoke("Quit", new Variant[]{});
            }
        }
    }

    /**
     * word文档转换txt函数
     *
     * @param docfile word文档的绝对路径加文件名(包含扩展名)
     * @param txtfile 转换后的html文件绝对路径和文件名(不含扩展名)
     */
    public static void wordTotxt(String docfile, String txtfile) {
        if (docfile.substring(docfile.lastIndexOf('.') + 1).indexOf("doc") < 0) {
            logger.info("OfficetoHtml.wordTotxt : 不是word文档");
            return;
        }
        ActiveXComponent app = null;
        try {
            app = new ActiveXComponent("Word.Application"); // 启动word
            app.setProperty("Visible", new Variant(false));
            // 设置word不可见
            Dispatch docs = app.getProperty("Documents").toDispatch();
            Dispatch doc = Dispatch.invoke(
                    docs,
                    "Open",
                    Dispatch.Method,
                    new Object[]{docfile, new Variant(false),
                            new Variant(true)}, new int[1]).toDispatch();
            // 打开word文件
            Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[]{
                    txtfile, new Variant(7)}, new int[1]);
            // 作为html格式保存到临时文件
            Variant f = new Variant(false);
            Dispatch.call(doc, "Close", f);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (app != null) {
                app.invoke("Quit", new Variant[]{});
            }
        }
    }

    /**
     * excel文档转换函数
     *
     * @param xlsfile  excel文档的绝对路径加文件名(包含扩展名)
     * @param htmlfile 转换后的html文件绝对路径和文件名(不含扩展名)
     */
    public static void excelTohtml(String xlsfile, String htmlfile) {
        if (xlsfile.substring(xlsfile.lastIndexOf('.') + 1).indexOf("xls") < 0) {
            logger.info("OfficetoHtml.excelTohtml : 不是excel文档");
            return;
        }
        ActiveXComponent app = null;
        try {
            app = new ActiveXComponent("Excel.Application"); // 启动excel
            app.setProperty("Visible", new Variant(false));
            // 设置word不可见
            Dispatch docs = app.getProperty("Workbooks").toDispatch();
            Dispatch doc = Dispatch.invoke(
                    docs,
                    "Open",
                    Dispatch.Method,
                    new Object[]{xlsfile, new Variant(false),
                            new Variant(true)}, new int[1]).toDispatch();
            // 打开word文件
            Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[]{
                    htmlfile, new Variant(44)}, new int[1]);
            // 作为html格式保存到临时文件
            Variant f = new Variant(false);
            Dispatch.call(doc, "Close", f);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            if (app != null) {
                app.invoke("Quit", new Variant[]{});
            }
        }
    }

    /**
     * xml转文件
     *
     * @param xmlFilePath xml模板目录
     * @param xmlFileName xml模板文件名
     * @param path        生成文件路径（含文件名）
     * @param mapq        替换的数据
     */
    public static void xmlToOffice(String xmlFilePath, String xmlFileName, String path, Map mapq) {
        //生成模板文件
        try {
            FreemarkerUtil.process(xmlFilePath, xmlFileName, path, mapq);
        } catch (Exception e) {
            logger.error("", e);
        }
    }

    public static void main(String[] strs) {
        OfficeUtil
                .excelTohtml(
                        "C:\\Users\\java_wolf\\Desktop\\test.xlsx",
                        "C:\\Users\\java_wolf\\Desktop\\test.xlsx.html");
//        WorkView
//                .wordTohtml(
//                        "C:\\Users\\java_wolf\\Desktop\\test.doc",
//                        "D:\\INTERROGATE_CENTER_PLATFORM\\interrogate-web\\src\\main\\webapp\\test.doc.html");
        // File f=new File("C:\\Program Files\\Apache Software
        // Foundation\\Tomcat 5.5\\webapps\\gzpysf\\a.htm");
        // f.delete();
    }
}
