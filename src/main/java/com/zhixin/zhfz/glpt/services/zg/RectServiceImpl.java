package com.zhixin.zhfz.glpt.services.zg;

import com.zhixin.zhfz.common.entity.ComboboxEntity;
import com.zhixin.zhfz.glpt.dao.zg.IRectAnswerMapper;
import com.zhixin.zhfz.glpt.dao.zg.IRectRequestMapper;
import com.zhixin.zhfz.glpt.entity.RectAnswerEntity;
import com.zhixin.zhfz.glpt.entity.RectRequestEntity;
import com.zhixin.zhfz.glpt.form.RectForm;
import com.zhixin.zhfz.glpt.vo.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RectServiceImpl implements IRectService{
    @Autowired
    private IRectRequestMapper requestMapper;

    @Autowired
    private IRectAnswerMapper answerMapper;

    @Override
    public PageResponse<RectRequestEntity> listRequest(Map<String, Object> params) {
        List rows;
        Integer count = requestMapper.count(params);
        if(count == 0)
            rows = new ArrayList<>();
        else
            rows = requestMapper.list(params);
        return PageResponse.of(rows,count);
    }

    @Override
    public void status(RectForm form) throws Exception {
        RectAnswerEntity entity = answerMapper.get(form);
        form.setRequestId(entity.getRequestId());
        form.setAuditContent(entity.getAuditContent() + form.getAuditContent() + "\n");
        form.setOpUserId(entity.getOpUserId() + form.getAuditUserId() + "-");
        if(form.getStatus() == 2 ){
            form.setCount( form.getCount() + 1);
        }
        requestMapper.status(form);
        answerMapper.status(form);
    }

    @Override
    public void insertRequest(RectRequestEntity entity) throws Exception {
        requestMapper.insert(entity);
    }

    @Override
    public PageResponse<RectAnswerEntity> listAnswer(Map<String, Object> params) {
        List rows = answerMapper.list(params);
        Integer count = answerMapper.count(params);
        return PageResponse.of(rows,count);
    }


    @Override
    public void insertAnswer(RectAnswerEntity entity) throws Exception {
        answerMapper.insert(entity);
    }


}
