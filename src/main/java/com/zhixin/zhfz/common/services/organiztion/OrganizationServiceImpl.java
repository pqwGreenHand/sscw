package com.zhixin.zhfz.common.services.organiztion;

import com.zhixin.zhfz.bacs.dao.area.IAreaMapper;
import com.zhixin.zhfz.bacs.entity.AreaEntity;
import com.zhixin.zhfz.common.dao.organiztion.IOrganizationMapper;
import com.zhixin.zhfz.common.dao.role.IRoleMapper;
import com.zhixin.zhfz.common.dao.user.IUserMapper;
import com.zhixin.zhfz.common.entity.OrganizationEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.entity.UserEntity;
import com.zhixin.zhfz.common.entity.UserImportEntity;

import com.zhixin.zhfz.common.utils.ControllerTool;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.*;

@Service
public class OrganizationServiceImpl implements IOrganizationService {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(OrganizationServiceImpl.class);

	private static List<OrganizationEntity> tempOrganizationList = new ArrayList<OrganizationEntity>();

	@Autowired
	private IOrganizationMapper organizationMapper;
	@Autowired
	private IRoleMapper roleMapper;
	@Autowired
	private IUserMapper userMapper;
	@Autowired
	private IAreaMapper areaMapper;

	@Override
	public List<OrganizationEntity> list(Map<String, Object> map) {
		return organizationMapper.list(map);
	}

	@Override
	public int count(Map<String, Object> map) {
		return organizationMapper.count(map);
	}

	@Override
	public List<OrganizationEntity> listChild(Integer parentId) {
		return this.organizationMapper.listChild(parentId);
	}

	@Override
	public List<OrganizationEntity> getAllSubOrgByParent(int parentOrgId) {
		List<OrganizationEntity> tempList = listChild(parentOrgId);
		if (tempList != null) {
			List<OrganizationEntity> tempList2 = new ArrayList<>();
			for (OrganizationEntity organizationEntity : tempList) {
				tempList2.addAll(getAllSubOrgByParent(organizationEntity.getId()));
			}
			tempList.addAll(tempList2);
		}
		return tempList;
	}

