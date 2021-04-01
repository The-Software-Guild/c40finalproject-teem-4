package com.sg.pokemonproject.Dao;

import com.sg.pokemonproject.Entity.Ability;
import com.sg.pokemonproject.Entity.Pokemon;
import com.sg.pokemonproject.Entity.Type;
import com.sg.pokemonproject.Entity.User;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserDaoDBTest {
    @Autowired
    UserDao userDao;

    @Autowired
    PokemonDao pokemonDao;

    @Autowired
    TypeDao typeDao;

    @Autowired
    AbilityDao abilityDao;

    @BeforeEach
    public void setUp() {
       List<User> users = userDao.getAll();
       for(User user : users) {
           userDao.deleteUser(user.getId());
       }

       List<Pokemon> pokemons = pokemonDao.getAll();
       for(Pokemon pokemon : pokemons) {
            pokemonDao.deletePokemonById(pokemon.getId());
        }
       List<Type> types = typeDao.getAll();
       for(Type type : types) {
           typeDao.deleteTypeById(type.getId());
       }
       List<Ability> abilities = abilityDao.getAll();
       for(Ability ability : abilities) {
           abilityDao.deleteAbilityById(ability.getId());
       }
    }

    @Test
    void getAll() {
        Ability ability= new Ability();
        ability.setName("Test Ability");
        ability.setAP(10);
        ability.setAttack(10);
        ability = abilityDao.addAbility(ability);
        List<Ability> abilities = abilityDao.getAll();

        Type type= new Type();
        type.setName("Test Type");
        type= typeDao.addType(type);


        Pokemon pokemon= new Pokemon();
        pokemon.setName("Test Pokemon");
        pokemon.setWeight(10);
        pokemon.setHeight(10);
        pokemon.setHealth(10);
        pokemon.setPrice(10);
        pokemon.setType(type);
        pokemon.setAbilities(abilities);
        pokemon = pokemonDao.addPokemon(pokemon);
        List<Pokemon> pokemons = pokemonDao.getAll();


        User user = new User();
        user.setFirstName("Test First Name");
        user.setLastName("Test Last Name");
        user.setEmail("Test@email.com");
        user.setPassword("Test Password");
        user.setMoney(12);
        user.setPokemons(pokemons);
        user = userDao.addUser(user);

        User user1 = new User();
        user1.setFirstName("Test First Name1");
        user1.setLastName("Test Last Name1");
        user1.setEmail("Test1@email.com");
        user1.setPassword("Test Password1");
        user1.setMoney(12);
        user1.setPokemons(pokemons);
        user1 = userDao.addUser(user1);

        List<User> users = userDao.getAll();

        assertEquals(2, users.size());
        assertTrue(users.contains(user));
        assertTrue(users.contains(user1));

    }


    @Test
    void addUser() {
        Ability ability= new Ability();
        ability.setName("Test Ability");
        ability.setAP(10);
        ability.setAttack(10);
        ability = abilityDao.addAbility(ability);
        List<Ability> abilities = abilityDao.getAll();

        Type type= new Type();
        type.setName("Test Type");
        type= typeDao.addType(type);

        Pokemon pokemon= new Pokemon();
        pokemon.setName("Test Pokemon");
        pokemon.setWeight(10);
        pokemon.setHeight(10);
        pokemon.setHealth(10);
        pokemon.setPrice(10);
        pokemon.setType(type);
        pokemon.setAbilities(abilities);
        pokemon = pokemonDao.addPokemon(pokemon);
        List<Pokemon> pokemons = pokemonDao.getAll();

        User user = new User();
        user.setFirstName("Test First Name");
        user.setLastName("Test Last Name");
        user.setEmail("Test@email.com");
        user.setPassword("Test Password");
        user.setMoney(12);
        user.setPokemons(pokemons);
        user = userDao.addUser(user);

        User fromDao = userDao.getUserById(user.getId());
        assertEquals(user, fromDao);
    }

    @Test
    void getUserById() {
        Ability ability= new Ability();
        ability.setName("Test Ability");
        ability.setAP(10);
        ability.setAttack(10);
        ability = abilityDao.addAbility(ability);
        List<Ability> abilities = abilityDao.getAll();

        Type type= new Type();
        type.setName("Test Type");
        type= typeDao.addType(type);

        Pokemon pokemon= new Pokemon();
        pokemon.setName("Test Pokemon");
        pokemon.setWeight(10);
        pokemon.setHeight(10);
        pokemon.setHealth(10);
        pokemon.setPrice(10);
        pokemon.setType(type);
        pokemon.setAbilities(abilities);
        pokemon = pokemonDao.addPokemon(pokemon);
        List<Pokemon> pokemons = pokemonDao.getAll();

        User user = new User();
        user.setFirstName("Test First Name");
        user.setLastName("Test Last Name");
        user.setEmail("Test@email.com");
        user.setPassword("Test Password");
        user.setMoney(12);
        user.setPokemons(pokemons);
        user = userDao.addUser(user);

        User fromDao = userDao.getUserById(user.getId());
        assertEquals(user, fromDao);
    }

    @Test
    void updateUser() {
        Ability ability= new Ability();
        ability.setName("Test Ability");
        ability.setAP(10);
        ability.setAttack(10);
        ability = abilityDao.addAbility(ability);
        List<Ability> abilities = abilityDao.getAll();

        Type type= new Type();
        type.setName("Test Type");
        type= typeDao.addType(type);

        Pokemon pokemon= new Pokemon();
        pokemon.setName("Test Pokemon");
        pokemon.setWeight(10);
        pokemon.setHeight(10);
        pokemon.setHealth(10);
        pokemon.setPrice(10);
        pokemon.setType(type);
        pokemon.setAbilities(abilities);
        pokemon = pokemonDao.addPokemon(pokemon);
        List<Pokemon> pokemons = pokemonDao.getAll();

        User user = new User();
        user.setFirstName("Test First Name");
        user.setLastName("Test Last Name");
        user.setEmail("Test@email.com");
        user.setPassword("Test Password");
        user.setMoney(12);
        user.setPokemons(pokemons);
        user = userDao.addUser(user);

        User fromDao = userDao.getUserById(user.getId());
        assertEquals(user, fromDao);


        user.setFirstName("Test First Name2");
        userDao.updateUser(user);
        assertNotEquals(user, fromDao);

        fromDao = userDao.getUserById(user.getId());

        assertEquals(user, fromDao);
    }


    @Test
    void deleteUser() {
        Ability ability= new Ability();
        ability.setName("Test Ability");
        ability.setAP(10);
        ability.setAttack(10);
        ability = abilityDao.addAbility(ability);
        List<Ability> abilities = abilityDao.getAll();

        Type type= new Type();
        type.setName("Test Type");
        type= typeDao.addType(type);

        Pokemon pokemon= new Pokemon();
        pokemon.setName("Test Pokemon");
        pokemon.setWeight(10);
        pokemon.setHeight(10);
        pokemon.setHealth(10);
        pokemon.setPrice(10);
        pokemon.setType(type);
        pokemon.setAbilities(abilities);
        pokemon = pokemonDao.addPokemon(pokemon);
        List<Pokemon> pokemons = pokemonDao.getAll();

        User user = new User();
        user.setFirstName("Test First Name");
        user.setLastName("Test Last Name");
        user.setEmail("Test@email.com");
        user.setPassword("Test Password");
        user.setMoney(12);
        user.setPokemons(pokemons);
        user = userDao.addUser(user);

        User fromDao = userDao.getUserById(user.getId());
        assertEquals(user, fromDao);

        userDao.deleteUser(user.getId());

        fromDao = userDao.getUserById(user.getId());
        assertNull(fromDao);
    }

    @Test
    void getPokemonsForUser() {
        Ability ability= new Ability();
        ability.setName("Test Ability");
        ability.setAP(10);
        ability.setAttack(10);
        ability = abilityDao.addAbility(ability);
        List<Ability> abilities = abilityDao.getAll();

        Type type= new Type();
        type.setName("Test Type");
        type= typeDao.addType(type);

        Pokemon pokemon= new Pokemon();
        pokemon.setName("Test Pokemon");
        pokemon.setWeight(10);
        pokemon.setHeight(10);
        pokemon.setHealth(10);
        pokemon.setPrice(10);
        pokemon.setType(type);
        pokemon.setAbilities(abilities);
        pokemon = pokemonDao.addPokemon(pokemon);
        List<Pokemon> pokemons = pokemonDao.getAll();

        User user = new User();
        user.setFirstName("Test First Name");
        user.setLastName("Test Last Name");
        user.setEmail("Test@email.com");
        user.setPassword("Test Password");
        user.setMoney(12);
        user.setPokemons(pokemons);
        user = userDao.addUser(user);

        User fromDao = userDao.getUserById(user.getId());
        assertEquals(user, fromDao);

        List<Pokemon> pokemonList = userDao.getPokemonsForUser(user);
        assertEquals(1, pokemonList.size());
        //assertTrue(pokemonList.contains(pokemon));
    }

    @Test
    void login() {
        Ability ability= new Ability();
        ability.setName("Test Ability");
        ability.setAP(10);
        ability.setAttack(10);
        ability = abilityDao.addAbility(ability);
        List<Ability> abilities = abilityDao.getAll();

        Type type= new Type();
        type.setName("Test Type");
        type= typeDao.addType(type);

        Pokemon pokemon= new Pokemon();
        pokemon.setName("Test Pokemon");
        pokemon.setWeight(10);
        pokemon.setHeight(10);
        pokemon.setHealth(10);
        pokemon.setPrice(10);
        pokemon.setType(type);
        pokemon.setAbilities(abilities);
        pokemon = pokemonDao.addPokemon(pokemon);
        List<Pokemon> pokemons = pokemonDao.getAll();

        String email= "Test@email.com";
        String password= "Test Password";
        User user = new User();
        user.setFirstName("Test First Name");
        user.setLastName("Test Last Name");
        user.setEmail(email);
        user.setPassword(password);
        user.setMoney(12);
        user.setPokemons(pokemons);
        user = userDao.addUser(user);

        boolean loginTest = userDao.login(email,password);
        assertEquals(loginTest, true);
    }
}