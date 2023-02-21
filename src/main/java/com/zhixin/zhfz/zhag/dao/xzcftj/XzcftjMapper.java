package com.zhixin.zhfz.zhag.dao.xzcftj;

import com.zhixin.zhfz.zhag.entity.CscfEntity;
import com.zhixin.zhfz.zhag.entity.JqxxEntity;

import java.util.List;
import java.util.Map;

public interface XzcftjMapper {

    //行政处罚名称
    List<CscfEntity> quyXZCfmcByDay(Map<String, Object> map);

    //行政处罚数量
    List<CscfEntity> quyXZCfzsByDay(Map<String, Object> map);

    //警情类别
    List<JqxxEntity> quyjqlbByDay(Map<String, Object> map);


    //创建日期
    List<CscfEntity> quyXZcfNumberByDay(Map<String, Object> map);


    //行政处罚执行时间
    List<CscfEntity> selectZXSJByDay(Map<String, Object> map);

    //行政处罚案别
    List<CscfEntity> selectCFABByDay(Map<String, Object> map);

    //通过日期查询行政处罚告警总数
    int quyXZcfGJByDate(Map<String, Object> map);

    //通过日期查询行政处罚预警总数
    int quyXZcfYJByDate(Map<String, Object> map);

}
