package org.lessons.wdpt6.pizzeria.la_mia_pizzeria.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "offerte")
public class Offerta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Specifica la data dell inizio dell offerta speciale.")
    @FutureOrPresent(message = "La data deve essere odierna o futura")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate inizioOfferta;

    @NotNull(message = "Specifica la data di fine offerta speciale.")
    @FutureOrPresent(message = "La data deve essere odierna o futura")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fineOfferta;

    @NotBlank(message = "Il titolo non può essere vuoto, nullo o con soli spazi.")
    private String titolo;

    // Relazione molti a uno con Pizza
    @ManyToOne
    @NotNull(message = "Seleziona una pizza")
    @JoinColumn(name = "pizzas_id", nullable = false)
    @JsonBackReference
    private Pizza pizza;

    // Getters e Setters
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getInizioOfferta() {
        return this.inizioOfferta;
    }

    public void setInizioOfferta(LocalDate inizioOfferta) {
        this.inizioOfferta = inizioOfferta;
    }

    public LocalDate getFineOfferta() {
        return this.fineOfferta;
    }

    public void setFineOfferta(LocalDate fineOfferta) {
        this.fineOfferta = fineOfferta;
    }

    public String getTitolo() {
        return this.titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public Pizza getPizza() {
        return this.pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }

}
