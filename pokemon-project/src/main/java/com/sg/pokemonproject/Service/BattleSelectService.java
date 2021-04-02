package com.sg.pokemonproject.Service;

import com.sg.pokemonproject.Dao.PokemonDao;
import com.sg.pokemonproject.Dao.UserDao;
import com.sg.pokemonproject.Entity.Pokemon;
import com.sg.pokemonproject.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BattleSelectService {
    @Autowired
    PokemonDao pokeDao;

    @Autowired
    UserDao userDao;

    public List<Pokemon> getUserPokemon(int userId) {
        User user = userDao.getUserById(userId);
        return userDao.getPokemonsForUser(user);
    }
    public List<Pokemon> getOtherPokemon(int userId) {
        User user = userDao.getUserById(userId);
        List<Pokemon> userPoke = userDao.getPokemonsForUser(user);
        List<Pokemon> allPoke = pokeDao.getAll();
        List<Pokemon> result = new ArrayList<>();
        for (Pokemon pokemon : allPoke) {
            if (!userPoke.contains(pokemon)) {
                result.add(pokemon);
            }
        }
        return result;
    }
}
