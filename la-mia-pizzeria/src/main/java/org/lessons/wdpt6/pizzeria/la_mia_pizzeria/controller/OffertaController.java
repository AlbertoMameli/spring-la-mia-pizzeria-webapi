package org.lessons.wdpt6.pizzeria.la_mia_pizzeria.controller;

import org.lessons.wdpt6.pizzeria.la_mia_pizzeria.model.Offerta;
import org.lessons.wdpt6.pizzeria.la_mia_pizzeria.repository.OffertaRepository;
import org.lessons.wdpt6.pizzeria.la_mia_pizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/offerte")
public class OffertaController {

    @Autowired
    private OffertaRepository offertaRepository;
    @Autowired
private PizzaRepository pizzaRepository; // aggiungi  repository delle pizze

@GetMapping("/create")
public String create(Model model) {
    model.addAttribute("offerta", new Offerta());
    model.addAttribute("pizze", pizzaRepository.findAll()); // per il <select> nel form
    return "offerte/create";
}


    @GetMapping
    public String index(Model model) {
        model.addAttribute("offerte", offertaRepository.findAll());
        return "offerte/index";

    }

    // parte relativa alla gestione delle offerte.. crea, cancella modifica

    @PostMapping
    public String store(@Valid @ModelAttribute("offerta") Offerta offertaForm, BindingResult bindingResult,
            Model model) {
        // In caso di errori allora mostrami questo
        if (bindingResult.hasErrors()) {
            model.addAttribute("offerta", offertaForm);
            model.addAttribute("pizza", offertaForm.getPizza());
            model.addAttribute("pizze", pizzaRepository.findAll());
            return "offerte/create";
        }
        // se non ci sono errori allora utilizza il metodo save per creare la mia
        // offerta e salvala nem mio db
        offertaRepository.save(offertaForm);
        return "redirect:/pizzas";

    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        offertaRepository.deleteById(id);
        return "redirect:/pizzas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("offerta", offertaRepository.findById(id).get());
        return "offerte/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Integer id, @Valid @ModelAttribute("offerta") Offerta offertaForm,
            BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("offerta", offertaForm);
            
            return "offerte/edit";
        }
        offertaRepository.save(offertaForm);
        return "redirect:/pizzas";
    }

}
