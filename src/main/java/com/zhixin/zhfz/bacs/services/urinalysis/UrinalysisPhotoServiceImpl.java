package com.zhixin.zhfz.bacs.services.urinalysis;

import com.zhixin.zhfz.bacs.dao.serial.ISerialMapper;
import com.zhixin.zhfz.bacs.dao.urinalysis.UrinalysisDetailMapper;
import com.zhixin.zhfz.bacs.dao.urinalysis.UrinalysisPhotoMapper;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.bacs.entity.UrinalysisPhotoEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.form.FileUploadForm;
import com.zhixin.zhfz.common.services.fileServiceConfig.IFileConfigService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class UrinalysisPhotoServiceImpl implements UrinalysisPhotoService {
    @Autowired
    private UrinalysisPhotoMapper urinalysisPhotoMapper;
    @Autowired
    private IFileConfigService fileConfigService;
    @Autowired
    private ISerialMapper serialMapper;
    @Override
    public void insert(UrinalysisPhotoEntity urinalysisPhotoEntity, MultipartFile file) throws  Exception{
        this.urinalysisPhotoMapper.insertSelective(urinalysisPhotoEntity);
        SerialEntity serialEntity = serialMapper.selectByUrinalysisPhotoId(urinalysisPhotoEntity.getUrinalysisId());
        fileConfigService.upload(FileUploadForm.of("ba", "nj", serialEntity.getUuid(), serialEntity.getAreaId(), urinalysisPhotoEntity.getUrl(), file));
    }

    @Override
    public List<UrinalysisPhotoEntity> getPhotoByUrinalysisId(Long urinalysisId) {
        return urinalysisPhotoMapper.getPhotoByUrinalysisId(urinalysisId);
    }

    @Override
    public void deletePhotoById(Long photoId) {
        urinalysisPhotoMapper.deleteByPrimaryKey(photoId);
    }

    @Override
    public void deleteByUrinalysisId(long urinalysisId) throws Exception {
        urinalysisPhotoMapper.deleteByUrinalysisId(urinalysisId);
    }
}
