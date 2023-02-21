package com.zhixin.zhfz.zhag.dao.xsajtj;

import com.zhixin.zhfz.jxkp.entity.RateSignEntity;
import com.zhixin.zhfz.zhag.entity.CriminalAndAdministrationCaseEntity;
import com.zhixin.zhfz.zhag.entity.GjxxEntity;

import java.util.List;
import java.util.Map;

public interface IXsajtjMapper {
    /**
     * 查询多少天内受理立案折线图
     *
     * @param map
     * @return
     */
    public List<CriminalAndAdministrationCaseEntity> selectSLLAByDay(Map<String, Object> map);

    /**
     * 查询多少天内刑事告警折线图
     *
     * @param map
     * @return
     */
    public List<GjxxEntity> selectXSGJByDay(Map<String, Object> map);

    //案件来源
    List<CriminalAndAdministrationCaseEntity> quyAJSourceByDay(Map<String, Object> map);

    //案件类型
    List<CriminalAndAdministrationCaseEntity> quyAJTypeByDay(Map<String, Object> map);

    //案件状态
    List<CriminalAndAdministrationCaseEntity> quyAJStateByDay(Map<String, Object> map);

    //案由
    List<CriminalAndAdministrationCaseEntity> quyAJCauseByDay(Map<String, Object> map);

    //刑事案件数量
    List<CriminalAndAdministrationCaseEntity> quyXSAJAllNumberByDay(Map<String, Object> map);
    //刑事、行政案件数量
    List<RateSignEntity> quyXSXZAJAllNumberByDay(Map<String, Object> map);
    List<RateSignEntity> listGjly(Map<String, Object> map);

    //告警类型
    List<GjxxEntity> quyGJTypeByDay(Map<String, Object> map);
}
