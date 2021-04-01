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
    public String addAbility(String organizationName, String organizationDescription,
                                  String address, String contactInfo) {

        Organizations organization = new Organizations();
        organization.setOrganizationName(organizationName);
        organization.setOrganizationDescription(organizationDescription);
        organization.setAddress(address);
        organization.setContactInfo(contactInfo);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(organization);

        if(violations.isEmpty()) {
            organizationsDao.addOrganization(organization);
        }

        return "redirect:/admin/displayAbility";
    }

    @GetMapping("admin/editAbility")
    public String editOrganization(Integer id, Model model) {
        Organizations organization = organizationsDao.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "admin/editAbility";
    }

    @PostMapping("editOrganization")
    public String performEditOrganization(@Valid Organizations organization, BindingResult result) {
        if(result.hasErrors()) {
            return "editOrganization";
        }
        organizationsDao.updateOrganization(organization);

        return "redirect:/organizations";
    }

    @GetMapping("organizationDetail")
    public String organizationDetail(Integer id, Model model) {
        Organizations organization = organizationsDao.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "organizationDetail";
    }

    @GetMapping("confirmDeleteOrganization")
    public String confirmDeleteOrganization(Integer id, Model model) {
        Organizations organization = organizationsDao.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "confirmDeleteOrganization";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer id) {
        organizationsDao.deleteOrganizationById(id);
        return "redirect:/organizations";
    }
}
