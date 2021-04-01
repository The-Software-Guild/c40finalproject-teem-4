package com.sg.pokemonproject.Dao;

import com.sg.pokemonproject.Entity.Ability;


import java.util.List;

public interface AbilityDao {

    public Ability addAbility(Ability ability);
    public Ability getAbilityById(int id);
    public void updateAbility(Ability ability);
    public void deleteAbilityById(int id);
    public List<Ability> getAll();
}