	private String orgStr(List<OrganizationEntity> orgs) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < orgs.size(); i++) {
			sb.append(orgs.get(i).getId());
			if (i != (orgs.size() - 1)) {
				sb.append(",");
			}
		}
		return sb.toString();
	}



	private static long cacheTime = 0L;

	private void initCache() {
		if (cacheTime <= System.currentTimeMillis() - 24 * 60 * 60 * 1000L) {
			// do nothing
			List<OrganizationEntity> list = organizationMapper.list(new HashMap<String, Object>());
			if (tempOrganizationList == null) {
				tempOrganizationList = new ArrayList<OrganizationEntity>();
			} else {
				tempOrganizationList.clear();
			}
			if (list != null) {
				tempOrganizationList.addAll(list);
			}
			cacheTime = System.currentTimeMillis();
		}
		logger.info("tempOrganizationList size:" + tempOrganizationList.size());
	}

	@Override
	public OrganizationEntity getOrganizationById(Integer id) {
		return this.organizationMapper.load(id);
	}

	@Override
	public void refreshSessionOrg(SessionInfo sessionInfo, Long areaId) {
		initCache();
		// 获取本部门的信息
		OrganizationEntity currentOrg = getOrganizationById(sessionInfo.getUser().getOrganizationId());
		//当前部门
		sessionInfo.setCurrentOrg(currentOrg);
		String currentOrgPid = "'"+currentOrg.getPid()+"'";
		//当前部门pid
		sessionInfo.setCurrentOrgPid(currentOrgPid);
		List<Long> childOrgs = new ArrayList<Long>();
//		findChildIds(currentOrg.getId().longValue(), childOrgs);
		List<OrganizationEntity> currentAndSubOrg = new ArrayList<OrganizationEntity>();
		if (!childOrgs.isEmpty()) {
			currentAndSubOrg = listByIds(childOrgs);
		}
		currentAndSubOrg.add(currentOrg);
		//当前部门以及下级部门
		sessionInfo.setCurrentAndSubOrg(currentAndSubOrg);
		//当前部门以及下级部门pid
		String currentAndSubOrgPid = "'"+currentOrg.getPid()+"%'";
		sessionInfo.setCurrentAndSubOrgPid(currentAndSubOrgPid);
		List<Long> subIdList = new ArrayList<Long>();
//		findSubIds(currentOrg.getId().longValue(), subIdList);
		List<OrganizationEntity> superAndSubOrg = new ArrayList<OrganizationEntity>();
//		if (!subIdList.isEmpty()) {
			superAndSubOrg = listByIds(subIdList);
//		}
		//上级部门以及下级部门
		sessionInfo.setSuperAndSubOrg(superAndSubOrg);
		String[] parentPid = currentOrg.getPid().split("_");
		String superAndSubOrgPid = "'";
		for(int i=0;i<parentPid.length-1;i++){
			if(i==parentPid.length-2){
				superAndSubOrgPid+=parentPid[i]+"%'";
			}else{
				superAndSubOrgPid+=parentPid[i]+"_";
			}
		}
		//上级部门以及下级部门Pid
		sessionInfo.setSuperAndSubOrgPid(superAndSubOrgPid);
		//获取本办案场所信息
		Map<String, Object> map = new HashMap<>();
		AreaEntity currentArea = null;
		if(areaId == null){
			map.put("orgStr", currentOrg.getId());
			List<AreaEntity> areaEntities = areaMapper.listAreaByOrgStr(map);
			currentArea = (areaEntities!=null&&(areaEntities.size()>0))?areaEntities.get(0):null;
			if (currentArea==null){
				currentArea = new AreaEntity();
				currentArea.setOrganizationId(-100);
				currentArea.setId(-100);
			}
		}else{
			map.put("id",areaId);
			currentArea = areaMapper.get(map);
		}
		sessionInfo.setCurrentArea(currentArea);
		//获取本部门下级所有的办案场所信息
		List<AreaEntity> currentOrgSubArea =null;
		map.clear();
		map.put("orgStr", orgStr(currentAndSubOrg));
		currentOrgSubArea=areaMapper.listAreaByOrgStr(map);
		if (currentOrgSubArea==null || currentOrgSubArea.size()==0){
			AreaEntity areaEntity = new AreaEntity();
			areaEntity.setOrganizationId(-100);
			areaEntity.setId(-100);
			currentOrgSubArea.add(areaEntity);
		}
		sessionInfo.setCurrentAndSubArea(currentOrgSubArea);


		//获取上级部门下面所有的办案场所信息
		List<AreaEntity> superOrgSubArea = null;
		if(sessionInfo.getUser().getId()==1){
			superOrgSubArea=areaMapper.listAllArea(map);
		}else{
			String orgString = orgStr(superAndSubOrg);
			if(orgString!=null && !"".equals(orgString.trim())){
				map.clear();
				map.put("orgStr", orgString);
				superOrgSubArea=areaMapper.listAreaByOrgStr(map);
			}
		}
		if(superOrgSubArea==null){
			superOrgSubArea=new ArrayList<AreaEntity>();
		}
		if(superAndSubOrg==null || superAndSubOrg.size()==0){
			AreaEntity areaEntity = new AreaEntity();
			areaEntity.setOrganizationId(-100);
			areaEntity.setId(-100);
			superOrgSubArea.add(areaEntity);
		}
		sessionInfo.setSuperAndSubArea(superOrgSubArea);
		logger.info(sessionInfo.toString());
	}

	@Override
	public void refreshSessionOrg(SessionInfo sessionInfo) {
		this.refreshSessionOrg(sessionInfo,null);
	}

	private void findChildIds(Long id, List<Long> idList) {
		if (id != null) {
			for (OrganizationEntity r : tempOrganizationList) {
				if (id.longValue() == r.getParentId().longValue()) {
					if (r.getId() != null) {
						idList.add(r.getId().longValue());
						findChildIds(r.getId().longValue(), idList);
					}

				}
			}
		}
	}

	private List<OrganizationEntity> listByIds(List<Long> ids) {
		List<OrganizationEntity> temp = new ArrayList<>();
		for (OrganizationEntity r : tempOrganizationList) {
			if (ids.contains(Long.valueOf(r.getId()))) {
				temp.add(r);
			}
		}
		return temp;
	}

	private void findSubIds(Long id, List<Long> idList) {
		if (id != null) {
			for (OrganizationEntity r : tempOrganizationList) {
				if (id.longValue() == r.getId().longValue()) {
					if (r.getParentId() != null) {
						idList.add(r.getParentId().longValue());
						findChildIds(r.getParentId().longValue(), idList);
					}

				}
			}
		}
	}

	@Override
	public List<OrganizationEntity> listAllOrganization(Map<String, Object> map) {
		return this.organizationMapper.list(map);
	}

	@Override
	public int insertOrganization(OrganizationEntity record) {
		return this.organizationMapper.insert(record);
	}

	@Override
	public void updateOrganization(OrganizationEntity record) {
		this.organizationMapper.update(record);
	}

	@Override
	public void deleteOrganization(int id) {
		this.organizationMapper.delete(id);
		Map<String, Object> map = new HashMap<String, Object>();
	}

	@Override
	public int childCount(Map<String, Object> map) {
		return organizationMapper.childCount(map);
	}

	@Override
	public List<OrganizationEntity> listChildOrg(Map<String, Object> map) {
		return organizationMapper.listChildOrg(map);
	}

	@Override
	public List<OrganizationEntity> getOrgByUserId(Integer userId) throws Exception {
		return organizationMapper.getOrgByUserId(userId);
	}

	@Override
	public Collection<OrganizationEntity> getOrganizationByUserId(Integer userId) throws Exception {
		Collection<OrganizationEntity> organizationEntities = organizationMapper.getOrganizationByUserId(userId);
		if (organizationEntities.size() > 0) {
			logger.trace("get Organization By User Id  size = " + organizationEntities.size());
		} else {
			logger.trace("get Organization By User Id size = 0");
		}
		return organizationEntities;
	}

	@Override
	public OrganizationEntity getOrganizationByUserId2(Integer userId) throws Exception {
		return organizationMapper.getOrganizationByUserId2(userId);
	}

	@Override
	public List<OrganizationEntity> listOrgByRegionCode(Long regionCode) {
		return organizationMapper.listOrgByRegionCode(regionCode);
	}

	@Override
	public List<OrganizationEntity> listMapOrg() {
		return organizationMapper.listMapOrg();
	}

	@Override
	public List<OrganizationEntity> listOrgByOrgCode(Long orgCode) {
		return organizationMapper.listOrgByOrgCode(orgCode);
	}

	@Override
	public void userImportByXls(MultipartFile file, HttpServletRequest request) throws Exception {
		// 首先检查后缀
		String fname = file.getOriginalFilename();
		Workbook workbook = null;
		logger.info(fname);
		if (fname.toLowerCase().endsWith(".xls")) {
			workbook = new HSSFWorkbook(file.getInputStream());
		} else if (fname.toLowerCase().endsWith(".xlsx")) {
			workbook = new XSSFWorkbook(file.getInputStream());
		} else {
			throw new Exception("文件不是excel文件");
		}
		InputStream input = null;
		if (!file.getInputStream().markSupported()) {
			input = new PushbackInputStream(file.getInputStream(), 8);
		}
		// 默認第一個sheet
		Sheet sheet = workbook.getSheetAt(0);
		// check title
		Row rowFrist = sheet.getRow(0);
		if (!CheckTitle(rowFrist)) {
			throw new Exception("文件不是默认格式文件");
		}
		List<UserImportEntity> list = new ArrayList<UserImportEntity>();
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			UserImportEntity entity = getUserImport(sheet.getRow(i));
			if (entity != null && entity.isGood()) {
				list.add(entity);
				logger.info(entity.toString());
			}
		}
		System.err.println(list);
		// 检查权限、警号
		checkUserNo(list);
		// 检查、插入组织
		synOrg(list, request);
