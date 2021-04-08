package com.sg.pokemonproject.Controller;

import com.sg.pokemonproject.Dao.UserDao;
import com.sg.pokemonproject.Entity.Pokemon;
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

    public boolean exist(List<User> users , String email)
    {
        for (int i = 0; i<users.size();i++)
        {
            if(users.get(i).getEmail().equals(email))
                return true;
        }
        return false;
    }

    @PostMapping("createAccount")
    public String addUser(String firstName, String lastName, String email, String password,Model model) {
        List<User> users = userDao.getAll();
        if (exist(users,email)){
            model.addAttribute("badCredentials", true);
            return "createAccount";
        }
        else {
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(password);
            user.setMoney(40);
            user.setPokemons(null);

            userDao.addUser(user);
            userDao.setUserConnected(user.getId());

            return "redirect:/home";
        }
    }
}
