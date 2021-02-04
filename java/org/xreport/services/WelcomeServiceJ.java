package org.xreport.services;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.xreport.entities.Welcome;


@Controller
@RequestMapping("/hello")
public class WelcomeServiceJ {


	@RequestMapping(method = RequestMethod.GET, value="{name}")
    public @ResponseBody Welcome hello(@PathVariable String name) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Welcome welcome = new Welcome();
        welcome.setName(name);
        welcome.setMessage("Welcome " + name + " (" + sdf.format(new Date()) + ")");
        return welcome;
    }
    
}
