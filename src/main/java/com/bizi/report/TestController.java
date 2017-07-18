package com.bizi.report;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by guofangbi on 2017/3/6.
 */
@Controller
public class TestController {
    @RequestMapping("/test")
    public ModelAndView test(){
        return new ModelAndView("index");
    }
    @RequestMapping("/test1")
    public String test1(){
        return "index";
    }
    @RequestMapping("/test2")
    public ModelAndView test2(){
        return new ModelAndView("index");
    }
}
