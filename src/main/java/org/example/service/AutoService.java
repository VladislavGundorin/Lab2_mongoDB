package org.example.service;

import org.example.entity.Auto;
import org.example.repository.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.query.Criteria;


import java.util.List;
import java.util.Map;

@Service
public class AutoService {
    private final AutoRepository autoRepository;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public AutoService(AutoRepository autoRepository, MongoTemplate mongoTemplate) {
        this.autoRepository = autoRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Auto createAuto(Auto auto) {
        return autoRepository.save(auto);
    }

    public Auto getAutoById(String id) {
        return autoRepository.findById(id).orElse(null);
    }

    public Auto updateAuto(String id, Auto auto) {
        if (autoRepository.existsById(id)) {
            auto.setId(id);
            return autoRepository.save(auto);
        }
        return null;
    }

    public void deleteAuto(String id) {
        autoRepository.deleteById(id);
    }

    public List<Auto> getAllAutos() {
        return autoRepository.findAll();
    }

    public List<Map> getYearWithMostCarsByCarClass() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("yearOfManufacture").is(2023)),
                Aggregation.group("carClass.nameCategory", "yearOfManufacture")
                        .addToSet("index").as("uniqueIndices"),
                Aggregation.project("_id")
                        .andExpression("size('$uniqueIndices')").as("count"),
                Aggregation.sort(Sort.Direction.DESC, "count"),
                Aggregation.project("count").and("_id").as("carClass")
        );

        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "autos", Map.class);
        return results.getMappedResults();
    }
}
