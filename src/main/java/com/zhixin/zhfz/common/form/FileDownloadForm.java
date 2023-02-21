package com.zhixin.zhfz.common.form;

/**
 * @program: zhfz
 * @description: 文件下载
 * @author: jzw
 * @create: 2019-03-12 18:57
 **/

public class FileDownloadForm {


    /**
     * @Author jzw
     * @Description 两个方法只是sysId类型不一样，这是因为项目中接口不统一
     * @Date 9:36 2019/3/14
     * @Param [sysType(ba=办案场所,aj=案件资料;必填),
     *         fileType(AJ=安检 BL=笔录 GJ=轨迹 RQ=入区 WP=物品 SA=涉案 QT=其他 HM=虹膜等等;必填),
     *         uuid(案件uuid=>ba_case表下uuid 或 嫌疑人uuid=>ba_serial表下uuid;必填),
     *         sysId(区域id;必填),
     *         fileName(文件名;必填)]
     * @return com.zhixin.zhfz.common.form.FileDownloadForm
     **/
    public static FileDownloadForm of(String sysType,String fileType,String uuid, Long sysId,String fileName){
        return new FileDownloadForm(sysType, fileType, uuid, String.valueOf(sysId), fileName);
    }

    public static FileDownloadForm of(String sysType,String fileType,String uuid, Integer sysId,String fileName){
        return new FileDownloadForm(sysType, fileType, uuid, String.valueOf(sysId), fileName);
    }

    public FileDownloadForm (){
    }

    public FileDownloadForm(String sysType,String fileType,String uuid, String sysId,String fileName){
        this.sysType = sysType;
        this.fileType = fileType;
        this.uuid = uuid;
        this.sysId = sysId;
        this.fileName = fileName;
    }

    private String sysType;

    private String uuid;

    private String fileName;

    private String sysId;

    private String fileType;

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getSysId() {
        return sysId;
    }

    public void setSysId(String sysId) {
        this.sysId = sysId;
    }
}
