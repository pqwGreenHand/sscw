package com.zhixin.zhfz.bacs.form;

import org.springframework.web.multipart.MultipartFile;

/**
 * @program: zhfz
 * @description: 笔录word模板添加参数
 * @author: jzw
 * @create: 2019-02-21 15:40
 **/

public class RightsTemplateSaveForm {

    private Long id;

    private Integer sortNo;

    private MultipartFile wordFile;

    public Integer getSortNo() {
        return sortNo;
    }

    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }

    @Override
    public String toString() {
        return "RightsTemplateSaveForm [id = " + id + ", sortNo = "+ this.sortNo + ",wordFile = " + this.wordFile.toString() + "]";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MultipartFile getWordFile() {
        return wordFile;
    }

    public void setWordFile(MultipartFile wordFile) {
        this.wordFile = wordFile;
    }
}
