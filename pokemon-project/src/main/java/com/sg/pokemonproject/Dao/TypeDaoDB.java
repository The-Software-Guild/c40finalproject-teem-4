package com.sg.pokemonproject.Dao;

import com.sg.pokemonproject.Entity.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TypeDaoDB implements TypeDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Type addType(Type type) {
        final String INSERT_TYPE = "INSERT INTO Type(`Name`) VALUES (?)";
        jdbc.update(INSERT_TYPE, type.getName());

        int newId = jdbc.queryForObject("SELECT_LAST_INSERT_ID()", Integer.class);
        type.setId(newId);

        return type;
    }

    @Override
    public Type getTypeById(int id) {
        final String SELECT_TYPE_BY_ID = "SELECT * from Type WHERE Typeid = ?";

        return jdbc.queryForObject(SELECT_TYPE_BY_ID, new TypeMapper(), id);
    }

    @Override
    public void updateType(Type type) {
        final String UPDATE_TYPE = "INSERT Type SET Name = ? WHERE Typeid = ?";

        jdbc.update(UPDATE_TYPE,
                type.getName(),
                type.getId());
    }

    @Override
    @Transactional
    public void deleteTypeById(int id) {
        final String DELETE_POKEMON_BY_TYPE_ID = "DELETE FROM Pokemon WHERE Typeid = ?";
        jdbc.update(DELETE_POKEMON_BY_TYPE_ID, id);

        final String DELETE_TYPE_BY_ID = "DELETE FROM Type WHERE Typeid = ?";
        jdbc.update(DELETE_TYPE_BY_ID, id);
    }

    @Override
    public List<Type> getAll() {
        final String SELECT_ALL_TYPES = "SELECT * FROM Type";

        return jdbc.query(SELECT_ALL_TYPES, new TypeMapper());
    }

    public static final class TypeMapper implements RowMapper<Type>{

        @Override
        public Type mapRow(ResultSet resultSet, int i) throws SQLException {
            Type type = new Type();
            type.setName(resultSet.getString("Name"));
            type.setId(resultSet.getInt("Typeid"));

            return type;
        }
    }
}
