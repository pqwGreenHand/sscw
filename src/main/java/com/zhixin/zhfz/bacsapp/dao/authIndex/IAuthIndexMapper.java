package com.zhixin.zhfz.bacsapp.dao.authIndex;

import com.zhixin.zhfz.bacsapp.entity.AuthIndexEntity;

import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 权限首页dao
 * @author: xcf
 * @create: 2019-04-01 11:04
 **/

public interface IAuthIndexMapper {


    List<AuthIndexEntity> selectAuthIndexList(Map<String,String> params);
}
