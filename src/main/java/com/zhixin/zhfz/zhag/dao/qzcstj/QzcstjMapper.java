package com.zhixin.zhfz.zhag.dao.qzcstj;

import com.zhixin.zhfz.zhag.entity.CscfEntity;
import com.zhixin.zhfz.zhag.entity.JqxxEntity;

import java.util.List;
import java.util.Map;

public interface QzcstjMapper {

    //强制措施名称
    List<CscfEntity> quyQzcsByDay(Map<String, Object> map);

    //强制措施数量
    List<CscfEntity> quyQzcszsByDay(Map<String, Object> map);

    //警情类别
    List<JqxxEntity> quyQzcsjqlbByDay(Map<String, Object> map);


    //创建日期
    List<CscfEntity> quyQzcsNumberByDay(Map<String, Object> map);


    //强制措施执行时间
    List<CscfEntity> selectQzcsZXSJByDay(Map<String, Object> map);

    //强制措施案别
    List<CscfEntity> selectQzcsABByDay(Map<String, Object> map);

    //通过日期查询强制措施告警总数
    int quyQzcsGJByDate(Map<String, Object> map);

    //通过日期查询强制措施预警总数
    int quyQzcsYJByDate(Map<String, Object> map);
}
