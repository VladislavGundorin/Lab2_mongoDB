package org.example.repository;

import org.example.entity.Auto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.ArrayList;
import java.util.List;

public interface AutoRepository extends MongoRepository<Auto, String> {
}
