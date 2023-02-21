package com.zhixin.zhfz.sacw.services.combobox;

import com.zhixin.zhfz.bacs.entity.ComboboxEntity;
import com.zhixin.zhfz.sacw.dao.combobox.IInvComboboxMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InvComboboxServiceImpl implements IInvComboboxService {
    @Autowired
    private IInvComboboxMapper comboboxMapper;

    @Override
    public List<ComboboxEntity> listCertificateType(Map<String, Object> map) throws Exception {
        return comboboxMapper.listCertificateType(map);
    }


    @Override
    public List<ComboboxEntity> getAllInterrogateAreaName(Map<String, Object> map) throws Exception {
        return comboboxMapper.getAllInterrogateAreaName(map);
    }

    @Override
    public List<ComboboxEntity> getAllUser(Map<String, Object> map) {

        return comboboxMapper.getAllUser(map);
    }

    @Override
    public List<ComboboxEntity> listAllRoomGroupName(Map<String, Object> map) {
        return comboboxMapper.listAllRoomGroupName(map);
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
    public List<ComboboxEntity> getAllInvLocation(Map<String, Object> params) {
        return comboboxMapper.getAllInvLocation(params);
    }

    @Override
    public List<ComboboxEntity> getAllShelf(Map<String, Object> params) {
        return comboboxMapper.getAllShelf(params);
    }

    @Override
    public List<ComboboxEntity> listOrgByType(Map<String, Object> map) throws Exception {
        return comboboxMapper.listOrgByType(map);
    }

    @Override
    public List<ComboboxEntity> listInRecordByWareHouseId(Map<String, Object> map) throws Exception {
        return comboboxMapper.listInRecordByWareHouseId(map);
    }

    @Override
    public List<ComboboxEntity> listLocation(Map<String, Object> map) throws Exception {
        return comboboxMapper.listLocation(map);
    }


    @Override
    public List<ComboboxEntity> listShelf(Map<String, Object> map) throws Exception {
        return comboboxMapper.listShelf(map);
    }

    @Override
    public List<ComboboxEntity> listCodeCombobox(Map<String, Object> map) throws Exception {
        return comboboxMapper.listCodeCombobox(map);
    }

    @Override
    public List<ComboboxEntity> getWareHouse(Map<String, Object> map) throws Exception {
        return comboboxMapper.getWareHouse(map);
    }

    @Override
    public List<ComboboxEntity> listCrimeTypeByNature(Map<String, Object> params) {
        return comboboxMapper.listCrimeTypeByNature(params);
    }

    @Override
    public List<ComboboxEntity> getInvolvedByCase(Map<String, Object> params) {
        return comboboxMapper.getInvolvedByCase(params);
    }

    @Override
    public List<ComboboxEntity> getInvolvedByCaseId(Map<String, Object> params) {
        return comboboxMapper.getInvolvedByCaseId(params);
    }


    @Override
    public List<ComboboxEntity> listInputType() {
        return comboboxMapper.listInputType();
    }
	/*@Override
	public List<ComboboxEntity> listWarehouseByCase() {
		return comboboxMapper.listInputType();

	}*/

}
