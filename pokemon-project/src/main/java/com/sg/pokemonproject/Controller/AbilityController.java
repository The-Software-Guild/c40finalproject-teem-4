package com.sg.pokemonproject.Controller;

import com.sg.pokemonproject.Dao.AbilityDao;
import com.sg.pokemonproject.Entity.Ability;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class AbilityController {
    @Autowired
    AbilityDao abilityDao;

    @GetMapping("admin/displayAbility")
    public String displayAbility(Model model) {
        List<Ability> abilities = abilityDao.getAll();
        model.addAttribute("abilities", abilities);
        return "admin/displayAbility";
    }

    @PostMapping("admin/addAbility")
    public String addAbility(String name, int AP, int attack) {

        Ability abilities = new Ability();
        abilities.setName(name);
        abilities.setAP(AP);
        abilities.setAttack(attack);

        abilityDao.addAbility(abilities);

        return "redirect:/admin/displayAbility";
    }

    @GetMapping("admin/editAbility")
    public String editAbility(Integer id, Model model) {
        Ability abilities = abilityDao.getAbilityById(id);
        model.addAttribute("abilities", abilities);
        return "admin/editAbility";
    }

    @PostMapping("admin/editAbility")
    public String performEditAbility(Ability abilities, BindingResult result) {
        if(result.hasErrors()) {
            return "admin/editAbility";
        }
        abilityDao.updateAbility(abilities);

        return "redirect:/admin/editAbility";
    }

    @GetMapping("admin/abilityDetail")
    public String abilityDetail(Integer id, Model model) {
        Ability abilities = abilityDao.getAbilityById(id);
        model.addAttribute("abilities", abilities);
        return "admin/abilityDetail";
    }

//    @GetMapping("admin/confirmDeleteAbility")
//    public String confirmDeleteAbility(Integer id, Model model) {
//        Ability abilities = abilityDao.getAbilityById(id);
//        model.addAttribute("abilities", abilities);
//        return "admin/confirmDeleteAbility";
//    }

    @GetMapping("admin/deleteAbility")
    public String deleteAbility(Integer id) {
        abilityDao.deleteAbilityById(id);
        return "redirect:/admin/displayAbility";
    }
}
