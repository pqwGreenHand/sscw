package com.zhixin.zhfz.bacs.services.belongtemp;

import com.zhixin.zhfz.bacs.dao.belongtemp.IBelongDetailTempMapper;
import com.zhixin.zhfz.bacs.dao.belongtemp.IBelongTempMapper;
import com.zhixin.zhfz.bacs.entity.BelongDetailTempEntity;
import com.zhixin.zhfz.bacs.entity.BelongTempEntity;
import com.zhixin.zhfz.bacs.entity.BelongingsPhotoTempEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class BelongTempServiceImpl implements IBelongTempService {
    @Resource
    private IBelongTempMapper belongTempMapper;

    @Override
    public void saveBelong(BelongTempEntity belongTempEntity) {
        belongTempMapper.saveBelong(belongTempEntity);
    }

    @Override
    public List<BelongTempEntity> getListBySfzh(Map<String, Object> sfzh) {
        return  belongTempMapper.getListBySfzh(sfzh);
    }

    @Override
    public void updateTempYjyj(BelongTempEntity updateTempYjyj) {
        belongTempMapper.updateTempYjyj(updateTempYjyj);
    }

    @Override
    public List<BelongTempEntity> getEntityById(Map<String, Object> map) {
        return belongTempMapper.getEntityById(map);
    }

    @Override
    public List<BelongTempEntity> list(Map<String, Object> map) {
        return belongTempMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return belongTempMapper.count(map);
    }

    @Override
    public void insertBelongphotoTemp(BelongingsPhotoTempEntity photoTempEntity) {
        belongTempMapper.insertBelongphotoTemp(photoTempEntity);
    }
}
