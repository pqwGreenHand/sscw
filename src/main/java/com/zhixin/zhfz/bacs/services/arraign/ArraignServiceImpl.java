package com.zhixin.zhfz.bacs.services.arraign;

import com.zhixin.zhfz.bacs.dao.arraign.IArraignMapper;
import com.zhixin.zhfz.bacs.dao.room.IRoomMapper;
import com.zhixin.zhfz.bacs.dao.waitingmanage.IWaitingManageMapper;
import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.bacs.form.ArraignForm;
import com.zhixin.zhfz.bacs.services.policeEntrance.IPoliceEntranceService;
import com.zhixin.zhfz.bacs.services.record.IRecordService;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.bacs.services.waitingmanage.IWaitingManageService;
import com.zhixin.zhfz.common.entity.MessageEntity;
import com.zhixin.zhfz.common.services.user.IUserService;
import com.zhixin.zhfz.common.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArraignServiceImpl implements IArraignService {


    @Autowired
    private IArraignMapper mapper;

    @Autowired
    private IRecordService recordService;
    @Autowired
    private ISerialService serialService;

    @Autowired
    private IRoomMapper roomMapper;

    @Autowired
    private IWaitingManageService waitingManageService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IPoliceEntranceService policeEntranceService;
    @Override
    public List<ArraignEntity> all(Map<String, Object> params) {
//        List<ArraignEntity> roomList = mapper.roomAll(params);
//        String param = "(0";
//        for(ArraignEntity room : roomList){
//            param += "," + room.getId();
//        }
//        param += ")";
//        params.put("roomIds",param);
//        List<ArraignEntity> personList = mapper.personAll(params);
//        if(personList != null && personList.size() > 0){
//            for(ArraignEntity person : personList){
//                for(ArraignEntity r : roomList){
//                    if(r.equals(person)){
//                        BeanUtils.copyPropertiesIgnoreNull(person,r);
//                        break;
//                    }
//                }
//            }
//        }
        return mapper.all(params);
    }

    @Override
    public RoomAssignEntity arraign(ArraignForm form) throws Exception{

        RoomEntity roomEntity = roomMapper.getRoomById(form.getRoomId());
        // 1.更新对应候问记录的提讯民警和登记民警信息,提讯去的房间
        WaitingRecordEntity waitingRecordEntity = new WaitingRecordEntity();
        waitingRecordEntity.setOutUserId(Integer.parseInt(form.getPoliceId()+""));
        waitingRecordEntity.setOutTime(new Date());
        waitingRecordEntity.setGetUserId1(Integer.parseInt(form.getPoliceId() + ""));
        waitingRecordEntity.setGetUserId2(Integer.parseInt(form.getPoliceId()+""));
        waitingRecordEntity.setGetResult(roomEntity!=null?roomEntity.getName():"");
        waitingRecordEntity.setRecordId(form.getRecordId());
        waitingRecordEntity.setSerialId(form.getSerialId());
        waitingManageService.updateWaitingRecord(waitingRecordEntity);//

        mapper.arraign(form);
        RoomAssignEntity entity = new RoomAssignEntity();
        BeanUtils.copyPropertiesIgnoreNull(form,entity);
        Map<String,Object> params = new HashMap<>();
        params.put("id",form.getId());
        params.put("status",1);
        roomMapper.updateRoomStatus(params);
        mapper.insertAssign(entity);
        //todo LED
        Map waitParam = new HashMap();
        waitParam.put("id",form.getRecordId());
        waitParam.put("direction",roomEntity.getName());
        System.out.println(waitParam);
        waitingManageService.saveDirection(waitParam);
        //等候管理--选择去向--4结束侯问
        serialService.updateStatusById(form.getSerialId(),4);
        System.out.println(form.toString());
        return entity;
    }

    @Override
    public Long getMaxId(){
        return mapper.getMaxId();
    }

    @Override
    public MessageEntity clear(Long roomId, Integer serialId, HttpServletRequest request) throws Exception{
        Map<String,Object> params = new HashMap<>();
        params.put("id",roomId);
        params.put("status",0);
        roomMapper.updateRoomStatus(params);
        RoomAssignEntity entity = new RoomAssignEntity();
        entity.setRoomId(roomId);
        mapper.updateAssign(entity);
//        WaitingRecordEntity waitingRecordEntity = new WaitingRecordEntity();
//        waitingRecordEntity.setSerialId(Long.parseLong(serialId+""));
//        WaitingRecordEntity oldEntity = waitingManageService.getWaitingRecordBySerialId2(waitingRecordEntity);
//        //查询判断嫌疑人是否在审讯中
//        Map<String, Object> map = new HashMap<>();
//        map.put("serialId", oldEntity.getSerialId());
//        List<RecordEntity> recordEntities = recordService.selectBySerialId(map);
//        if (recordEntities != null && recordEntities.size() > 0) {
//            for (int i = 0; i < recordEntities.size(); i++) {
//                RecordEntity recordEntity = recordEntities.get(i);
//                if (recordEntity.getEndTime() == null || recordEntity.getEndTime().equals("")) {
//                    return new MessageEntity().addCode(1).addIsError(false).addTitle("提醒").addContent("该嫌疑人正在审讯中,无法返回侯问室!");
//                }
//            }
//        }
//        oldEntity.setStatus(1);
//        waitingManageService.updateStatusByIdForGoBack(oldEntity.getRecordId()+"");
//        WaitingRecordEntity wentity = new WaitingRecordEntity();
//        wentity.setSerialId(oldEntity.getSerialId());
//        wentity.setPersonId(oldEntity.getPersonId());
//        wentity.setInUserId(oldEntity.getInUserId());
//        wentity.setStatus(0);
//        wentity.setAreaId(oldEntity.getAreaId());
//        wentity.setCaseId(oldEntity.getCaseId());
//        wentity.setRoomId(oldEntity.getRoomId());
//        wentity.setSendUserId1(oldEntity.getSendUserId1());
//        wentity.setOpPid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
//        wentity.setOpUserId(ControllerTool.getUserID(request));
//        waitingManageService.insert(wentity);
        return new MessageEntity().addCode(0).addIsError(false).addTitle("通知").addContent("清理成功!");
    }


    @Override
    public List<ArraignEntity> listAllRecordRoom(Map<String, Object> params) {
        return mapper.listAllRecordRoom(params);
    }
    @Override
    public List<ArraignEntity> listAllRecordRoom1(Map<String, Object> params) {
        return mapper.listAllRecordRoom1(params);
    }

    @Override
    public void updateRoom(ArraignEntity entity) {
        mapper.updateRoom(entity);
    }

    @Override
    public List<ArraignEntity> queryRoomByPerson(Map<String, Object> param) {
        return mapper.queryRoomByPerson(param);
    }

    @Override
    public List<ArraignEntity> queryRoomInfo(Map<String, Object> map) {
        return mapper.queryRoomInfo(map);
    }

    @Override
    public void updateRoomInfo(Map<String, Object> map) {
         mapper.updateRoomInfo(map);
    }
}
