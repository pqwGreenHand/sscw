package com.zhixin.zhfz.glpt.services.duban;

import com.zhixin.zhfz.common.entity.ComboboxEntity;
import com.zhixin.zhfz.common.utils.BeanUtils;
import com.zhixin.zhfz.glpt.dao.duban.ISupervisorAnswerMapper;
import com.zhixin.zhfz.glpt.dao.duban.ISupervisorRequestMapper;
import com.zhixin.zhfz.glpt.dao.zg.IRectRequestMapper;
import com.zhixin.zhfz.glpt.entity.RectRequestEntity;
import com.zhixin.zhfz.glpt.entity.SupervisorAnswerEntity;
import com.zhixin.zhfz.glpt.entity.SupervisorRequestEntity;
import com.zhixin.zhfz.glpt.form.SupervisorForm;
import com.zhixin.zhfz.glpt.vo.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class SupervisorServiceImpl implements ISupervisorService{
    @Autowired
    private ISupervisorRequestMapper requestMapper;

    @Autowired
    private ISupervisorAnswerMapper answerMapper;

    private IRectRequestMapper rectMapper;

    @Override
    public PageResponse<SupervisorRequestEntity> listRequest(Map<String, Object> params) {
        List rows;
        Integer count = requestMapper.count(params);
        if(count == 0)
            rows = new ArrayList<>();
        else
            rows = requestMapper.list(params);
        return PageResponse.of(rows,count);
    }

    @Override
    public void status(SupervisorForm form) throws Exception {
        SupervisorAnswerEntity entity = answerMapper.get(form);
        form.setRequestId(entity.getRequestId());
        form.setAuditContent(entity.getAuditContent() + form.getAuditContent() + "\n");
        form.setOpUserId(entity.getOpUserId() + form.getAuditUserId() + "-");
        if(form.getStatus() == 2 ){
            form.setCount( form.getCount()==null?0:form.getCount() + 1);
        }
        requestMapper.status(form);
        answerMapper.status(form);
        if(form.getCount() == 2){
            SupervisorRequestEntity requestEntity = requestMapper.get(form);
            RectRequestEntity rectRequestEntity = new RectRequestEntity();
            BeanUtils.copyPropertiesIgnoreNull(requestEntity,rectRequestEntity);
            rectRequestEntity.setSupId(rectRequestEntity.getId());
            rectMapper.insert(rectRequestEntity);
        }

    }

    @Override
    public void insertRequest(SupervisorRequestEntity entity) throws Exception {
        requestMapper.insert(entity);
    }

    @Override
    public PageResponse<SupervisorAnswerEntity> listAnswer(Map<String, Object> params) {
        List rows = answerMapper.list(params);
        Integer count = answerMapper.count(params);
        return PageResponse.of(rows,count);
    }


    @Override
    public void insertAnswer(SupervisorAnswerEntity entity) throws Exception {
        answerMapper.insert(entity);
    }


    @Override
    public List<ComboboxEntity> caseCombobox(Map<String,Object> params){
        return requestMapper.caseCombobox(params);
    }

}
