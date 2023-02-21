package com.zhixin.zhfz.zhag.services.qzcstj;

import com.zhixin.zhfz.zhag.entity.CscfEntity;
import com.zhixin.zhfz.zhag.entity.JqxxEntity;

import java.util.List;

public interface QzcstjService {

    /**
     * 强制措施名称
     *
     * @param day
     * @return
     * @throws Exception
     */
    List<CscfEntity> quyQzcsByDay(Integer day) throws Exception;

    /**
     * 强制措施总数量
     *
     * @param day
     * @return
     * @throws Exception
     */
    List<CscfEntity> quyQzcszsByDay(Integer day) throws Exception;

    /**
     * 警情类别
     *
     * @param day
     * @return
     * @throws Exception
     */
    List<JqxxEntity> quyQzcsjqlbByDay(Integer day) throws Exception;


    /**
     * 创建日期
     *
     * @param day
     * @return
     * @throws Exception
     */
    List<CscfEntity> quyQzcsNumberByDay(Integer day) throws Exception;

    /**
     * 强制措施执行时间
     *
     * @param day
     * @return
     */
    List<CscfEntity> selectQzcsZXSJByDay(Integer day);

    /**
     * 强制措施案别
     *
     * @param day
     * @return
     */
    List<CscfEntity> selectQzcsABByDay(Integer day);

    /**
     * 通过日期查询强制措施告警总数
     *
     * @param useDate
     * @return
     */
    int quyQzcsGJByDate(String useDate);

    /**
     * 通过日期查询强制措施预警总数
     *
     * @param useDate
     * @return
     */
    int quyQzcsYJByDate(String useDate);
}
