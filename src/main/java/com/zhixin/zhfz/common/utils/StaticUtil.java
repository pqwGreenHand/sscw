package com.zhixin.zhfz.common.utils;

import com.zhixin.zhfz.common.entity.FileServiceConfigEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 静态类
 * @author: jzw
 * @create: 2019-03-12 19:20
 **/

public class StaticUtil {

    private static Map<String, FileServiceConfigEntity> fileServiceConfigMap = new HashMap<>();

    public static FileServiceConfigEntity getFileServiceConfigEntity(String sysType,String sysId){
        return fileServiceConfigMap.get(sysType + sysId);
    }

    public synchronized static void setFileServiceConfigEntity(FileServiceConfigEntity entity){
        setFileServiceConfigEntity(entity,true);
    }

    public synchronized static void setFileServiceConfigEntity(FileServiceConfigEntity entity,Boolean flag){
        if(flag)
            fileServiceConfigMap.put(entity.getSysType() + String.valueOf(entity.getSysId()),entity);
        else
            fileServiceConfigMap.put(entity.getSysType() + String.valueOf(entity.getSysId()),null);
    }
}
