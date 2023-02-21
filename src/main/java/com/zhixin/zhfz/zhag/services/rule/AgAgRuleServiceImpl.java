package com.zhixin.zhfz.zhag.services.rule;

import com.zhixin.zhfz.zhag.dao.rule.IAgRuleMapper;
import com.zhixin.zhfz.zhag.entity.AbstractTree;
import com.zhixin.zhfz.zhag.entity.RuleInfoEntity;
import com.zhixin.zhfz.zhag.form.RuleInfoForm;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AgAgRuleServiceImpl implements IAgRuleService {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(AgAgRuleServiceImpl.class);

	@Autowired
	private IAgRuleMapper ruleMapper;

	@Override
	public List<AbstractTree> listAllRule() {
		// TODO Auto-generated method stub
//		return ruleMapper.queryRule();
		RuleInfoForm form = new RuleInfoForm();
//		form.setId(typeLong);
		List<RuleInfoEntity> list = ruleMapper.listData(form);
		List<AbstractTree> treeList = shortTree(list,false);
		return treeList;
	}

	private List<AbstractTree> shortTree(List<RuleInfoEntity> list,boolean rootZero){
		List<AbstractTree> treeList=new ArrayList<AbstractTree>();
		Map<Long, RuleInfoEntity> map = new LinkedHashMap<Long, RuleInfoEntity>();
		for (RuleInfoEntity entity : list) {
				map.put(entity.getId(), entity);
		}
		if (rootZero) {
			RuleInfoEntity zero = map.get(1L);
			if (zero != null) {
				treeList.add(getAbstractTree(zero,map));
			}
		} else {
			for (RuleInfoEntity entity : map.values()) {
				if (entity.getIsDirectories()==0 && entity.getParentRuleId()==0) {
					treeList.add(getAbstractTree(entity,map));
				}
			}
		}
		return treeList;
	}
	private AbstractTree getAbstractTree(RuleInfoEntity entity,Map<Long, RuleInfoEntity> map ){
		AbstractTree tree = new AbstractTree();
			tree.setId(entity.getId());
			tree.setText(entity.getRuleName());
			tree.setType(entity.getIsDirectories());
			tree.setSftx(entity.getSftx());
			tree.setTznr(entity.getTxnr());
		for (Long key : map.keySet()) {
			RuleInfoEntity c=map.get(key);
			if(c!=null &&c.getParentRuleId() ==entity.getId())
			{
				AbstractTree ct=getAbstractTree(c, map );
				tree.addChildren(ct);
			}
			
		}
		return tree;
	}

	@Override
	public List<RuleInfoEntity> listRule(Map<String,Object> param) {
		// TODO Auto-generated method stub
		return ruleMapper.listRule(param);
	}

	@Override
	public void editTXContent(Map<String,Object> param) {
		ruleMapper.editTXContent(param);
	}
}
