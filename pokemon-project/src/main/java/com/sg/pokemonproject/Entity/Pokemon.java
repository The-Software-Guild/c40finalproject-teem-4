package com.sg.pokemonproject.Entity;

import java.util.List;
import java.util.Objects;

public class Pokemon {

    private int id;
    private String name;
    private int health;
    private double weight;
    private double height;
    private String image;
    private double price;
    private Type type;
    private List<Ability> abilities;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return id == pokemon.id && health == pokemon.health && Double.compare(pokemon.weight, weight) == 0 && Double.compare(pokemon.height, height) == 0 && Double.compare(pokemon.price, price) == 0 && Objects.equals(name, pokemon.name) && Objects.equals(image, pokemon.image) && Objects.equals(type, pokemon.type) && Objects.equals(abilities, pokemon.abilities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, health, weight, height, image, price, type, abilities);
    }

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

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }
}
