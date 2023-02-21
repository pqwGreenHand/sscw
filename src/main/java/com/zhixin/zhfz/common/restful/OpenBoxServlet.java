package com.zhixin.zhfz.common.restful;

import com.zhixin.zhfz.common.entity.CabinetUtilZhiXin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wolf on 2019/6/19.
 */
public class OpenBoxServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(OpenBoxServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String boxgroup = req.getParameter("boxgroup");
        String lockNo = req.getParameter("lockNo");
        String boxip = req.getParameter("boxip");
        Integer boxport = req.getParameter("boxport")!=null?Integer.parseInt(req.getParameter("boxport")+""):0;
        CabinetUtilZhiXin util = new CabinetUtilZhiXin();
        try {
            util.openBoxOne(boxgroup, lockNo, boxip, boxport);
        } catch (Exception e) {
            logger.info("开柜失败："+e.fillInStackTrace());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
