package com.comeon.serverb.model;

/**
 * Created by s_lor_000 on 10/2/2015.
 */
public class WsData {

    private long number;
    private String text;
    private int refreshView;

    public WsData() {
    }

    public WsData(long number, String text, int refreshView) {
        this.number = number;
        this.text = text;
        this.refreshView = refreshView;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getRefreshView() {
        return refreshView;
    }

    public void setRefreshView(int refreshView) {
        this.refreshView = refreshView;
    }

    @Override
    public String toString() {
        return "WsData{" +
                "number=" + number +
                ", text='" + text + '\'' +
                ", refreshView=" + refreshView +
                '}';
    }
}
