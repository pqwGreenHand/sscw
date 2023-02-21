package com.zhixin.zhfz.bacs.dao.person;

import com.zhixin.zhfz.bacs.entity.PoliceLockersEntity;

import java.util.List;
import java.util.Map;

public interface IPoliceLockersMapper {

    /**
     * 查询全部
     *
     * @param
     * @return
     * @throws Exception
     */
    public List<PoliceLockersEntity> listPersonLockers() throws Exception;

    /**
     * 查询全部
     *
     * @param
     * @return
     * @throws Exception
     */
    public Integer countPersonLockers() throws Exception;

    /**
     * 根据id查询信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    public PoliceLockersEntity getPersonLockersById(Long id) throws Exception;

    /**
     * 根据开柜柜号查询信息
     *
     * @param map
     * @return
     * @throws Exception
     */
    public PoliceLockersEntity getPersonLockersByBox(Map<String, Object> map) throws Exception;

    /**
     * 增加信息
     *
     * @param entity
     * @throws Exception
     */
    public void insertPersonLockers(PoliceLockersEntity entity) throws Exception;

    /**
     * 修改信息
     *
     * @param entity
     * @throws Exception
     */
    public void updatePersonLockers(PoliceLockersEntity entity) throws Exception;

    /**
     * 删除信息
     *
     * @param id
     * @throws Exception
     */
    public void deletePersonLockersById(Long id) throws Exception;

    /**
     * 根据柜门查询信息
     *
     * @param lockerId
     * @return
     * @throws Exception
     */
    public List<PoliceLockersEntity> listPersonLockersByLockerId(Long lockerId) throws Exception;

    /**
     * 根据柜门id查询总数
     *
     * @param lockerId
     * @return
     * @throws Exception
     */
    public Integer countPersonLockersByLockerId(Long lockerId) throws Exception;

    /**
     * 查询民警存物统计
     *
     * @param policeId
     * @return
     * @throws Exception
     */
    public List<PoliceLockersEntity> listPersonLockersByPoliceId(Long policeId) throws Exception;

    /**
     * 统计民警存物统计
     *
     * @param policeId
     * @return
     * @throws Exception
     */
    public Integer countPersonLockersByPoliceId(Long policeId) throws Exception;

    /**
     * 根据柜门查询
     *
     * @param lockerId
     * @return
     * @throws Exception
     */
    public PoliceLockersEntity getPersonLockersByLockerId(Long lockerId) throws Exception;
}