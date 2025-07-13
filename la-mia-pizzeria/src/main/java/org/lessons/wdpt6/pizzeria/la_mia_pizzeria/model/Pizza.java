package org.lessons.wdpt6.pizzeria.la_mia_pizzeria.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "pizzas")
public class Pizza implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @NotBlank(message = "Name must not be null, blank or empty")
    private String name;

    @Lob
    @NotBlank(message = "Description must not be null, blank or empty")
    private String description;

    @Lob
    @NotBlank(message = "Image URL must not be null, blank or empty")
    private String imageUrl;

    @Min(value = 0, message = "The price must be greater than zero")
    private float price;

  //relazione
    @OneToMany(mappedBy = "pizza")
    private List<Offerta> offerte;

    public Pizza() {
    }


    @ManyToMany()
    @JoinTable(
        name = "ingrediente_pizza",
        joinColumns = @JoinColumn(name = "pizza_id"),
        inverseJoinColumns = @JoinColumn(name = "ingrediente_id"))
        private Set<Ingrediente> ingredienti;

    // Getters e Setters



    public Set<Ingrediente> getIngredienti() {
        return ingredienti;
    }

    public void setIngredienti(Set<Ingrediente> ingredienti) {
        this.ingredienti = ingredienti;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imageUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imageUrl = imgUrl;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<Offerta> getOfferte() {
        return offerte;
    }

    public void setOfferte(List<Offerta> offerte) {
        this.offerte = offerte;
    }
}
