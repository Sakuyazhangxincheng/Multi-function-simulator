package com.example.jiemian.bean;

import java.io.Serializable;

public class Kuaijie implements Serializable {
    private int id;
    private String biaoti;
    private String wujiaoxing;
    private String touxiang;
    private String dandu;

    public Kuaijie( String biaoti, String wujiaoxing, String touxiang, String dandu) {
        this.biaoti = biaoti;
        this.wujiaoxing = wujiaoxing;
        this.touxiang = touxiang;
        this.dandu = dandu;
    }

    public String getBiaoti() {
        return biaoti;
    }

    public void setBiaoti(String biaoti) {
        this.biaoti = biaoti;
    }

    public String getWujiaoxing() {
        return wujiaoxing;
    }

    public void setWujiaoxing(String wujiaoxing) {
        this.wujiaoxing = wujiaoxing;
    }

    public String getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang;
    }

    public String getDandu() {
        return dandu;
    }

    public void setDandu(String dandu) {
        this.dandu = dandu;
    }
}
