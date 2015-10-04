package com.comeon.serverb.controller;

import com.comeon.serverb.model.WsData;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WsController {

    private static WsData data = new WsData(7777777777l, "default text", 5);

    @MessageMapping("/update")
    public void changeData(WsData inData) {
        data = inData;
    }

    @MessageMapping("/refresh")
    @SendTo("/feed/in")
    public WsData greeting(){
        return new WsData(data.getNumber(), data.getText(), data.getRefreshView());
    }

}
