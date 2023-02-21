package com.zhixin.zhfz.zhag.services.xsajtj;

import com.zhixin.zhfz.jxkp.entity.RateSignEntity;
import com.zhixin.zhfz.zhag.entity.CriminalAndAdministrationCaseEntity;
import com.zhixin.zhfz.zhag.entity.GjxxEntity;

import java.util.List;

public interface IXsajtjService {
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

    /**
     * 案件来源
     *
     * @param day
     * @return
     * @throws Exception
     */
    List<CriminalAndAdministrationCaseEntity> quyAJSourceByDay(Integer day) throws Exception;

    /**
     * 案件类型
     *
     * @param day
     * @return
     * @throws Exception
     */
    List<CriminalAndAdministrationCaseEntity> quyAJTypeByDay(Integer day) throws Exception;

    /**
     * 案件状态
     *
     * @param day
     * @return
     * @throws Exception
     */
    List<CriminalAndAdministrationCaseEntity> quyAJStateByDay(Integer day) throws Exception;

    /**
     * 案由
     *
     * @param day
     * @return
     * @throws Exception
     */
    List<CriminalAndAdministrationCaseEntity> quyAJCauseByDay(Integer day) throws Exception;

    /**
     * 刑事案件数量
     *
     * @param day
     * @return
     * @throws Exception
     */
    List<CriminalAndAdministrationCaseEntity> quyXSAJAllNumberByDay(Integer day) throws Exception;
    List<RateSignEntity> quyXSXZAJAllNumberByDay(Integer day) throws Exception;
    List<RateSignEntity> listGjly(Integer day) throws Exception;

    /**
     * 告警类型
     *
     * @param day
     * @return
     * @throws Exception
     */
    List<GjxxEntity> quyGJTypeByDay(Integer day) throws Exception;
}
