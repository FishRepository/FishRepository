package com.yy.online.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;

@Controller("/pay")
public class PayController {

    @RequestMapping("")
    public ModelAndView index(){
        return new ModelAndView("yoyo-admin/wxPay.jsp");
    }

}
