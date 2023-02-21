package com.zhixin.zhfz.bacs.services.record;

import com.zhixin.zhfz.bacs.dao.record.IRecordMapper;
import com.zhixin.zhfz.bacs.entity.RecordEntity;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @program: zhfz
 * @description: 审讯
 * @author: jzw
 * @create: 2019-02-26 10:51
 **/
@Service
public class RecordServiceImpl implements IRecordService {

    @Autowired
    private IRecordMapper mapper;

    @Autowired
    private ISerialService serialService;

    @Override
    public List<RecordEntity> list(Map<String, Object> map) throws Exception {
        return mapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) throws Exception {
        return mapper.count(map);
    }

    @Override
    public String getRecordingRoomName(Map<String, Object> map) throws Exception {
        return mapper.getRecordingRoomName(map);
    }

    @Override
    public void delete(Long id) throws Exception {
        mapper.delete(id);
    }

    @Override
    public RecordEntity insert(RecordEntity entity) throws Exception {
        if(entity.getUuid()==null){
            entity.setUuid(UUID.randomUUID().toString());
        }
        entity.setCount(this.mapper.queryNextCount(entity.getSerialId()));
        mapper.insert(entity);
        serialService.updateStatusById(entity.getSerialId(), 6);
        return entity;
    }

    @Override
    public Long getMaxId(){
        return mapper.getMaxId();
    }

    @Override
    public void update(RecordEntity entity) throws Exception {
        mapper.update(entity);
    }

    @Override
    public int getSerialRecording(Long serialId) {
        return mapper.getSerialRecording(serialId);
    }

    @Override
    public Map<String, String> getRecordIps(Long recordId) {
        return mapper.getRecordIps(recordId);
    }

    @Override
    public int queryNextCount(Long serialId) {
        return mapper.queryNextCount(serialId);
    }

    @Override
    public int getRoomRecording(Map<String, Object> map) {
        return mapper.getRoomRecording(map);
    }

    @Override
    public void updateStatus(Map<String, Object> map) throws Exception {
        mapper.updateStatus(map);
        if (Integer.valueOf(map.get("status").toString()) == 3) {
            serialService.updateStatusById((Long) map.get("serialId"), 7);
        }
    }

    @Override
    public RecordEntity getRecordInfo(Long id) {
        return mapper.getRecordInfo(id);
    }

    @Override
    public String getRoomCameraNo(Map<String, Object> map) {
        return mapper.getRoomCameraNo(map);
    }

    @Override
    public List<RecordEntity> selectBySerialId(Map<String, Object> map) {
        return mapper.selectBySerialId(map);
    }

    @Override
    public List<RecordEntity> getRecordRoomInfoBySerialId(Map<String, Object> map) throws Exception {
        return mapper.getRecordRoomInfoBySerialId(map);
    }

    /**
     * 根据serialId和personid判断嫌疑人是否已审讯
     *
     * @param map
     * @return
     */
    @Override
    public List<RecordEntity> selectBySerialIdAndPersonId(Map<String, Object> map) {
        return mapper.selectBySerialIdAndPersonId(map);
    }

    @Override
    public void updateStatusNew(RecordEntity entity) {
        mapper.updateStatusNew(entity);
    }

    @Override
    public List<RecordEntity> listRecordQuartz() throws Exception {
        return mapper.listRecordQuartz();
    }

    @Override
    public void updateStatusQuartz(RecordEntity obj) {
        mapper.updateStatusQuartz(obj);
    }
}
