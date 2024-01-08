package com.example.accessingdatamysql.Repositories;

import com.example.accessingdatamysql.Classes.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface  CommentRepository extends CrudRepository<Comment, Integer> {
    Comment findByName(String name);

    Iterable<Comment> findAllByName(String name);
}

