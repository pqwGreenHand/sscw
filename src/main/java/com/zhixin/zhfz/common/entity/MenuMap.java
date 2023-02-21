package com.zhixin.zhfz.common.entity;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * 菜单Map
 *
 * @author wgh
 */
public class MenuMap implements Serializable {

    private static final long serialVersionUID = 8856024350859746791L;

    private Long id;
    private String title;
    private Integer type;
    private String url;
    private Long parentId;
    private long sortNumber;
    private String icon;
    private String bigIcon;
    private String smallIcon;
    private LinkedList<MenuMap> children = null;

    public MenuMap() {
    }

    /**
     * 权限赋值
     * @param entity
     */
    public MenuMap(AuthorityEntity entity) {
        initData(entity, true);
    }

    /**
     * 是否有下级目录并赋值
     * @param entity
     * @param initChild
     */
    public MenuMap(AuthorityEntity entity, boolean initChild) {
        initData(entity, initChild);
    }

    /**
     * 为权限实体赋值
     * @param entity
     * @param initChild
     */
    private void initData(AuthorityEntity entity, boolean initChild) {
        if (entity != null) {
            this.id = entity.getId();
            this.title = entity.getTitle();
            this.type = entity.getType();
            this.url = entity.getUrl();
            this.parentId = entity.getParentId();
            this.icon = entity.getIcon();
            this.bigIcon = entity.getBigIcon();
            this.smallIcon = entity.getSmallIcon();
            this.sortNumber = entity.getSortNumber() == null ? 99999999L : entity.getSortNumber();
            if (entity.getChildren() != null && initChild) {
                for (AuthorityEntity c : entity.getChildren()) {
                    if (c != null) {
                        MenuMap cMap = new MenuMap(c);
                        addChild(cMap);
                    }
                }
            }

        }
    }

    /**
     * 增加下级菜单
     * @param menuMap
     */
    public void addChild(MenuMap menuMap) {
        if (children == null) {
            children = new LinkedList<MenuMap>();
        }
        if (children.isEmpty() || menuMap.getSortNumber() == 0L) {
            children.add(menuMap);
        } else {
            boolean insertFlag = false;
            for (int i = 0; i < children.size(); i++) {
                MenuMap c = children.get(i);
                if (c.getSortNumber() == 0L) {
                    children.add(i, menuMap);
                    insertFlag = true;
                    break;
                } else if (c.getSortNumber() > menuMap.getSortNumber()) {
                    if (i == 0) {
                        children.addFirst(menuMap);
                    } else {
                        children.add(i, menuMap);
                    }
                    insertFlag = true;
                    break;
                } else if (c.getSortNumber() == menuMap.getSortNumber() && c.getId() > menuMap.getId()) {
                    children.add(i, menuMap);
                    insertFlag = true;
                    break;
                }
            }
            if (!insertFlag) {
                children.add(menuMap);
            }
        }

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public LinkedList<MenuMap> getChildren() {
        return children;
    }

    public void setChildren(LinkedList<MenuMap> children) {
        this.children = children;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }


    public long getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(long sortNumber) {
        this.sortNumber = sortNumber;
    }


    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBigIcon() {
        return bigIcon;
    }

    public void setBigIcon(String bigIcon) {
        this.bigIcon = bigIcon;
    }

    public String getSmallIcon() {
        return smallIcon;
    }

    public void setSmallIcon(String smallIcon) {
        this.smallIcon = smallIcon;
    }

    @Override
    public String toString() {
        return "MenuMap [id=" + id + ", title=" + title + ", type=" + type + ", url=" + url + ", parentId=" + parentId
                + ", sortNumber=" + sortNumber + ", icon=" + icon + ", bigIcon=" + bigIcon + ", smallIcon=" + smallIcon
                + ", children=" + children + "]";
    }


}
