package com.zhixin.zhfz.zhag.dao.rule;

import com.zhixin.zhfz.zhag.entity.RuleConfigEntity;

import java.util.List;
import java.util.Map;


public interface IAgRuleConfigMapper {

	List<RuleConfigEntity> listAllRuleConfig(Map<String, Object> map);

	int countConfig(Map<String, Object> map);

	List<RuleConfigEntity> listAllRuleConfigGj(Map<String, Object> map);

	int countConfigGj(Map<String, Object> map);

	void insertRuleConfig(RuleConfigEntity entity);

	void deleteRuleConfig(Long id);

	void updateRuleConfig(RuleConfigEntity entity);

	List<RuleConfigEntity> listAllRuleConfigInfo();
}
