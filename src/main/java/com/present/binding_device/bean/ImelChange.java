package com.present.binding_device.bean;

import java.util.Date;

/**
 * Created by Laiyin on 2017/9/5.
 */
public class ImelChange {

    /**
     * imel id
     */
    private int id;

    /**
     * imel
     */
    private String imel;

    /**
     * imel���һ�ε�½���û�id
     */
    private String userId;

    /**
     * imel���һ�ε�½ʱ��
     */
    private long changeTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImel() {
        return imel;
    }

    public void setImel(String imel) {
        this.imel = imel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public long getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(long changeTime) {
        this.changeTime = changeTime;
    }

}
