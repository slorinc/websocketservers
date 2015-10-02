package com.comeon.serverb;

/**
 * Created by s_lor_000 on 10/2/2015.
 */
public class WsData {

    private long number;
    private String text;

    public WsData() {
    }

    public WsData(Long number, String text) {
        this.number = number;
        this.text = text;
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
}
