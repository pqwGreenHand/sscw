package com.zhixin.zhfz.zhag.services.xzcftj;

import com.zhixin.zhfz.zhag.entity.CscfEntity;
import com.zhixin.zhfz.zhag.entity.JqxxEntity;

import java.util.List;

public interface XzcftjService {

    /**
     * 行政处罚名称
     *
     * @param day
     * @return
     * @throws Exception
     */
    List<CscfEntity> quyXZCfmcByDay(Integer day) throws Exception;

    /**
     * 行政处罚总数量
     *
     * @param day
     * @return
     * @throws Exception
     */
    List<CscfEntity> quyXZCfzsByDay(Integer day) throws Exception;

    /**
     * 警情类别
     *
     * @param day
     * @return
     * @throws Exception
     */
    List<JqxxEntity> quyjqlbByDay(Integer day) throws Exception;


    /**
     * 创建日期
     *
     * @param day
     * @return
     * @throws Exception
     */
    List<CscfEntity> quyXZcfNumberByDay(Integer day) throws Exception;

    /**
     * 行政处罚执行时间
     *
     * @param day
     * @return
     */
    List<CscfEntity> selectZXSJByDay(Integer day);

    /**
     * 行政处罚案别
     *
     * @param day
     * @return
     */
    List<CscfEntity> selectCFABByDay(Integer day);

    /**
     * 通过日期查询行政处罚告警总数
     *
     * @param useDate
     * @return
     */
    int quyXZcfGJByDate(String useDate);

    /**
     * 通过日期查询行政处罚预警总数
     *
     * @param useDate
     * @return
     */
    int quyXZcfYJByDate(String useDate);

}
