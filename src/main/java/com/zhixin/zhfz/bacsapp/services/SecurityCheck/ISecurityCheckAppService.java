package com.zhixin.zhfz.bacsapp.services.SecurityCheck;

import com.zhixin.zhfz.bacsapp.entity.SecurityAppEntity;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 安全检查服务接口
 * @author: xcf
 * @create: 2019-04-02 14:44
 **/

public interface ISecurityCheckAppService {

    List<SecurityAppEntity> getSecurityList(Map<String, Object> param);

}
