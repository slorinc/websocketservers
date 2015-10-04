package com.comeon.servera.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by s_lor_000 on 10/4/2015.
 */
@Controller
public class WsController {

    @Value("${serverb.frontend.url}")
    private String frontEndUrl;

    @RequestMapping
    public ModelAndView redirectToServerB(){
        return new ModelAndView("redirect:" + frontEndUrl);
    }

}
