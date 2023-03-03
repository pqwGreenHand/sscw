package com.zhixin.zhfz.common.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;

public class FreemarkerUtil {
    /**
     * @param clazz
     * @param clazzPath
     * @return
     */
    public static Configuration builderConfig(Class<?> clazz, String clazzPath) {
        Configuration config = new Configuration();
        config.setDefaultEncoding("utf-8");
        config.setClassForTemplateLoading(clazz, clazzPath);
        config.setClassicCompatible(true);
        return config;
    }

    public static Configuration builderConfig(String templatePath) throws IOException {
        Configuration config = new Configuration();
        config.setDefaultEncoding("utf-8");
        config.setDirectoryForTemplateLoading(new File(templatePath));
        config.setClassicCompatible(true);
        return config;
    }

    /**
     * 获取模板
     *
     * @param clazz
     * @param clazzPath
     * @param templateName
     * @return
     * @throws IOException
     */
    public static Template getTemplate(Class<?> clazz, String clazzPath, String templateName) throws IOException {
        Template t = builderConfig(clazz, clazzPath).getTemplate(templateName);
        t.setEncoding("utf-8");
        return t;
    }

    /**
     * 获取模板
     *
     * @param templatePath 模板所在文件夹路径
     * @param templateName 模板名
     * @return
     * @throws IOException
     */
    public static Template getTemplate(String templatePath, String templateName) throws IOException {
        Template t = builderConfig(templatePath).getTemplate(templateName);
        t.setEncoding("utf-8");
        return t;
    }

    /**
     * 输出内容
     *
     * @param t
     * @param dataMap
     * @param out
     * @throws TemplateException
     * @throws IOException
     */
    public static void process(Template t, Map<?, ?> dataMap, Writer out) throws TemplateException, IOException {
        t.process(dataMap, out);
        out.close();
    }

    /**
     * @param templatePath 模板文件存放路径
     * @param templateName 模板文件名称
     * @param fileName     生成的文件名称
     * @param dataMap
     * @throws IOException
     * @throws TemplateException
     */
    public static void process(String templatePath, String templateName, String fileName, Map<?, ?> dataMap) throws IOException, TemplateException {
        Template t = getTemplate(templatePath, templateName);
        // 合并数据模型与模板
        FileOutputStream fos = new FileOutputStream(new File(fileName));
        Writer out = new OutputStreamWriter(fos, "UTF-8");
        t.process(dataMap, out);
        out.flush();
        out.close();
    }

    public static void process(String templatePath, String templateName, Map<?, ?> dataMap, Writer out) throws IOException, TemplateException {
        // 获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
        // 否则会出现乱码
        Template t = getTemplate(templatePath, templateName);
        t.process(dataMap, out);
        out.close();
    }

    /**
     * 输出内容
     *
     * @param clazz
     * @param clazzPath
     * @param templateName
     * @param dataMap
     * @param out
     * @throws IOException
     * @throws TemplateException
     */
    public static void process(Class<?> clazz, String clazzPath, String templateName, Map<?, ?> dataMap, Writer out) throws IOException, TemplateException {
        Template t = getTemplate(clazz, clazzPath, templateName);
        t.process(dataMap, out);
        out.close();
    }
}
