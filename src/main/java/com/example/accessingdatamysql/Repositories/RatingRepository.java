package com.example.accessingdatamysql.Repositories;

import com.example.accessingdatamysql.Classes.Book;
import com.example.accessingdatamysql.Classes.Rating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface RatingRepository extends CrudRepository<Rating, Integer> {
    Rating findByName(String name);

    Rating findByBook(String book);

    Iterable<Rating> findByNameOrderByStar(String name);

    Iterable<Rating> findAllByBook(String book);

    Iterable<Rating> findAllByName(String name);
}

