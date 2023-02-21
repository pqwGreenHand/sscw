package com.zhixin.zhfz.common.controller.authority;

import com.zhixin.zhfz.common.entity.*;
import com.zhixin.zhfz.common.form.AuthorityForm;
import com.zhixin.zhfz.common.form.IDForm;
import com.zhixin.zhfz.common.services.authority.IAuthorityService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.utils.PowerCacheUtil;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.util.*;

/**
 * 权限
 */
@Controller("AuthorityController")
@RequestMapping("/zhfz/common/authority")
public class AuthorityController {

    private static final Logger logger = LoggerFactory.getLogger(AuthorityController.class);

    @Autowired
    private IAuthorityService authorityService;
    @Autowired
    private IOperLogService operLogService;
    @Autowired
    private PowerCacheUtil powerCacheUtil;

    /**
     * 根据用户标识，获取所有类型为0(即：系统)的权限
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listSystemMenu")
    @ResponseBody
    public List<AuthorityEntity> listSystemMenu(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return new PowerCacheUtil().getUserAllSysAutontity(Long.parseLong(ControllerTool.getUserID(request) + ""));
    }

    /**
     * 根据user和系统权限 查询权限下的菜单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/listMenuByPid")
    @ResponseBody
    public Map<String, Object> listMenuByPid(HttpServletRequest request) {
        Long pid = Long.parseLong(request.getParameter("pid") + "");
        List<MenuTree> value = new ArrayList();
        SessionInfo sessionInfo = ControllerTool.getSessionInfo(request);
        Integer userid = sessionInfo.getUser().getId();
        List<AuthorityEntity> listmenu = authorityService.getAuthorityDataById(userid, pid);
        if (listmenu != null && listmenu.size() > 0) {
            //排序
            Collections.sort(listmenu, new Comparator<AuthorityEntity>() {
                @Override
                public int compare(AuthorityEntity arg0, AuthorityEntity arg1) {
                    if (arg0.getSortNumber() != arg1.getSortNumber()) {
                        return arg1.getSortNumber().compareTo(arg0.getSortNumber());
                    }
                    return arg0.getId().compareTo(arg1.getId());
                }
            });
            for (int i = 0; i < listmenu.size(); i++) {
                AuthorityEntity authorityEntity = listmenu.get(i);
                if (authorityEntity.getParentId() == pid) {
                    MenuTree mt = new MenuTree();
                    mt.setId(authorityEntity.getId());
                    mt.setParentTitle(authorityEntity.getTitle());
                    List<LowerMenu> lms = new ArrayList<>();
                    for (AuthorityEntity ae : listmenu) {
                        if (ae.getParentId().equals(authorityEntity.getId())) {
                            LowerMenu lm = new LowerMenu();
                            lm.setId(ae.getId());
                            lm.setUrl(ae.getUrl());
                            lm.setTitle(ae.getTitle());
                            lm.setJsMethod(ae.getJsMethod());
                            if (ae.getJsMethod() != null && ae.getJsMethod().equals("CS")) {

                                String url = ae.getUrl();
                                lm.setUrl(url.replace("@orgId@", sessionInfo.getCurrentOrg().getId().toString()));
                            }
                            lms.add(lm);
                        }
                    }
                    mt.setLowerMenu(lms);
                    value.add(mt);
                }
            }
        }
        AuthorityEntity authorityEntity = authorityService.queryAuthorityById(pid);
        Map<String, Object> result = new HashMap<>();
        result.put("authInfo", authorityEntity);
        result.put("childAuth", value);
        return result;
    }

    /**
     * 查看角色的具体权限
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getRoleAuthority")
    @ResponseBody
    public List<Long> getRoleAuthority(@RequestParam Long id) throws Exception {
        List<Long> authorityIds = authorityService.getAuthorityIdsByRole(id);
        return authorityIds;
    }

    /**
     * 查看Cs角色的具体权限
     *
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getRoleAuthorityCs")
    @ResponseBody
    public List<Long> getRoleAuthorityCs(@RequestParam Long id) throws Exception {
        List<Long> authorityIds = authorityService.getAuthorityCsIdsByRole(id);
        return authorityIds;
    }

    /**
     * 查询所有权限并生成树
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/getAuthorityTree")
    @ResponseBody
    public void getAuthorityTree(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("---------------------------getAuthorityTree----------------------------");
        List<AbstractTreeEntity> treeList = new ArrayList<AbstractTreeEntity>();
       // if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuth(request))) {
            treeList = authorityService.getAuthorityTree();
        //}
        response.setCharacterEncoding("UTF-8");
        Writer writer = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(writer);
        JsonGenerator jsonGenerator2 = objectMapper.getJsonFactory().createJsonGenerator(System.out);
        jsonGenerator2.writeObject(treeList);
        jsonGenerator.writeObject(treeList);
    }

    /**
     * 查询cs角色所有权限并生成树
     */
    @RequestMapping(value="/getCsAuthorityTree")
    @ResponseBody
    public void getCsAuthorityTree(HttpServletRequest request, HttpServletResponse response) throws Exception{
        List<AbstractTreeEntity> treeList = new ArrayList<AbstractTreeEntity>();
       // if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuth(request))) {
            treeList = authorityService.getCsAuthorityTree();
        //}
        response.setCharacterEncoding("UTF-8");
        Writer writer = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(writer);
        JsonGenerator jsonGenerator2 = objectMapper.getJsonFactory().createJsonGenerator(System.out);
        jsonGenerator2.writeObject(treeList);
        jsonGenerator.writeObject(treeList);
    }

