package com.sg.pokemonproject.Controller;

import com.sg.pokemonproject.Entity.Ability;
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
    private final BattleService battleService;
    private final BattleSelectService battleSelect;
    int AP = 6;

    @Autowired
    public BattleController(BattleService service, BattleSelectService select) {
        this.battleService = service;
        this.battleSelect = select;
    }

    @GetMapping("battleSelect/{id}")
    public String battleSelect(@PathVariable("id") int userId, Model model) {
        List<Pokemon> userPokemon = battleSelect.getUserPokemon(userId);
        List<Pokemon> otherPokemon = battleSelect.getOtherPokemon(userId);
        model.addAttribute("userPokemon", userPokemon);
        model.addAttribute("otherPokemon", otherPokemon);
        return "battleSelect";
    }
    @PostMapping("battleSelect")
    public String performBattleSelect(Integer userId, HttpServletRequest request) {
        String userPokeId = request.getParameter("userPokeId");
        String opponentPokeId = request.getParameter("opponentPokeId");
        battleService.setUser(userId);
        battleService.setUserPokemon(Integer.parseInt(userPokeId));
        battleService.setOpponent(Integer.parseInt(opponentPokeId));
        return "redirect:/battle"; //+userId+"/"+userPokeId+"/"+opponentPokeId
    }

    @GetMapping("battle")
    ///{userId}/{userPokeId}/{opponentId}
    public String battle(Integer userId, Integer userPokeId, Integer opponentId, Model model) {
        List<Ability> abilities = battleService.getUserPokemon().getAbilities();
        model.addAttribute("user", battleService.getUser());
        model.addAttribute("userPoke", battleService.getUserPokemon());
        model.addAttribute("opponent", battleService.getOpponent());
        model.addAttribute("ability1", abilities.get(0));
        model.addAttribute("ability2", abilities.get(1));
        model.addAttribute("ability3", abilities.get(2));
        model.addAttribute("ability4", abilities.get(3));
        return "battle";
    }

    // either find a way to change the message every few seconds or use a button to get to next message
    @GetMapping("attack/{abilityId}")
    public String attack(@PathVariable("abilityId") int abilityId, Model model) {
        String message = battleService.userAttack(abilityId);
        model.addAttribute("message", message);
        model.addAttribute("userAP", battleService.getUserAP());
        return "battle";
    }

    //button to end user turn then display opponent turn messages
    @GetMapping("endTurn")
    public String endTurn(Model model) {
        battleService.endUserTurn();
        List<String> opponentMessages = battleService.opponentTurn();
        model.addAttribute("userAP", battleService.getUserAP());
        model.addAttribute("opponentMessages", opponentMessages);
        return "battle";
    }

    //button to return to battle select page
    @GetMapping("returnToSelect")
    public String returnToSelect() {
        int userId = battleService.getUser().getId();
        return "redirect:/battleSelect/"+userId;
    }
}
