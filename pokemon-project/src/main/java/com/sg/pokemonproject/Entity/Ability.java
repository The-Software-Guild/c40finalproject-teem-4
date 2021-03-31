package com.sg.pokemonproject.Entity;

import java.util.Objects;

public class Ability {
    private int id;
    private String name;
    private int AP;
    private int attack;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAP() {
        return AP;
    }

    public void setAP(int AP) {
        this.AP = AP;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ability ability = (Ability) o;
        return id == ability.id && AP == ability.AP && attack == ability.attack && Objects.equals(name, ability.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, AP, attack);
    }
}