    /**
     * 角色保存所属权限
     *
     * @param roleId
     * @param authorityIds
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/savaRoleAuthority", method = RequestMethod.POST)
    @ResponseBody
    public MessageEntity savaRoleAuthority(@RequestParam Long roleId, String authorityIds,HttpServletRequest request) throws Exception {
        // 先清除所有权限
        authorityService.delAuthorityByRole(roleId);
        // 保存最新的权限
        if (authorityIds != null && !"".equals(authorityIds.trim())) {
            try {
                authorityService.batchInsertRoleAuthority(roleId, authorityIds);
                this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "角色权限添加日志" + roleId,  ControllerTool.getLoginName(request), true, "系统配置");
            } catch (Exception e) {
                e.printStackTrace();
                this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "角色权限添加日志" + roleId,  ControllerTool.getLoginName(request), false, "系统配置");
                return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("权限保存失败!");
            }
        }
        return new MessageEntity();
    }

    /**
     * cs角色保存所属权限
     *
     * @param roleId
     * @param authorityIds
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/savaRoleAuthorityCs", method = RequestMethod.POST)
    @ResponseBody
    public MessageEntity savaRoleAuthorityCs(@RequestParam Long roleId,String authorityIds,HttpServletRequest request) throws Exception {
           // 先清楚权限
        authorityService.delAuthorityCsByRole(roleId);
        // 保存最新的权限
        if (authorityIds != null && !"".equals(authorityIds.trim())) {
            try {
                authorityService.batchInsertRoleAuthorityCs(roleId, authorityIds);
                this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "角色权限添加日志" + roleId,  ControllerTool.getLoginName(request), true, "系统配置");
            } catch (Exception e) {
                e.printStackTrace();
                this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "角色权限添加日志" + roleId,  ControllerTool.getLoginName(request), false, "系统配置");
                return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("权限保存失败!");
            }
        }
        return new MessageEntity();
    }

    /**
     * 刷新权限
     *
     * @return
     */
    @RequestMapping(value = "/refrshAuthority")
    @ResponseBody
    public MessageEntity refrshAuthority() {
        //刷新
        try {
            powerCacheUtil.refreshCacheAsyc();
        } catch (Exception e1) {
            e1.printStackTrace();
            return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("刷新权限失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("刷新权限成功!");
    }

    /**
     * 权限查询表树形
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/allTree")
    @ResponseBody
    public List<AuthorityEntity> getAuthorityAllTree(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        logger.debug("---------------------------getAuthorityAllTree----------------------------");
        List<AuthorityEntity> list = new ArrayList<AuthorityEntity>();
        if (RoleEntity.DATA_AUTH_FULL == (ControllerTool.getRoleDataAuth(request))) {
            list = authorityService.getAuthorityData();
        }
        response.setCharacterEncoding("UTF-8");
        return list;
    }

    /**
     * 权限新增添加
     *
     * @param form
     * @param request
     * @return
     */
    @SuppressWarnings("unused")
    @RequestMapping(value = "/addAuthority")
    @ResponseBody
    public MessageEntity add(@RequestBody AuthorityForm form, HttpServletRequest request) {
        int id1 = 0;
        AuthorityEntity entity = new AuthorityEntity();
        entity.setId(form.getAuthorityId());
        entity.setTitle(form.getTitle());
        entity.setEnglishTitle(form.getEnglishTitle());
        entity.setDescription(form.getDescription());
        entity.setType(form.getType());
        entity.setParentId(form.getParentId());
        // 查找根节点
        if (entity.getParentId() != null) {
            List<AuthorityEntity> objList = authorityService.queryRootid(entity.getParentId());
            if (objList != null) {
                for (int i = 0; i < objList.size(); i++) {
                    AuthorityEntity obj = objList.get(0);
                    if (obj != null) {
                        entity.setRootId(obj.getRootId());
                    }
                }
            } else {
                id1 = (int) (Math.random() * 99999999);
                entity.setRootId(Long.parseLong(String.valueOf(id1)));
            }
        } else {
            Integer maxRootId = authorityService.queryMaxRootId();
            if (maxRootId != null && maxRootId > 0) {
                id1 = maxRootId + 1;
            }
            entity.setRootId(Long.parseLong(String.valueOf(id1)));
        }
        entity.setSortNumber(form.getSortNumber());
        entity.setUrl(form.getUrl());
        if (form.getJsMethod() != null && form.getJsMethod() != "" && form.getJsMethod1() != null && form.getJsMethod1() != "") {
            entity.setJsMethod(form.getJsMethod() + "," + form.getJsMethod1());
        }
        if (form.getJsMethod() != null && form.getJsMethod() != "" && form.getJsMethod1() == null || form.getJsMethod1() == "") {
            entity.setJsMethod(form.getJsMethod());
        }
        if (form.getJsMethod() == null && form.getJsMethod1() != null && form.getJsMethod1() != "" || form.getJsMethod() == "") {
            entity.setJsMethod(form.getJsMethod1());
        }
        entity.setIcon(form.getIcon());
        entity.setBigIcon(form.getBigIcon());
        entity.setSmallIcon(form.getSmallIcon());
        try {
            boolean value = this.authorityService.insert(entity);
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "权限添加日志" + entity.toString(),  ControllerTool.getLoginName(request), true, "系统配置");
        } catch (Exception e) {
            this.operLogService.insertLog(OperLogEntity.INSERT_TYPE, "权限添加日志" + entity.toString(),  ControllerTool.getLoginName(request), false, "系统配置");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("添加失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("添加成功!");
    }

    /**
     * root树
     * @param request
     * @param response
     * @param type
     * @throws Exception
     */
    @RequestMapping(value = "/rootTree")
    @ResponseBody
    public void getAuthorityTreeData(HttpServletRequest request, HttpServletResponse response,@RequestParam(required=false,value="type") Long type) throws Exception {
        long typeLong=7L;
        List<AbstractTreeEntity> list = authorityService.getAuthorityTreeData(typeLong);
        for(AbstractTreeEntity obj : list){
        }
        response.setCharacterEncoding("UTF-8");
        Writer writer = response.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonGenerator jsonGenerator = objectMapper.getJsonFactory().createJsonGenerator(writer);
        jsonGenerator.writeObject(list);
    }

    /**
     * 修改权限信息
     * @param form
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/editAuthority")
    @ResponseBody
    public MessageEntity edit(@RequestBody AuthorityForm form, HttpServletRequest request,
                              HttpServletResponse response) {
        int id1 = 0;
        AuthorityEntity entity = new AuthorityEntity();
        entity.setId(form.getId());
        entity.setTitle(form.getTitle());
        entity.setEnglishTitle(form.getEnglishTitle());
        entity.setDescription(form.getDescription());
        entity.setType(form.getType());
        entity.setParentId(form.getParentId());
        // 查找根节点
        if (entity.getParentId() != null) {
            List<AuthorityEntity> objList = authorityService.queryRootid(entity.getParentId());
            if (objList != null) {
                for (int i = 0; i < objList.size(); i++) {
                    AuthorityEntity obj = objList.get(0);
                    if (obj != null) {
                        entity.setRootId(obj.getRootId());
                    }
                }
            } else {
                id1 = (int) (Math.random() * 99999999);
                entity.setRootId(Long.parseLong(String.valueOf(id1)));
            }
        } else {
            entity.setRootId(Long.parseLong(String.valueOf(id1)));
        }
        entity.setSortNumber(form.getSortNumber());
        entity.setUrl(form.getUrl());
        if(form.getJsMethod()!=null &&form.getJsMethod()!=""&&form.getJsMethod1()!=null &&form.getJsMethod1()!=""){
            entity.setJsMethod(form.getJsMethod());
        }
        if(form.getJsMethod()!=null&&form.getJsMethod()!=""&&form.getJsMethod1()==null||form.getJsMethod1()==""){
            entity.setJsMethod(form.getJsMethod());
        }
        if(form.getJsMethod()==null&&form.getJsMethod1()!=null&&form.getJsMethod1()!=""||form.getJsMethod()==""){
            entity.setJsMethod(form.getJsMethod1());
        }
        entity.setIcon(form.getIcon());
        entity.setBigIcon(form.getBigIcon());
        entity.setSmallIcon(form.getSmallIcon());
        // entity.setFlag(form.getFlag());
        try {
            this.authorityService.update(entity);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "权限修改日志" + entity.getId(),  ControllerTool.getLoginName(request), true,"系统配置");
        } catch (Exception e) {
            logger.error("Error on edit Authority!", e);
            this.operLogService.insertLog(OperLogEntity.EDIT_TYPE, "权限修改日志" + entity.getId(),  ControllerTool.getLoginName(request), false,"系统配置");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("修改失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("修改成功!");
    }

    /**
     * 删除权限
     * @param form
     * @param request
     * @return
     */
    @RequestMapping(value = "/removeAuthority")
    @ResponseBody
    public MessageEntity remove(@RequestBody IDForm form, HttpServletRequest request) {
        logger.info("++++++++remove++++++id=  " + form.getId());
        try {
            boolean value = this.authorityService.deleteSelf(form.getLongID());
            boolean value1 = this.authorityService.delete(form.getLongID());
            // 删除role_authority 数据
            this.authorityService.deleteRoleAuthority(form.getLongID());
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "权限删除日志" + form.getLongID(), ControllerTool.getLoginName(request), true,"系统配置");
        } catch (Exception e) {
            logger.error("Error on deleting removeAuthority!", e);
            this.operLogService.insertLog(OperLogEntity.DELETE_TYPE, "权限删除日志" + form.getLongID(), ControllerTool.getLoginName(request), false,"系统配置");
            return new MessageEntity().addCode(1).addIsError(true).addTitle("Error").addContent("删除失败!");
        }
        return new MessageEntity().addCode(1).addIsError(false).addTitle("Message").addContent("删除成功!");
    }
}
