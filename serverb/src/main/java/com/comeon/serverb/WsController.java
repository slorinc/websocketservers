package com.comeon.serverb;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class WsController {

    private static WsData data = new WsData(101001l, "SiRe");

    @MessageMapping("/update")
    public void changeData(WsData inData){
        data = inData;
    }

    @MessageMapping("/refresh")
    @SendTo("/feed/in")
    public WsData greeting() throws Exception {
        data.setNumber(data.getNumber() + 1);
        return new WsData(data.getNumber(), data.getText());
    }

}
