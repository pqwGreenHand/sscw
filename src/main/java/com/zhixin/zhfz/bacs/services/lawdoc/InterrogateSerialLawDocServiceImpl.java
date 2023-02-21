package com.zhixin.zhfz.bacs.services.lawdoc;

import com.zhixin.zhfz.bacs.dao.lawdoc.IDocInNotificationMapper;
import com.zhixin.zhfz.bacs.entity.DocInNotificationEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterrogateSerialLawDocServiceImpl  implements IInterrogateSerialLawDocService{

    @Autowired
    private IDocInNotificationMapper iDocInNotificationMapper;

    @Override
    public List<DocInNotificationEntity> queryInNotificationById(Long serialId) {
        return iDocInNotificationMapper.queryInNotificationById(serialId);
    }
}
