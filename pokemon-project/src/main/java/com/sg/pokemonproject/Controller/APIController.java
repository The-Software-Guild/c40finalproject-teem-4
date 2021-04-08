package com.sg.pokemonproject.Controller;

import com.sg.pokemonproject.Dao.AbilityDao;
import com.sg.pokemonproject.Dao.PokemonDao;
import com.sg.pokemonproject.Dao.TypeDao;
import com.sg.pokemonproject.Entity.Ability;
import com.sg.pokemonproject.Entity.Pokemon;
import com.sg.pokemonproject.Entity.Type;
import com.sg.pokemonproject.models.Abilities;
import com.sg.pokemonproject.models.PokemonInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
//@RequestMapping("/")
public class APIController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    TypeDao typeDao;

    @Autowired
    AbilityDao abilityDao;

    @Autowired
    PokemonDao pokemonDao;

    String api_url = "https://pokeapi.co/api/v2/pokemon/";

    @GetMapping("/{id}")
    public String consumePokemon(@PathVariable Integer id){

        // 39 = jigglypuff
        if(pokemonDao.getAll().isEmpty()){
            String url = api_url + "/{id}";

            while(id < 62){
                PokemonInformation pokemonInformation = restTemplate.getForObject(url, PokemonInformation.class, id);

                Type type = new Type();
                type.setName(pokemonInformation.getTypes().get(0).getType().getName());
                type = typeDao.addType(type);

                List<Ability> abilityList = new ArrayList<>();
                int adjustAttackAndAP = 2;
                for(Abilities abilities : pokemonInformation.getAbilities()){
                    Ability ability = new Ability();
                    ability.setName(abilities.getAbility().getName());
                    ability.setAP(adjustAttackAndAP); //2 and then 4
                    ability.setAttack(adjustAttackAndAP); // 2 and then 4
                    ability = abilityDao.addAbility(ability);
                    abilityList.add(ability);
                    adjustAttackAndAP += 2;
                }

                Pokemon pokemon = new Pokemon();
                pokemon.setName(pokemonInformation.getName());
                pokemon.setHealth(20);
                pokemon.setWeight(pokemonInformation.getWeight());
                pokemon.setHeight(pokemonInformation.getHeight());
                pokemon.setImage(pokemonInformation.getSprites().getOther().getOfficialArtwork().getFront_default());
                pokemon.setPrice(20);
                pokemon.setType(type);
                pokemon.setAbilities(abilityList);

                pokemonDao.addPokemon(pokemon);
                id+=3; // getting every third pokemon for now since 2nd and 3rd are just different evolutions of the same Pokemon
            }
        }
        return "home";
    }

}
