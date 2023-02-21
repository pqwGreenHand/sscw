package com.zhixin.zhfz.bacs.daoOracle;

import java.util.List;
import java.util.Map;

public interface ErQiOracleMapper {


	List<Map<String, Object>> queryOraclePersonXz(Map<String, Object> map);

	List<Map<String, Object>> queryOraclePersonXs(Map<String, Object> map);

	List<Map<String, Object>> queryOracleRycljd(Map<String, Object> map);

}
