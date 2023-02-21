package com.zhixin.zhfz.sacw.services.involveddoc;


import com.zhixin.zhfz.sacw.entity.InvolvedDocEntity;
import com.zhixin.zhfz.sacw.form.InvolvedDocForm;

import javax.servlet.http.HttpServletRequest;

public interface IInvolvedDocService {

    InvolvedDocEntity getProcessData(InvolvedDocForm form, HttpServletRequest request);

}
