/*
 * FileName: IPersonLockersService.java
 * auto create by wangguhua
 * Author:
 * Description: PersonLockersService实体类
 */

package com.zhixin.zhfz.bacs.services.person;

import com.zhixin.zhfz.bacs.entity.BelongEntity;
import com.zhixin.zhfz.bacs.entity.ExhibitEntity;
import com.zhixin.zhfz.bacs.entity.PersonLockersEntity;

import java.util.List;
import java.util.Map;

/**
 * IPersonLockersService table: person_lockers
 *
 * @author wangguhua
 */
public interface IPersonLockersService {
    /**
     * 增加存物信息
     *
     * @param entity
     * @throws Exception
     */
    public void addBelong(BelongEntity entity) throws Exception;

    /**
     * 修改嫌疑人存取
     *
     * @param entity
     * @throws Exception
     */
    public void updatePersonLockers(PersonLockersEntity entity) throws Exception;

    /**
     * 增加嫌疑人存物记录
     *
     * @param entity
     * @throws Exception
     */
    public void insertPersonLockers(PersonLockersEntity entity) throws Exception;

    /**
     * 删除存物信息
     *
     * @param belongingsId
     * @throws Exception
     */
    public void removeBelong(Integer belongingsId) throws Exception;

    /**
     * 根据存物id修改嫌疑人存物信息
     *
     * @param belongingsId
     * @throws Exception
     */
    public void getBelong(Integer belongingsId) throws Exception;

    /**
     * 增加涉案存物信息
     *
     * @param entity
     * @throws Exception
     */
    public void addExhibit(ExhibitEntity entity) throws Exception;

    /**
     * 删除涉案存物惜
     *
     * @param exhibitId
     * @throws Exception
     */
    public void removeExhibit(Integer exhibitId) throws Exception;

    /**
     * 根据存物id修改嫌疑人存物信息
     *
     * @param exhibitId
     * @throws Exception
     */
    public void getExhibit(Integer exhibitId) throws Exception;
}