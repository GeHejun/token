package com.example.demo;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/index")
public class DemoController {

    @Token("1234567")
    @RequestMapping("/toPage")
    public String toPage(HttpServletRequest request) {
        return "/demo";
    }

    @RemoveToken("1234567")
    @RequestMapping("/commit")
    @ResponseBody
    public String commit(HttpServletRequest request) {
        return "success";
    }

}
