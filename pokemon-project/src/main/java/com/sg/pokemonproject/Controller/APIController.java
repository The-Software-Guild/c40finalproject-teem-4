package com.sg.pokemonproject.Controller;

import com.sg.pokemonproject.Entity.Pokemon;
import com.sg.pokemonproject.models.PokemonInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
/*
@RestController
@RequestMapping("/")

 */
public class APIController {
    /*
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{pokemonId}")
    public Pokemon getPokemonInfo(@PathVariable("pokemonId") String pokemonId) {
        PokemonInformation pokemonInformation = restTemplate.getForObject(
                "https://pokeapi.co/api/v2/pokemon/" + pokemonId, PokemonInformation.class
        );
        return new Pokemon(pokemonId, pokemonInformation.getName(), );
    }
    */
}
