package com.example.accessingdatamysql.Repositories;

import com.example.accessingdatamysql.Classes.Admin;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface AdminRepository extends CrudRepository<Admin, Integer> {
    Optional<Admin> findByName(String name);
}



