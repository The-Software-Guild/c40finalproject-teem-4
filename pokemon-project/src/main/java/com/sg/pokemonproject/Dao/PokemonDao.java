package com.sg.pokemonproject.Dao;

import com.sg.pokemonproject.Entity.Ability;
import com.sg.pokemonproject.Entity.Pokemon;
import com.sg.pokemonproject.Entity.Type;


import java.util.List;

public interface PokemonDao {
    public Pokemon addPokemon(Pokemon pokemon);
    public Pokemon getPokemonById(int id);
    public void updatePokemon(Pokemon pokemon);
    public void deletePokemonById(int id);
    public List<Pokemon> getAll();

}
