package com.sg.pokemonproject.Controller;

import com.sg.pokemonproject.Dao.UserDao;
import com.sg.pokemonproject.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserDao userDao;

    @GetMapping("admin/displayUsers")
    public String displayUser(Model model) {
        List<User> user = userDao.getAll();
        model.addAttribute("user", user);
        return "admin/displayUsers";
    }

    @PostMapping("admin/addUser")
    public String addUser(String firstName, String lastName, String email, String password, double money) {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setMoney(money);
        user.setPokemons(pokemon);

        userDao.addUser(user);

        return "redirect:/admin/displayUsers";
    }

    @GetMapping("admin/deleteUser")
    public String deleteUser(Integer id) {
        userDao.deleteUser(id);
        return "redirect:/admin/displayUsers";
    }

    @GetMapping("admin/editUser")
    public String editUser(Integer id, Model model) {
        User user = userDao.getUserById(id);
        model.addAttribute("user", user);
        return "admin/editUser";
    }

    @PostMapping("admin/editUser")
    public String performEditLocation(User user, BindingResult result) {
        if(result.hasErrors()) {
            return "admin/editUser";
        }
        userDao.updateUser(user);

        return "redirect:/admin/displayUsers";
    }

//    @GetMapping("admin/confirmDeleteUser")
//    public String confirmDeleteUser(Integer id, Model model) {
//        User user = userDao.getUserById(id);
//        model.addAttribute("user", user);
//        return "admin/confirmDeleteUser";
//    }

    @GetMapping("admin/userDetail")
    public String userDetail(Integer id, Model model) {
        User user = userDao.getUserById(id);
        model.addAttribute("user", user);
        return "admin/userDetail";
    }

}
