/*
 * FileName: PersonLockersService.java
 * auto create by wangguhua
 * Author:
 * Description: PersonLockersService实体类
 */

package com.zhixin.zhfz.bacs.services.person;

import com.zhixin.zhfz.bacs.dao.belong.IBelongMapper;
import com.zhixin.zhfz.bacs.dao.exhibit.IExhibitMapper;
import com.zhixin.zhfz.bacs.dao.person.IPersonLockersMapper;
import com.zhixin.zhfz.bacs.entity.BelongEntity;
import com.zhixin.zhfz.bacs.entity.ExhibitEntity;
import com.zhixin.zhfz.bacs.entity.PersonLockersEntity;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 实体类PersonLockersService table: person_lockers
 *
 * @author wangguhua
 */
@Service
public class PersonLockersServiceImpl implements IPersonLockersService {

    private static Logger logger = Logger.getLogger(PersonLockersServiceImpl.class);

    @Autowired
    private IPersonLockersMapper personLockersMapper;

    //	@Autowired
//	private IBelongService belongService;
    @Autowired
    private IBelongMapper belongMapper;
    @Autowired
    private IExhibitMapper exhibitMapper;

    @Autowired
    private IOperLogService operLogService;
    @Autowired
    private ISerialService serialService;

    private static final int TYPE_ADD = 0;

    private static final int TYPE_GET = 1;

    private static final int TYPE_REMOVE = 2;

    /**
     * 保存存物信息
     *
     * @param entity
     * @throws Exception
     */
    @Override
    public void addBelong(BelongEntity entity) throws Exception {
        changePersonLockers(null, Long.valueOf(entity.getLockerId()), entity.getSerialId(), TYPE_ADD);

    }

    /**
     * 嫌疑人存物
     *
     * @param belongId
     * @param lockerId
     * @param serialId
     * @param type
     * @throws Exception
     */
    public void changePersonLockers(Integer belongId, Long lockerId, Long serialId, int type) throws Exception {
        PersonLockersEntity entity = null;
        try {
            entity = personLockersMapper.getPersonLockersByLockerId(lockerId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        //判断该物品柜是否已存物
        if (entity == null) {
            if (type == TYPE_ADD) {
                entity = new PersonLockersEntity();
                entity.setLockerId(lockerId);
                entity.setSerialId(serialId);
                entity.setIsGet(0);
                try {
                    insertPersonLockers(entity);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        } else {
            if (entity.getSerialId() == null || entity.getSerialId() == 0L || serialId == 0L || entity.getSerialId().equals(serialId)) {
                //更新最后时间
                if (type == TYPE_ADD) {
                    entity.setLockerId(lockerId);
                    entity.setSerialId(serialId);
                    entity.setIsGet(0);
                } else if (type == TYPE_REMOVE) {
                    entity.setLockerId(lockerId);
                    entity.setSerialId(0L);
                    entity.setIsGet(1);
                } else {
                    //检查是否最后一个物品要清空
                    int dd = belongMapper.countDetByLockerId(lockerId.intValue());
                    if (dd == 0) {
                        entity.setSerialId(null);
                        entity.setIsGet(1);
                        entity.setLastLockTime(null);
                        entity.setFristLockTime(null);
                    }

                }
                updatePersonLockers(entity);
            } else {
                if (type == TYPE_ADD) {
                    throw new Exception("该储物柜已有其他人");
                } else {
                    throw new Exception("该储物柜是其他人的");
                }
            }
        }
    }

    /**
     * 增加嫌疑人存物记录
     *
     * @param entity
     * @throws Exception
     */
    @Override
    public void insertPersonLockers(PersonLockersEntity entity) throws Exception {
        if (entity != null) {
            entity.setFristLockTime(new Date());
            entity.setLastLockTime(new Date());
            personLockersMapper.insertPersonLockers(entity);
        }

    }

    /**
     * 修改嫌疑人存取
     *
     * @param entity
     * @throws Exception
     */
    @Override
    public void updatePersonLockers(PersonLockersEntity entity) throws Exception {
        if (entity != null) {
            entity.setLastLockTime(new Date());
            personLockersMapper.updatePersonLockers(entity);
        }

    }

    /**
     * 删除嫌疑人的存物信息
     *
     * @param belongingsId
     * @throws Exception
     */
    @Override
    public void removeBelong(Integer belongingsId) throws Exception {
        BelongEntity entity = belongMapper.getBelongById(belongingsId);
        if (entity != null) {
            changePersonLockers(belongingsId, Long.valueOf(entity.getLockerId()), entity.getSerialId(), TYPE_REMOVE);
        }
    }

    /**
     * 根据存物id修改嫌疑人存物信息
     *
     * @param belongingsId
     * @throws Exception
     */
    @Override
    public void getBelong(Integer belongingsId) throws Exception {
        BelongEntity entity = belongMapper.getBelongById(belongingsId);
        if (entity != null) {
            changePersonLockers(belongingsId, Long.valueOf(entity.getLockerId()), entity.getSerialId(), TYPE_GET);
        }
    }

    /**
     * 增加涉案存物信息
     *
     * @param entity
     * @throws Exception
     */
    public void addExhibit(ExhibitEntity entity) throws Exception {
        changePersonLockers(null, Long.valueOf(entity.getLockerId()), entity.getSerialId(), TYPE_ADD);
    }

    /**
     * 删除涉案存物信息
     *
     * @param exhibitId
     * @throws Exception
     */
    public void removeExhibit(Integer exhibitId) throws Exception {
        if (exhibitId != null) {
            ExhibitEntity entity = exhibitMapper.getExhibitById(exhibitId.longValue());
            if (entity != null) {
                changePersonLockers(exhibitId, Long.valueOf(entity.getLockerId()), entity.getSerialId(), TYPE_REMOVE);
            }
        }
    }

    /**
     * 根据存物id修改嫌疑人存物信息
     *
     * @param exhibitId
     * @throws Exception
     */
    public void getExhibit(Integer exhibitId) throws Exception {
        if (exhibitId != null) {
            ExhibitEntity entity = exhibitMapper.getExhibitById(exhibitId.longValue());
            if (entity != null) {
                changePersonLockers(exhibitId, Long.valueOf(entity.getLockerId()), entity.getSerialId(), TYPE_GET);
            }
        }
    }
}