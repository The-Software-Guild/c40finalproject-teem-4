package com.sg.pokemonproject.Controller;

import com.sg.pokemonproject.Dao.UserDao;
import com.sg.pokemonproject.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CreateAccountController {

    @Autowired
    UserDao userDao;

    @GetMapping("createAccount")
    public String displayUser(Model model) {
        List<User> user = userDao.getAll();
        model.addAttribute("user", user);
        return "createAccount";
    }

    @PostMapping("createAccount")
    public String addUser(String firstName, String lastName, String email, String password) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setMoney(10);
        user.setPokemons(null);

        userDao.addUser(user);

        return "redirect:/cards";
    }
}
