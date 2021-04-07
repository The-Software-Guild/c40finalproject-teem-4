package com.sg.pokemonproject.Entity;

import java.util.Objects;

public class Battle {
    private int id;
    private int userId;
    private int opponentId;
    private int userPokemonId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(int opponentId) {
        this.opponentId = opponentId;
    }

    public int getUserPokemonId() {
        return userPokemonId;
    }

    public void setUserPokemonId(int userPokemonId) {
        this.userPokemonId = userPokemonId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Battle battle = (Battle) o;
        return id == battle.id && userId == battle.userId && opponentId == battle.opponentId && userPokemonId == battle.userPokemonId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
