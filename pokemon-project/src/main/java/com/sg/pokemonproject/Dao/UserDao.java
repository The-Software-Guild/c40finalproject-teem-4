package com.sg.pokemonproject.Dao;

import com.sg.pokemonproject.Entity.Pokemon;
import com.sg.pokemonproject.Entity.User;

import java.util.List;

public interface UserDao {
    public User addUser(User user);
    public User getUserById(int id);
    public void updateUser(User user);
    public List<User> getAll();
    public void deleteUser(int id);

    public List<Pokemon> getPokemonsForUser(User user);

    public boolean login(String email, String password);

}
