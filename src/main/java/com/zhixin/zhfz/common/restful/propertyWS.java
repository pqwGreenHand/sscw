package com.zhixin.zhfz.common.restful;

import com.sun.jersey.api.core.InjectParam;
import com.sun.jersey.spi.resource.Singleton;
import com.zhixin.zhfz.bacs.entity.ResultEntity;
import com.zhixin.zhfz.common.common.AESUtil;
import com.zhixin.zhfz.common.utils.PropertyUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("/common/propertyws")
@Component
@Scope("request")
@Singleton
public class propertyWS {

    private static Logger logger = Logger.getLogger(propertyWS.class);

    @POST
    @Path("/getPropertyDetial")
    @Produces(MediaType.APPLICATION_JSON)
    public ResultEntity getPropertyConfig(@FormParam("propertyType") String type) throws Exception {
        ResultEntity resultEntity = new ResultEntity();
        if (type == null || type == "") {

            resultEntity.setStatus(false);
            resultEntity.setMessage("propertyType is null");
            return resultEntity;
        }
        Object propertyV = PropertyUtil.getContextProperty("webServiceAesKey");
        if (propertyV == null) {

            resultEntity.setStatus(false);
            resultEntity.setMessage("webServiceKey is null");
            return resultEntity;
        }
        List<PropertyDetailEntity> entities = new ArrayList<>();
        try {
            String propertyKey = AESUtil.aesDecrypt(type, propertyV.toString());

            if (propertyKey != null && propertyKey != "" && propertyKey.equals("zhfz_conn")) {
                Object url = PropertyUtil.getContextProperty("zhfz.jdbc.connection.url");
                if (url != null && url != "") {
                    String str = url.toString();
                    str = str.substring(str.indexOf("//") + 2, str.indexOf(":3306"));
                    PropertyDetailEntity entity = new PropertyDetailEntity();
                    entity.setParamKey("ip");
                    entity.setParamValue(str);
                    entities.add(entity);
                    str = url.toString();
                    str = str.substring(str.indexOf(":3306") + 6, str.indexOf("?"));
                    entity = new PropertyDetailEntity();
                    entity.setParamKey("dbName");
                    entity.setParamValue(str);
                    entities.add(entity);
                }
                Object user = PropertyUtil.getContextProperty("zhfz.jdbc.connection.username");
                Object pwd = PropertyUtil.getContextProperty("zhfz.jdbc.connection.password");
                if (user != null && pwd != null) {
                    PropertyDetailEntity entity = new PropertyDetailEntity();
                    entity.setParamKey("userName");
                    entity.setParamValue(user.toString());
                    entities.add(entity);
                    entity = new PropertyDetailEntity();
                    entity.setParamKey("password");
                    entity.setParamValue(pwd.toString());
                    entities.add(entity);
                    resultEntity.setStatus(true);
                    resultEntity.setJsonString(AESUtil.aesEncrypt(entities.toString(), propertyV.toString()));
                    return resultEntity;
                }
            } else {
                resultEntity.setStatus(false);
                resultEntity.setMessage("propertyType key is invalid");
                return resultEntity;

            }
            resultEntity.setStatus(false);
            resultEntity.setJsonString(entities.toString());
        } catch (Exception e) {
            logger.info(" getPropertyDetial：" + e.getMessage());
            resultEntity.setStatus(false);
            resultEntity.setMessage(e.getMessage());
        }
        return resultEntity;
    }


    class PropertyDetailEntity implements Serializable {

        private static final long serialVersionUID = 6107046760789030997L;


        public String getParamKey() {
            return paramKey;
        }

        public void setParamKey(String paramKey) {
            this.paramKey = paramKey;
        }

        public String getParamValue() {
            return paramValue;
        }

        public void setParamValue(String paramValue) {
            this.paramValue = paramValue;
        }

        private String paramKey;
        private String paramValue;

        @Override
        public String toString() {
            return "{\"paramKey\":\"" + paramKey + "\", \"paramValue\":\"" + paramValue + "\"}";
        }
    }
}


