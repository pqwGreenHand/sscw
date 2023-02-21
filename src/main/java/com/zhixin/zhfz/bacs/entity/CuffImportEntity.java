package com.zhixin.zhfz.bacs.entity;

import com.zhixin.zhfz.common.utils.ControllerTool;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class CuffImportEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4614966861269493860L;

	private Integer rowNum;

	private String cuffNo;// 定位标签编号

	private String icNo;// 定位IC卡编号


	private String type;// 标签类型 0手环 1卡片

    private CuffEntity cuff=null;

    /* 标示 检查出的 -------------------*/

    private boolean repetitionNo=false;// 重复标签号

    private Integer areaId;

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public boolean isGood() {
		if (StringUtils.isEmpty(cuffNo) || StringUtils.isEmpty(icNo)|| StringUtils.isEmpty(type)) {
			return false;
		}
		return true;
	}


    public CuffEntity getCuffEntityByThis(HttpServletRequest request){
        CuffEntity u=new CuffEntity();
        u.setCuffNo(this.cuffNo);
        u.setIcNo(this.icNo);
        u.setInterrogateAreaId(this.areaId);
        u.setOp_Pid(ControllerTool.getSessionInfo(request).getCurrentOrg().getPid());
        u.setOp_User_Id(ControllerTool.getSessionInfo(request).getUser().getId());
        if("卡片".equals(this.type)){
            u.setType(1);
        }else{
            u.setType(0);
        }
            u.setStatus(0);
        return u;
    }


    public Integer getRowNum() {
        return rowNum;
    }

    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public String getCuffNo() {
        return cuffNo;
    }

    public void setCuffNo(String cuffNo) {
        this.cuffNo = cuffNo;
    }

    public String getIcNo() {
        return icNo;
    }

    public void setIcNo(String icNo) {
        this.icNo = icNo;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isRepetitionNo() {
        return repetitionNo;
    }

    public void setRepetitionNo(boolean repetitionNo) {
        this.repetitionNo = repetitionNo;
    }

    public CuffEntity getCuff() {
        return cuff;
    }


    public void setCuff(CuffEntity user) {
        this.cuff = cuff;
    }
}