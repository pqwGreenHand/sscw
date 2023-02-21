/*
 * FileName: ActionLogEntity.java
 * auto create by wangguhua 2016
 * Author:   
 * Date:     2016-12-13 11:10:07
 * Description: IActionLogMapper实体类   
 */
 
package com.zhixin.zhfz.bacs.dao.actionlog;



import com.zhixin.zhfz.bacs.entity.ActionLogEntity;

import java.util.List;
import java.util.Map;

/**
 * 实体类IActionLogMapper table: action_log
 * 
 * @author wangguhua
 */
public interface IActionLogMapper {
	public void insertActionLog(ActionLogEntity entity) throws Exception;
}