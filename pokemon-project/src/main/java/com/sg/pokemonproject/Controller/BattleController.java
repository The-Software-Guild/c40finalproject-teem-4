package com.sg.pokemonproject.Controller;

import com.sg.pokemonproject.Dao.BattleDao;
import com.sg.pokemonproject.Entity.Ability;
import com.sg.pokemonproject.Entity.Battle;
import com.sg.pokemonproject.Entity.Pokemon;
import com.sg.pokemonproject.Service.BattleSelectService;
import com.sg.pokemonproject.Service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class BattleController {
    @Autowired
    BattleDao battleDao;

    @Autowired
    BattleService battleService;

    @Autowired
    BattleSelectService battleSelectService;

    int AP = 8;

    @GetMapping("battleSelect/{id}")
    public String battleSelect(@PathVariable("id") int userId, Model model) {
        battleService.setUser(userId);
        List<Pokemon> userPokemon = battleSelectService.getUserPokemon(userId);
        List<Pokemon> otherPokemon = battleSelectService.getOtherPokemon(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("userPokemon", userPokemon);
        model.addAttribute("otherPokemon", otherPokemon);
        return "battleSelect";
    }

    @PostMapping("battleSelect")
    public String performBattleSelect(HttpServletRequest request) {

        String uPokeId = request.getParameter("userPokeId");
        String oPokeId = request.getParameter("opponentPokeId");
        battleService.setUserPokemon(Integer.parseInt(uPokeId));
        battleService.setOpponent(Integer.parseInt(oPokeId));
        /*
        Battle battle = new Battle();
        battle.setUserId(battleService.getUser().getId());
        battle.setUserPokemonId(Integer.parseInt(uPokeId));
        battle.setOpponentId(Integer.parseInt(oPokeId));
        battleSelectService.addBattle(battle);*/
        return "redirect:/battle";//Select/"+userId;///"+userId+"/"+userPokeId+"/"+opponentPokeId;
    }

    @GetMapping("battle")
    //@GetMapping("battle/{userId}/{oppId}")
    public String battle(Model model) {
    //public String battle(@PathVariable("userId") int userId, @PathVariable("oppId") int oppId, Model model) {
        List<Ability> abilities = battleService.getUserPokemon().getAbilities();
        model.addAttribute("user", battleService.getUser());
        model.addAttribute("userAP", battleService.getUserAP());//user's AP starts at 8 for every turn
        model.addAttribute("message", "Choose an ability!");

        model.addAttribute("userPoke", battleService.getUserPokemon());
        model.addAttribute("opponent", battleService.getOpponent());

        //set initial health levels to full health
        model.addAttribute("userHP", battleService.getUserMaxHp());
        model.addAttribute("opponentHP", battleService.getOpponentMaxHp());
        model.addAttribute("maxUserHP", battleService.getUserMaxHp());
        model.addAttribute("maxOppHP", battleService.getOpponentMaxHp());

        model.addAttribute("ability1", abilities.get(0));
        model.addAttribute("ability2", abilities.get(1));
        return "battle";
    }

    // either find a way to change the message every few seconds or use a button to get to next message
    @GetMapping("battle/attack/{abilityId}")
    public String attack(@PathVariable("abilityId") int abilityId, Model model) {
        model.addAttribute("user", battleService.getUser());
        model.addAttribute("userPoke", battleService.getUserPokemon());
        model.addAttribute("opponent", battleService.getOpponent());
        List<Ability> abilities = battleService.getUserPokemon().getAbilities();
        model.addAttribute("ability1", abilities.get(0));
        model.addAttribute("ability2", abilities.get(1));

        String message = battleService.userAttack(abilityId);
        model.addAttribute("message", message);
        model.addAttribute("userAP", battleService.getUserAP()); //subtract used AP

        model.addAttribute("maxUserHP", battleService.getUserMaxHp());
        model.addAttribute("maxOppHP", battleService.getOpponentMaxHp());

        model.addAttribute("userHP", battleService.getUserPokemonHp());
        model.addAttribute("opponentHP", battleService.getOpponentHp()); //set opponent HP

        return "battle";
    }

    //button to end user turn then display opponent turn messages
    @GetMapping("battle/endTurn")
    public String endTurn(Model model) {
        model.addAttribute("user", battleService.getUser());
        model.addAttribute("userPoke", battleService.getUserPokemon());
        model.addAttribute("opponent", battleService.getOpponent());
        List<Ability> abilities = battleService.getUserPokemon().getAbilities();
        model.addAttribute("ability1", abilities.get(0));
        model.addAttribute("ability2", abilities.get(1));

        String gameOverMessage = battleService.endUserTurn();
        List<String> opponentMessages = battleService.opponentTurn();

        model.addAttribute("maxUserHP", battleService.getUserMaxHp());
        model.addAttribute("maxOppHP", battleService.getOpponentMaxHp());

        model.addAttribute("userAP", battleService.getUserAP());//reset user's AP to 8
        model.addAttribute("opponentHP", battleService.getOpponentHp()); //set User HP
        model.addAttribute("userHP", battleService.getUserPokemonHp());

        //messages from opponent's attacks
        String message = String.join("\n", opponentMessages);
        if (!gameOverMessage.equals("")) {
            model.addAttribute("message", gameOverMessage);
        } else {
            model.addAttribute("message", message);
        }
        return "battle";
    }

    //button to return to battle select page
    @GetMapping("battle/returnToSelect")
    public String returnToSelect() {
        int userId = battleService.getUser().getId();
        return "redirect:/battleSelect/"+userId;
    }
}
