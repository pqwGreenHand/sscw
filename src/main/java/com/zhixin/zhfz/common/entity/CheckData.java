package com.zhixin.zhfz.common.entity;

public class CheckData implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Long id;
    private String text = null;
    private boolean checked = false;

    public CheckData() {
    }

    public CheckData(Long id, String text) {
        this.id = id;
        this.text = text;
    }

    @Override
    public String toString() {
        return "CheckData [id=" + id + ", text=" + text + ", checked=" + checked + "]";
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the checked
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * @param checked the checked to set
     */
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

}
