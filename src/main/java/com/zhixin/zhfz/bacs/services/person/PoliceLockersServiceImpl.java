package com.zhixin.zhfz.bacs.services.person;

import com.zhixin.zhfz.bacs.dao.person.IPoliceLockersMapper;
import com.zhixin.zhfz.bacs.dao.policebelong.IPoliceBelongMapper;
import com.zhixin.zhfz.bacs.entity.PoliceBelongEntity;
import com.zhixin.zhfz.bacs.entity.PoliceLockersEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class PoliceLockersServiceImpl implements IPoliceLockersService {

    private static Logger logger = Logger.getLogger(PoliceLockersServiceImpl.class);

    @Autowired
    private IPoliceLockersMapper personLockersMapper = null;

    @Autowired
    private IPoliceBelongMapper belongMapper;

    private static final int TYPE_ADD = 0;

    private static final int TYPE_GET = 1;

    private static final int TYPE_REMOVE = 2;

    /**
     * 查询全部
     *
     * @return
     * @throws Exception
     */
    @Override
    public List<PoliceLockersEntity> listAllPersonLockers() throws Exception {
        return personLockersMapper.listPersonLockers();
    }

    /**
     * 查询全部
     *
     * @return
     * @throws Exception
     */
    @Override
    public Integer countAllPersonLockers() throws Exception {
        return personLockersMapper.countPersonLockers();
    }

    /**
     * 查询全部
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public List<PoliceLockersEntity> listPersonLockers(Map<String, Object> map) throws Exception {
        return personLockersMapper.listPersonLockers();
    }

    /**
     * 查询全部
     *
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public Integer countPersonLockers(Map<String, Object> map) throws Exception {
        return personLockersMapper.countPersonLockers();
    }

    /**
     * 根据id查询信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public PoliceLockersEntity getPersonLockersById(Long id) throws Exception {
        return personLockersMapper.getPersonLockersById(id);
    }

    @Override
    public void addBelong(PoliceBelongEntity entity) throws Exception {
        changePersonLockers(null, Long.valueOf(entity.getLockerId()), entity.getPoliceId(), TYPE_ADD);
    }

    @Override
    public void removeBelong(Long belongingsId) throws Exception {
        PoliceBelongEntity entity = belongMapper.getBelongById(belongingsId);
        if (entity != null) {
            changePersonLockers(belongingsId, Long.valueOf(entity.getLockerId()), entity.getPoliceId(), TYPE_REMOVE);
        }
    }

    @Override
    public void getBelong(Long belongingsId) throws Exception {
        PoliceBelongEntity entity = belongMapper.getBelongById(belongingsId);
        if (entity != null) {
            changePersonLockers(belongingsId, Long.valueOf(entity.getLockerId()), entity.getPoliceId(), TYPE_GET);
        }
    }

    public void changePersonLockers(Long belongId, Long lockerId, Long serialId, int type) throws Exception {
        PoliceLockersEntity entity = null;
        try {
            entity = personLockersMapper.getPersonLockersByLockerId(lockerId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (entity == null) {
            if (type == TYPE_ADD) {
                entity = new PoliceLockersEntity();
                entity.setLockerId(lockerId);
                entity.setPoliceId(serialId);
                entity.setIsGet(0);
                try {
                    insertPersonLockers(entity);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        } else {
            if (entity.getPoliceId() == null || entity.getPoliceId() == 0L || serialId == 0L || entity.getPoliceId().equals(serialId)) {
                //更新最后时间
                if (type == TYPE_ADD) {
                    entity.setLockerId(lockerId);
                    entity.setPoliceId(serialId);
                    entity.setIsGet(0);
                } else if (type == TYPE_REMOVE) {
                    entity.setLockerId(lockerId);
                    entity.setPoliceId(0L);
                    entity.setIsGet(1);
                } else {
                    logger.info("last item");
                    int dd = belongMapper.countDetByLockerId(lockerId.intValue());
                    if (dd == 0) {
                        //clean
                        entity.setPoliceId(null);
                        entity.setIsGet(1);
                        entity.setLastLockTime(null);
                        entity.setFirstLockTime(null);
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

    public void cleanPersonLockers(Long lockerId, Long serialId) throws Exception {
        PoliceLockersEntity entity = null;
        try {
            entity = personLockersMapper.getPersonLockersByLockerId(lockerId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (entity != null) {
            entity = new PoliceLockersEntity();
            entity.setLockerId(lockerId);
            entity.setPoliceId(0L);
            entity.setIsGet(0);
            try {
                insertPersonLockers(entity);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    /**
     * 增加信息
     *
     * @param entity
     * @throws Exception
     */
    @Override
    public void insertPersonLockers(PoliceLockersEntity entity) throws Exception {
        if (entity != null) {
            entity.setFirstLockTime(new Date());
            entity.setLastLockTime(new Date());
            personLockersMapper.insertPersonLockers(entity);
        }
    }

    /**
     * 修改信息
     *
     * @param entity
     * @throws Exception
     */
    @Override
    public void updatePersonLockers(PoliceLockersEntity entity) throws Exception {
        if (entity != null) {
            entity.setLastLockTime(new Date());
            personLockersMapper.updatePersonLockers(entity);
        }
    }

    /**
     * 删除信息
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void deletePersonLockersById(Long id) throws Exception {
        personLockersMapper.deletePersonLockersById(id);
    }

    /**
     * 根据柜门查询信息
     *
     * @param lockerId
     * @return
     * @throws Exception
     */
    @Override
    public List<PoliceLockersEntity> listPersonLockersByLockerId(Long lockerId) throws Exception {
        return personLockersMapper.listPersonLockersByLockerId(lockerId);
    }

    /**
     * 根据柜门id查询总数
     *
     * @param lockerId
     * @return
     * @throws Exception
     */
    @Override
    public Integer countPersonLockersByLockerId(Long lockerId) throws Exception {
        return personLockersMapper.countPersonLockersByLockerId(lockerId);
    }

    /**
     * 查询民警存物统计
     *
     * @param policeId
     * @return
     * @throws Exception
     */
    @Override
    public List<PoliceLockersEntity> listPersonLockersByPoliceId(Long policeId) throws Exception {
        return personLockersMapper.listPersonLockersByPoliceId(policeId);
    }

    /**
     * 统计民警存物统计
     *
     * @param policeId
     * @return
     * @throws Exception
     */
    @Override
    public Integer countPersonLockersByPoliceId(Long policeId) throws Exception {
        return personLockersMapper.countPersonLockersByPoliceId(policeId);
    }
}