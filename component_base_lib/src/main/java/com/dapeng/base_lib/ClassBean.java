package com.dapeng.base_lib;

/**
 * Created by ldp.
 * <p>
 * Date: 2020-12-18
 * <p>
 * Summary:
 * <p>
 * api path:
 */
public class ClassBean {

    private Class<?> clazz;
    private String title;
    private Object iconUrl;

    public ClassBean(Class<?> clazz, String title) {
        this(clazz, title, null);
    }

    public ClassBean(Class<?> clazz, String title, Object iconUrl) {
        this.clazz = clazz;
        this.title = title;
        this.iconUrl = iconUrl;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(Object iconUrl) {
        this.iconUrl = iconUrl;
    }
}
