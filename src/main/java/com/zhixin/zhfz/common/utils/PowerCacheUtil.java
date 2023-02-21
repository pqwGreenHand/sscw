package com.zhixin.zhfz.common.utils;

import com.zhixin.zhfz.bacs.dao.area.IAreaMapper;
import com.zhixin.zhfz.bacs.entity.AreaEntity;
import com.zhixin.zhfz.common.dao.authortity.IAuthorityMapper;
import com.zhixin.zhfz.common.dao.role.IRoleMapper;
import com.zhixin.zhfz.common.dao.user.IUserMapper;
import com.zhixin.zhfz.common.entity.AuthorityEntity;
import com.zhixin.zhfz.common.entity.RoleAuthorityEntity;
import com.zhixin.zhfz.common.entity.RoleEntity;
import com.zhixin.zhfz.common.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Component
public class PowerCacheUtil implements InitializingBean,Runnable {
	
	private static final Logger logger =LoggerFactory.getLogger(PowerCacheUtil.class);
	
	public static PowerCacheUtil powerCacheUtil;

	@PostConstruct
	public void init(){
		powerCacheUtil = this;
	}

	@Autowired
	private IAuthorityMapper authorityMapper;
	
	@Autowired
	private IRoleMapper roleMapper;

	@Autowired
	private IUserMapper userMapper;

	@Autowired
	private IAreaMapper areaMapper;

	private final static ReadWriteLock rwl = new ReentrantReadWriteLock();
	private final static Lock readLock = rwl.readLock();
	private final static Lock writeLock = rwl.writeLock();
	
	// 用户对应的所有权限(key: userId, value：roleID集合)
	private static Map<Long, List<Long>> user_RoleIdsMap = null;

	// 用户对应的所有权限(key: roleId, value：权限ID集合)
	private static Map<Long, List<Long>> role_AuthorityIdsMap = null;

	// 所有系统权限(key: authorityId, value：权限集合)
	private static Map<Long, AuthorityEntity> allAuthorityMap = null;

	//所有办案中心
	private static List<AreaEntity> allAreaList = null;

	//最后更新时间map(key:项目名,value:time)
	private static long lastLoadTime=0L;

	public void loadCacheData() {
		long statTime=System.currentTimeMillis();
		logger.info("###################加载用户权限数据缓存(" + statTime + ")#####################");
		loadAllAuthorityMap();
		loadRoleAuthorityIdsMap();
		loadUserRoleIdsMap();
		loadAllArea();
		lastLoadTime=System.currentTimeMillis();
		logger.info("###################加载用户权限数据缓存 end all cost" + (System.currentTimeMillis()-statTime) + " ms#####################");
		logger.info("user_RoleIdsMap size:"+user_RoleIdsMap.size());
		logger.info("role_AuthorityIdsMap size:"+role_AuthorityIdsMap.size());
		logger.info("allAuthorityMap size:"+allAuthorityMap.size());
	}

	private void loadAllArea() {
		writeLock.lock();
		try {
			allAreaList = areaMapper.listAllArea(new HashMap<String, Object>());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			writeLock.unlock();
		}

	}

