package com.zhixin.zhfz.bacsapp.services.Information;

import com.zhixin.zhfz.bacsapp.dao.information.INoticeMapper;
import com.zhixin.zhfz.bacsapp.dao.information.ITodoMapper;
import com.zhixin.zhfz.bacsapp.entity.InformationEntity;
import com.zhixin.zhfz.bacsapp.vo.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class InformationServiceImpl implements IInformationService {

    @Autowired
    private INoticeMapper noticeMapper;

    @Autowired
    private ITodoMapper todoMapper;


    @Override
    public PageResponse noticePage(Map<String, Object> params) {
        return PageResponse.of(this.noticeMapper.list(params),this.noticeMapper.count(params));
    }

    @Override
    public int noticeCount(Map<String, Object> params) {
        return this.noticeMapper.count(params);
    }

    @Override
    public PageResponse todoPage(Map<String, Object> params) {
        return PageResponse.of(this.todoMapper.list(params),this.todoMapper.count(params));
    }

    @Override
    public int todoCount(Map<String, Object> params) {
        return this.todoMapper.count(params);
    }

    @Override
    public void deal(Map<String, Object> params) throws Exception {
        if("0".equals(params.get("type").toString())){
            noticeMapper.deal(params);
        }else{
            todoMapper.deal(params);
        }
    }

    @Override
    public void insertInform(InformationEntity entity) throws Exception {
        noticeMapper.insertInform(entity);
    }

	@Override
	public int isInform(InformationEntity entity) throws Exception {
		return noticeMapper.isInform(entity);
	}

	 
}
