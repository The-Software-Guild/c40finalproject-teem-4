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
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Locale;

@Controller
public class CardsController {
    @Autowired
    PokemonDao pokemonDao;

    @Autowired
    TypeDao typeDao;

    @Autowired
    AbilityDao abilityDao;

    @Autowired
    UserDao userDao;

    String Error ="";

    @GetMapping("cards")
    public String displayCards(Model model) {
        if(userDao.getUserConnected() == 0)
        {
            return "redirect:/signin";
        }
        double money = userDao.getUserById(userDao.getUserConnected()).getMoney();

        model.addAttribute("money", money);
        List<Pokemon> pokemon = pokemonDao.getAll();
        List<Type> type = typeDao.getAll();
        List<Ability> abilities = abilityDao.getAll();
        model.addAttribute("Error", Error);
        model.addAttribute("pokemon", pokemon);
        model.addAttribute("type", type);
        model.addAttribute("abilities", abilities);
        return "cards";
    }

    public boolean exist(List<Pokemon> pokemons , Pokemon pokemon)
    {
        for (int i = 0; i<pokemons.size();i++)
        {
            if(pokemons.get(i).getId()==pokemon.getId())
                return true;
        }
        return false;
    }

    @PostMapping("buy")
    public String buy(int pokemonId) {
        Error ="";
        System.out.println(userDao.getUserConnected());
        if(userDao.getUserConnected() == 0)
        {
            return "redirect:/signin";
        }
        else {
            User user = userDao.getUserById(userDao.getUserConnected());

            Pokemon pokemon = pokemonDao.getPokemonById(pokemonId);

            if (user.getMoney() - pokemon.getPrice() < 0) {
                Error = " You don't have enough money";
            } else if (exist(user.getPokemons(), pokemon)) {
                Error += " You already have this Pokemon : " + pokemon.getName().toUpperCase();
            } else {
                Error = "";
                user.setMoney(user.getMoney() - pokemon.getPrice());
                userDao.updateUser(user);

                userDao.addPokemonForUser(user, pokemon);
            }

            return "redirect:/cards";
        }
    }


}
