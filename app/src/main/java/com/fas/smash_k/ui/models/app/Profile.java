package com.fas.smash_k.ui.models.app;

import java.io.Serializable;

public class Profile implements Serializable {
    private long ID;
    private String StatusUrl;
    private String imgUrl1;
    private String imgUrl2;
    private String imgUrl3;
    private String imgUrl4;
    private String lastSeen;
    private String name;
    private String realStatus;

    public Profile(long id) {
        this.ID = id;
    }

    public Profile(long id, String imgUrl1, String imgUrl2, String imgUrl3, String imgUrl4, String name, String lastSeen, String statusUrl, String realStatus) {
        this.ID = id;
        this.imgUrl1 = imgUrl1;
        this.imgUrl2 = imgUrl2;
        this.imgUrl3 = imgUrl3;
        this.imgUrl4 = imgUrl4;
        this.name = name;
        this.lastSeen = lastSeen;
        this.StatusUrl = statusUrl;
        this.realStatus = realStatus;
    }

    public String getImgUrl1() {
        return this.imgUrl1;
    }

    public void setImgUrl1(String imgUrl1) {
        this.imgUrl1 = imgUrl1;
    }

    public String getImgUrl2() {
        return this.imgUrl2;
    }

    public void setImgUrl2(String imgUrl2) {
        this.imgUrl2 = imgUrl2;
    }

    public String getImgUrl3() {
        return this.imgUrl3;
    }

    public void setImgUrl3(String imgUrl3) {
        this.imgUrl3 = imgUrl3;
    }

    public String getImgUrl4() {
        return this.imgUrl4;
    }

    public void setImgUrl4(String imgUrl4) {
        this.imgUrl4 = imgUrl4;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastSeen() {
        return this.lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getStatusUrl() {
        return this.StatusUrl;
    }

    public void setStatusUrl(String statusUrl) {
        this.StatusUrl = statusUrl;
    }

    public String getRealStatus() {
        return this.realStatus;
    }

    public void setRealStatus(String realStatus) {
        this.realStatus = realStatus;
    }

    public long getID() {
        return this.ID;
    }
}
