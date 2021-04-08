package com.sg.pokemonproject.Controller;

import com.sg.pokemonproject.Dao.PokemonDao;
import com.sg.pokemonproject.Dao.UserDao;
import com.sg.pokemonproject.Entity.Ability;
import com.sg.pokemonproject.Entity.Pokemon;
import com.sg.pokemonproject.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.expression.Ids;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserDao userDao;

    @Autowired
    PokemonDao pokemonDao;

    @GetMapping("admin/displayUsers")
    public String displayUser(Model model) {
        List<User> user = userDao.getAll();
        List<Pokemon> pokemon = pokemonDao.getAll();
        model.addAttribute("user", user);
        model.addAttribute("pokemon", pokemon);
        return "admin/displayUsers";
    }

    @PostMapping("admin/addUser")
    public String addUser(User user, Model model, HttpServletRequest request) {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String money = request.getParameter("money");
        String[] pokemonIds = request.getParameterValues("pokemonId");

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setMoney(Double.parseDouble(money));

        List<Pokemon> pokemon = new ArrayList<>();
        if (pokemonIds != null) {
            for (String pokemonId : pokemonIds) {
                pokemon.add(pokemonDao.getPokemonById(Integer.parseInt(pokemonId)));
            }
        }

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
        List<Pokemon> pokemon = pokemonDao.getAll();
        model.addAttribute("user", user);
        model.addAttribute("pokemon", pokemon);
        return "admin/editUser";
    }

    @PostMapping("admin/editUser")
    public String performEditLocation(User user, BindingResult result, HttpServletRequest request, Model model) {
        String id = request.getParameter("id");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String money = request.getParameter("money");
        String[] userpokemonIds = request.getParameterValues("pokemonId");

        FieldError error;

        if(userpokemonIds.length == 0){
            error = new FieldError("user", "pokemonId", "Must contain at least 1 pokemon");
            result.addError(error);
        }

        if(result.hasErrors()) {
            model.addAttribute("pokemon", pokemonDao.getAll());
            model.addAttribute("user", user);
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

    @GetMapping("admin/userDetails")
    public String userDetail(Integer id, Model model) {
        User user = userDao.getUserById(id);
        model.addAttribute("user", user);
        return "admin/userDetails";
    }

}
