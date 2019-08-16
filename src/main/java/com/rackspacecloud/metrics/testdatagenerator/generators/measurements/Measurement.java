package com.rackspacecloud.metrics.testdatagenerator.generators.measurements;

import javafx.util.Pair;
import lombok.Data;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Data
public class Measurement {
    protected String collectionName;
    protected String collectionLabel;
    protected String collectionTarget;
    protected Map<String, String> collectionMetadata;
    // Each field is a pair of field name and it's unit
    protected List<Pair<String, String>> fields;

    protected Measurement(final String collectionName, final String collectionLabel, final String collectionTarget) {
        this.collectionLabel = collectionLabel;
        this.collectionName = collectionName;
        this.collectionTarget = collectionTarget;

        this.collectionMetadata = new HashMap<>();
        this.fields = new ArrayList<>();
    }

    protected void addToCollectionMetadata(Map<String, String> metadata) {
        collectionMetadata.putAll(metadata);
    }

    protected void addFields(Pair<String, String>... fieldsCollection) {
        fields.addAll(Arrays.asList(fieldsCollection));
    }

    protected static long getNextLongValue() {
        return ThreadLocalRandom.current().nextLong(1000L, 50_000L);
    }

    public Map<String, Long> getIValues() {
        Map<String, Long> iValues = new HashMap<>();
        fields.forEach(pair -> iValues.put(pair.getKey(), getNextLongValue()));
        return iValues;
    }

    public Map<String, String> getUnits() {
        Map<String, String> units = new HashMap<>();
        fields.forEach(pair -> units.put(pair.getKey(), pair.getValue()));

        return units;
    }
}
