package com.zhixin.zhfz.sacw.dao.involveddoc;


import com.zhixin.zhfz.sacw.entity.InputBookEntity;

import java.util.List;
import java.util.Map;

public interface IInvolvedDocMapper {
    /**
     * 查询涉案物品入库台账
     *
     * @return
     * @throws Exception
     */
    List<InputBookEntity> listInputBook(Map<String, Object> map) throws Exception;

    /**
     * 查询涉案物品入库台账
     *
     * @return
     * @throws Exception
     */
    List<InputBookEntity> listOutputBook(Map<String, Object> map) throws Exception;

    /**
     * 查询未破获案件案卷入库台账
     *
     * @return
     * @throws Exception
     */
    List<InputBookEntity> listUnCaseInputBook(Map<String, Object> map) throws Exception;

    /**
     * 查询未破获案件案卷出库台账
     *
     * @return
     * @throws Exception
     */
    List<InputBookEntity> listUnCaseOutputBook(Map<String, Object> map) throws Exception;

}
