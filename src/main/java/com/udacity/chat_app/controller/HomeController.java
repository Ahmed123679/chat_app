package com.udacity.chat_app.controller;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.udacity.chat_app.model.RedirectResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;



@Controller
public class HomeController {
    
    
    


    @RequestMapping(path="/", method=RequestMethod.GET)
    public String homeLogin() {
        return "login";
    }
    @RequestMapping(path="/chat", method=RequestMethod.GET)
    public String chatPage(Model model, HttpServletRequest request) 
    {
        model.addAttribute("username", (String)request.getSession().getAttribute("username"));
        return "chat";
    }

    @RequestMapping(path="login", method=RequestMethod.POST)
    @ResponseBody
    public RedirectResponse validateUser(@RequestBody Map<String,String> username,HttpServletRequest request) 
    {
        RedirectResponse redirectResponse = new RedirectResponse();
        System.out.println("/login is called");
        String name = (String)username.getOrDefault("username", null);
        System.out.println("/login username: " + name);
        
        if(name != null && !name.isEmpty())
        {
            request.getSession().setAttribute("username", name);
            
            redirectResponse.setRedirect("/chat");
            redirectResponse.setStatus("success");

        }else {
            redirectResponse.setStatus("failed");

        }
        return redirectResponse;
        
    }
    
    


}
