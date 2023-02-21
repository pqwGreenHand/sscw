package com.zhixin.zhfz.sacw.services.shelf;

import com.zhixin.zhfz.sacw.dao.shelf.IShelfMapper;
import com.zhixin.zhfz.sacw.entity.ShelfEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ShelfServiceImpl implements IShelfService {

    @Autowired
    private IShelfMapper shelfMapper;

    @Override
    public List<ShelfEntity> listAllShelf(Map<String, Object> map) throws Exception {
        return shelfMapper.listAllShelf(map);
    }

    @Override
    public void insertShelf(ShelfEntity shelfEntity) throws Exception {
        shelfMapper.insertShelf(shelfEntity);
    }

    @Override
    public void deleteShelf(int id) throws Exception {
        shelfMapper.deleteShelf(id);
    }

    @Override
    public void updateShelf(ShelfEntity shelfEntity) throws Exception {
        shelfMapper.updateShelf(shelfEntity);
    }

    @Override
    public int count(Map<String, Object> map) throws Exception {
        return shelfMapper.count(map);
    }

    @Override
    public List<ShelfEntity> listAllShelfByLocationId(Map<String, Object> map) throws Exception {
        // TODO Auto-generated method stub
        return shelfMapper.listAllShelfByLocationId(map);
    }

}
