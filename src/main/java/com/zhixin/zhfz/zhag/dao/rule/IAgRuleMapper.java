package com.zhixin.zhfz.zhag.dao.rule;

import com.zhixin.zhfz.zhag.entity.RuleInfoEntity;
import com.zhixin.zhfz.zhag.form.RuleInfoForm;

import java.util.List;
import java.util.Map;


public interface IAgRuleMapper {

	List<RuleInfoEntity> queryRule();

	List<RuleInfoEntity> listData(RuleInfoForm form);

	List<RuleInfoEntity> listRule(Map<String, Object> param);

   void editTXContent(Map<String, Object> param);
}
