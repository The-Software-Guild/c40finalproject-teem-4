package com.sg.pokemonproject.Controller;

import com.sg.pokemonproject.Dao.AbilityDao;
import com.sg.pokemonproject.Dao.PokemonDao;
import com.sg.pokemonproject.Dao.TypeDao;
import com.sg.pokemonproject.Entity.Ability;
import com.sg.pokemonproject.Entity.Pokemon;
import com.sg.pokemonproject.Entity.Type;
import com.sg.pokemonproject.models.PokemonInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class APIController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    TypeDao typeDao;

    @Autowired
    AbilityDao abilityDao;

    @Autowired
    PokemonDao pokemonDao;

    String api_url = "https://pokeapi.co/api/v2/pokemon";

    // Just using dummy values for fields not coming from the API for now
    @GetMapping("/pokemon/{id}")
    public void consumePokemon(@PathVariable Integer id){

        String url = api_url + "/{id}";

        while(id < 29){
            PokemonInformation pokemonInformation = restTemplate.getForObject(url, PokemonInformation.class, id);

            Type type = new Type();
            type.setName("Rain");
            type = typeDao.addType(type);

            List<Ability> abilities = new ArrayList<>();
            Ability ability = new Ability();
            ability.setAP(10);
            ability.setAttack(5);
            ability.setName("Stab");
            ability = abilityDao.addAbility(ability);
            abilities.add(ability);

            Pokemon pokemon = new Pokemon();
            pokemon.setName(pokemonInformation.getName());
            pokemon.setHealth(20);
            pokemon.setWeight(pokemonInformation.getWeight());
            pokemon.setHeight(pokemonInformation.getHeight());
            pokemon.setImage(pokemonInformation.getSprites().getOther().getOfficialArtwork().getFront_default());
            pokemon.setPrice(20);
            pokemon.setType(type);
            pokemon.setAbilities(abilities);

            pokemonDao.addPokemon(pokemon);
            id+=3;
        }
    }

}
