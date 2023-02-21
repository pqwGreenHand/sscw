package com.zhixin.zhfz.bacs.controller.lawdoc;


import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.bacs.form.LawDocForm;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.glpt.entity.BaCaseEntity;
import com.zhixin.zhfz.glpt.services.IBaCaseService;
import com.zhixin.zhfz.sacw.common.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/zhfz/bacs/lawdoc")
public class LawDocController {

   /* @Autowired
    private ILawDocService lawDocService;*/

    @Autowired
    private IBaCaseService baCaseService;

    @RequestMapping(value = "/listPerson")
    @ResponseBody
    public Map<String,Object> list(@RequestParam Map<String,Object> param, HttpServletRequest request){
        Map<String, Object> map = ControllerTool.mapFilter(param);
        Map<String,Object> result = new HashMap<>();
        List<BaCaseEntity> list = new ArrayList<BaCaseEntity>();
        int total = 0;
        boolean flag = true;
        if (RoleEntity.DATA_AUTH_SELF == (ControllerTool.getRoleDataAuth(request))) {
            // 办案人员-本人
            map.put("dataAuth", " (  bs.in_register_user_id="
                    + ControllerTool.getUserID(request) + " or bs.out_register_user_id="
                    + ControllerTool.getUserID(request) + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 办案场所-本办案场所
            map.put("dataAuth", " bb.area_id=" + ControllerTool.getCurrentAreaID(request));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEAREA == (ControllerTool.getRoleDataAuth(request))) {
            // 管理员 -本办案场所及下级办案场所
            map.put("dataAuth", ControllerTool.getAreasInSql("bb.area_id",
                    ControllerTool.getSessionInfo(request).getCurrentAndSubArea()));
        } else if (RoleEntity.DATA_AUTH_DOWN_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 公安领导-本部门及下级部门
            String sql = ControllerTool
                    .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentAndSubOrg());
            map.put("dataAuth",
                    " ( bs.in_register_user_id " + sql
                            + " or bs.out_register_user_id " + sql + " ) ");
        } else if (RoleEntity.DATA_AUTH_UP_CASCADEORG == (ControllerTool.getRoleDataAuth(request))) {
            // 法制人员-上级部门及下级部门
            String sql = ControllerTool
                    .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getSuperAndSubOrg());
            map.put("dataAuth",
                    " ( bs.in_register_user_id " + sql
                            + " or bs.out_register_user_id " + sql + " ) ");
        } else if (RoleEntity.DATA_AUTH_SELFORG == (ControllerTool.getRoleDataAuth(request))) {
            // 本部门
            String sql = ControllerTool
                    .queryUsersInSqlByOrganizations(ControllerTool.getSessionInfo(request).getCurrentOrgs());
            map.put("dataAuth",
                    " ( bs.in_register_user_id " + sql
                            + " or bs.out_register_user_id " + sql + " ) ");
        } else {
            flag = true;
        }

        if (flag) {
            list = this.baCaseService.listAllLawPerson(map);
            total = this.baCaseService.countAllLawPerson(map);
        }
        /*List<SerialEntity> list = serialService.listAllLawDoc(map);
        int count = serialService.countAllLawDoc(map);*/
        result.put("rows",list);
        result.put("total",total);
        return result;
    }


    @RequestMapping(value = "/listlawdoc")
    @ResponseBody
    public Map<String,Object> list(@RequestParam(required = false) String name, @RequestParam(required = false, value = "userId") Long userId, @RequestParam(required = false, value = "serialNo") String serialNo, @RequestParam(required = false, value = "serialId") Long serialId)throws Exception {
        String path = LawDocController.class.getClassLoader().getResource("").toURI().getPath();
        List<String> lawDocList = Utils.readTxtFile(path + "lawdoc_list.txt");
        List<LawDocForm> lawDocs = new ArrayList<LawDocForm>();
        for (String str : lawDocList) {
            String[] temp = str.split(" ");
            if (temp.length == 3) {
                LawDocForm lawDocForm = new LawDocForm();
                lawDocForm.setNumber(Integer.parseInt(temp[0]));
                lawDocForm.setName(temp[1]);
                lawDocForm.setType(Integer.parseInt(temp[2]));
                if (userId != null) {
                    lawDocForm.setUserId(userId);
                }
                if (serialNo != null & !"".equals(serialNo)) {
                    lawDocForm.setSerialNo(serialNo);
                }
                if (serialId != null & !"".equals(serialId)) {
                    lawDocForm.setSerialId(serialId);
                }
                if (name != null && !"".equals(name.trim())) {
                    if (lawDocForm.getName().indexOf(Utils.encodeStr(name)) > 0) {
                        lawDocs.add(lawDocForm);
                    }
                } else {
                    lawDocs.add(lawDocForm);
                }
            }
        }
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", lawDocs);
        return result;
    }


    public static int getAge(Date birth) {

        if (birth == null)
            throw new RuntimeException("出生日期不能为null");

        int age = 0;

        Date now = new Date();

        SimpleDateFormat format_y = new SimpleDateFormat("yyyy");
        SimpleDateFormat format_M = new SimpleDateFormat("MM");

        String birth_year = format_y.format(birth);
        String this_year = format_y.format(now);

        String birth_month = format_M.format(birth);
        String this_month = format_M.format(now);

        // 初步，估算
        age = Integer.parseInt(this_year) - Integer.parseInt(birth_year);

        // 如果未到出生月份，则age - 1
        if (this_month.compareTo(birth_month) < 0)
            age -= 1;
        if (age < 0)
            age = 0;
        return age;
    }
}
