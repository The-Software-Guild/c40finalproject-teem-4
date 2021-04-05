package com.sg.pokemonproject.Dao;

import com.sg.pokemonproject.Entity.Pokemon;
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
public class UserDaoDB implements UserDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public User addUser(User user) {

        final String INSERT_User = "INSERT INTO User(FirstName, LastName, Money, Email, password) " +
                "VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_User,
                user.getFirstName(),
                user.getLastName(),
                user.getMoney(),
                user.getEmail(),
                user.getPassword());

        int newId = jdbc.queryForObject("Select Last_Insert_Id()", Integer.class);
        user.setId(newId);
        if(user.getPokemons() != null)
            insertUserPokemons(user);
        return user;
    }

    private void insertUserPokemons(User user) {
        final String sql = "INSERT INTO "
                + "User_Pokemon (Pokemonid, Userid) VALUES(?,?)";
        for(Pokemon pokemon : user.getPokemons()) {
            jdbc.update(sql,
                    pokemon.getId(),
                    user.getId());
        }
    }


    @Override
    public User getUserById(int id) {
        try {
            final String sql = "SELECT * FROM user WHERE Userid = ? ;";
            User user = jdbc.queryForObject(sql, new UserMapper(), id);
            user.setPokemons(getPokemonsForUser(user));
            return user;
        }catch(
                DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updateUser(User user) {
        final String sql = "UPDATE user SET "
                + "FirstName = ?, "
                + "LastName= ?, "
                + "Money= ?, "
                + "Email= ?, "
                + "password= ? "
                + "WHERE Userid = ?;";

        jdbc.update(sql,
                user.getFirstName(),
                user.getLastName(),
                user.getMoney(),
                user.getEmail(),
                user.getPassword(),
                user.getId());
    }

    @Override
    public List<User> getAll() {
        final String sql = "SELECT * FROM user ;";
        List<User> users = jdbc.query(sql, new UserMapper());
        associatePokemons(users);
        return users;
    }

    private void associatePokemons(List<User> users) {
        for (User user : users) {
            user.setPokemons(getPokemonsForUser(user));
        }
    }

    @Override
    @Transactional
    public void deleteUser(int id) {
        final String DELETE_USER_POKEMON = "DELETE FROM User_Pokemon WHERE Userid = ?";
        jdbc.update(DELETE_USER_POKEMON, id);

        final String DELETE_USER = "DELETE FROM user WHERE Userid = ?";
        jdbc.update(DELETE_USER, id);
    }

    @Override
    public List<Pokemon> getPokemonsForUser(User user) {
        final String sql = "SELECT p.*  FROM pokemon p " +
                " join User_Pokemon up on p.Pokemonid=up.Pokemonid " +
                " where up.Userid=? ;";

        return jdbc.query(sql, new PokemonDaoDB.PokemonMapper(), user.getId());
    }

    @Override
    public boolean login(String email, String password) {
        try {
            final String sql = "SELECT * FROM user WHERE Email = ? and password = ? ;";
            User user = jdbc.queryForObject(sql, new UserMapper(), email, password);
            return user != null;
        }catch(
                DataAccessException ex) {
            return false;
        }
    }

    public static final class UserMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int index) throws SQLException {
            User user = new User();
            user.setId(rs.getInt("Userid"));
            user.setFirstName(rs.getString("FirstName"));
            user.setLastName(rs.getString("LastName"));
            user.setEmail(rs.getString("Email"));
            user.setPassword(rs.getString("password"));
            user.setMoney(rs.getDouble("Money"));
            return user;
        }
    }
}
