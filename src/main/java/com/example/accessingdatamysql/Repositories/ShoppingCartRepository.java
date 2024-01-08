package com.example.accessingdatamysql.Repositories;

import com.example.accessingdatamysql.Classes.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface  ShoppingCartRepository extends CrudRepository<ShoppingCart, Integer> {
    Optional<ShoppingCart> findByUser(String name);

}