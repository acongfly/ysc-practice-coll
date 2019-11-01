package com.acongfly.studyjava.javaStudy.executorsStudy;

/**
 * Created by hc on 2018/7/9.
 */
public class Study {

    private String id;

    private String notityName;

    private String notityTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNotityName() {
        return notityName;
    }

    public void setNotityName(String notityName) {
        this.notityName = notityName;
    }

    public String getNotityTime() {
        return notityTime;
    }

    public void setNotityTime(String notityTime) {
        this.notityTime = notityTime;
    }

    @Override
    public String toString() {
        return "Study{" + "id='" + id + '\'' + ", notityName='" + notityName + '\'' + ", notityTime='" + notityTime
            + '\'' + '}';
    }
}
