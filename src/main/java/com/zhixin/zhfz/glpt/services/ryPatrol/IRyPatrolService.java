package com.zhixin.zhfz.glpt.services.ryPatrol;

import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.glpt.form.PersonTrailForm;
import java.util.List;
import java.util.Map;

/**
 * @prgram: zhfz
 * @Description: 人员查询接口
 * @Auther: xcf
 * @Date: 2019-04-18 11:10
 */
public interface IRyPatrolService {

    PersonTrailForm selectOrderTrail(Map<String, Object> map);

    List<SerialEntity> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    PersonTrailForm selectSerialTrail(Map<String, Object> map);

    List<PersonTrailForm> selectCheckTrail(Map<String, Object> map);

    List<PersonTrailForm> selectBelongTrail(Map<String, Object> map);

    List<PersonTrailForm> selectWaitRecordTrail(Map<String, Object> map);

    List<PersonTrailForm> selectRecordTrail(Map<String, Object> map);

    List<PersonTrailForm> selectUrinalysisTrail(Map<String, Object> map);

    List<PersonTrailForm> selectJzAjxx(Map<String, Object> map);

    List<PersonTrailForm> selectJzPerson(Map<String, Object> map);

    PersonTrailForm selectCollectionTrail(Map<String, Object> map);
}
