package com.zhixin.zhfz.bacsapp.services.authIndex;

import com.zhixin.zhfz.bacsapp.dao.authIndex.IAuthIndexMapper;
import com.zhixin.zhfz.bacsapp.entity.AuthIndexEntity;
import com.zhixin.zhfz.common.entity.CodeEntity;
import com.zhixin.zhfz.common.services.code.ICodeService;
import com.zhixin.zhfz.common.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 权限首页实现类
 * @author: xcf
 * @create: 2019-04-01 11:07
 **/
@Service
public class AuthIndexServiceImpl implements IAuthIndexService{

    @Autowired
    private IAuthIndexMapper authIndexMapper;

    @Autowired
    private ICodeService codeService;

    @Override
    public List<AuthIndexEntity> getAuthList() throws Exception {
        List<CodeEntity> codeEntities = codeService.listByType("APP");
        List<AuthIndexEntity> authIndexEntities = new ArrayList<>();
        if(BeanUtils.notEmpty(codeEntities)){
            Map<String,String> params = new HashMap<>();
            params.put("name",codeEntities.get(0).getCodeValue());
            authIndexEntities = authIndexMapper.selectAuthIndexList(params);
        }
        return authIndexEntities;
    }
}
