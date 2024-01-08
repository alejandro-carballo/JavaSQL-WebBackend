package com.example.accessingdatamysql.Repositories;

import com.example.accessingdatamysql.Classes.Author;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer> {
    Optional<Author> findByFirstnameAndLastname(String firstName, String lastName);


    Optional<Author> findAllByPublisher(String name);


    Optional<Author> findById(Integer id);

}

