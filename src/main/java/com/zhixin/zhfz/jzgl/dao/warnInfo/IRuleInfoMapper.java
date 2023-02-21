package com.zhixin.zhfz.jzgl.dao.warnInfo;

import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.RuleInfoEntity;

public interface IRuleInfoMapper {
	
    public List<RuleInfoEntity> getRuleAllRoot() throws Exception;
	
    public List<RuleInfoEntity> getRuleChildrenByRuleId(Map<String, Object> map) throws Exception;

}
