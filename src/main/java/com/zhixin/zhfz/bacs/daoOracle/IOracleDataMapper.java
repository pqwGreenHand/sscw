package com.zhixin.zhfz.bacs.daoOracle;


import java.util.List;
import java.util.Map;


public interface IOracleDataMapper {

	List<Map<String, Object>> queryAjztMap();

	List<Map<String, Object>> queryOrgMap();

	List<Map<String, Object>> queryUserMap();

}