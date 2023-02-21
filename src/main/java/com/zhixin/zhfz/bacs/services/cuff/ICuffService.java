package com.zhixin.zhfz.bacs.services.cuff;

import com.zhixin.zhfz.bacs.entity.CuffEntity;
import com.zhixin.zhfz.bacs.entity.PoliceEntranceEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ICuffService {
	
	//手环操作类型 0:上下班,1:他所民警,2:嫌疑人,3:其他人员
	public static final int BIND_TYPE_ATTENDANCE=0;
	public static final int BIND_TYPE_OTHER_POLICE=1;
	public static final int BIND_TYPE_CRIMINAL=2;
	public static final int BIND_TYPE_OTHER_PERSON=3;
	
	
	List<CuffEntity> list(Map<String, Object> params) throws Exception;

	int count(Map<String, Object> params) throws Exception;

	List<CuffEntity> listAll() throws Exception;

	void deleteCuff(Long id) throws Exception;

	void insert(CuffEntity entity);

	void update(CuffEntity entity);

	//void insertLog(String string, int sHId, Integer status);

	List<CuffEntity> queryArea();
	
	CuffEntity getCuffById(Integer id) throws Exception;
	
	CuffEntity getCuffByNo(Map<String, Object> params) throws Exception;

	CuffEntity getCuffByCuffNo(Map<String, Object> params) throws Exception;
    //导入
	void userImportByXls(MultipartFile file, Integer areaId, HttpServletRequest request) throws Exception;

	CuffEntity getCuffNoByIcNoAndType(Map<String, Object> params) throws Exception;

	/**
	 * 嫌疑人绑定手环
	 * @param entity
	 * @throws Exception
	 */
	CuffEntity criminalBindCuff(SerialEntity entity) throws Exception;
	/**
	 * 解绑手环
	 * @param id
	 * @return
	 */
	void unbindCuffById(Integer id);

	/**
	 * 其他派出所民警绑定手环
	 * @param cuffId
	 * @param userId
	 * @throws Exception
	 */
	void otherPoliceBindCuff(PoliceEntranceEntity detailEntity) throws Exception;

	/**
	 * 临时出区，设置手环状态手环
	 * @param id
	 * @return
	 */
	void tempOutUnbindCuffById(Integer id);

}
