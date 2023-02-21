package com.zhixin.zhfz.bacsapp.services.authIndex;

import com.zhixin.zhfz.bacsapp.entity.AuthIndexEntity;

import java.util.List;

/**
 * @program: zhfz
 * @description: 权限首页实现接口
 * @author: xcf
 * @create: 2019-04-01 11:07
 **/

public interface IAuthIndexService {
    List<AuthIndexEntity> getAuthList() throws Exception;
}
