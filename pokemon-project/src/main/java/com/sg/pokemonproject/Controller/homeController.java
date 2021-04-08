package com.sg.pokemonproject.Controller;

import com.sg.pokemonproject.Dao.AbilityDao;
import com.sg.pokemonproject.Dao.PokemonDao;
import com.sg.pokemonproject.Dao.TypeDao;
import com.sg.pokemonproject.Dao.UserDao;
import com.sg.pokemonproject.Entity.Ability;
import com.sg.pokemonproject.Entity.Pokemon;
import com.sg.pokemonproject.Entity.Type;
import com.sg.pokemonproject.models.Abilities;
import com.sg.pokemonproject.models.PokemonInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
public class homeController {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    TypeDao typeDao;

    @Autowired
    AbilityDao abilityDao;

    @Autowired
    PokemonDao pokemonDao;
    String api_url = "https://pokeapi.co/api/v2/pokemon/";


    public void consumePokemon(int id){

        // 39 = jigglypuff
        if(pokemonDao.getAll().isEmpty()){
            while(id < 62){
                String url = api_url + "/"+id;
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
                pokemon.setPrice(((pokemonInformation.getHeight() % 3) + 1) * 20);
                pokemon.setType(type);
                pokemon.setAbilities(abilityList);

                pokemonDao.addPokemon(pokemon);
                id+=3; // getting every third pokemon for now since 2nd and 3rd are just different evolutions of the same Pokemon
            }
        }

    }
  @GetMapping("home")
    public String displayHome(Model model) {
        consumePokemon(1);
        return "home";
    }
}
