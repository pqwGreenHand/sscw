package com.zhixin.zhfz.glpt.services.ryPatrol;

import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.glpt.dao.ryPatrol.IRyPatrolMapper;
import com.zhixin.zhfz.glpt.form.PersonTrailForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * @prgram: zhfz
 * @Description: 人员查询实现类
 * @Auther: xcf
 * @Date: 2019-04-18 11:09
 */
@Service
public class RyPatrolServiceImpl implements IRyPatrolService{

    @Autowired
    private IRyPatrolMapper ryPatrolMapper;

    @Override
    public PersonTrailForm selectOrderTrail(Map<String, Object> map) {

       PersonTrailForm info=this.ryPatrolMapper.selectOrderTrail(map);

        return info;
    }

    @Override
    public List<SerialEntity> list(Map<String, Object> map) {

        return this.ryPatrolMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return this.ryPatrolMapper.count(map);
    }

    @Override
    public PersonTrailForm selectSerialTrail(Map<String, Object> map) {

        return this.ryPatrolMapper.selectSerialTrail(map);

    }

    @Override
    public List<PersonTrailForm> selectCheckTrail(Map<String, Object> map) {

        return this.ryPatrolMapper.selectCheckTrail(map);

    }

    @Override
    public List<PersonTrailForm> selectBelongTrail(Map<String, Object> map) {

        return this.ryPatrolMapper.selectBelongTrail(map);

    }

    @Override
    public List<PersonTrailForm> selectWaitRecordTrail(Map<String, Object> map) {

        return this.ryPatrolMapper.selectWaitRecordTrail(map);

    }

    @Override
    public List<PersonTrailForm> selectRecordTrail(Map<String, Object> map) {

        return this.ryPatrolMapper.selectRecordTrail(map);

    }

    @Override
    public List<PersonTrailForm> selectUrinalysisTrail(Map<String, Object> map) {

        return this.ryPatrolMapper.selectUrinalysisTrail(map);

    }

    @Override
    public List<PersonTrailForm> selectJzAjxx(Map<String, Object> map) {
        return ryPatrolMapper.selectJzAjxx(map);
    }

    @Override
    public List<PersonTrailForm> selectJzPerson(Map<String, Object> map) {
        return ryPatrolMapper.selectJzPerson(map);
    }

    @Override
    public PersonTrailForm selectCollectionTrail(Map<String, Object> map) {

        return this.ryPatrolMapper.selectCollectionTrail(map);

    }
}
