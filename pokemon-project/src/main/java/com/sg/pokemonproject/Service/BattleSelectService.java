package com.sg.pokemonproject.Service;

import com.sg.pokemonproject.Dao.BattleDao;
import com.sg.pokemonproject.Dao.PokemonDao;
import com.sg.pokemonproject.Dao.UserDao;
import com.sg.pokemonproject.Entity.Battle;
import com.sg.pokemonproject.Entity.Pokemon;
import com.sg.pokemonproject.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BattleSelectService {
    /*@Autowired
    BattleDao battleDao;*/

    @Autowired
    PokemonDao pokeDao;

    @Autowired
    UserDao userDao;

    /*public void addBattle(Battle battle) {
        battleDao.addBattle(battle);
    }*/

    public int getUserId() {
        return userDao.getUserConnected();
    }

    public List<Pokemon> getUserPokemon() {
        User user = userDao.getUserById(userDao.getUserConnected());
        return userDao.getPokemonsForUser(user);
    }
    public List<Pokemon> getOtherPokemon() {
        User user = userDao.getUserById(userDao.getUserConnected());
        List<Pokemon> userPoke = userDao.getPokemonsForUser(user);
        List<String> userNames = new ArrayList<>();
        for (Pokemon pokemon : userPoke) {
            userNames.add(pokemon.getName());
        }
        List<Pokemon> allPoke = pokeDao.getAll();
        List<Pokemon> result = new ArrayList<>();
        for (Pokemon pokemon : allPoke) {
            if (!userNames.contains(pokemon.getName())) {
                result.add(pokemon);
            }
        }
        return result;
    }
}
