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

    Integer userAP = 8;

    Pokemon userPokemon;
    Pokemon opponent;
    int userMaxHp;
    int opponentMaxHp;

    //user has 6 AP at the beginning of their turn and chooses an ability until no AP is left
    //once opponent is at 0 HP, user wins and gains x amount of coins and adds the opponent Pokemon into their collection
    //if opponent wins, user gets neither money nor the pokemon

    public Pokemon getUserPokemon() {
        return userPokemon;
    }
    public void setUserPokemon(int pokeId) {
        this.userMaxHp = pokeDao.getPokemonById(pokeId).getHealth();
        this.userPokemon = pokeDao.getPokemonById(pokeId);
    }
    public int getUserPokemonHp() {
        return userPokemon.getHealth();
    }

    public Pokemon getOpponent() {
        return opponent;
    }
    public void setOpponent(int opponentId) {
        this.opponentMaxHp = pokeDao.getPokemonById(opponentId).getHealth();
        this.opponent = pokeDao.getPokemonById(opponentId);
    }
    public int getOpponentHp() {
        return opponent.getHealth();
    }

    public int getUserAP() {
        return userAP;
    }
    public void setUserAP(int newAP) {
        this.userAP = newAP;
    }

    public int getUserMaxHp() {
        return userMaxHp;
    }
    public int getOpponentMaxHp() {
        return opponentMaxHp;
    }

    public String userAttack(int chosenAbilityId) {
        User user = userDao.getUserById(userDao.getUserConnected());
        if (this.getOpponentHp() <= 0) {
            return "Game over! Return to the select page to battle another Pokemon!";
        }
        else if (userPokemon.getHealth() <= 0) {
            return userPokemon.getName() + " has fainted! Return to select page to battle again.";
        }
        Ability chosenAbility = abilityDao.getAbilityById(chosenAbilityId);
        if (chosenAbility.getAP() > userAP) {
            return "Not enough AP!";
        }
        String message = "";
        message += userPokemon.getName()+" uses "+chosenAbility.getName()+"!\n";
        Random rand = new Random();
        int critChance = rand.nextInt(10);
        int atk = chosenAbility.getAttack();
        if (critChance < 2) { // 20% chance for critical damage
            message += "Critical hit! ";
            atk = (int) Math.round(1.5 * atk); // critical damage is 50% more than regular attack damage
        }
        this.setUserAP(userAP - chosenAbility.getAP()); // subtract amount of AP used from ability
        int opponentHealth = opponent.getHealth();
        if (opponentHealth - atk <= 0) { // user wins
            //message += opponent.getName() + " has fainted! " + opponent.getName() + " has been added to your bag!\n";
            message += opponent.getName() + " has fainted! You gain 5 coins and " + opponent.getName() + " has been added to your bag!\n";
            message += "Return to the select page to battle another Pokemon!";
            opponent.setHealth(0);
            double userMoney = user.getMoney();
            user.setMoney(userMoney + 5);
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

    public String endUserTurn() {
        this.setUserAP(8);
        if (this.getOpponentHp() <= 0) {
            return "Game over! Return to the select page to battle another Pokemon!";
        }
        return "";
    }

    public List<String> opponentTurn() {
        int opponentAP = 8;
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
            if (userHealth - atk <= 0) { // opponent wins
                message += userName + " has fainted! Return to select page to battle again.";
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
