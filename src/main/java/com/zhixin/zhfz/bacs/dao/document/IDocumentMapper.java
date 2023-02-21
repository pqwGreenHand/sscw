package com.zhixin.zhfz.bacs.dao.document;

import com.zhixin.zhfz.bacs.entity.DocumenEntity;
import org.apache.xmlbeans.impl.piccolo.xml.DocumentEntity;

import java.util.Map;

public interface IDocumentMapper {

    DocumenEntity selectdocument(Map<String, Object> map);
}
