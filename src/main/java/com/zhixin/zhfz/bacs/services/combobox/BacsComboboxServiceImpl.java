package com.zhixin.zhfz.bacs.services.combobox;


import com.zhixin.zhfz.bacs.dao.cabinetConfig.ICabinetConfigMapper;
import com.zhixin.zhfz.bacs.dao.combobox.IBacsComboboxMapper;
import com.zhixin.zhfz.bacs.entity.CabinetConfigEntity;
import com.zhixin.zhfz.bacs.entity.ComboboxEntity;
import com.zhixin.zhfz.bacs.entity.LockerEntity;
import com.zhixin.zhfz.bacs.entity.PersonalConfigEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BacsComboboxServiceImpl implements IComboboxService {

	@Autowired
	private IBacsComboboxMapper comboboxMapper;

	@Autowired
	private ICabinetConfigMapper cabinetConfigMapper;
	@Override
	public List<ComboboxEntity> listCertificateType(Map<String, Object> map) throws Exception {
		return comboboxMapper.listCertificateType(map);
	}
	@Override
	public List<ComboboxEntity> getAllInterrogateAreaName(Map<String, Object> map) throws Exception {
		return comboboxMapper.getAllInterrogateAreaName(map);
	}


	@Override
	public List<ComboboxEntity> getAllCuff(Map<String, Object> map) throws Exception {
		return comboboxMapper.getAllCuff(map);
	}

	@Override
	public List<ComboboxEntity> listAllOrganizationName(Map<String, Object> params) {
		return comboboxMapper.listAllOrganizationName(params);

	}

	@Override
	public List<ComboboxEntity> listAllRoomName(Map<String, Object> map) {
		return comboboxMapper.listAllRoomName(map);
	}



	@Override
	public List<ComboboxEntity> listRoomBySerial(Map<String, Object> params) {
		return comboboxMapper.listRoomBySerial(params);
	}

	@Override
	public List<ComboboxEntity> listAllOrganization(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return comboboxMapper.listAllOrganization(params);
	}

	@Override
	public List<ComboboxEntity> listAllOrganization1(Map<String, Object> map) {
		return comboboxMapper.listAllOrganization1(map);
	}


	@Override
	public List<ComboboxEntity> listLawType(Map<String, Object> params) {
		return comboboxMapper.listLawType(params);
	}

	@Override
	public List<ComboboxEntity> listCrimeDefine(Map<String, Object> params) {
		return comboboxMapper.listCrimeDefine(params);
	}

	@Override
	public List<ComboboxEntity> listKnowledgeCrime(Map<String, Object> params) {
		return comboboxMapper.listKnowledgeCrime(params);
	}

	@Override
	public List<ComboboxEntity> listSerial(Map<String, Object> params) {
		return comboboxMapper.listSerial(params);
	}

	@Override
	public List<ComboboxEntity> listSerialPolice(Map<String, Object> params) {
		return comboboxMapper.listSerialPolice(params);
	}

	@Override
	public List<ComboboxEntity> listRecordTemplate(Map<String, Object> params) {
		return comboboxMapper.listRecordTemplate(params);
	}


	@Override
	public List<ComboboxEntity> listLawName(Map<String, Object> params) {
		return comboboxMapper.listLawName(params);
	}



	/*@Override
	public List<ComboboxEntity> getAllInterrogateAreaName(Map<String, Object> params) {
		return comboboxMapper.getAllInterrogateAreaName(params);
	}*/
	@Override
	public List<ComboboxEntity> listCrimeTypeByNature(Map<String, Object> params) {
		return comboboxMapper.listCrimeTypeByNature(params);
	}

	/**
	 * 拼接存物柜下拉框信息
	 * @param areaId
	 * @param lockerId
	 * @return
	 */
	@Override
	public List<ComboboxEntity> listBelongLockerBox(Integer areaId,Long lockerId){
		List<ComboboxEntity> result=new ArrayList<>();
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("areaId", areaId);
		params.put("lockerId", lockerId);
		try {
			params.put("codeKey", 1);// 个人随物
			List<CabinetConfigEntity> configList = cabinetConfigMapper.listFn(params);
			if (configList != null) {
				for (int i = 0; i < configList.size(); i++) {
					LockerEntity loc = new LockerEntity();
					loc.setId(configList.get(i).getId());
					params.put("configId", configList.get(i).getId());
					List<ComboboxEntity> list=comboboxMapper.listBelongLockerBox(params);
					if(list!=null){
						for (ComboboxEntity com :list){
							com.setValue((i+1)+"组"+com.getValue());
							result.add(com);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 拼接存物柜下拉框信息
	 * @param areaId
	 * @param lockerId
	 * @return
	 */
	@Override
	public List<ComboboxEntity> listExhibitLockerBox(Integer areaId,Long lockerId){
		List<ComboboxEntity> result=new ArrayList<>();
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("areaId", areaId);
		params.put("lockerId", lockerId);
		try {
			params.put("codeKey", 2);// 个人随物
			List<CabinetConfigEntity> configList = cabinetConfigMapper.listFn(params);
			if (configList != null) {
				for (int i = 0; i < configList.size(); i++) {
					LockerEntity loc = new LockerEntity();
					loc.setId(configList.get(i).getId());
					params.put("configId", configList.get(i).getId());
					List<ComboboxEntity> list=comboboxMapper.listExhibitLockerBox(params);
					if(list!=null){
						for (ComboboxEntity com :list){
							com.setValue((i+1)+"组"+com.getValue());
							result.add(com);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 拼接存物柜下拉框信息
	 * @param areaId
	 * @param lockerId
	 * @return
	 */
	@Override
	public List<ComboboxEntity> listPoliceBelongLockerBox(Integer areaId,Long lockerId){
		List<ComboboxEntity> result=new ArrayList<>();
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("areaId", areaId);
		params.put("lockerId", lockerId);
		try {
			params.put("codeKey", 3);// 个人随物
			List<CabinetConfigEntity> configList = cabinetConfigMapper.listFn(params);
			if (configList != null) {
				for (int i = 0; i < configList.size(); i++) {
					LockerEntity loc = new LockerEntity();
					loc.setId(configList.get(i).getId());
					params.put("configId", configList.get(i).getId());
					List<ComboboxEntity> list=comboboxMapper.listPoliceBelongLockerBox(params);
					if(list!=null){
						for (ComboboxEntity com :list){
							com.setValue((i+1)+"组"+com.getValue());
							result.add(com);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@Override
	public List<ComboboxEntity> listAlarmType(Map<String, Object> map) throws Exception {
		return comboboxMapper.listAlarmType(map);
	}

	@Override
	public List<ComboboxEntity> listOfficer(Map<String, Object> map) throws Exception {
		return comboboxMapper.listOfficer(map);
	}

	@Override
	public List<ComboboxEntity> getSerialList(Map<String, Object> map) {
		return comboboxMapper.getSerialList(map);
	}

	@Override
	public List<ComboboxEntity> listAllOrganizationCode(Map<String, Object> params) {
		return comboboxMapper.listAllOrganizationCode(params);
	}

	@Override
	public List<ComboboxEntity> listPersonByCaseId(Map<String, Object> params) {
		return comboboxMapper.listPersonByCaseId(params);
	}
}
