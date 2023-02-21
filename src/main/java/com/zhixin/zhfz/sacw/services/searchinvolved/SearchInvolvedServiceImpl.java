package com.zhixin.zhfz.sacw.services.searchinvolved;

import com.zhixin.zhfz.sacw.dao.searchinvolved.ISearchInvolvedMapper;
import com.zhixin.zhfz.sacw.entity.SearchInvolvedEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SearchInvolvedServiceImpl implements ISearchInvolvedService {

    @Autowired
    ISearchInvolvedMapper mapper;


    @Override
    public List<SearchInvolvedEntity> list(Map<String, Object> map) throws Exception {
        return mapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) throws Exception {
        return mapper.count(map);
    }
}
