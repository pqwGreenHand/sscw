package com.zhixin.zhfz.bacs.daoOracle;


import java.util.List;
import java.util.Map;


public interface IAjxxOracleMapper  {


	List<Map<String, Object>> getAjxxFromOracleXz(Map<String, Object> map);

	List<Map<String, Object>> getAjxxFromOracleXs(Map<String, Object> map);

	List<Map<String, Object>> queryOracleRycljd(Map<String, Object> map);

	
}