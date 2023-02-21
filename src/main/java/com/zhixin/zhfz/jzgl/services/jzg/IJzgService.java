package com.zhixin.zhfz.jzgl.services.jzg;



import java.util.List;
import java.util.Map;

import com.zhixin.zhfz.jzgl.entity.JzgEntity;

/**
 * IJzgMysqlService table: jzg
 * 
 * @author xdp
 */
public interface IJzgService  {
 
    /**
     * 卷柜可视化
     * @param map
     * @return
     */
    public List<JzgEntity> queryAllJzgGmxx(Map<String, Object> map);
    public int countAllJzgGmxx(Map<String, Object> map);
    
    /**
     * 柜子名称
     * @param map
     * @return
     */
    List<JzgEntity> queryAllGm(Map<String, Object> map);
    
}