package com.sg.pokemonproject.Service;

import com.sg.pokemonproject.Dao.PokemonDao;
import com.sg.pokemonproject.Dao.UserDao;
import com.sg.pokemonproject.Entity.Ability;
import com.sg.pokemonproject.Entity.Pokemon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class BattleService {
    @Autowired
    PokemonDao pokeDao;

    @Autowired
    UserDao userDao;

    final Integer AP = 6;

    //user has 6 AP at the beginning of their turn and chooses an ability until no AP is left
    //once opponent is at 0 HP, user wins and gains x amount of coins and adds the opponent Pokemon into their collection
    //if opponent wins, user get neither money nor the pokemon

    public void userAttack(Ability chosenAbility, Pokemon opponent) {
        Random rand = new Random();
        int critChance = rand.nextInt(10);
        int atk = chosenAbility.getAttack();
        if (critChance < 2) { // 20% chance for critical damage
            atk = (int) Math.round(1.5 * atk); // critical damage is 50% more than regular attack damage
        }
        int opponentHealth = opponent.getHealth();
        if (opponentHealth - atk < 0) { // user wins
            opponent.setHealth(0);
        }
        opponent.setHealth(opponentHealth - atk); // subtract atk damage from opponent health
    }

    public void opponentTurn(Pokemon user, Pokemon opponent) {
        int opponentAP = AP;
        Random rand = new Random();
        int numOpponentAbilities = opponent.getAbilities().size();
        while (opponentAP > 0) {
            // get a random ability from opponent that there's enough AP for
            Ability opponentAbility = opponent.getAbilities().get(rand.nextInt(numOpponentAbilities));
            while (opponentAbility.getAP() > opponentAP) {
                opponentAbility = opponent.getAbilities().get(rand.nextInt(numOpponentAbilities));
            }
            int atk = opponentAbility.getAttack();
            int critChance = rand.nextInt(10);
            if (critChance < 2) { // 20% chance for critical damage
                atk = (int) Math.round(1.5 * atk); // critical damage is 50% more than regular damage
            }
            opponentAP -= opponentAbility.getAP(); // subtract AP used
            int userHealth = user.getHealth();
            if (userHealth - atk < 0) { // opponent wins
                user.setHealth(0);
            }
            user.setHealth(userHealth - atk); // subtract damage from user health
        }
    }
}
