package com.comeon.servera.controllers;

import com.comeon.servera.beans.RefreshValuesBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by s_lor_000 on 10/2/2015.
 */
@Controller("adminController")
@RequestMapping("internal/admin")
public class AdminController {

    @Autowired
    private RefreshValuesBean refreshValuesBean;

    @RequestMapping
    public String admin(@ModelAttribute RefreshValuesBean refreshValuesBean) {
        return "form";
    }

    @RequestMapping(value = "post", method = RequestMethod.POST)
    public String handleFromPost(@ModelAttribute RefreshValuesBean refreshValuesBean) {
        this.refreshValuesBean.setRefreshData(refreshValuesBean.getRefreshData());
        this.refreshValuesBean.setRefreshView(refreshValuesBean.getRefreshView());
        return "form";
    }
}
