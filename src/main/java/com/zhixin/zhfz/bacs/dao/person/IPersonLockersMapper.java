/*
 * FileName: PersonLockersEntity.java
 * auto create by wangguhua 2016
 * Author:
 * Date:     2016-8-15 9:31:00
 * Description: IPersonLockersMapper实体类
 */

package com.zhixin.zhfz.bacs.dao.person;


import com.zhixin.zhfz.bacs.entity.PersonLockersEntity;

import java.util.List;
import java.util.Map;

/**
 * 实体类IPersonLockersMapper table: person_lockers
 *
 * @author wangguhua
 */
public interface IPersonLockersMapper {
    /**
     * 查询所有嫌疑人存取信息
     *
     * @return
     * @throws Exception
     */
    public List<PersonLockersEntity> listAllPersonLockers() throws Exception;

    /**
     * 查询所有嫌疑人存取记录数量统计
     *
     * @return
     * @throws Exception
     */
    public Integer countAllPersonLockers() throws Exception;

    /**
     * 查询所有嫌疑人存取信息
     *
     * @param map
     * @return
     * @throws Exception
     */
    public List<PersonLockersEntity> listPersonLockers(Map<String, Object> map) throws Exception;

    /**
     * 查询所有嫌疑人存取记录数量统计
     *
     * @param map
     * @return
     * @throws Exception
     */
    public Integer countPersonLockers(Map<String, Object> map) throws Exception;

    /**
     * 根据id查询嫌疑人开柜记录
     *
     * @param id
     * @return
     * @throws Exception
     */
    public PersonLockersEntity getPersonLockersById(Long id) throws Exception;

    /**
     * 根据柜门id嫌疑人开柜记录
     *
     * @param lockerId
     * @return
     * @throws Exception
     */
    public PersonLockersEntity getPersonLockersByLockerId(Long lockerId) throws Exception;

    /**
     * 根据配置id和柜门查询嫌疑人开柜记录
     *
     * @param map (String boxNum,Long personalConfigId)
     * @return
     * @throws Exception
     */
    public PersonLockersEntity getPersonLockersByBox(Map<String, Object> map) throws Exception;

    /**
     * 增加嫌疑人存取信息
     *
     * @param entity
     * @throws Exception
     */
    public void insertPersonLockers(PersonLockersEntity entity) throws Exception;

    /**
     * 根据id修改信息
     *
     * @param entity
     * @throws Exception
     */
    public void updatePersonLockers(PersonLockersEntity entity) throws Exception;

    /**
     * 根据id删除信息
     *
     * @param id
     * @throws Exception
     */
    public void deletePersonLockersById(Long id) throws Exception;

    /**
     * 根据柜门id查询嫌疑人存取操作
     *
     * @param lockerId
     * @return
     * @throws Exception
     */
    public List<PersonLockersEntity> listPersonLockersByLockerId(Long lockerId) throws Exception;

    /**
     * 根据柜门id查询嫌疑人存取操作总数根据柜门id查询嫌疑人存取操作总数
     *
     * @param lockerId
     * @return
     * @throws Exception
     */
    public Integer countPersonLockersByLockerId(Long lockerId) throws Exception;

    /**
     * 根据searilid查询所有嫌疑人
     *
     * @param serialId
     * @return
     * @throws Exception
     */
    public List<PersonLockersEntity> listPersonLockersBySerialId(Long serialId) throws Exception;

    /**
     * 根据searilid查询所有嫌疑人开柜总和
     *
     * @param serialId
     * @return
     * @throws Exception
     */
    public Integer countPersonLockersBySerialId(Long serialId) throws Exception;


}