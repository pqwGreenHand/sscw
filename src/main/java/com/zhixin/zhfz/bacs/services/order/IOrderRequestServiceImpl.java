package com.zhixin.zhfz.bacs.services.order;

import com.zhixin.zhfz.bacs.dao.order.IOrderRequestMapper;
import com.zhixin.zhfz.bacs.dao.order.IOrderStatusMapper;
import com.zhixin.zhfz.bacs.dao.person.IPersonMapper;
import com.zhixin.zhfz.bacs.entity.*;
import com.zhixin.zhfz.common.entity.SessionInfo;
import com.zhixin.zhfz.common.utils.ControllerTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class IOrderRequestServiceImpl implements IOrderRequestService {
	@Resource
	private IOrderRequestMapper orderRequestMapper;
	@Resource
	private IOrderStatusMapper orderStatusMapper;
	@Resource
	private IPersonMapper personMapper;
	@Override
	public List<OrderRequestEntity> list(Map<String, Object> params) throws Exception {

		return orderRequestMapper.list(params);
	}

	@Override
	public int count(Map<String, Object> params) throws Exception {

		return orderRequestMapper.count(params);
	}

	@Override
	public int insertOrderRequest(OrderRequestEntity entity, HttpServletRequest request) {
		// 添加预约信息
		orderRequestMapper.insertSelective(entity);
		System.out.println("=============@@@@@@@@@@@@@@@======" + entity.getId());
		request.getSession().setAttribute("orderRequestId", entity.getId());
		return entity.getId();
	}

	@Override
	public int checkCertificateNoInOrder(Map<String, Object> map) {

		return orderRequestMapper.checkCertificateNoInOrder(map);
	}
	/***
	 * @author lihf $20151001 入参OrderRequestEntity 返回参数 String 可以预约返回 success
	 *         否则返回不可预约原因描述 规则：一次预约 不同类型的4种 男 女 特殊 未成年 不能放入同一个侯问室 其中男女可进入普通侯问室
	 *         特殊进入特殊 未成年进入未成年 同一次预约同一种类型人群 不能在同一个侯问室 该方法必须满足已经成功的预约必须负责规则
	 *         否则可能出现判断错误
	 **/
	@Override
	public String checkOrderRequest(OrderRequestEntity entity) {
		String reStr = "success";
		Map<String, Object> param = new HashMap();
		AlarmEntity alarm = new AlarmEntity();
		alarm.setAreaId(entity.getAreaId().toString());
		if (entity.getAreaId() == null || entity.getAreaId().equals("")) {
			alarm.setAreaId("-100");
			return "无办案场所信息，预约失败！";
		}
		param.put("areaId", entity.getAreaId());
		// 1 从waiting_record表中获取实际已经被占用的 侯问室 数量 占用类型
		// 普通类型roomType为1的 可以被男 女 占用 7为特殊侯问室 8为未成年侯问室
		List<OrderRequestCheckEntity> occupylist = orderRequestMapper.getOccupyRoomAmount(param);
		// 查询出所有已预约的预约信息
		List<OrderRequestEntity> requestList = orderRequestMapper.getOrderRequestlistByAreaId(param);
		Map<String, Object> occupyMap = new LinkedHashMap();
		for (OrderRequestCheckEntity ocEntity : occupylist) {
			occupyMap.put(ocEntity.getRoomId(), ocEntity);
			System.out.println("----------00000------------" + ocEntity.getRoomId() + "--------" + ocEntity.getAmount()
					+ "----------" + ocEntity.getSex());
		}
		System.out.println("requestList+++++"+requestList);
		// 循环预约申请单 分别取各种类型人群
		for (OrderRequestEntity orEntity : requestList) {
			alarm.setAreaId(orEntity.getAreaId().toString());
			alarm.setPoliceId(orEntity.getPoliceId());
			// 计算男性
			int maleCount =orEntity.getMaleCount()!=null? orEntity.getMaleCount().intValue():0;
			int femalCount = orEntity.getFemaleCount()!=null? orEntity.getFemaleCount().intValue():0;
			int specialCount =orEntity.getSpecialCount()!=null? orEntity.getSpecialCount().intValue():0;
			int juvenilesCount = orEntity.getJuvenilesCount()!=null ?orEntity.getJuvenilesCount().intValue():0;

			System.out.println("-----------111111-----------" + orEntity.getId() + "-----" + orEntity.getMaleCount()
					+ "-----" + orEntity.getFemaleCount() + "----------" + orEntity.getSpecialCount() + "-----------"
					+ orEntity.getJuvenilesCount());
			for (Iterator<String> it = occupyMap.keySet().iterator(); it.hasNext();) {
				String key = it.next();
				OrderRequestCheckEntity ocEntity = (OrderRequestCheckEntity) occupyMap.get(key);
				// 如果侯问室类型
				if (ocEntity.getRoomTypeId().equals("1")) {
					// 如果侯问室的已经有男或者侯问室是空 可以存男人
					if (ocEntity.getSex().equals("m") || ocEntity.getSex().equals("")) {
						if (maleCount > 0) {
							if (ocEntity.getAmount() > 0 && ocEntity.getAmount() < ocEntity.getRoomVolume()) {
								maleCount--;
								ocEntity.setAmount(ocEntity.getAmount() + 1);
							} else if (ocEntity.getAmount() == 0) {
								maleCount--;
								ocEntity.setAmount(1);
								ocEntity.setSex("m");
							}
						}
					}
					// 如果侯问室的已经有女或者侯问室是空 可以存女人
					if (ocEntity.getSex().equals("fm") || ocEntity.getSex().equals("")) {
						if (femalCount > 0) {
							if (ocEntity.getAmount() > 0 && ocEntity.getAmount() < ocEntity.getRoomVolume()) {
								femalCount--;
								ocEntity.setAmount(ocEntity.getAmount() + 1);
							} else if (ocEntity.getAmount() == 0) {
								femalCount--;
								ocEntity.setAmount(1);
								ocEntity.setSex("fm");
							}
						}
					}
				}
				// 如果侯问室类型为特殊人群
				if (ocEntity.getRoomTypeId().equals("7")) {
					// 如果侯问室的已经有男或者侯问室是空 可以存男人
					if (specialCount > 0) {
						if (ocEntity.getAmount() > 0 && ocEntity.getAmount() < ocEntity.getRoomVolume()) {
							specialCount--;
							ocEntity.setAmount(ocEntity.getAmount() + 1);
						} else if (ocEntity.getAmount() == 0) {
							specialCount--;
							ocEntity.setAmount(1);
						}
					}
				}
				// 如果侯问室类型为未成年人群
				if (ocEntity.getRoomTypeId().equals("8")) {
					// 如果侯问室的已经有男或者侯问室是空 可以存男人
					if (juvenilesCount > 0) {
						if (ocEntity.getAmount() > 0 && ocEntity.getAmount() < ocEntity.getRoomVolume()) {
							juvenilesCount--;
							ocEntity.setAmount(ocEntity.getAmount() + 1);
						} else if (ocEntity.getAmount() == 0) {
							juvenilesCount--;
							ocEntity.setAmount(1);
						}
					}
				}
				System.out.println("----------222222------------" + ocEntity.getRoomId() + "--------"
						+ ocEntity.getAmount() + "----------" + ocEntity.getSex());
				occupyMap.put(ocEntity.getRoomId(), ocEntity);
			}

		}
		int maleCountAdd = entity.getMaleCount().intValue();
		int femalCountAdd = entity.getFemaleCount().intValue();
		int specialCountAdd = entity.getSpecialCount().intValue();
		int juvenilesCountAdd = entity.getJuvenilesCount().intValue();
		// 获取到占用和预约以后的所有room以后 开始计算本次预约是否可以成功
		for (Iterator<String> it = occupyMap.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			OrderRequestCheckEntity ocEntity = (OrderRequestCheckEntity) occupyMap.get(key);

			// 如果侯问室类型
			if (ocEntity.getRoomTypeId().equals("1")) {
				// 如果侯问室的已经有男或者侯问室是空 可以存男人
				if (ocEntity.getSex().equals("m") || ocEntity.getSex().equals("")) {
					if (maleCountAdd > 0) {
						if (ocEntity.getAmount() > 0 && ocEntity.getAmount() < ocEntity.getRoomVolume()) {
							maleCountAdd--;
							ocEntity.setAmount(ocEntity.getAmount() + 1);
						} else if (ocEntity.getAmount() == 0) {
							maleCountAdd--;
							ocEntity.setAmount(1);
							ocEntity.setSex("m");
						}
					}
				}
				// 如果侯问室的已经有女或者侯问室是空 可以存女人
				if (ocEntity.getSex().equals("fm") || ocEntity.getSex().equals("")) {
					if (femalCountAdd > 0) {
						if (ocEntity.getAmount() > 0 && ocEntity.getAmount() < ocEntity.getRoomVolume()) {
							femalCountAdd--;
							ocEntity.setAmount(ocEntity.getAmount() + 1);
						} else if (ocEntity.getAmount() == 0) {
							femalCountAdd--;
							ocEntity.setAmount(1);
							ocEntity.setSex("fm");
						}
					}
				}
			}
			// 如果侯问室类型为特殊人群
			if (ocEntity.getRoomTypeId().equals("7")) {
				// 如果侯问室的已经有男或者侯问室是空 可以存男人
				if (specialCountAdd > 0) {
					if (ocEntity.getAmount() > 0 && ocEntity.getAmount() < ocEntity.getRoomVolume()) {
						specialCountAdd--;
						ocEntity.setAmount(ocEntity.getAmount() + 1);
					} else if (ocEntity.getAmount() == 0) {
						specialCountAdd--;
						ocEntity.setAmount(1);
					}
				}
			}
			// 如果侯问室类型为未成年人群
			if (ocEntity.getRoomTypeId().equals("8")) {
				// 如果侯问室的已经有男或者侯问室是空 可以存男人
				if (juvenilesCountAdd > 0) {
					if (ocEntity.getAmount() > 0 && ocEntity.getAmount() < ocEntity.getRoomVolume()) {
						juvenilesCountAdd--;
						ocEntity.setAmount(ocEntity.getAmount() + 1);
					} else if (ocEntity.getAmount() == 0) {
						juvenilesCountAdd--;
						ocEntity.setAmount(1);
					}
				}
			}
			System.out.println("-----------room_id--------" + ocEntity.getRoomId() + "-----room_type======"
					+ ocEntity.getRoomTypeId() + "" + "------------rommAmount========" + ocEntity.getAmount()
					+ "--------sex-----" + ocEntity.getSex());

			alarm.setRoomId(Integer.parseInt(ocEntity.getRoomId()));
			alarm.setAlarmType(10);
			alarm.setStatus(0);
		}
		if (maleCountAdd > 0) {
			alarm.setAlarmName("侯问室不满足男性数量");
//			areaPatrolMapper.addAlarm(alarm);
			return "侯问室不满足男性数量！";
		}
		if (femalCountAdd > 0) {
			alarm.setAlarmName("侯问室不满足女性数量");
			return "侯问室不满足女性数量！";
		}
		//传染病人
		if (specialCountAdd > 0) {
			alarm.setAlarmName("侯问室不满足传染病人数量");
			//return "侯问室不满足未成年人数量！";
			return "侯问室不满足传染病人数量！";
		}
		//未成年人
		if (juvenilesCountAdd > 0) {
			alarm.setAlarmName("侯问室不满足未成年人数量");
			//return "侯问室不满足传染病人数量！";
			return "侯问室不满足未成年人数量！";
		}

		return reStr;
	}
	@Override
	public OrderRequestEntity queryStatus(Integer orderRequestId) {
		return orderRequestMapper.queryStatus(orderRequestId);
	}

	@Override
	public void updateOrderRequest(OrderRequestEntity entity,int oUserId,String opPid) {
		// 修改status
		OrderStatusEntity obj2 = new OrderStatusEntity();
		obj2.setOpPid(opPid);
		obj2.setOpUserId(oUserId);
		orderRequestMapper.updateByPrimaryKeySelective(entity);

		obj2.setUpdatedTime(entity.getUpdatedTime());
		obj2.setStatusTime(new Date());
		obj2.setOrderRequestId(entity.getId());
		obj2.setStatusName("预约修改");
		orderStatusMapper.insert(obj2);
	}
	@Override
	public int countWuMs()  throws Exception{
		return personMapper.countWuMs();
	}

	@Override
	public List<OrderRequestEntity> listByCaseId(int caseId) throws Exception {
		return null;
	}

	@Override
	public void deleteOrderRequestByCaseId(int caseId) throws Exception {
	}

	@Override
	public void changStatus(OrderRequestEntity entity) {
		orderRequestMapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public void deleteById(Integer orderRequestId) {
		orderRequestMapper.deleteById(orderRequestId);
	}
}
