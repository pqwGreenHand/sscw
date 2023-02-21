package com.zhixin.zhfz.zhag.services.rule;

import com.zhixin.zhfz.zhag.entity.AbstractTree;
import com.zhixin.zhfz.zhag.entity.RuleInfoEntity;

import java.util.List;
import java.util.Map;

public interface IAgRuleService {

	List<AbstractTree> listAllRule();

	List<RuleInfoEntity> listRule(Map<String, Object> param);

	void editTXContent(Map<String, Object> param);
}
