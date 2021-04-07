package com.sg.pokemonproject.Dao;

import com.sg.pokemonproject.Entity.Battle;
import com.sg.pokemonproject.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BattleDaoDB implements BattleDao{
    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Battle addBattle(Battle battle) {
        final String INSERT_BATTLE = "INSERT INTO Battle(Userid, Opponentid, UserPokemonid) " +
                "VALUES(?,?,?)";
        jdbc.update(INSERT_BATTLE,
                battle.getUserId(),
                battle.getOpponentId(),
                battle.getUserPokemonId());

        int newId = jdbc.queryForObject("Select Last_Insert_Id()", Integer.class);
        battle.setId(newId);
        return battle;
    }

    @Override
    public Battle getBattleById(int id) {
        try {
            final String SELECT_BATTLE_BY_ID = "SELECT * from Battle WHERE Battleid = ?";
            return jdbc.queryForObject(SELECT_BATTLE_BY_ID, new BattleMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Battle> getAll() {
        final String SELECT_ALL_BATTLES = "SELECT * FROM Battle";
        return jdbc.query(SELECT_ALL_BATTLES, new BattleMapper());
    }

    @Override
    public void deleteBattleById(int id) {
        final String DELETE_BATTLE_BY_ID = "DELETE FROM Battle WHERE Battleid = ?";
        jdbc.update(DELETE_BATTLE_BY_ID, id);
    }

    @Override
    public void deleteBattleByUserId(int userId) {
        final String DELETE_BATTLE_BY_USER_ID = "DELETE FROM Battle WHERE Userid = ?";
        jdbc.update(DELETE_BATTLE_BY_USER_ID, userId);
    }

    public static final class BattleMapper implements RowMapper<Battle> {
        @Override
        public Battle mapRow(ResultSet rs, int index) throws SQLException {
            Battle battle = new Battle();
            battle.setId(rs.getInt("Battleid"));
            battle.setUserId(rs.getInt("Userid"));
            battle.setOpponentId(rs.getInt("Opponentid"));
            battle.setUserPokemonId(rs.getInt("UserPokemonid"));
            return battle;
        }
    }
}