	private void loadAllAuthorityMap(){
		long statTime=System.currentTimeMillis();
		logger.info("--------------initAllAuthorityMap start(" + statTime + ")------------------------");
		writeLock.lock();
		try {
			List<AuthorityEntity> authoritys = authorityMapper.listAuthority();
			allAuthorityMap=new HashMap<Long, AuthorityEntity>();
			if(authoritys!=null){
				for(AuthorityEntity authorityEntity:authoritys){
					allAuthorityMap.put(authorityEntity.getId(), authorityEntity);
				}
			}
			//开始设置childIds (children作废)
			if(authoritys!=null){
				for(AuthorityEntity authorityEntity:authoritys){
					if(authorityEntity.getParentId()!=null){
						AuthorityEntity pAuthority=allAuthorityMap.get(authorityEntity.getParentId());
						if(pAuthority!=null){
							pAuthority.addChildId(authorityEntity.getId());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			writeLock.unlock();
		}
		logger.info("--------------initAllAuthorityMap end cost" + (System.currentTimeMillis()-statTime) + " ms------------------------");
	}
	

	
	//init role_AuthorityIdsMap
	private void loadRoleAuthorityIdsMap(){
		long statTime=System.currentTimeMillis();
		logger.info("--------------initRoleAuthorityIdsMap start(" + statTime + ")------------------------");
		writeLock.lock();
		try {
			Collection<RoleAuthorityEntity>  roleAuthoritys=roleMapper.getAllRoleAuthority();
			role_AuthorityIdsMap=new HashMap<Long, List<Long>>();
			if(roleAuthoritys!=null){
				for(RoleAuthorityEntity roleAuthorityEntity:roleAuthoritys){
					//因为2个值都是PK不用检查有效性
					List<Long> aIds=role_AuthorityIdsMap.get(roleAuthorityEntity.getRoleId());
					if(aIds!=null){
						aIds.add(roleAuthorityEntity.getAuthorityId());
					}else{
						aIds=new ArrayList<Long>();
						aIds.add(roleAuthorityEntity.getAuthorityId());
						role_AuthorityIdsMap.put(roleAuthorityEntity.getRoleId(), aIds);
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.fillInStackTrace().toString());
		}finally{
			writeLock.unlock();
		}
		logger.info("--------------initRoleAuthorityIdsMap end cost" + (System.currentTimeMillis()-statTime) + " ms------------------------");
	}
	
	//init user_RoleIdsMap
	private void loadUserRoleIdsMap(){
		long statTime=System.currentTimeMillis();
		logger.info("--------------initUserRoleIdsMap start(" + statTime + ")------------------------");
		writeLock.lock();
		try {
			
			user_RoleIdsMap=new HashMap<Long, List<Long>>();
			Collection<UserEntity> users=userMapper.getAllUsers();
			if(users!=null && users.size()>0){
				for(UserEntity u : users){
					//因为2个值都是PK不用检查有效性
					List<Long> rIds=user_RoleIdsMap.get(u.getId());
					if(rIds!=null){
						rIds.add(Long.valueOf(u.getRoleId().toString()));
					}else{
						rIds=new ArrayList<Long>();
						rIds.add(Long.parseLong(u.getRoleId().toString()));
						user_RoleIdsMap.put(Long.valueOf(u.getId()), rIds);
					}
					
				}
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}finally{
			writeLock.unlock();
		}
		logger.info("--------------initUserRoleIdsMap end cost" + (System.currentTimeMillis()-statTime) + " ms------------------------");
	}
	
	@Override
	public void run() {
		loadCacheData();
	}
	

	
	private static boolean isLoad(){
//		if(lastLoadTime>=System.currentTimeMillis()-24*60*60*1000L){
			return true;
//		}else{
//			return false;
//		}
		
	}
	
	/**
	 * 异步方式刷新缓存
	 */
	public void refreshCacheAsyc(){
		new Thread(this).start();
	}
	

	// 根据用户标识，获取所有的权限
	public List<AuthorityEntity> getUserAuthorityById(Long userId) {
		long statTime=System.currentTimeMillis();
		logger.info("--------------getUserAuthorityById start(" + statTime + ")------------------------");
		if(!isLoad()){
			loadCacheData();
		}
		
		
		readLock.lock();
		List<AuthorityEntity> authList=new ArrayList<AuthorityEntity>();
		try {
			Set<Long> authIdSet=getAuthIdSetByUserId(userId);
			logger.info(""+authIdSet);
			//
			for(Long authId:authIdSet){
				AuthorityEntity authorityEntity=allAuthorityMap.get(authId);
				if(authorityEntity!=null){
					authList.add(authorityEntity.clone());
				}
			}
			logger.info("authIdSet:"+authIdSet.size());
			logger.info("authList:"+authList.size());
		} finally {
			readLock.unlock();
		}
		logger.info("--------------getUserAuthorityById end cost" + (System.currentTimeMillis()-statTime) + " ms------------------------");
		return authList;
	}
	
	/**
	 * 根据用户ID获取所有AuthIds
	 * @param userId
	 * @return
	 */
	private Set<Long> getAuthIdSetByUserId(Long userId){
		Set<Long> authIdSet=new HashSet<Long>();
		List<Long> roleIds=user_RoleIdsMap.get(userId);
		if(roleIds!=null){
			for(Long roleId:roleIds){
				List<Long> authIds=role_AuthorityIdsMap.get(roleId);
				if(authIds!=null){
					authIdSet.addAll(authIds);
				}
			}
		}
		return authIdSet;
	}
	

	// 根据用户标识，获取所有类型为0(即：系统)的权限
	public  List<AuthorityEntity> getUserAllSysAutontity(Long userId) {
		List<AuthorityEntity> allAuthoritys=getUserAuthorityById(userId);
		return filterAuthorityByType(allAuthoritys,0);
		
	}

	// 根据用户标识，获取所有类型为1或者2(即：菜单)的权限
	public  List<AuthorityEntity> getUserMenuAutontity(Long userId) {
		List<AuthorityEntity> allAuthoritys=getUserAuthorityById(userId);
		int[] types={1,2};
		return filterAuthorityByType(allAuthoritys,types);
	}

	// 根据用户标识，获取所有类型为3(即：页面按钮)的权限
	public  List<AuthorityEntity> getUserPageButtonAutontity(Long userId) {
		List<AuthorityEntity> allAuthoritys=getUserAuthorityById(userId);
		return filterAuthorityByType(allAuthoritys,3);

	}

	// 根据用户标识，获取所有类型为4(即：表格按钮)的权限
	public  List<AuthorityEntity> getUserGridButtonAutontity(Long userId) {
		List<AuthorityEntity> allAuthoritys=getUserAuthorityById(userId);
		return filterAuthorityByType(allAuthoritys,4);
	}

	// 根据用户标识，获取所有类型为6(即：导航)的权限
	public  List<AuthorityEntity> getUserNavigationAutontity(Long userId) {
		List<AuthorityEntity> allAuthoritys=getUserAuthorityById(userId);
		return filterAuthorityByType(allAuthoritys,6);
	}
	
	/**
	 * 根据type类型过滤authoritys
	 * @param authoritys
	 * @param type
	 * @return
	 */
	private  List<AuthorityEntity> filterAuthorityByType(List<AuthorityEntity> authoritys,int type){
		int[] types={type};
		return filterAuthorityByType(authoritys,types);
	}
	
	/**
	 * 根据types类型集合过滤authoritys
	 * @param authoritys
	 * @param types
	 * @return
	 */
	private  synchronized List<AuthorityEntity> filterAuthorityByType(List<AuthorityEntity> authoritys,int[] types){
		List<AuthorityEntity> newAuthoritys=new ArrayList<AuthorityEntity>();
		Map<Long,String> autorityTempMap=new HashMap<>();
		if(authoritys!=null && types!=null){
			for(AuthorityEntity auth:authoritys){
				for(int type:types){
					if(auth.getType() == type){
						if(autorityTempMap!=null&&autorityTempMap.get(auth.getId())==null){
							autorityTempMap.put(auth.getId(),auth.getTitle());
							newAuthoritys.add(auth);
						}
						break;
					}
				}
				
			}
		}
		return newAuthoritys;
	}
	
	/**获取某一权限下所有权限
	 * @param startAuthId
	 * @param list
	 * @return
	 */
	private synchronized List<AuthorityEntity> getAuthorityEntitysByUserAndStartId( Long startAuthId,List<AuthorityEntity> list){
		
		AuthorityEntity startAuth=allAuthorityMap.get(startAuthId);
		if(startAuth!=null){
			list.add(startAuth);
			if(startAuth.getChildIds()!=null){
				for(Long cId:startAuth.getChildIds()){
					getAuthorityEntitysByUserAndStartId( cId,list);
				}
			}
		}
		return list;
	}
	
	private synchronized List<AuthorityEntity> filterAuthorityByAuthIds(List<AuthorityEntity> list, Collection<Long> authIdSet){
		List<AuthorityEntity> newList=new ArrayList<AuthorityEntity>();
		if(list!=null && list!=null){
			for(AuthorityEntity auth:list){
				for(Long authId:authIdSet){
					if(authId.longValue()==auth.getId().longValue()){
						newList.add(auth);
					}
				}
			}
		}
		return newList;
	}
	

	// 根据用户标识与系统权限标识，获取该系统下的菜单权限
	public  synchronized List<AuthorityEntity> getUserSysMenuAutontity(Long userId, Long systemAuthId) {
		List<AuthorityEntity> allAuthoritys=getUserAuthorityById(userId);
		Set<Long> authIdSet=getAuthIdSetByUserId(userId);

		int[] types={1,2};
		return filterAuthorityByType(filterAuthorityByAuthIds(getAuthorityEntitysByUserAndStartId(systemAuthId,allAuthoritys),authIdSet),types);
	}
	
	

	// 根据用户标识与系统权限标识，获取该系统下的页面按钮权限
	public  List<AuthorityEntity> getUserSysPageButtonAutontity(Long userId, Long systemAuthId) {
		List<AuthorityEntity> allAuthoritys=getUserAuthorityById(userId);
		Set<Long> authIdSet=getAuthIdSetByUserId(userId);

		return filterAuthorityByType(filterAuthorityByAuthIds(getAuthorityEntitysByUserAndStartId(systemAuthId,allAuthoritys),authIdSet),3);
	}

	// 根据用户标识与系统权限标识，获取该系统下的表格按钮权限
	public  List<AuthorityEntity> getUserSysGridButtonAutontity(Long userId, Long systemAuthId) {
		List<AuthorityEntity> allAuthoritys=getUserAuthorityById(userId);
		Set<Long> authIdSet=getAuthIdSetByUserId(userId);

		return filterAuthorityByType(filterAuthorityByAuthIds(getAuthorityEntitysByUserAndStartId(systemAuthId,allAuthoritys),authIdSet),4);
	

	}
	


	// 根据用户标识，获取所有url权限(按钮url和隐藏url)
	public  List<String> getUserUrlAuthority(Long userId) {
		List<AuthorityEntity> allAuthoritys=getUserAuthorityById(userId);
		List<String> userUrls=new ArrayList<String>();
		for(AuthorityEntity auth:allAuthoritys){
			if(auth.getUrl()!=null){
				for (String url : auth.getUrl().split(";")) {
					if(!url.isEmpty()){
						userUrls.add(url);
					}
					
				}
			}
			if (auth.getType() == 2 && auth.getChildIds()!=null){
				//检查下方隐藏链接
				for(Long cId:auth.getChildIds()){
					AuthorityEntity hideAuth=allAuthorityMap.get(cId);
					if(hideAuth!=null && hideAuth.getUrl()!=null && hideAuth.getType()==5){
						for (String url2 : hideAuth.getUrl().split(";")) {
							if(!url2.isEmpty()){
								userUrls.add(url2);
							}
							
						}
					}
					
				}
			}
			
		}
		return userUrls;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		refreshCacheAsyc();
	}


	// 根据部门标识，获取所有用户Id
	public  List<Long> getUsersByOrg(Object orgId) {
		readLock.lock();
		List<Long> userIds = new ArrayList<>();
		try {
			long _orgId=Long.valueOf(String.valueOf(orgId));
			List<UserEntity> users = powerCacheUtil.userMapper.getUsersByOrgId(_orgId);
			if (users!=null){
				for (UserEntity u : users){
					userIds.add(u.getId().longValue());
				}
			}
			return userIds;
		} catch (Exception e) {
			logger.info(e.fillInStackTrace().toString());
		} finally {
			readLock.unlock();
		}
		return userIds;
	}
	public List<AreaEntity> getAreaListByOrg(Integer orgId) {
		List<AreaEntity> areaEntityList = new ArrayList<>();
		if (allAreaList != null && allAreaList.size() != 0) {
			for (AreaEntity areaEntity : allAreaList) {
				if (areaEntity.getOrganizationId().intValue() == orgId.intValue()) {
					areaEntityList.add(areaEntity);
				}
			}
		}
		return areaEntityList;

	}

}
