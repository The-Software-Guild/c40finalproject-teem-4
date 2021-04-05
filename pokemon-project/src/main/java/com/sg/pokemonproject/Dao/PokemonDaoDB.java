package com.sg.pokemonproject.Dao;

import com.sg.pokemonproject.Entity.Ability;
import com.sg.pokemonproject.Entity.Pokemon;
import com.sg.pokemonproject.Entity.Type;
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
public class PokemonDaoDB implements PokemonDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Pokemon addPokemon(Pokemon pokemon) {
        final String INSERT_POKEMON = "INSERT INTO pokemon(name, health, weight, height, typeid, image, price) "
                + "VALUES(?,?,?,?,?,?,?)";
        jdbc.update(INSERT_POKEMON,
                pokemon.getName(),
                pokemon.getHealth(),
                pokemon.getWeight(),
                pokemon.getHeight(),
                pokemon.getType().getId(),
                pokemon.getImage(),
                pokemon.getPrice());

        int newId = jdbc.queryForObject("Select Last_Insert_Id()", Integer.class);
        pokemon.setId(newId);
        insertPokeAndAbility(pokemon);
        return pokemon;
    }

    private void insertPokeAndAbility(Pokemon pokemon) {
        final String INSERT_POKE_ABILITY = "INSERT INTO poke_ability(pokemonId, abilityId) "
                + "VALUES(?,?)";
        for(Ability ability : pokemon.getAbilities()) {
            jdbc.update(INSERT_POKE_ABILITY, pokemon.getId(), ability.getId());
        }
    }

    @Override
    public Pokemon getPokemonById(int id) {
        try {
            final String SELECT_POKEMON_BY_ID = "SELECT * FROM pokemon WHERE pokemonid = ?";
            Pokemon pokemon = jdbc.queryForObject(SELECT_POKEMON_BY_ID, new PokemonMapper(), id);
            pokemon.setType(getTypeForPokemon(id));
            pokemon.setAbilities(getAbilitiesForPokemon(id));
            return pokemon;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void updatePokemon(Pokemon pokemon) {
        final String UPDATE_POKEMON= "UPDATE pokemon SET name = ?, health = ?, weight = ?, height = ?, image = ?, price = ?, typeid = ? "
                + "WHERE pokemonid = ?";
        jdbc.update(UPDATE_POKEMON,
                pokemon.getName(),
                pokemon.getHealth(),
                pokemon.getWeight(),
                pokemon.getHeight(),
                pokemon.getImage(),
                pokemon.getPrice(),
                pokemon.getType().getId(),
                pokemon.getId());

        final String DELETE_POKE_ABILITY = "DELETE FROM poke_ability WHERE pokemonid = ?";
        jdbc.update(DELETE_POKE_ABILITY, pokemon.getId());
        insertPokeAndAbility(pokemon);
    }

    @Override
    @Transactional
    public void deletePokemonById(int id) {
        final String DELETE_POKE_ABILITY = "DELETE FROM poke_ability WHERE pokemonid = ?";
        jdbc.update(DELETE_POKE_ABILITY, id);

        final String DELETE_POKEMON = "DELETE FROM pokemon WHERE pokemonid = ?";
        jdbc.update(DELETE_POKEMON, id);
    }

    @Override
    public List<Pokemon> getAll() {
        final String SELECT_ALL_POKEMON = "SELECT * FROM pokemon";
        List<Pokemon> pokemon = jdbc.query(SELECT_ALL_POKEMON, new PokemonMapper());
        associateTypeAndAbilities(pokemon);
        return pokemon;
    }

    private void associateTypeAndAbilities(List<Pokemon> pokemon) {
        for (Pokemon pokemons : pokemon) {
            pokemons.setType(getTypeForPokemon(pokemons.getId()));
            pokemons.setAbilities(getAbilitiesForPokemon(pokemons.getId()));
        }

    }

    private List<Ability> getAbilitiesForPokemon(int id) {
        final String SELECT_ABILITY_FOR_POKE = "SELECT a.* FROM ability a JOIN poke_ability pa ON a.abilityId = pa.abilityId WHERE pa.pokemonid = ?";
        return jdbc.query(SELECT_ABILITY_FOR_POKE, new AbilityDaoDB.AbilityMapper(), id);
    }


    private Type getTypeForPokemon(int id) {
        final String SELECT_TYPE_FOR_POKE = "SELECT t.* FROM type t JOIN pokemon p ON t.typeId = p.typeId WHERE p.pokemonid = ?";
        return jdbc.queryForObject(SELECT_TYPE_FOR_POKE, new TypeDaoDB.TypeMapper(), id);
    }

    public static final class PokemonMapper implements RowMapper<Pokemon> {

        @Override
        public Pokemon mapRow(ResultSet rs, int index) throws SQLException {
            Pokemon pokemon = new Pokemon();
            pokemon.setId(rs.getInt("Pokemonid"));
            pokemon.setName(rs.getString("Name"));
            pokemon.setHealth(rs.getInt("Health"));
            pokemon.setWeight(rs.getDouble("Weight"));
            pokemon.setHeight(rs.getDouble("Height"));
            pokemon.setImage(rs.getString("Image"));
            pokemon.setPrice(rs.getDouble("Price"));

            return pokemon;
        }
    }
}
