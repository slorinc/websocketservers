package com.comeon.servera.controllers;

import com.comeon.servera.model.RefreshValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by s_lor_000 on 10/2/2015.
 */
@Controller("adminController")
@RequestMapping("internal/admin")
public class AdminController {

    @Autowired
    private RefreshValues refreshValues;

    @RequestMapping
    public String admin(@ModelAttribute RefreshValues refreshValues) {
        return "form";
    }

    @RequestMapping(value = "post", method = RequestMethod.POST)
    public String handleFromPost(@ModelAttribute RefreshValues refreshValues) {
        this.refreshValues = refreshValues;
        return "form";
    }
}
