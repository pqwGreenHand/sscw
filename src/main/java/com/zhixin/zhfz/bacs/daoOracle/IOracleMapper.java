package com.zhixin.zhfz.bacs.daoOracle;

import java.util.List;
import java.util.Map;

public interface IOracleMapper {

    List<Map<String, Object>> queryJzDataAjxx(Map<String, Object> map);

    int queryJzDataAjxxCount(Map<String, Object> map);

    List<Map<String, Object>> queryJzDataPerson(Map<String, Object> params);

    List<Map<String, Object>> jzFillByRybhAndAJbh(Map<String, Object> map);
}
