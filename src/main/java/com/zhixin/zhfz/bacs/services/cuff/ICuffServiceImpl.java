package com.zhixin.zhfz.bacs.services.cuff;


import com.ideal.netcare.v5.common.util.api.dls.DLSProcessApiService;
import com.zhixin.zhfz.bacs.dao.cuff.ICuffMapper;
import com.zhixin.zhfz.bacs.entity.CuffEntity;
import com.zhixin.zhfz.bacs.entity.CuffImportEntity;
import com.zhixin.zhfz.bacs.entity.PoliceEntranceEntity;
import com.zhixin.zhfz.bacs.entity.SerialEntity;
import com.zhixin.zhfz.bacs.services.serial.ISerialService;
import com.zhixin.zhfz.common.services.operLog.IOperLogService;
import com.zhixin.zhfz.common.utils.PowerCacheUtil;
import com.zhixin.zhfz.common.utils.RMIUtil;
import org.apache.log4j.Logger;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ICuffServiceImpl implements ICuffService {

	private static Logger logger = Logger.getLogger(ICuffServiceImpl.class);

	@Resource
	private ICuffMapper cuffMapper;
	@Autowired
	private PowerCacheUtil powerCacheUtil;
	@Autowired
	private ISerialService serialService;




	@Autowired
	private IOperLogService operLogService;

	// 查询
	@Override
	public List<CuffEntity> list(Map<String, Object> params) throws Exception {

		return cuffMapper.list(params);
	}

	// 总数
	@Override
	public int count(Map<String, Object> params) throws Exception {
		return this.cuffMapper.count(params);
	}

	@Override
	public List<CuffEntity> listAll() throws Exception {
		return this.cuffMapper.listAll();
	}

	// 删除
	@Override
	public void deleteCuff(Long id) throws Exception {
		this.cuffMapper.delete(id);

	}

	// 添加
	@Override
	public void insert(CuffEntity entity) {
		this.cuffMapper.insertSelective(entity);

	}

	// 修改
	@Override
	public void update(CuffEntity entity) {

		this.cuffMapper.updateByPrimaryKeySelective(entity);
	}
	@Override
	public List<CuffEntity> queryArea() {
		return cuffMapper.queryArea();
	}
	@Override
	public CuffEntity getCuffById(Integer id) throws Exception {
		return this.cuffMapper.getCuffById(id);
	}
	@Override
	public CuffEntity getCuffByNo(Map<String, Object> params) throws Exception {
		 return  cuffMapper.getCuffByNo(params);
	}
	@Override
	public CuffEntity getCuffByCuffNo(Map<String, Object> params) throws Exception {
		return cuffMapper.getCuffByCuffNo(params);
	}
	@Override
	public void userImportByXls(MultipartFile file,Integer areaId,HttpServletRequest request) throws Exception{
		//首先检查后缀
		String fname=file.getOriginalFilename();
		Workbook workbook =null;
		logger.info(fname);
		if(fname.toLowerCase().endsWith(".xls")){
			workbook =new HSSFWorkbook(file.getInputStream());
		}else if( fname.toLowerCase().endsWith(".xlsx")){
			workbook =new XSSFWorkbook(file.getInputStream());
		}else{
			throw new Exception("文件不是excel文件");
		}
		InputStream input=null;
		if(!file.getInputStream().markSupported()) {
			input = new PushbackInputStream(file.getInputStream(), 8);
		}
		if (POIFSFileSystem.hasPOIFSHeader(input)) {
			workbook =new HSSFWorkbook(input);
		}else if (POIXMLDocument.hasOOXMLHeader(input)) {
			workbook =new XSSFWorkbook(OPCPackage.open(input));
		}else{
			throw new Exception("文件不是excel文件");
		}
		//默認第一個sheet
		Sheet sheet=workbook.getSheetAt(0);
		//check title
		Row rowFrist=sheet.getRow(0);
		System.err.print(rowFrist);
		if(!CheckTitle(rowFrist)){
			throw new Exception("文件不是默认格式文件");
		}
		List<CuffImportEntity> list=new ArrayList<CuffImportEntity>();
		for(int i=1;i<=sheet.getLastRowNum();i++){
			CuffImportEntity entity=getUserImport(sheet.getRow(i));
					entity.setAreaId(areaId);  //获取当前办案中心id

			if(entity!=null && entity.isGood()){
				list.add(entity);
				logger.info(entity.toString());
			}
		}
		//插入 手环
		importCuff(list, request);
		try {
			powerCacheUtil.refreshCacheAsyc();
		} catch (Exception e1) {
			logger.info("============cacheUtil.refreshCacheAsyc()================刷新失败");
			e1.printStackTrace();
		}
	}
	@Override
	public CuffEntity getCuffNoByIcNoAndType(Map<String, Object> params) throws Exception {
		return cuffMapper.getCuffNoByIcNoAndType(params);
	}
	@Override
	public CuffEntity criminalBindCuff(SerialEntity entity) throws Exception {
		CuffEntity cuff = bindPersonCuffById(entity.getCuffId(), entity.getPersonId());
		this.serialService.insert(entity);
		cuff.setPersonId(entity.getPersonId());
		cuff.setLastBindType(BIND_TYPE_CRIMINAL);
		cuff.setLastBindId(entity.getId());
		try {
			logger.info(cuff);
			cuffMapper.bindPersonCuffById(cuff);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("绑定手环错误");
		}
		return cuff;
	}
	@Override
	public void unbindCuffById(Integer id) {
		logger.info("unbindCuffById:" + id);
		try {
			CuffEntity checkEntity = cuffMapper.getCuffById(id);
			if (checkEntity != null) {
				cuffMapper.unbindCuffById(id);
				try {
					dlsUnBindCuff(checkEntity);
				} catch (Exception e2) {
					logger.error(e2.getMessage(), e2);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 临时出区，设置手环状态手环
	 *
	 * @param id
	 * @return
	 */
	@Override
	public void tempOutUnbindCuffById(Integer id) {
		logger.info("tempOutUnbindCuffById:" + id);
		try {
			CuffEntity checkEntity = cuffMapper.getCuffById(id);
			if (checkEntity != null) {
				checkEntity.setStatus(3);
				cuffMapper.updateByPrimaryKey(checkEntity);
				try {
					dlsUnBindCuff(checkEntity);
				} catch (Exception e2) {
					logger.error(e2.getMessage(), e2);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public String dlsUnBindCuff(CuffEntity cuffEntity) {
		backThread thread = new backThread(cuffEntity, false);
		thread.start();
		return "";
	}
	private synchronized String dlsFunction(CuffEntity cuffEntity, boolean bind) {
		StringBuilder bundlingResult = new StringBuilder();
		if (cuffEntity != null) {
			if (cuffEntity.getType() == 0) {
				bundlingResult.append("手环");
			} else if (cuffEntity.getType() == 0) {
				bundlingResult.append("卡片");
			}
			if (bind) {
				bundlingResult.append("绑定");
			} else {
				bundlingResult.append("解绑");
			}
			try {
				/* 通知DLS定位系统 */
				logger.info("===============定位系统处理解绑数据：" + cuffEntity.getCuffNo());
				DLSProcessApiService service = (DLSProcessApiService) RMIUtil.getInstance().lookupService("DLSProcessApiService");
				service.isActiveTag(Integer.parseInt(cuffEntity.getCuffNo()), bind);
				// 临时出区返回 手环带入，
				bundlingResult.append("成功！");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				bundlingResult.append("失败！");
			}
		}
		return bundlingResult.toString();
	}



	/**
	 * row 转换为UserImportEntity
	 * @param row
	 * @return
	 */
	private CuffImportEntity getUserImport(Row row){
		CuffImportEntity entity=new CuffImportEntity();
		entity.setRowNum(row.getRowNum());
		entity.setCuffNo(getStringValue(row.getCell(0)));
		entity.setIcNo(getStringValue(row.getCell(1)));

		entity.setType(getStringValue(row.getCell(2)));

		return entity;
	}
	/**
	 * 检查title是否合法
	 * @param rowFrist
	 * @return
	 */
	private boolean CheckTitle(Row rowFrist){
			String[] titles={"定位标签编号","定位IC卡编号","类型"};
		for(int i=0;i<titles.length;i++){
			if(!titles[i].equals(getStringValue(rowFrist.getCell(i)))){
				logger.info(titles[i]+","+getStringValue(rowFrist.getCell(i)));
				return false;
			}
		}
		return true;
	}

	/**
	 * 插入 手环
	 * @param list
	 */
	private void importCuff(List<CuffImportEntity> list, HttpServletRequest request){
		for(CuffImportEntity ui:list){
			if(!ui.isRepetitionNo() ){
				CuffEntity cuff=ui.getCuffEntityByThis( request);
				try {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("cuffNo", cuff.getCuffNo());
					map.put("IcfNo", cuff.getIcNo());
					CuffEntity	entity1 = null;
					CuffEntity	entity2 = null;
					entity1 = cuffMapper.getCuffByCuffNo(map);
					entity2 = cuffMapper.getCuffByNo(map);
					if(entity1!=null||entity2!=null){
					logger.info("数据重复");
					}else {
						cuffMapper.insert(cuff);

						//将正确插入的放入
						ui.setCuff(cuff);
					}
				} catch (Exception e) {
					//一般为登录名重复错误
					logger.error(e.getMessage(),e);
				}
			}

		}

	}

	private String getStringValue(Cell cell) {
		if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
			return String.valueOf(cell.getBooleanCellValue()).trim();
		} else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			 String.valueOf(cell.getNumericCellValue()).trim();
			double d = cell.getNumericCellValue();
			NumberFormat nf = NumberFormat.getInstance();
			String s = nf.format(d);
			if (s.indexOf(",") >= 0) {
				/**
				 * 描述: <br>
				 * 〈这种方法对于自动加".0"的数字可直接解决,但如果是科学计数法的数字就转换成了带逗号的，例如：12345678912345的科学计数法是1.23457E+13，经过这个格式化后就变成了字符串“12,345,678,912,345”，这也并不是想要的结果，所以要将逗号去掉〉
				 *
				 * @return:java.lang.String
				 * @since: 1.0.0
				 * @Author:ML
				 * @Date: 2019/2/23 12:46
				 */
				s = s.replace(",", "");
			}
			return s;
		} else {
			return String.valueOf(cell.getStringCellValue()).trim();
		}
	}
	/**
	 * 绑定手环给民警(改内部方法)
	 * @param cuffId
	 * @param personId
	 * @return
	 * @throws Exception
	 */
	private CuffEntity bindPersonCuffById(Integer cuffId, Long personId) throws Exception {
		CuffEntity cuff = new CuffEntity();
		cuff.setId(cuffId);
		cuff.setPersonId(personId);
		CuffEntity checkEntity = null;
		// 线检查人与手环是绑定
		try {
			checkEntity = cuffMapper.getBindCuffByPersonId(personId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("检查绑定手环错误");
		}
		System.out.println("----=====222888===---09-----=====----" + checkEntity);
		if (checkEntity != null) {
			System.out.println("----========--------=====----" + checkEntity);
			throw new Exception("该用户已绑定了其他手环");
		}
		// 检查手环是否绑定人
		try {
			checkEntity = cuffMapper.getCuffById(cuffId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("检查绑定手环错误");
		}
		if (checkEntity == null) {
			throw new Exception("该手环不存在");
		}
		return checkEntity;

	}

	@Override
	public void otherPoliceBindCuff(PoliceEntranceEntity detailEntity) throws Exception {
		CuffEntity cuff = bindUserCuffById(detailEntity.getCuffId(), Long.valueOf(detailEntity.getPoliceId()));
		logger.info("PoliceEntranceDetailEntity id:" + detailEntity.getId());
		cuff.setUserId(Long.parseLong(detailEntity.getPoliceId() + ""));
		cuff.setLastBindType(BIND_TYPE_OTHER_POLICE);
		cuff.setLastBindId(detailEntity.getId());
		try {
			logger.info(cuff);
			cuffMapper.bindUserCuffById(cuff);
			try {
				dlsBindCuff(cuff);
			} catch (Exception e2) {
				logger.error(e2.getMessage(), e2);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("绑定卡片错误");
		}
	}

	private String dlsBindCuff(CuffEntity cuffEntity) {

		backThread thread = new backThread(cuffEntity, true);
		thread.start();
		return "";
		// return dlsFunction(cuffEntity,true);
	}

	/**
	 * 绑定手环给民警(改内部方法)
	 *
	 * @param
	 * @return
	 */
	private CuffEntity bindUserCuffById(Integer cuffId, Long userId) throws Exception {
		CuffEntity cuff = new CuffEntity();
		cuff.setId(cuffId);
		cuff.setUserId(userId);
		CuffEntity checkEntity = null;
		// 线检查人与手环是绑定
		try {
			checkEntity = cuffMapper.getBindCuffByUserId(userId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("检查绑定卡片错误");
		}
		if (checkEntity != null) {
			throw new Exception("该用户已绑定了其他卡片");
		}
		// 检查手环是否绑定人
		try {
			checkEntity = cuffMapper.getCuffById(cuffId);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new Exception("检查绑定卡片错误");
		}
		if (checkEntity == null) {
			throw new Exception("该卡片不存在");
		}

		if (checkEntity.getType() == 0) {
			throw new Exception("民警不能绑定手环");

		}
		// if(BIND_TYPE_CRIMINAL==bindType && checkEntity.getType()!=0 ){
		// throw new Exception("嫌疑人智能绑定手环");
		// }

		return checkEntity;

	}

	class backThread extends Thread {
		CuffEntity _entity;
		boolean _flag = false;
		backThread(CuffEntity entity, boolean flag) {
			_entity = entity;
			_flag = flag;
		}
		public void run() {
			try {
				dlsFunction(_entity, _flag);
			}
			catch (Exception e) {
			}
		}
	}
}
