package com.sg.pokemonproject.Controller;

import com.sg.pokemonproject.Dao.TypeDao;
import com.sg.pokemonproject.Entity.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class TypeController {

    @Autowired
    TypeDao typeDao;

    @GetMapping("admin/displayTypes")
    public String displayType(Model model) {
        List<Type> type = typeDao.getAll();
        model.addAttribute("type", type);
        return "admin/displayTypes";
    }

    @PostMapping("admin/addType")
    public String addType(String name) {
        Type type = new Type();
        type.setName(name);

        typeDao.addType(type);

        return "redirect:/admin/addType";
    }

    @GetMapping("admin/deleteType")
    public String deleteType(Integer id) {
        typeDao.deleteTypeById(id);
        return "redirect:/admin/deleteType";
    }


    @GetMapping("admin/editType")
    public String editType(Integer id, Model model) {
        Type type = typeDao.getTypeById(id);
        model.addAttribute("type", type);
        return "admin/editType";
    }

    @PostMapping("admin/editType")
    public String performEditType(Type type, BindingResult result) {
        if(result.hasErrors()) {
            return "admin/editType";
        }
        typeDao.updateType(type);

        return "redirect:/admin/displayTypes";
    }

//    @GetMapping("admin/confirmDeleteType")
//    public String confirmDeleteType(Integer id, Model model) {
//        Type type = typeDao.getTypeById(id);
//        model.addAttribute("type", type);
//        return "admin/confirmDeleteType";
//    }
}
