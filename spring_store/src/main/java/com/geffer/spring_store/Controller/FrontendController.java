package com.geffer.spring_store.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontendController {
    @RequestMapping(value = { "/", "/categories/**", "/products/**", "/clients/**", "/providers/**", "/orders/**",
            "/orderdetails/**" })
    public String forward() {
        return "forward:/index.html";
    }
}