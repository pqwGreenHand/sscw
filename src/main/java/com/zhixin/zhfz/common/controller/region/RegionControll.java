package com.zhixin.zhfz.common.controller.region;

import com.zhixin.zhfz.common.entity.RegionEntity;
import com.zhixin.zhfz.common.services.region.RegionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/zhfz/common/region")
public class RegionControll {
    @Autowired
    private RegionService regionService;

    private static final Logger logger = LoggerFactory.getLogger(RegionControll.class);

    @RequestMapping(value = "/list")
    @ResponseBody
    public List<RegionEntity> list(@RequestParam Map<String, Object> params) {
        return regionService.list(params);
    }


    @RequestMapping(value = "/selectByCode")
    @ResponseBody
    public RegionEntity selectByCode(@RequestParam int code) throws Exception{
        return regionService.selectByCode(code);
    }
}
