package com.zhixin.zhfz.glpt.services;

import com.zhixin.zhfz.glpt.dao.bacase.IBaCaseMapper;
import com.zhixin.zhfz.glpt.entity.BaCaseEntity;
import com.zhixin.zhfz.glpt.entity.BaSerialEntity;
import com.zhixin.zhfz.glpt.entity.ChartEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class BaCaseServiceImpl implements IBaCaseService {

   @Autowired
    private IBaCaseMapper baCaseMapper;

    //30天内刑事行政统计
    @Override
    public List<BaCaseEntity> query30DayByCategory(Map<String, Object> map) {
        List<BaCaseEntity> parm = baCaseMapper.query30DayByCategory(map);
        return  parm;
    }
    //获取各个办案中心总的裁决统计
    @Override
    public List<BaSerialEntity> queryAdjudicationByAll(Map<String, Object> map) {
        List<BaSerialEntity> list = baCaseMapper.queryAdjudicationByAll(map);
        return list;
    }

    //获取各个办案中心一周内入区统计
    @Override
    public List<BaSerialEntity> queryIntoByWeek(Map<String,Object> map) {
        List<BaSerialEntity> list = baCaseMapper.queryIntoByWeek(map);
        return list;
    }

    //<!-- 获取各个办案中心总的收押人数-->
    @Override
    public List<BaSerialEntity> queryDetainByAll(Map<String, Object> map) {
        List<BaSerialEntity> list = baCaseMapper.queryDetainByAll(map);
        return list;
    }
    //获取各个办案中心24小时内每天出区人数
    @Override
    public List<BaSerialEntity> query24HourOut(Map<String, Object> map) {
        List<BaSerialEntity> param = baCaseMapper.query24HourOut(map);
        return param;
    }
    // 获取各个办案中心总的案件类别统计
    @Override
    public List<BaCaseEntity> queryCategoryByAll(Map<String, Object> map) {
        List<BaCaseEntity> param = baCaseMapper.queryCategoryByAll(map);
        return param;
    }
    //获取各个办案中心总的候问看押容积
    @Override
    public Map<String, Integer> queryDetainByCount(Map<String,Object> map) {
        Map<String, Integer> param=new HashMap<String, Integer>();
        param = baCaseMapper.queryDetainByCount(map);
        return param;
    }
    //获取各个办案中心入区嫌疑人状态
    @Override
    public List<BaSerialEntity> queryPersonType(Map<String, Object> map) {
        List<BaSerialEntity> param = baCaseMapper.queryPersonType(map);
        return param;
    }
    @Override
    public List<Integer> queryInAndTotal(Map<String,Object> map) {
        return baCaseMapper.queryInAndTotal(map);
    }
    @Override
    public List<Integer> queryUseRateCount(Map<String,Object> map) {
        return baCaseMapper.queryUseRateCount(map);
    }

    @Override
    public List<ChartEntity> queryInInterrogateNum(Map<String,Object> map) {
        return baCaseMapper.queryInInterrogateNum(map);
    }
    @Override
    public List<ChartEntity> queryCaseTop10(Map<String,Object> map) {
        return baCaseMapper.queryCaseTop10(map);
    }

    @Override
    public List<Integer> queryEntranceCountEveryMonth(Map<String,Object> map) {
        return baCaseMapper.queryEntranceCountEveryMonth(map);
    }
    @Override
    public List<ChartEntity> queryEveryInterrogateIn(Map<String,Object> map) {
        return baCaseMapper.queryEveryInterrogateIn( map);
    }

    @Override
    public List<BaCaseEntity> listAllLawPerson(Map<String, Object> map) {
        return baCaseMapper.listAllLawPerson(map);
    }

    @Override
    public int countAllLawPerson(Map<String, Object> map) {
        return baCaseMapper.countAllLawPerson(map);
    }
}
