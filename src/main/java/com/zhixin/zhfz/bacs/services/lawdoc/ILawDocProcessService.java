package com.zhixin.zhfz.bacs.services.lawdoc;

import com.zhixin.zhfz.bacs.entity.LawDocProcessEntity;
import com.zhixin.zhfz.bacs.form.BelongDocForm;
import com.zhixin.zhfz.bacs.form.CheckbodyLawDocForm;
import com.zhixin.zhfz.bacs.form.LawDocForm;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.List;

public interface ILawDocProcessService {

	LawDocProcessEntity getProcessData(LawDocForm form, HttpServletRequest request, HttpSession session) throws ParseException;

	LawDocProcessEntity getProcessData4(BelongDocForm form, LawDocProcessEntity result);

	LawDocProcessEntity getProcessData55(BelongDocForm form, LawDocProcessEntity result);

	LawDocProcessEntity getProcessData57(BelongDocForm form, LawDocProcessEntity result);

	LawDocProcessEntity getProcessData5(BelongDocForm form, LawDocProcessEntity result);

    LawDocProcessEntity getProcessData1(CheckbodyLawDocForm form);
}
