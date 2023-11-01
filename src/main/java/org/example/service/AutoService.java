package org.example.service;

import org.example.entity.Auto;
import org.example.repository.AutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    private boolean hasEmptyField(Auto auto) {
        return auto.getType() == null ||
                auto.getCarClass() == null || auto.getCarClass().getNameCategory() == null || auto.getCarClass().getNameCategory().isEmpty() ||
                auto.getConcern() == null || auto.getConcern().isEmpty() ||
                auto.getMarka() == null || auto.getMarka().isEmpty() ||
                auto.getYearOfManufacture() == 0 ||
                auto.getEquipment() == null || auto.getEquipment().getPower() == null || auto.getEquipment().getPower().isEmpty() ||
                auto.getEquipment() == null || auto.getEquipment().getMaterialSalona() == null || auto.getEquipment().getMaterialSalona().isEmpty() ||
                auto.getEquipment() == null || auto.getEquipment().getTransmission() == null || auto.getEquipment().getTransmission().isEmpty() ||
                auto.getEngine() == null || auto.getEngine().getType() == null || auto.getEngine().getType().isEmpty() ||
                auto.getEngine() == null || auto.getEngine().getEngineFuel() == null || auto.getEngine().getEngineFuel().isEmpty() ||
                auto.getIndex() == 0;
    }

    public void deleteIfAnyFieldIsEmpty(String autoId) {
        Auto auto = autoRepository.findById(autoId).orElse(null);
        if (auto != null && hasEmptyField(auto)) {
            autoRepository.deleteById(autoId);
        }
    }
    public int countAutosByType(String type) {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("type").is(type)),
                Aggregation.group().count().as("count")
        );

        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "autos", Map.class);
        List<Map> mappedResults = results.getMappedResults();
        if (!mappedResults.isEmpty()) {
            Map result = mappedResults.get(0);
            Object countObject = result.get("count");
            return countObject != null ? (Integer) countObject : 0;
        }
        return 0;
    }



}