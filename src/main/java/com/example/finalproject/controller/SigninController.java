package com.example.finalproject.controller;

import com.example.finalproject.appuser.AppUser;
import com.example.finalproject.appuser.AppUserService;
import com.example.finalproject.registration.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@AllArgsConstructor
@Controller
public class SigninController {
    private AppUserService appUserService;

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/")
    public String defaultPath(){
        return "redirect:/login";
    }

    @RequestMapping(value = "signup")
    public String signup(Model model){
        model.addAttribute("user",new RegistrationRequest());
        return "signup";
    }

    @RequestMapping("/homepage")
    public String homePage(@AuthenticationPrincipal AppUser user, Model model){
        List<AppUser> appUserList = appUserService.getAllUsers();
        model.addAttribute("users",appUserList);
        return "homepage";
    }
}
