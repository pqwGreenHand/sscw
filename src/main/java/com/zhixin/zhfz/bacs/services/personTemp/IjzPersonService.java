package com.zhixin.zhfz.bacs.services.personTemp;



import com.zhixin.zhfz.bacs.entity.JzPersonEntity;

import java.util.List;

public interface IjzPersonService {

    List<JzPersonEntity> queryPersonLrrqMaxXz();

    List<JzPersonEntity> queryPersonLrrqMaxXs();

    void insert(JzPersonEntity entity);
}