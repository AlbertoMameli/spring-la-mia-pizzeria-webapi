package org.lessons.wdpt6.pizzeria.la_mia_pizzeria.controller;

import java.util.List;

import org.lessons.wdpt6.pizzeria.la_mia_pizzeria.model.Ingrediente;
import org.lessons.wdpt6.pizzeria.la_mia_pizzeria.model.Pizza;
import org.lessons.wdpt6.pizzeria.la_mia_pizzeria.repository.IngredienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredienti")
public class IngredienteController {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("ingredienti", ingredienteRepository.findAll());
        return "ingredienti/index";
    }

    @GetMapping("{id}")
    public String show(@PathVariable Integer id, Model model) {
        model.addAttribute("ingrediente", ingredienteRepository.findById(id).get());
        return "ingredienti/show";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("ingrediente", new Ingrediente());
        return "ingredienti/create";
    }

    @PostMapping()
    public String store(@Valid @ModelAttribute("ingrediente") Ingrediente formIngrediente, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "ingredienti/create";
        }
        ingredienteRepository.save(formIngrediente);
        return "redirect:/ingredienti";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("ingrediente", ingredienteRepository.findById(id).get());
        return "ingredienti/edit";
    }

    @PostMapping("/{id}")
    public String update(@Valid @ModelAttribute("ingrediente") Ingrediente formIngrediente, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "ingredienti/edit";
        }
        ingredienteRepository.save(formIngrediente);
        return "redirect:/ingredienti";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Ingrediente igredienteDacancellare = ingredienteRepository.findById(id).get();

        for (Pizza pizza : igredienteDacancellare.getPizzas()) {
            pizza.getIngredienti().remove(igredienteDacancellare);

        }

        ingredienteRepository.deleteById(id);
        return "redirect:/ingredienti";
    }

    @PostMapping("/delete-multiple")
    public String deleteMultiple(@RequestParam(name = "ids", required = false) List<Integer> ids) {
        if (ids != null && !ids.isEmpty()) {
            ingredienteRepository.deleteAllById(ids);
        }
        return "redirect:/ingredienti";
    }

}
