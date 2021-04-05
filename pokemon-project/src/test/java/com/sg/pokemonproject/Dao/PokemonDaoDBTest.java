package com.sg.pokemonproject.Dao;

import com.sg.pokemonproject.Entity.Ability;
import com.sg.pokemonproject.Entity.Pokemon;
import com.sg.pokemonproject.Entity.Type;
import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.event.AncestorEvent;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PokemonDaoDBTest {

    @Autowired
    PokemonDao pokemonDao;

    @Autowired
    AbilityDao abilityDao;

    @Autowired
    TypeDao typeDao;

    public PokemonDaoDBTest(){}

    @Before
    public void setUp() {
        List<Pokemon> pokemons = pokemonDao.getAll();
        for(Pokemon pokemon : pokemons){
            pokemonDao.deletePokemonById(pokemon.getId());
        }

        List<Ability> abilities = abilityDao.getAll();
        for(Ability ability : abilities){
            abilityDao.deleteAbilityById(ability.getId());
        }

        List<Type> types = typeDao.getAll();
        for(Type type : types){
            typeDao.deleteTypeById(type.getId());
        }
    }

    @Test
    public void testAddAndGetPokemon(){
        List<Ability> abilities = new ArrayList<>();
        Ability ability = new Ability();
        ability.setName("Test Ability");
        ability.setAttack(5);
        ability.setAP(5);
        abilities.add(ability);
        abilityDao.addAbility(ability);

        Type type = new Type();
        type.setName("Test Type");
        typeDao.addType(type);

        Pokemon pokemon = new Pokemon();
        pokemon.setHealth(1);
        pokemon.setHeight(1.1);
        pokemon.setWeight(12.34);
        pokemon.setName("Test Name");
        pokemon.setImage("Test URL");
        pokemon.setPrice(1.00);
        pokemon.setAbilities(abilities);
        pokemon.setType(type);

        pokemon = pokemonDao.addPokemon(pokemon);

        Pokemon fromDao = pokemonDao.getPokemonById(pokemon.getId());
        TestCase.assertEquals(pokemon, fromDao);

    }

    @Test
    public void testGetAllPokemon(){
        List<Ability> abilities = new ArrayList<>();
        Ability ability = new Ability();
        ability.setName("Test Ability");
        ability.setAttack(5);
        ability.setAP(5);
        abilities.add(ability);
        abilityDao.addAbility(ability);

        Type type = new Type();
        type.setName("Test Type");
        typeDao.addType(type);

        Pokemon pokemon = new Pokemon();
        pokemon.setHealth(1);
        pokemon.setHeight(1.1);
        pokemon.setWeight(12.34);
        pokemon.setName("Test Name");
        pokemon.setImage("Test URL");
        pokemon.setPrice(1.00);
        pokemon.setAbilities(abilities);
        pokemon.setType(type);

        pokemon = pokemonDao.addPokemon(pokemon);

        Pokemon pokemon2 = new Pokemon();
        pokemon2.setHealth(1);
        pokemon2.setHeight(1.1);
        pokemon2.setWeight(12.34);
        pokemon2.setName("Test Name 2");
        pokemon2.setImage("Test URL");
        pokemon2.setPrice(1.00);
        pokemon2.setAbilities(abilities);
        pokemon2.setType(type);

        pokemon2 = pokemonDao.addPokemon(pokemon2);

        List<Pokemon> pokemons = pokemonDao.getAll();

        TestCase.assertEquals(2, pokemons.size());
        TestCase.assertTrue(pokemons.contains(pokemon));
        TestCase.assertTrue(pokemons.contains(pokemon2));

    }

    @Test
    public void testUpdatePokemon(){
        List<Ability> abilities = new ArrayList<>();
        Ability ability = new Ability();
        ability.setName("Test Ability");
        ability.setAttack(5);
        ability.setAP(5);
        abilities.add(ability);
        abilityDao.addAbility(ability);

        Type type = new Type();
        type.setName("Test Type");
        typeDao.addType(type);

        Pokemon pokemon = new Pokemon();
        pokemon.setHealth(1);
        pokemon.setHeight(1.1);
        pokemon.setWeight(12.34);
        pokemon.setName("Test Name");
        pokemon.setImage("Test URL");
        pokemon.setPrice(1.00);
        pokemon.setAbilities(abilities);
        pokemon.setType(type);

        pokemon = pokemonDao.addPokemon(pokemon);

        Pokemon fromDao = pokemonDao.getPokemonById(pokemon.getId());
        TestCase.assertEquals(pokemon,fromDao);

        pokemon.setName("Updated Pokemon");
        pokemonDao.updatePokemon(pokemon);
        TestCase.assertNotSame(pokemon, fromDao);

        fromDao = pokemonDao.getPokemonById(pokemon.getId());
        TestCase.assertEquals(pokemon, fromDao);
    }

    @Test
    public void testDeletePokemon(){
        List<Ability> abilities = new ArrayList<>();
        Ability ability = new Ability();
        ability.setName("Test Ability");
        ability.setAttack(5);
        ability.setAP(5);
        abilities.add(ability);
        abilityDao.addAbility(ability);

        Type type = new Type();
        type.setName("Test Type");
        typeDao.addType(type);

        Pokemon pokemon = new Pokemon();
        pokemon.setHealth(1);
        pokemon.setHeight(1.1);
        pokemon.setWeight(12.34);
        pokemon.setName("Test Name");
        pokemon.setImage("Test URL");
        pokemon.setPrice(1.00);
        pokemon.setAbilities(abilities);
        pokemon.setType(type);

        pokemon = pokemonDao.addPokemon(pokemon);

        Pokemon fromDao = pokemonDao.getPokemonById(pokemon.getId());
        pokemonDao.deletePokemonById(pokemon.getId());

        fromDao = pokemonDao.getPokemonById(pokemon.getId());

        TestCase.assertNull(fromDao);
    }


}