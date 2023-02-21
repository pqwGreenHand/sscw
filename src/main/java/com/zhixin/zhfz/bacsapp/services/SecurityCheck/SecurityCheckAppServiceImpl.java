package com.zhixin.zhfz.bacsapp.services.SecurityCheck;

import com.zhixin.zhfz.bacsapp.dao.SecurityCheck.ISecurityCheckAppMapper;
import com.zhixin.zhfz.bacsapp.entity.SecurityAppEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 安全检查实现类
 * @author: xcf
 * @create: 2019-04-02 14:43
 **/
@Service
public class SecurityCheckAppServiceImpl implements ISecurityCheckAppService {

    @Autowired
    private ISecurityCheckAppMapper securityCheckAppMapper;

    @Override
    public List<SecurityAppEntity> getSecurityList(Map<String, Object> param) {

        this.securityCheckAppMapper.list(param);
        return null;
    }
}
