package com.sg.pokemonproject.Controller;

import com.sg.pokemonproject.Dao.AbilityDao;
import com.sg.pokemonproject.Dao.PokemonDao;
import com.sg.pokemonproject.Dao.TypeDao;
import com.sg.pokemonproject.Entity.Ability;
import com.sg.pokemonproject.Entity.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PokemonController {
    @Autowired
    TypeDao typeDao;

    @Autowired
    AbilityDao abilityDao;

    @Autowired
    PokemonDao pokemonDao;

    @GetMapping("admin/displayPokemon")
    public String displayPokemon(Model model) {
        List<Pokemon> pokemon = pokemonDao.getAll();
        List<Type> type = typeDao.getAll();
        List<Ability> abilities = abilityDao.getAll();
        model.addAttribute("pokemon", pokemon);
        model.addAttribute("type", type);
        model.addAttribute("abilities", abilities);
        return "admin/displayPokemon";
    }

    @PostMapping("admin/addPokemon")
    public String addPokemon(Pokemon pokemon, Model model, HttpServletRequest request) {
        String name = request.getParameter("name");
        String health = request.getParameter("health");
        String weight = request.getParameter("weight");
        String height = request.getParameter("height");
        String image = request.getParameter("image");
        String price = request.getParameter("price");
        String typeId = request.getParameter("typeId");
        String[] abilityIds = request.getParameterValues("abilityId");

        pokemon.setName(name);
        pokemon.setHealth(Integer.parseInt(health));
        pokemon.setWeight(Double.parseDouble(weight));
        pokemon.setHeight(Double.parseDouble(height));
        pokemon.setImage(image);
        pokemon.setPrice(Double.parseDouble(price));
        pokemon.setType(typeDao.getTypeById(Integer.parseInt(typeId)));

        List<Ability> abilities = new ArrayList<>();
        if (abilityIds != null) {
            for (String abilityId : abilityIds) {
                abilities.add(abilityDao.getAbilityById(Integer.parseInt(abilityId)));
            }
        }

        pokemon.setAbilities(abilities);

        pokemonDao.addPokemon(pokemon);

        return "redirect:/admin/displayPokemon";
    }

    // To delete a super human by id pulled from database/html, redirects to super humans
    @GetMapping("admin/deletePokemon")
    public String deletePokemon(Integer id) {
        pokemonDao.deletePokemonById(id);
        return "redirect:/admin/displayPokemon";
    }

    // Use a get mapping to direct to this page, then populate the fields using the supers object
    @GetMapping("admin/editPokemon")
    public String editPokemon(Integer id, Model model) {
        Pokemon pokemon = pokemonDao.getPokemonById(id);
        List<Type> type = typeDao.getAll();
        List<Ability> abilities = abilityDao.getAll();
        model.addAttribute("pokemon", pokemon);
        model.addAttribute("type", type);
        model.addAttribute("abilities", abilities);
        return "admin/editPokemon";
    }

    // Use a post mapping to update the database with new values, check for errors, otherwise update and redirect to previous page
    @PostMapping("admin/editPokemon")
    public String performEditPokemon(Pokemon pokemon, BindingResult result, HttpServletRequest request, Model model) {
        String name = request.getParameter("name");
        String health = request.getParameter("health");
        String weight = request.getParameter("weight");
        String height = request.getParameter("height");
        String image = request.getParameter("image");
        String price = request.getParameter("price");
        String typeId = request.getParameter("typeId");
        String[] abilityIds = request.getParameterValues("abilityId");

        pokemon.setName(name);
        pokemon.setHealth(Integer.parseInt(health));
        pokemon.setWeight(Double.parseDouble(weight));
        pokemon.setHeight(Double.parseDouble(height));
        pokemon.setImage(image);
        pokemon.setPrice(Double.parseDouble(price));
        pokemon.setType(typeDao.getTypeById(Integer.parseInt(typeId)));

        List<Ability> abilities = new ArrayList<>();
        if (abilityIds != null) {
            for (String abilityId : abilityIds) {
                abilities.add(abilityDao.getAbilityById(Integer.parseInt(abilityId)));
            }
        }

        pokemon.setAbilities(abilities);

        pokemonDao.updatePokemon(pokemon);

        return "redirect:/admin/displayPokemon";
    }

    // Use a get mapping to direct to this page, use the value of id to get the details of the object, use an attribute to attach the fields
    // into the html page
    @GetMapping("admin/pokemonDetail")
    public String pokemonDetail(Integer id, Model model) {
        Pokemon pokemon = pokemonDao.getPokemonById(id);
        model.addAttribute("pokemon", pokemon);
        return "admin/pokemonDetail";
    }

    // Use a get mapping to direct to this page, use the id value retrieved as a hidden element in html
    // If user hits "yes", pokemon will be deleted using the id
    // This is an addition I made to the superheroes since I used a page rather than javascript
//    @GetMapping("admin/confirmDeletePokemon")
//    public String confirmDeletePokemon(Integer id, Model model) {
//        Pokemon pokemon = pokemonDao.getPokemonById(id);
//        model.addAttribute("pokemon", pokemon);
//        return "admin/confirmDeletePokemon";
//    }

}
