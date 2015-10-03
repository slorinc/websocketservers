package com.comeon.servera.controllers;

import com.comeon.servera.beans.RefreshValuesBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;

/**
 * Created by s_lor_000 on 10/2/2015.
 */
@Controller("adminController")
@RequestMapping("internal/admin")
public class AdminController {

    private final static Logger LOGGER = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private RefreshValuesBean refreshValuesBean;


    @RequestMapping
    public String admin(Model model) {
        model.addAttribute("refreshValuesBean",refreshValuesBean);
        return "form";
    }

    @RequestMapping(value = "post", method = RequestMethod.POST)
    public String handleFromPost(@Valid @ModelAttribute RefreshValuesBean refreshValuesBean, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "form";
        }
        LOGGER.info("{} values added through admin form.", refreshValuesBean.toString());
        this.refreshValuesBean.setRefreshData(refreshValuesBean.getRefreshData());
        this.refreshValuesBean.setRefreshView(refreshValuesBean.getRefreshView());
        model.addAttribute("successMessage","Refresh times are updated successfully.");
        return "form";
    }
}
