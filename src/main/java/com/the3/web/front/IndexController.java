package com.the3.web.front;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * IndexController.java
 *
 * @author Ethan Wong
 * @time 2014年3月16日上午12:16:49
 */
@Controller
@RequestMapping("/birthday")
public class IndexController {
	
    
    @RequestMapping("")
    public String birthday(Model model) {
        
        return "front/birthday";
    }
}


