package com.zhixin.zhfz.jzgl.dao.gmxxPz;

import java.util.List;

import com.zhixin.zhfz.jzgl.entity.GmxxPzEntity;

/**
 * 实体类 table:jz_gmxx_pz
 * 
 * @author xdp
 */
public interface IGmxxPzMapper {
	List<GmxxPzEntity> queryAllPz();
}