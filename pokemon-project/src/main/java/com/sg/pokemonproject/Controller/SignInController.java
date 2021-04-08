package com.sg.pokemonproject.Controller;

import com.sg.pokemonproject.Dao.UserDao;
import com.sg.pokemonproject.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SignInController {

    @Autowired
    UserDao userDao;

    @GetMapping("signin")
    public String addUser( Model model){
       return  "signin";
    }



    @PostMapping("signin")
    public String SignIn(String email, String password, Model model){

        if(email == null && password == null){
            return "signin";
        }

        if(userDao.login(email, password)){
           User user = userDao.getUserByEmail(email);
            userDao.setUserConnected(user.getId());
            return "home";

        }
        else{
            model.addAttribute("badCredentials", true);
            return "signin";
        }

    }
}
