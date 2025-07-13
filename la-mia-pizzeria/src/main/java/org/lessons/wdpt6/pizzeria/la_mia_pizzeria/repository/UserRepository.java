package org.lessons.wdpt6.pizzeria.la_mia_pizzeria.repository;

import java.util.Optional;

import org.lessons.wdpt6.pizzeria.la_mia_pizzeria.model.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User>  findByUsername(String username);//dato un username, dammi tutti quelli presenti. solitamnente è 1 solo.. univoco, ma mai dire mai

}
