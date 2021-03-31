package com.sg.pokemonproject.Dao;

import com.sg.pokemonproject.Entity.Type;

import java.util.List;

public interface TypeDao {

    public Type addType(Type type);
    public Type getTypeById(int id);
    public void updateType(Type type);
    public void deleteTypeById(int id);
    public List<Type> getAll();

}
