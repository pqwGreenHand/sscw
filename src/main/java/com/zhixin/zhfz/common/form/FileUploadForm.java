package com.zhixin.zhfz.common.form;

import com.zhixin.zhfz.common.common.FileUtil;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @program: zhfz
 * @description: 文件上传
 * @author: jzw
 * @create: 2019-03-12 18:46
 **/

public class FileUploadForm extends FileDownloadForm {

    //上传文件类型
    //入区
    public static final String FILETYPRRQ = "RQ";
    //签字
    public static final String FILETYPRQZ = "QZ";
    //嫌疑人入区条码文件名后缀
    public static final String BARCODEXYRRQ = "-bc.jpg";

    /**
     * @Author jzw   当前uuid下只有一张图片时使用之方法
     * @Description 下面两个方法只是sysId类型不一样，这是因为项目中接口不统一
     * @Date 9:36 2019/3/14
     * @Param [sysType(ba=办案场所,aj=案件资料;必填),
     *         fileType(AJ=安检 BL=笔录 GJ=轨迹 RQ=入区 WP=物品 SA=涉案 QT=其他 HM=虹膜等等;必填),
     *         uuid(案件uuid=>ba_case表下uuid 或 嫌疑人uuid=>ba_serial表下uuid;必填),
     *         sysId(区域id;必填),
     *         file(上传的MultipartFile文件;必填;注：base64字符流图片一样可以从前端发到后端MultipartFile文件中)]
     * @return com.zhixin.zhfz.common.form.FileUploadForm
     **/
    public static FileUploadForm of(String sysType,String fileType,String uuid, Long sysId,MultipartFile file){
        return new FileUploadForm(sysType, fileType, uuid,  String.valueOf(sysId), null, file);
    }
    public static FileUploadForm of(String sysType,String fileType,String uuid, Integer sysId,MultipartFile file){
        return new FileUploadForm(sysType, fileType, uuid,  String.valueOf(sysId), null, file);
    }

    public static FileUploadForm of(String sysType,String fileType,String uuid, Long sysId,File file){
        return new FileUploadForm(sysType, fileType, uuid,  String.valueOf(sysId), null, file);
    }
    public static FileUploadForm of(String sysType,String fileType,String uuid, Integer sysId,File file){
        return new FileUploadForm(sysType, fileType, uuid,  String.valueOf(sysId), null, file);
    }
    /**
     * @Author jzw  当前uuid下只有多张图片时使用之方法
     * @Description 下面两个方法只是sysId类型不一样，这是因为项目中接口不统一
     * @Date 9:36 2019/3/14
     * @Param [sysType(ba=办案场所,aj=案件资料;必填),
     *         fileType(AJ=安检 BL=笔录 GJ=轨迹 RQ=入区 WP=物品 SA=涉案 QT=其他 HM=虹膜等等;必填),
     *         uuid(案件uuid=>ba_case表下uuid 或 嫌疑人uuid=>ba_serial表下uuid;必填),
     *         sysId(区域id;必填),
     *         fileName(文件名：可加后缀，可不加后缀;可选项),
     *         file(上传的MultipartFile文件;必填;注：base64字符流图片一样可以从前端发到后端MultipartFile文件中)]
     * @return com.zhixin.zhfz.common.form.FileUploadForm
     **/
    public static FileUploadForm of(String sysType,String fileType,String uuid, Long sysId,String fileName,MultipartFile file){
        return new FileUploadForm(sysType, fileType, uuid, String.valueOf(sysId), fileName, file);
    }

    public static FileUploadForm of(String sysType,String fileType,String uuid, Integer sysId,String fileName,MultipartFile file){
        return new FileUploadForm(sysType, fileType, uuid, String.valueOf(sysId), fileName, file);
    }

    public static FileUploadForm of(String sysType,String fileType,String uuid, Long sysId,String fileName,File file){
        return new FileUploadForm(sysType, fileType, uuid, String.valueOf(sysId), fileName, file);
    }

    public static FileUploadForm of(String sysType,String fileType,String uuid, Integer sysId,String fileName,File file){
        return new FileUploadForm(sysType, fileType, uuid, String.valueOf(sysId), fileName, file);
    }

    public FileUploadForm(){}

    public FileUploadForm(String sysType,String fileType,String uuid, String sysId, String fileName,MultipartFile file){
        super( sysType, fileType, uuid,  sysId, fileName);
        this.file = file;
        this.suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
    }
    public FileUploadForm(String sysType,String fileType,String uuid, String sysId, String fileName,File file){
        super( sysType, fileType, uuid,  sysId, fileName);
        this.f = file;
        this.suffix = file.getName().substring(file.getName().lastIndexOf("."));
    }

    private String suffix;

    private MultipartFile file;

    private File f;

    public File getFile() throws Exception{
        if(f == null && file != null){
            f = FileUtil.multipartFileToFile(this.file);
        }
        return f;
    }

    public void setFile(MultipartFile file) {
        this.suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        this.file = file;
    }

    public String getSuffix() {
        return suffix;
    }

}
