package com.zhixin.zhfz.bacs.services.urinalysis;

import com.zhixin.zhfz.bacs.entity.UrinalysisPhotoEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UrinalysisPhotoService {
    public void insert(UrinalysisPhotoEntity urinalysisPhotoEntity, MultipartFile file) throws  Exception;

    public List<UrinalysisPhotoEntity> getPhotoByUrinalysisId(Long urinalysisId );

    public void deletePhotoById(Long photoId);

    public void deleteByUrinalysisId(long urinalysisId) throws Exception;
}
