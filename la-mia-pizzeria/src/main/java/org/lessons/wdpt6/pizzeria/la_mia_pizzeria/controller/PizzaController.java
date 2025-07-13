package org.lessons.wdpt6.pizzeria.la_mia_pizzeria.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.lessons.wdpt6.pizzeria.la_mia_pizzeria.model.Pizza;
import org.lessons.wdpt6.pizzeria.la_mia_pizzeria.model.Offerta;
import org.lessons.wdpt6.pizzeria.la_mia_pizzeria.repository.IngredienteRepository;
import org.lessons.wdpt6.pizzeria.la_mia_pizzeria.repository.OffertaRepository;
import org.lessons.wdpt6.pizzeria.la_mia_pizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private OffertaRepository offertaRepository;
    @Autowired
    private IngredienteRepository ingredienteRepository;

    @GetMapping
    public String index(Model model, @RequestParam(name = "Keyword", required = false) String keyword) {
        List<Pizza> pizzas;
        if (keyword != null && !keyword.isEmpty()) {
            pizzas = pizzaRepository.findByNameContainingIgnoreCase(keyword);
        } else {
            pizzas = pizzaRepository.findAll();
        }
        model.addAttribute("pizzas", pizzas);
        return "pizzas/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model) {
        model.addAttribute("pizza", pizzaRepository.findById(id).get());
        return "pizzas/show";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredienti", ingredienteRepository.findAll());
        return "pizzas/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredienti", ingredienteRepository.findAll());
            return "pizzas/create";
        }
        pizzaRepository.save(formPizza);
        return "redirect:/pizzas";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("pizza", pizzaRepository.findById(id).get());
        model.addAttribute("ingredienti", ingredienteRepository.findAll());

        return "pizzas/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza,
            BindingResult bindingResult, Model model) {
        model.addAttribute("ingredienti", ingredienteRepository.findAll());
        if (bindingResult.hasErrors()) {
            return "pizzas/edit";
        }
        pizzaRepository.save(formPizza);
        return "redirect:/pizzas";
    }

    // qui dico che se devo cancellare il libro, per ogni offerta presente nella ,
    // cancellami anche l'offerta collegata alla pizza
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id, Model model) {
        Pizza pizzaToDelete = pizzaRepository.findById(id).get();
        for (Offerta offerta : pizzaToDelete.getOfferte()) {
            offertaRepository.delete(offerta);
        }
        pizzaRepository.delete(pizzaToDelete);

        return "redirect:/pizzas";
    }

    @GetMapping("/{id}/offerta")
    public String offerta(@PathVariable("id") Integer id, Model model) {
        Optional<Pizza> pizzaOptional = pizzaRepository.findById(id);

        if (pizzaOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Non ci sono pizze con l'id " + id);
        }
        model.addAttribute("pizza", pizzaOptional.get());
        Offerta offerta = new Offerta();
        offerta.setPizza(pizzaOptional.get());
        offerta.setInizioOfferta(LocalDate.now());
        model.addAttribute("offerta", offerta);

        return "offerte/create"; // template per creare l'offerta
    }

}
