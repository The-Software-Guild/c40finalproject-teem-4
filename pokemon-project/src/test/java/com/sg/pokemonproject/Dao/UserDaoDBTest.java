package com.sg.pokemonproject.Dao;

import com.sg.pokemonproject.Entity.Pokemon;
import com.sg.pokemonproject.Entity.User;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserDaoDBTest {
    @Autowired
    UserDao userDao;

    @Autowired
    PokemonDao pokemonDao;

    @Before
    public void setUp() {

        List<User> users = userDao.getAll();
        for(User user : users) {
            userDao.deleteUser(user.getId());
        }
      //  List<Pokemon> pokemons = pokemonDao.getAll();
      //  for(Pokemon pokemon : pokemons) {
      //      pokemonDao.deletePokemonById(pokemon.getId());
     //   }
    }


    @Test
    void addUser() {
    }

    @Test
    void getUserById() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void getAll() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getPokemonsForUser() {
    }

    @Test
    void login() {
    }
}