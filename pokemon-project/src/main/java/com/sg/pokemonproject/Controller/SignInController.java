package com.sg.pokemonproject.Controller;

import com.sg.pokemonproject.Dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SignInController {

    @Autowired
    UserDao userDao;

    @GetMapping("signin")
    public String addUser(HttpServletRequest request){
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if(userDao.login(email, password)){
            return "cards";
        }
        else{
            return "signin";
        }

    }
}