//		synPorg(list);
		// 插入 用户
		importAddUser(list, request);

	}

	/**
	 * 插入 用户
	 * 
	 * @param list
	 */
	private void importAddUser(List<UserImportEntity> list, HttpServletRequest request) {
		System.err.println("----------------------------------" + list);
		for (UserImportEntity ui : list) {
			if (!ui.isRepetitionNo() && !ui.isErrorRoleName()) {
				UserEntity user = ui.getUserEntityByThis();
				Map<String, OrganizationEntity> oldMap = getOrgMap();
				OrganizationEntity entity = oldMap.get(ui.getOrg());
				Map<String, RoleEntity> roleMap = getRoleMap();
				System.err.println("----------------------------------" + roleMap);
				RoleEntity roleEntity = roleMap.get(ui.getRoleName());
				try {
					user.setOrganizationId(entity.getId());
					user.setRoleId(roleEntity.getId());
					user.setType(2);
					user.setOp_Pid(1+"");
					user.setOp_User_Id(1);
					userMapper.insertUser(user);
					// 将正确插入的放入
					ui.setUser(user);
				} catch (Exception e) {
					// 一般为登录名重复错误
					logger.error(e.getMessage(), e);
				}
			}

		}

	}

	/**
	 * 检查title是否合法
	 * 
	 * @param rowFrist
	 * @return
	 */
	private boolean CheckTitle(Row rowFrist) {
		String[] titles = { "姓名", "警号", "职务", "性别", "手机", "所属部门", "所属部门上级部门", "角色" };
		for (int i = 0; i < titles.length; i++) {
			if (!titles[i].equals(getStringValue(rowFrist.getCell(i)))) {
				logger.info(titles[i] + "," + getStringValue(rowFrist.getCell(i)));
				return false;
			}
		}
		return true;
	}

	/**
	 * row 转换为UserImportEntity
	 * 
	 * @param row
	 * @return
	 */
	private UserImportEntity getUserImport(Row row) {
		UserImportEntity entity = new UserImportEntity();
		entity.setRowNum(row.getRowNum());
		entity.setName(getStringValue(row.getCell(0)));
		entity.setNo(getStringValue(row.getCell(1)));
		entity.setTitle(getStringValue(row.getCell(2)));
		entity.setSex(getStringValue(row.getCell(3)));
		entity.setMobile(getStringValue(row.getCell(4)));
		entity.setOrg(getStringValue(row.getCell(5)));
		entity.setPorg(getStringValue(row.getCell(6)));
		entity.setRoleName(getStringValue(row.getCell(7)));

		return entity;
	}

	private String getStringValue(Cell cell) {
		if (cell != null) {
			if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
				return String.valueOf(cell.getBooleanCellValue()).trim();
			} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				return String.valueOf(cell.getNumericCellValue()).trim();
			} else {
				return String.valueOf(cell.getStringCellValue()).trim();
			}
		} else {
			return null;
		}
	}

	/**
	 * 检查用戶
	 * 
	 * @param list
	 */
	private boolean checkUserNo(List<UserImportEntity> list) {
		String roles = "上下级办案场所,上级部门及下级部门,东奥,办案中心管理员,办案中心管理民警,办案中心领导,办案区管理民警,办案民警,本办案场所及以下,本部门,本部门及下级,法制支队,用户管理,系统管理员";
		String[] allRole = roles.split(",");
		boolean hasBad = false;
		for (int i = 0; i < list.size(); i++) {
			UserImportEntity thisEntity = list.get(i);
			for (int j = i + 1; j < list.size(); j++) {
				// 检查警号是否重复
				UserImportEntity afterEntity = list.get(j);
				if (thisEntity.getNo().equals(afterEntity.getNo())) {
					thisEntity.setRepetitionNo(true);
					afterEntity.setRepetitionNo(true);
					hasBad = true;
				}
			}
			// 检查权限是否正确
			boolean isGoodRole = false;
			for (String r : allRole) {
				if (r.equals(thisEntity.getRoleName())) {
					isGoodRole = true;
					break;
				}
			}
			if (!isGoodRole) {
				thisEntity.setErrorRoleName(true);
				hasBad = true;
			}
		}
		return hasBad;
	}

	/**
	 * 获取角色map
	 * 
	 * @return
	 */
	private Map<String, RoleEntity> getRoleMap() {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, RoleEntity> map = new HashMap<String, RoleEntity>();
		List<RoleEntity> list = roleMapper.getAllRoleImport(param);
		if (list != null) {
			for (RoleEntity role : list) {
				map.put(role.getName(), role);
			}
		}
		return map;
	}

	/**
	 * 获取orgMap
	 * 
	 * @return
	 */
	private Map<String, OrganizationEntity> getOrgMap() {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, OrganizationEntity> map = new HashMap<String, OrganizationEntity>();
		List<OrganizationEntity> list = organizationMapper.list(param);
		if (list != null) {
			for (OrganizationEntity org : list) {
				map.put(org.getName(), org);
			}
		}
		return map;
	}

	/**
	 * 同步组织
	 * 
	 * @param list
	 */
	private void synOrg(List<UserImportEntity> list,HttpServletRequest request) {
		Set<String> set = new HashSet<String>();
		// String prog="";
		for (UserImportEntity ui : list) {
			set.add(ui.getOrg());
			set.add(ui.getPorg());
		}
		Map<String, OrganizationEntity> oldMap = getOrgMap();
		for (String name : set) {
			OrganizationEntity entity = oldMap.get(name);
			if (entity == null && StringUtils.isNotEmpty(name)) {
//				entity = new OrganizationEntity();
////				entity.setName(name);
////				entity.setType("1");// 办案中心 默认
////				entity.setOrgStatus(1 + "");
////				entity.setCreatedTime(new Date());
////				entity.setUpdatedTime(new Date());
////				organizationMapper.insert(entity);
				entity = new OrganizationEntity();
				entity.setName(name);
				entity.setType("2");// 办案部门 默认
				entity.setOrgStatus(1 + "");
				entity.setCreatedTime(new Date());
				entity.setUpdatedTime(new Date());
				entity.setParentId(3);// 上级部门 默认
				entity.setPid("1_3_");// 所在单位 默认
				entity.setOp_pid(1+"");
				entity.setOp_User_Id(1);
				try {
					organizationMapper.insert(entity);
				} catch (Exception e) {
					logger.error("synOrg exception=", e);
				}
			}
		}
	}

	// gengxin
	private void synPorg(List<UserImportEntity> list) {
		Map<String, Object> param = new HashMap<String, Object>();
		List<OrganizationEntity> entity = null;
		for (UserImportEntity ui : list) {
			OrganizationEntity record = new OrganizationEntity();
			String org = ui.getOrg();
			String porg = ui.getPorg();
			param.put("organization", org);
			entity = organizationMapper.list(param);
			record.setId(entity.get(0).getId());
			if (entity.get(0).getParentId() == null) {
				param.put("organization", porg);
				entity = organizationMapper.list(param);
				if (entity != null) {
					record.setParentId(entity.get(0).getId());
					record.setPid(entity.get(0).getPid()+"_"+record.getId());
				} else {
					//默认没有上级部门的是1
					record.setParentId(0);
					record.setPid(record.getId()+"");
				}
				organizationMapper.updateParentId(record);
			}
		}
	}


}
