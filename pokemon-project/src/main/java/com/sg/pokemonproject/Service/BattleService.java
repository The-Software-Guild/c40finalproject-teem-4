package com.sg.pokemonproject.Service;

import com.sg.pokemonproject.Dao.AbilityDao;
import com.sg.pokemonproject.Dao.PokemonDao;
import com.sg.pokemonproject.Dao.UserDao;
import com.sg.pokemonproject.Entity.Ability;
import com.sg.pokemonproject.Entity.Pokemon;
import com.sg.pokemonproject.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class BattleService {
    @Autowired
    PokemonDao pokeDao;

    @Autowired
    AbilityDao abilityDao;

    @Autowired
    UserDao userDao;

    final Integer AP = 6;

    User user;
    Pokemon userPokemon;
    Pokemon opponent;

    public BattleService() {
    }

    //user has 6 AP at the beginning of their turn and chooses an ability until no AP is left
    //once opponent is at 0 HP, user wins and gains x amount of coins and adds the opponent Pokemon into their collection
    //if opponent wins, user gets neither money nor the pokemon

    public User getUser() {
        return user;
    }
    public void setUser(int userId) {
        this.user = userDao.getUserById(userId);
    }

    public Pokemon getUserPokemon() {
        return userPokemon;
    }
    public void setUserPokemon(int pokeId) {
        this.userPokemon = pokeDao.getPokemonById(pokeId);
    }

    public Pokemon getOpponent() {
        return opponent;
    }
    public void setOpponent(int opponentId) {
        this.opponent = pokeDao.getPokemonById(opponentId);
    }

    public String userAttack(int ap, int chosenAbilityId) {
        Ability chosenAbility = abilityDao.getAbilityById(chosenAbilityId);
        if (chosenAbility.getAP() > ap) {
            return "Not enough AP!";
        }
        String message = "";
        Random rand = new Random();
        int critChance = rand.nextInt(10);
        int atk = chosenAbility.getAttack();
        if (critChance < 2) { // 20% chance for critical damage
            message += "Critical hit! ";
            atk = (int) Math.round(1.5 * atk); // critical damage is 50% more than regular attack damage
        }
        int opponentHealth = opponent.getHealth();
        if (opponentHealth - atk < 0) { // user wins
            message += opponent.getName() + " has fainted! You gain 50 coins and " + opponent.getName() + " has been added to your bag!";
            opponent.setHealth(0);
            double userMoney = user.getMoney();
            user.setMoney(userMoney + 50);
            List<Pokemon> userPokemon = user.getPokemons();
            userPokemon.add(pokeDao.getPokemonById(opponent.getId())); // adds to user pokemon from pokeDao since opponent health is modified
            user.setPokemons(userPokemon);
            userDao.updateUser(user);
        } else {
            message += opponent.getName() + " loses " + atk + " HP!";
            opponent.setHealth(opponentHealth - atk); // subtract atk damage from opponent health
        }
        return message;
    }

    public List<String> opponentTurn() {
        int opponentAP = AP;
        List<String> opponentTurnMessages = new ArrayList<>();
        String opponentName = opponent.getName();
        String userName = userPokemon.getName();
        Random rand = new Random();
        int numOpponentAbilities = opponent.getAbilities().size();
        while (opponentAP > 0) {
            String message = "";
            // get a random ability from opponent that there's enough AP for
            Ability opponentAbility = opponent.getAbilities().get(rand.nextInt(numOpponentAbilities));
            while (opponentAbility.getAP() > opponentAP) {
                opponentAbility = opponent.getAbilities().get(rand.nextInt(numOpponentAbilities));
            }
            message += opponentName + " uses " + opponentAbility.getName() + "!\n";
            int atk = opponentAbility.getAttack();
            int critChance = rand.nextInt(10);
            if (critChance < 2) { // 20% chance for critical damage
                message += "Critical hit! ";
                atk = (int) Math.round(1.5 * atk); // critical damage is 50% more than regular damage
            }
            opponentAP -= opponentAbility.getAP(); // subtract AP used
            int userHealth = userPokemon.getHealth();
            if (userHealth - atk < 0) { // opponent wins
                message += userName + " has fainted!";
                userPokemon.setHealth(0);
            } else {
                message += userName + " loses " + atk + "HP!";
                userPokemon.setHealth(userHealth - atk); // subtract damage from user health
            }
            opponentTurnMessages.add(message);
        }
        return opponentTurnMessages;
    }
}
