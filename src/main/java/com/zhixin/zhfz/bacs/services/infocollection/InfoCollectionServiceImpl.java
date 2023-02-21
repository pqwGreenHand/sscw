package com.zhixin.zhfz.bacs.services.infocollection;

import com.zhixin.zhfz.bacs.dao.infocollection.IInfocollectionMapper;
import com.zhixin.zhfz.bacs.entity.InfoCollectionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InfoCollectionServiceImpl implements IInfoCollectionService {

    @Autowired
    private IInfocollectionMapper infocollectionMapper;

    //查询serailNo字段，判断专属编号书否存在
    @Override
    public int selectserialNo(Long serialNo) {
        return infocollectionMapper.getSerialByNo(serialNo);
    }

    @Override
    public InfoCollectionEntity selectByInterrogateSerialId(Long  serialId) {
        Map<String,Object> map =  new HashMap<String,Object>();
        map.put("serialId",serialId);
        return infocollectionMapper.selectByInterrogateSerialId(map);
    }

    /**
     * 修改采集信息
     */
    @Override
    public void updateByPrimaryKeySelective(InfoCollectionEntity infoCollectionEntity) {
        infocollectionMapper.updateByPrimaryKeySelective(infoCollectionEntity);
    }

    // 分页查询所有嫌疑人信息
    @Override
    public List<InfoCollectionEntity> getAllInfocollection(Map<String, Object> map) {

        return  infocollectionMapper.getAllInfocollection(map);
    }

    // 查询总数
    @Override
    public int count(Map<String, Object> map) {
        return infocollectionMapper.count(map);
    }

    @Override
    public InfoCollectionEntity getInfoCollectionBySerialId(InfoCollectionEntity temInfoCollection) {
        List<InfoCollectionEntity> list=infocollectionMapper.getInfoCollectionBySerialId(temInfoCollection);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

   /* @Override
    public int count(Map<String, Object> map) {
        return infocollectionMapper.count(map);
    }

    @Override
    public int selectserialNo(String serialNo) {
        return 0;
    }

    @Override
    public InfoCollectionEntity selectByInterrogateSerialId(Long id) {
        return null;
    }

    @Override
    public void updateByPrimaryKeySelective(InfoCollectionEntity infoCollectionEntity) {

    }

    @Override
    public void deleteByPrimaryKey(int i) {
        infocollectionMapper.deleteByPrimaryKey(i);
    }*/
}
