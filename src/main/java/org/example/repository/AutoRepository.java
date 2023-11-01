package org.example.repository;

import org.example.entity.Auto;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutoRepository extends MongoRepository<Auto, String> {

}
