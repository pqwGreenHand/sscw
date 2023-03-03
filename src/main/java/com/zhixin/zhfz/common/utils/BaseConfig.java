/**
 *
 */
package com.zhixin.zhfz.common.utils;

/**
 * @author zhiqiang
 */
public class BaseConfig {

    private static BaseConfig _instance = null;

    private BaseConfig() {

    }

    /**
     * @return
     */
    public static BaseConfig getInstance() {
        return _instance;
    }

    private String rootDir;

    private String officeDir;
    // classes文件夹的地址
    private String classesDir;

    private String templateDir;

    static {
        _instance = new BaseConfig();
        _instance.classesDir = BaseConfig.class.getResource("/").getPath();
        _instance.templateDir = _instance.classesDir + "template/";
        if (_instance.classesDir.indexOf("target") > -1) {
            _instance.rootDir = _instance.classesDir.substring(1, _instance.classesDir.indexOf("target"))
                    + "src/main/webapp/";
        } else {
            _instance.rootDir = _instance.classesDir.substring(1, _instance.classesDir.indexOf("WEB-INF"));
        }
        _instance.officeDir = _instance.rootDir + "officefile/";
    }

    /**
     * 取得实际的web应用资源的根路径，此目录为html文件存放地址
     *
     * @return
     */
    public String getRootDir() {
        return _instance.rootDir;
    }

    /**
     * 取得实际的officefile的路径
     *
     * @return
     */
    public String getOfficeFileDir() {
        return _instance.officeDir;
    }

    /**
     * 取得实际的classes实际的目录
     *
     * @return
     */
    public String getClassesDir() {
        return _instance.classesDir;
    }

    /**
     * @return
     */
    public String getTemplateDir() {
        return _instance.templateDir;
    }
}
