package com.zhixin.zhfz.glpt.services;

import com.zhixin.zhfz.glpt.entity.BaCaseEntity;
import com.zhixin.zhfz.glpt.entity.BaSerialEntity;
import com.zhixin.zhfz.glpt.entity.ChartEntity;

import java.util.List;
import java.util.Map;

public interface IBaCaseService {
    //30天内刑事行政统计
    List<BaCaseEntity> query30DayByCategory(Map<String, Object> map);

    //获取各个办案中心总的裁决统计
    List<BaSerialEntity>  queryAdjudicationByAll(Map<String, Object> map);

    //获取各个办案中心7天内总男女入区统计
    List<BaSerialEntity>  queryIntoByWeek(Map<String,Object> map);

    //<!-- 获取各个办案中心总的收押人数-->
    List<BaSerialEntity>  queryDetainByAll(Map<String, Object> map);

    //获取各个办案中心24小时内每天出区人数
    List<BaSerialEntity>  query24HourOut(Map<String, Object> map);

    // 获取各个办案中心总的案件类别统计
    List<BaCaseEntity>  queryCategoryByAll(Map<String, Object> map);

    //获取各个办案中心总的候问看押容积
    Map<String, Integer>  queryDetainByCount(Map<String,Object> param);

    //获取各个办案中心入区嫌疑人状态
    List<BaSerialEntity>  queryPersonType(Map<String, Object> map);

    //查询当前在区人数，历史总人数，历史总案件
    List<Integer> queryInAndTotal(Map<String,Object> map);

    //在使用中的办案中心个数
    List<Integer> queryUseRateCount(Map<String,Object> map);

    //每个办案中心在区人数
    List<ChartEntity> queryInInterrogateNum(Map<String,Object> map);

    //案件Top10
    List<ChartEntity> queryCaseTop10(Map<String,Object> map);

    //查询每个月进入办案中心嫌疑人人数
    List<Integer> queryEntranceCountEveryMonth(Map<String,Object> map);

    //每个办案中心在区人数
    List<ChartEntity> queryEveryInterrogateIn(Map<String,Object> map);

    List<BaCaseEntity> listAllLawPerson(Map<String, Object> map);

    int countAllLawPerson(Map<String, Object> map);
}
