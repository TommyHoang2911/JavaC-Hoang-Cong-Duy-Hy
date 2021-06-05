package com.example.finalproject.controller;

import com.example.finalproject.registration.RegistrationRequest;
import com.example.finalproject.registration.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@Controller
public class RegistrationController {
    private final RegistrationService registrationService;

    @RequestMapping(value = "save" , method = RequestMethod.POST)
    public String registerNewUser(RegistrationRequest request, Model model){
        Boolean error = false;
        if(request.getFirstName().equals("")){
            model.addAttribute("firstNameInvalid", "* First name invalid *");
            error =  true;
        }
        if(request.getLastName().equals("")){
            model.addAttribute("lastNameInvalid", "* Last name invalid *");
            error =  true;
        }
        if(!request.checkEmailPattern(request.getEmail())){
            System.out.println(request.getEmail());
            model.addAttribute("emailInvalid", "* Email invalid *");
            error =  true;
        }
        if(!request.checkPassword(request.getPassword())){
            model.addAttribute("passwordInvalid","* Example password : Examplepass123 *");
            error =  true;
        }
        if(!(request.getPassword().equals("") && request.getRePassword().equals(""))){
            if(!(request.getPassword().equals(request.getRePassword()))){
                model.addAttribute("rePasswordInvalid","* Re-Password invalid *");
                error = true;
            }
        }

        if(error){
            return "forward:/signup";
        }
// if user exist -> notification , else -> signup and send email with token
        if(registrationService.register(request).equals("1")){
            model.addAttribute("emailInvalid", "* Email existed *");
            return "forward:/signup";
        }

        return "redirect:/signup_success";
    }

    @RequestMapping(value = "signup_success")
    public String signupSuccess(){
        return "signup_success";
    }

//    Confirm mail
    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }

    @RequestMapping(value = "confirm_email_success")
    public String confirmSuccess(){
        return "confirm_email_success";
    }
}
