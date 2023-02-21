package com.zhixin.zhfz.bacs.services.qlywgzs;

import com.zhixin.zhfz.bacs.dao.qlywgzs.IRightsTemplateMapper;
import com.zhixin.zhfz.bacs.entity.RightsTemplateEntity;
import com.zhixin.zhfz.bacs.form.RightsTemplateSaveForm;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @program: zhfz
 * @description: 笔录word模板service
 * @author: jzw
 * @create: 2019-02-21 11:33
 **/
@Service
public class RightsTemplateServiceImpl implements IRightsTemplateService {

    @Autowired
    private IRightsTemplateMapper dao;

    @Override
    public List<RightsTemplateEntity> list(Map<String, Object> map) throws Exception {
        return dao.list(map);
    }

    @Override
    public int count(Map<String, Object> map) throws Exception {
        return dao.count(map);
    }

    @Override
    public void delete(Long id) throws Exception {
        dao.delete(id);
    }

    @Override
    public void insert(RightsTemplateEntity entity) throws Exception {
        dao.insert(entity);
    }

    @Override
    public void insert(RightsTemplateSaveForm form,HttpServletRequest request) throws Exception{
        RightsTemplateEntity entity = new RightsTemplateEntity();
        String fileName = form.getWordFile().getOriginalFilename();
        entity.setContent(getContent(form.getWordFile()));
        entity.setWord(form.getWordFile().getBytes());
        entity.setName(fileName);
        entity.setSortNo(form.getSortNo());
        entity.setOp_Pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        entity.setOp_User_Id(ControllerTool.getSessionInfo(request).getUser().getId());
        this.insert(entity);
    }

    @Override
    public void update(RightsTemplateSaveForm form) throws Exception {
        RightsTemplateEntity entity = new RightsTemplateEntity();
        if(form.getWordFile() != null){
            entity.setWord(form.getWordFile().getBytes());
            entity.setContent(getContent(form.getWordFile()));
        }
        entity.setSortNo(form.getSortNo());
        entity.setId(form.getId());
        dao.update(entity);
    }

    @Override
    public RightsTemplateEntity getWordById(Long id) throws Exception {
        return dao.getWordById(id);
    }


    private String getContent(MultipartFile file) throws Exception{
        String buffer = "";
        if (file.getOriginalFilename().endsWith(".doc")) {
            WordExtractor ex = new WordExtractor(file.getInputStream());
            buffer = ex.getText();
            ex.close();
        } else {
            System.out.println("此文件不是word文件！");
        }
        return buffer;
    }

}
