package com.zhixin.zhfz.bacs.dao.belong;

import com.zhixin.zhfz.bacs.entity.BelongEntity;
import java.util.List;
import java.util.Map;

/**
 * 存物具体物品详情
 */
public interface IBelongdetMapper {
	/**
	 * 增加具体的物品存放记录
	 * @param obj
	 */
	void insertBelongdet(BelongEntity obj);


	/**
	 * 查询某条存物的所有物品信息
	 * @param map2
	 * @return
	 */
	List<BelongEntity> listAllBelongdet(Map<String, Object> map2);

	/**
	 * 根据存物详情id删除
	 * @param id
	 */
	void deleteBelongdet(Long id);

	/**
	 * 根据存物id查询全部详情数量
	 * @param params
	 * @return
	 */
	int count1(Map<String, Object> params);

	/**
	 * 查询未领取的存物信息
	 * @param map
	 * @return
	 */
	List<BelongEntity> listAllBelongdet2(Map<String, Object> map);

	/**
	 * 修改存物具体物品信息
	 * @param entity
	 */
	void updateBoxopenouts(BelongEntity entity);

	/**
	 * 创建开柜记录
	 * @param entity
	 */
	void creatBoxopenouts(BelongEntity entity);

	/**
	 * 更新该专属编号下每件物品的提取状态，信息
	 * @param obj
	 */
	void updateBoxopenoutdets(BelongEntity obj);

	/**
	 * 根据储物柜门编号查询具体存物信息
	 * @param map
	 * @return
	 */
	List<BelongEntity> setSerialId(Map<String, Object> map);

	/**
	 * 查询未领取的存物信息
	 * @param map
	 * @return
	 */
	List<BelongEntity> listAllBelongdet3(Map<String, Object> map);

	/**
	 * 根据存物id删除所有的具体物品信息
	 * @param id
	 */
	void deleteBelongdetsecond(Long id);

	/**
	 * 根据存物id查询具体存物数量
	 * @param belongId
	 * @return
	 */
	int countByBelongId(int belongId);

	/**
	 * 查询随身物品开柜记录
	 * @param map3
	 * @return
	 */
	List<BelongEntity> queryBelongingsCabinetLlog(Map<String, Object> map3);

	/**
	 * 随身物品开柜记录总数
	 * @param map3
	 * @return
	 */
	int countBelongingsCabinetLog(Map<String, Object> map3);

	/**
	 * 根据储物柜编号查询存物总数
	 * @param lockerId
	 * @return
	 */
	int countByLockerId(int lockerId);
	/**
	 * 根据柜门id查询柜门存储信息
	 * @param map
	 * @return
	 */
	List<BelongEntity> listAllBelongdetByLockerId(Map<String, Object> map);

	BelongEntity getById(Integer id);
}