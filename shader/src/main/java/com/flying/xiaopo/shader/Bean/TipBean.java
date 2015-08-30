package com.flying.xiaopo.shader.Bean;

/**
 * Created by lenovo on 2015/6/13.
 */
public class TipBean {
    private int id;
    private String title;
    private String date;
    private String time;

    public TipBean(int id, String title, String date, String time) {
        this.date = date;
        this.id = id;
        this.time = time;
        this.title = title;
    }

    public TipBean(String title, String date, String time) {
        this.date = date;
        this.time = time;
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
