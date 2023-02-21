package com.zhixin.zhfz.bacs.services.zfba;

import java.util.List;
import java.util.Map;

public interface IZfbaService {

    List<Map<String, Object>> queryAjxx(Map<String, Object> param);

    Integer queryAjxxCount(Map<String, Object> param);

    List<Map<String, Object>> queryPerson(Map<String, Object> param);

    List<Map<String, Object>> queryBelong(Map<String, Object> param);

    Map<String, Object> queryAjxxById(String id);

    Map<String, Object> queryYyxxById(String id);
}
