package com.sg.pokemonproject.Controller;


import com.sg.pokemonproject.Dao.AbilityDao;
import com.sg.pokemonproject.Dao.PokemonDao;
import com.sg.pokemonproject.Dao.TypeDao;
import com.sg.pokemonproject.Dao.UserDao;
import com.sg.pokemonproject.Entity.Ability;
import com.sg.pokemonproject.Entity.Pokemon;
import com.sg.pokemonproject.Entity.Type;
import com.sg.pokemonproject.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserCardsController {
    @Autowired
    UserDao userDao;

    @Autowired
    PokemonDao pokemonDao;

    @Autowired
    TypeDao typeDao;

    @Autowired
    AbilityDao abilityDao;


    @GetMapping("UserCards")
    public String displayCards(Model model) {
        if(userDao.getUserConnected() == 0)
        {
            return "redirect:/signin";
        }
        else {
            User user = userDao.getUserById(userDao.getUserConnected());
            double money = userDao.getUserById(userDao.getUserConnected()).getMoney();

            model.addAttribute("money", money);
            List<Pokemon> pokemons1 = userDao.getPokemonsForUser(user);
            List<Pokemon> pokemon = new ArrayList<>();
            for (Pokemon pokemon1 : pokemons1) {
                pokemon.add(pokemonDao.getPokemonById(pokemon1.getId()));
            }
            List<Type> type = typeDao.getAll();
            List<Ability> abilities = abilityDao.getAll();
            model.addAttribute("pokemon", pokemon);
            model.addAttribute("type", type);
            model.addAttribute("abilities", abilities);
            return "UserCards";
        }
    }

}
