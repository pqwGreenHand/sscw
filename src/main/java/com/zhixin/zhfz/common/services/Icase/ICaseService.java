package com.zhixin.zhfz.common.services.Icase;


import com.zhixin.zhfz.common.entity.CaseEntity;

import java.util.List;
import java.util.Map;

public interface ICaseService {


        /**
         * 查询及条件查询(分页与查询所有共用)
         * @param map
         * @return
         * @throws Exception
         */
        List<CaseEntity> list(Map<String, Object> map)throws Exception;
        /**
         * 分页
         * @param map
         * @return
         * @throws Exception
         */
        int count(Map<String, Object> map)throws Exception;

        /**
         * 插入案件信息
         * @param caseEntity
         * @throws Exception
         */
        int insertCase(CaseEntity caseEntity) throws Exception;

        int getCaseId(String ajbh) throws Exception;

        void deleteCase(int caseId)throws Exception;

        /**
         * 修改案件信息
         * @param caseEntity
         * @throws Exception
         */
        void updateCase(CaseEntity caseEntity) throws Exception;

        CaseEntity getCaseById(Integer id) throws Exception;

        CaseEntity getCaseInfoById(Integer id) throws Exception;
        /**
         * 查询及条件查询(分页与查询所有共用)
         * @param areaId,str
         * @return
         * @throws Exception
         */
        List<CaseEntity> listCase(Integer areaId, String str) throws Exception;


}
