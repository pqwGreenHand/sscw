package com.zhixin.zhfz.common.filter;

import com.zhixin.zhfz.common.services.common.ICommonService;
import com.zhixin.zhfz.common.utils.ControllerTool;
import com.zhixin.zhfz.common.utils.PowerCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class UserAuthorityInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(UserAuthorityInterceptor.class);

    private static final String loaclhost="127.0.0.1";

    private static List<String> exceptionUrlList = null; //存放例外的url集合

    @Autowired
    private PowerCacheUtil powerCacheUtil = null;

    @Autowired
    private ICommonService commonService;

    static {
        exceptionUrlList = new ArrayList<>();
        exceptionUrlList.add("login.do");
        exceptionUrlList.add("logout.do");
        exceptionUrlList.add("/common/listSystemMenu.do");
        exceptionUrlList.add("/common/getSessionInfo.do");
        exceptionUrlList.add("/common/listTimeoutRecode.do");
        exceptionUrlList.add("/common/updateTimeoutRecode.do");
        exceptionUrlList.add("/common/listMenuByPid.do");
        exceptionUrlList.add("/common/listMenuByPidMvw.do");
        exceptionUrlList.add("/common/listButtonAuthority.do");
        exceptionUrlList.add("/bacs/cuff/refrshCuff.do");
        exceptionUrlList.add("/common/fileServiceConfig/url.do");
        exceptionUrlList.add("/common/inform/listInform.do");
        exceptionUrlList.add("/common/inform/updateInform.do");
        exceptionUrlList.add("/common/schedule/listSchedule.do");
        exceptionUrlList.add("/common/schedule/updateSchedule.do");
        exceptionUrlList.add("/common/fileServiceConfig/download.do");
        exceptionUrlList.add("/zhfz/common/weboffice/read.jsp");
        exceptionUrlList.add("/zhfz/bacs/serial/downloadWordBase64.do");
        exceptionUrlList.add("/zhfz/bacs/serial/downloadWord.do");
        exceptionUrlList.add("/zhfz/lawdocProcess/download.do");
        exceptionUrlList.add("/zhfz/sacw/involveddoc/download.do");
        exceptionUrlList.add("/zhfz/bacs/urinalysis/download.do");
        exceptionUrlList.add("/zhfz/common/checkIP.do");
        exceptionUrlList.add("/zhfz/common/user/changePsw.do");
        exceptionUrlList.add("/common/listBaqOutAlarmData.do");
        exceptionUrlList.add("/common/updateBaqOutAlarm.do");
        exceptionUrlList.add("/common/queryUserByCuffNo.do");
        exceptionUrlList.add("/common/querySerialByUser12.do");

        exceptionUrlList.add("/zhfz/bacs/belong/updateTempYjyj.do");
        exceptionUrlList.add("/zhfz/bacs/belong/queryBelongTempBySfzh.do");
        exceptionUrlList.add("/zhfz/bacs/belong/queryBelongDtailById.do");
        exceptionUrlList.add("/zhfz/bacs/statistics/countOrderRequest.do");
        exceptionUrlList.add("/zhfz/bacs/statistics/countSerial.do");
        exceptionUrlList.add("/zhfz/bacs/statistics/countWaitingRecord.do");
        exceptionUrlList.add("/zhfz/bacs/statistics/countRecord.do");
        exceptionUrlList.add("/zhfz/bacs/statistics/countotherentrance.do");
        exceptionUrlList.add("/zhfz/bacs/serial/timeoutRecodelist.do");
        exceptionUrlList.add("/zhfz/bacs/export/expOutTimeExecl.do");
        exceptionUrlList.add("/zhfz/bacs/statistics/countkyouttime.do");
        exceptionUrlList.add("/zhfz/bacs/statistics/listJyOutTime.do");
        exceptionUrlList.add("/zhfz/bacs/led/saveLedAll.do");
        exceptionUrlList.add("/zhfz/bacs/policeEntrance/addPolice.do");
        exceptionUrlList.add("/zhfz/bacs/export/exportZip.do");
        exceptionUrlList.add("/zhfz/common/operLog/addPolice.do");
        exceptionUrlList.add("/zhfz/common/operLog/batchClByIds.do");
        exceptionUrlList.add("/zhfz/bacs/combobox/listAllOrganizationCode.do");
        exceptionUrlList.add("/zhfz/zhfz/bacs/combobox/listAllOrganizationCode.do");
        exceptionUrlList.add("/zhfz/common/getUser.do");
        exceptionUrlList.add("/zhfz/bacs/order/orderSh.do");
        exceptionUrlList.add("/zhfz/bacs/order/orderYjSh.do");
        exceptionUrlList.add("/zhfz/bacs/order/queryXtStatus.do");
        exceptionUrlList.add("/zhfz/bacs/order/removeOrderRequest.do");

        exceptionUrlList.add("/zhfz/common/inform/listInform.do");
        exceptionUrlList.add("/zhfz/common/schedule/listSchedule.do");
        exceptionUrlList.add("/zhfz/bacs/statistics/countOrderRequest.do");
        exceptionUrlList.add("/zhfz/bacs/statistics/countSerial.do");
        exceptionUrlList.add("/zhfz/bacs/statistics/countWaitingRecord.do");
        exceptionUrlList.add("/zhfz/bacs/statistics/countRecord.do");
        exceptionUrlList.add("/zhfz/bacs/statistics/countotherentrance.do");
        exceptionUrlList.add("/zhfz/bacs/statistics/countkyouttime.do");
        exceptionUrlList.add("/zhfz/bacs/statistics/countotherentrance.do");
        exceptionUrlList.add("/zhfz/bacs/statistics/countRecord.do");
        exceptionUrlList.add("/zhfz/bacs/console/console.jsp");
        exceptionUrlList.add("/zhfz/bacs/console/queryLinshiByDay.do");
        exceptionUrlList.add("/zhfz/bacs/console/queryChuquByDay.do");
        exceptionUrlList.add("/zhfz/bacs/console/countWaitingRecord.do");
        exceptionUrlList.add("/zhfz/bacs/console/countInterrogateRecord.do");
        exceptionUrlList.add("/zhfz/bacs/console/countotherentrance.do");
        exceptionUrlList.add("/zhfz/bacs/console/countOrderRequest.do");
        exceptionUrlList.add("/zhfz/bacs/console/countInterrogateSerial.do");
        exceptionUrlList.add("/zhfz/bacs/console/queryRenByAreaId.do");
        exceptionUrlList.add("/zhfz/bacs/console/queryCountByAreaId.do");
        exceptionUrlList.add("/zhfz/bacs/console/queryPersonType.do");
        exceptionUrlList.add("/zhfz/bacs/console/queryCenters.do");
        exceptionUrlList.add("/zhfz/common/inform/ggInformInsert.do");
        exceptionUrlList.add("/zhfz/bacs/export/exportExcelForOrder.do");
        exceptionUrlList.add("/zhfz/bacs/lawdoc/listPerson.do");
        exceptionUrlList.add("/zhfz/bacs/archives/onlineupload.do");
        exceptionUrlList.add("/zhfz/bacs/combobox/listPerson.do");
        exceptionUrlList.add("/zhfz/bacs/clue/download.do");
        exceptionUrlList.add("/zhfz/bacs/belong/queryCase.do");
        exceptionUrlList.add("/zhfz/bacs/belong/queryPerson.do");
        exceptionUrlList.add("/zhfz/common/combogrid/getPersonBelong.do");
        exceptionUrlList.add("/zhfz/bacs/console/queryCaseType.do");
        exceptionUrlList.add("/zhfz/bacs/console/queryCscwType.do");
        exceptionUrlList.add("/zhfz/bacs/console/countSscw.do");
        exceptionUrlList.add("/zhfz/bacs/console/countSsjs.do");
        exceptionUrlList.add("/zhfz/bacs/console/countSsyj.do");
        exceptionUrlList.add("/zhfz/bacs/console/countSsjj.do");
        exceptionUrlList.add("/zhfz/bacs/console/countSscwTj.do");
        exceptionUrlList.add("/zhfz/bacs/console/countTj.do");
        exceptionUrlList.add("/zhfz/bacs/belong/listBelongTemp.do");
        exceptionUrlList.add("/zhfz/bacs/belong/listBelongTempDetail.do");
        exceptionUrlList.add("/zhfz/bacs/belong/updateBelongTempStatusById.do");
        exceptionUrlList.add("/zhfz/bacs/belong/getImagesTemp.do");
        exceptionUrlList.add("/zhfz/bacs/belong/saveYjBelong.do");
        exceptionUrlList.add("/zhfz/bacs/belong/queryBelongDtailByUUID.do");
        exceptionUrlList.add("/zhfz/bacs/belong/addBelongcodNew.do");
        exceptionUrlList.add("/zhfz/bacs/console/queryBelongCountByAreaId.do");
        exceptionUrlList.add("/zhfz/bacs/person/insertPerson.do");
        exceptionUrlList.add("/zhfz/bacs/person/updatePerson.do");
        exceptionUrlList.add("/zhfz/bacs/person/deletePerson.do");
        exceptionUrlList.add("/zhfz/bacs/iriscollection/imageshow.do");
        exceptionUrlList.add("/zhfz/bacs/belong/outgetpicturenew.do");
        exceptionUrlList.add("/zhfz/common/combobox/listAllOrganizationNameCombobox.do");
        exceptionUrlList.add("/zhfz/common/region/list.do");
        exceptionUrlList.add("/zhfz/common/code/listCodeByType.do");
        exceptionUrlList.add("/zhfz/bacs/combobox/listcrimetypebynature.do");
        exceptionUrlList.add("/zhfz/bacs/combobox/listAllOrganizationNameComboboxWithNo.do");
        exceptionUrlList.add("/zhfz/bacs/order/searchUser.do");
        exceptionUrlList.add("/zhfz/bacs/combobox/certificateTypes.do");
        exceptionUrlList.add("/zhfz/common/region/selectByCode.do");
        exceptionUrlList.add("/zhfz/common/case/listXbPolice.do");
        exceptionUrlList.add("/zhfz/common/case/listPolice.do");
        exceptionUrlList.add("/zhfz/bacs/belong/onlineupload.do");
        exceptionUrlList.add("/zhfz/bacs/belong/listBelongVideo.do");
        exceptionUrlList.add("/zhfz/bacs/belong/removeBelongVideo.do");
        exceptionUrlList.add("/zhfz/bacs/belong/listBelongVideo.do");
        exceptionUrlList.add("/zhfz/common/combobox/listWMSCount");
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = false;
        String url = request.getRequestURI();
        String ip = getRemoteHost(request);
//        if(url.indexOf("/zhfz/common/checkIP.do")<0 && !commonService.checkIp(ip)){
//        if(url.indexOf("/zhfz/common/checkIP.do")<0 && commonService.checkIp(ip)){
//            logger.info("无效的ip地址(" + ip + ")");
//            response.sendError(403);//跳转到web.xml里面配置的error-page
//        }else{
            if (url.contains("common/cshelper/getCsUrl.do")) {
                logger.info("例外放行(" + url + ")");
                return true;
            }
            if (isException(url) || isAuthority(url, request)) {
                logger.info("已通过(" + url + ")");
                return true;
            } else {
                logger.info("已拦截(" + url + ")");
                response.sendError(403);//跳转到web.xml里面配置的error-page
            }
//        }
        return flag;
    }

    /**
     * 是否为例外url
     *
     * @param url
     * @return
     */
    private Boolean isException(String url) {
        for (String exception : exceptionUrlList) {
            if (url.indexOf(exception) > -1)
                return true;
        }
        return false;
    }

    /**
     * 是否有权限
     *
     * @param url
     * @return
     */
    private Boolean isAuthority(String url, HttpServletRequest request) {
        for (String authority : powerCacheUtil.getUserUrlAuthority(ControllerTool.getUserID(request).longValue())) {
            if (url.indexOf(authority) > -1)
                return true;
        }
        return false;
    }

    /**
     * 获取本地电脑ip地址
     * @param request
     * @return
     */
    private String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("x-forwarded-for");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? loaclhost : ip;
    }
}
