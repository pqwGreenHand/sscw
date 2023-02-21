package com.zhixin.zhfz.zhag.services.xzajtj;

import com.zhixin.zhfz.zhag.entity.CriminalAndAdministrationCaseEntity;
import com.zhixin.zhfz.zhag.entity.GjxxEntity;

import java.util.List;

public interface XzajtjService {


    /**
     * 案件来源
     *
     * @param day
     * @return
     * @throws Exception
     */
    List<CriminalAndAdministrationCaseEntity> quyXZAJSourceByDay(Integer day) throws Exception;

    /**
     * 案件类型
     *
     * @param day
     * @return
     * @throws Exception
     */
    List<CriminalAndAdministrationCaseEntity> quyXZAJTypeByDay(Integer day) throws Exception;

    /**
     * 案件状态
     *
     * @param day
     * @return
     * @throws Exception
     */
    List<CriminalAndAdministrationCaseEntity> quyXZAJStateByDay(Integer day) throws Exception;

    /**
     * 案由
     *
     * @param day
     * @return
     * @throws Exception
     */
    List<CriminalAndAdministrationCaseEntity> quyXZAJCauseByDay(Integer day) throws Exception;

    /**
     * 刑事案件数量
     *
     * @param day
     * @return
     * @throws Exception
     */
    List<CriminalAndAdministrationCaseEntity> quyXZAJAllNumberByDay(Integer day) throws Exception;

    /**
     * 告警类型
     *
     * @param day
     * @return
     * @throws Exception
     */
    List<GjxxEntity> quyXZGJTypeByDay(Integer day) throws Exception;

    /**
     * 查询多少天内受理立案折线图
     *
     * @param day
     * @return
     */
    public List<CriminalAndAdministrationCaseEntity> selectSLLAByDay(Integer day);

    /**
     * 查询多少天内刑事告警折线图
     *
     * @param day
     * @return
     */
    public List<GjxxEntity> selectXSGJByDay(Integer day);
}
