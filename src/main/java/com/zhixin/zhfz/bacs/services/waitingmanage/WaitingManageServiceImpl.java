package com.zhixin.zhfz.bacs.services.waitingmanage;

import com.zhixin.zhfz.bacs.dao.actionlog.IActionLogMapper;
import com.zhixin.zhfz.bacs.dao.waitingmanage.IWaitingManageMapper;
import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacs.services.record.IRecordService;
import com.zhixin.zhfz.bacs.services.room.IRoomService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.common.entity.OperLogEntity;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WaitingManageServiceImpl implements IWaitingManageService{
    private static final Logger logger = LoggerFactory.getLogger(WaitingManageServiceImpl.class);
    @Autowired
    private IWaitingManageMapper waitingManageMapper;

    @Resource
    private ISerialService  serialService;

    @Autowired
    private IOperLogService operLogService;

    @Autowired
    private IActionLogMapper actionLogMapper;

    @Autowired
    private IRecordService recordService;

    @Autowired
    private IRoomService roomService;
    @Override
    public List<WaitingRecordEntity> getWaitingRecordBySerialId(WaitingRecordEntity temWaitingRecord) {
        return waitingManageMapper.getWaitingRecordBySerialId(temWaitingRecord);
    }

    @Override
    public List<WaitingPersonCountEntity> personRoomCountList(Map<String, Object> map) {
        return waitingManageMapper.personRoomCountList(map);
    }

    @Override
    public Integer selectPeopleCountTypeKy(Map<String,Object> map) {
        return waitingManageMapper.selectPeopleCountTypeKy(map);
    }

    @Override
    public Integer selectPeopleCountTypeSx(Map<String,Object> map) {
        return waitingManageMapper.selectPeopleCountTypeSx(map);
    }

    @Override
    public Integer selectPeopleCountTypeZq(Map<String, Object> map) {
        return waitingManageMapper.selectPeopleCountTypeZq(map);
    }

    @Override
    public Integer selectPeopleCountTypeRj(Map<String, Object> map) {
        return waitingManageMapper.selectPeopleCountTypeRj(map);
    }

    @Override
    public Integer selectPeopleCountTypeLs(Map<String, Object> map) {
        return waitingManageMapper.selectPeopleCountTypeLs(map);
    }

    @Override
    public Integer selectPeopleCountTypeOther(Map<String, Object> map) {
        return waitingManageMapper.selectPeopleCountTypeOther(map);
    }

    @Override
    public WaitingRecordEntity queryBySerialId(String serialId) {
        return waitingManageMapper.queryBySerialId(serialId);
    }

    @Override
    public void updateWaitingRecord(WaitingRecordEntity entity) {
        serialService.changestatus(entity.getSerialId(),3);
        waitingManageMapper.updateWaitingRecord(entity);
    }

    @Override
    public void updateShowBySerialId(String serialId) {
        waitingManageMapper.updateShowBySerialId(serialId);
    }

    @Override
    public int insert(WaitingRecordEntity record) {
        ActionLogEntity actionLogEntity=new ActionLogEntity(record,true);
        return addWaitingRecord(record,actionLogEntity);
    }

    private int addWaitingRecord(WaitingRecordEntity entity, ActionLogEntity actionLogEntity){
        int n=0;
        entity.setBurcode(getBurcode());
        try {
            actionLogMapper.insertActionLog(actionLogEntity);
            logger.info(actionLogEntity.toString());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        try {
            n=waitingManageMapper.insertWaitingRecord(entity);
            serialService.changestatus(entity.getSerialId(), 3);
            // 查询inquest_record
            Map<String, Object> recordMap = new HashMap<>();
            recordMap.put("serialId", entity.getSerialId());
            recordMap.put("personId", entity.getPersonId());
            List<RecordEntity> recordList = waitingManageMapper.getInquestRecord(recordMap);
            if (recordList != null && recordList.size() > 0) {
                    RecordEntity inqeustRecordEntity = recordList.get(0);
                if (inqeustRecordEntity.getEndTime() == null) {
                    // 如果为空 审讯结束 更新 InqeustRecord
                    logger.info("审讯结束 更新 InqeustRecord");
                    logger.info("inqeustRecordEntity============"+inqeustRecordEntity);
                    recordService.update(inqeustRecordEntity);
                    // download
                    RecordEntity resultInqeust=recordService.getRecordInfo(inqeustRecordEntity.getId());
                    logger.error("resultInqeust="+resultInqeust);
                    //这里先把调用下载摄像头的方法注释了
                    /*insertDownloadTack(resultInqeust);*/
                    //更新LED
                    //查询房间名
                    RoomEntity roomEntity =roomService.getRoomById(new Long(inqeustRecordEntity.getRoomId()));
                    roomService.updateRoomStatus(Integer.valueOf(inqeustRecordEntity.getRoomId().toString()), 0,roomEntity.getName());
                }
            }
        } catch (Exception e) {
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "审讯结束 更新InqeustRecord" , "system", false,OperLogEntity.SYSTEM_BACS);
            logger.error(e.getMessage(),e);

        }

        return n;
    }
    private String getBurcode(){
        return System.nanoTime() + "";
    }

    @Override
    public List<WaitingRecordEntity> selectAllSuspectOfRoom(Map<String, Object> map) {
        return waitingManageMapper.selectAllSuspectOfRoom(map);
    }

    @Override
    public void changeRoom(Map<String, Object> map) {
        waitingManageMapper.changeRoom(map);
    }

    @Override
    public void saveDirection(Map<String, Object> map) {
        logger.info("saveDirection===="+map.toString());
        try {
            Integer id=Integer.valueOf(map.get("id").toString());
            WaitingRecordEntity old=waitingManageMapper.queryById(id);
            if(map.get("direction")!=""){
                old.setGetResult(map.get("direction").toString());
            }
            ActionLogEntity actionLogEntity=new ActionLogEntity(old,false);
            actionLogMapper.insertActionLog(actionLogEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        waitingManageMapper.saveDirection(map);
    }

    @Override
    public WaitingRecordEntity getWaitingRecordById(String id) {
        return waitingManageMapper.getWaitingRecordById(id);
    }

    @Override
    public void updateStatusByIdForGoBack(String id) {
        waitingManageMapper.updateStatusByIdForGoBack(id);
    }

    @Override
    public List<WaitingRecordEntity> listRecord(Map<String, Object> map) {
        return waitingManageMapper.listRecord(map);
    }

    @Override
    public Integer listRecordCount(Map<String, Object> map) {
        return waitingManageMapper.listRecordCount(map);
    }
    @Override
    public List<WaitingPersonCountEntity> queryRoomSameCase(Map<String, Object> map) {
        return waitingManageMapper.queryRoomSameCase(map);
    }

    @Override
    public WaitingRecordEntity getWaitingRecordBySerialId2(WaitingRecordEntity temWaitingRecord) {
        return waitingManageMapper.getWaitingRecordBySerialId2(temWaitingRecord);
    }

    @Override
    public Integer selectCountIsJuvenile(Map<String, Object> map) {
        return waitingManageMapper.selectCountIsJuvenile(map);
    }

    @Override
    public Integer selectCountIsDisease(Map<String, Object> map) {
        return waitingManageMapper.selectCountIsDisease(map);
    }

    @Override
    public Integer selectCountIsGravida(Map<String, Object> map) {
        return waitingManageMapper.selectCountIsGravida(map);
    }

    @Override
    public Integer selectCountWaitingRoom(Map<String, Object> map) {
        return waitingManageMapper.selectCountWaitingRoom(map);
    }

    @Override
    public Integer selectCountshedu(Map<String, Object> map) {
        return waitingManageMapper.selectCountshedu(map);
    }

    @Override
    public Integer selectCountZhongdian(Map<String, Object> map) {
        return waitingManageMapper.selectCountZhongdian(map);
    }

    @Override
    public List<WaitingRecordEntity> selectIsJuvenileAll(Map<String, Object> map) {
        return waitingManageMapper.selectIsJuvenileAll(map);
    }

    @Override
    public List<WaitingRecordEntity> selectIsDiseaseAll(Map<String, Object> map) {
        return waitingManageMapper.selectIsDiseaseAll(map);
    }

    @Override
    public List<WaitingRecordEntity> selectIsGravidaAll(Map<String, Object> map) {
        return waitingManageMapper.selectIsGravidaAll(map);
    }

    @Override
    public List<WaitingRecordEntity> selectWaitingRoomAll(Map<String, Object> map) {
        return waitingManageMapper.selectWaitingRoomAll(map);
    }

    @Override
    public List<WaitingRecordEntity> selectsheduAll(Map<String, Object> map) {
        return waitingManageMapper.selectsheduAll(map);
    }

    @Override
    public List<WaitingRecordEntity> selectZhongdianAll(Map<String, Object> map) {
        return waitingManageMapper.selectZhongdianAll(map);
    }

    @Override
    public WaitingRecordEntity queryRoomStatusBySerialId(Map<String, Object> map) {
        return waitingManageMapper.queryRoomStatusBySerialId(map);
    }

	@Override
	public int queryLastRoom(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return waitingManageMapper.queryLastRoom(map);
	}

    @Override
    public List<Map<String, Object>> queryRecord(int sId) {
        return waitingManageMapper.queryRecord(sId);
    }

    @Override
    public int queryStatusBySid(Map<String, Object> map) {
        return waitingManageMapper.queryStatusBySid(map);
    }
}
