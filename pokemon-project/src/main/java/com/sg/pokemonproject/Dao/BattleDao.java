package com.sg.pokemonproject.Dao;

import com.sg.pokemonproject.Entity.Battle;

import java.util.List;

public interface BattleDao {
    public Battle addBattle(Battle battle);
    public Battle getBattleById(int id);
    public List<Battle> getAll();
    public void deleteBattleById(int id);
    public void deleteBattleByUserId(int userId);
}
