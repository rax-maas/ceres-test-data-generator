package com.rackspacecloud.metrics.testdatagenerator.generators.measurements;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class Memory extends Measurement {
    public Memory() {
        super("mem",
                "mem" + "-" + "collection-label", "");
        setCollectionMetadata();
        addFields();
    }

    private void setCollectionMetadata() {
        Map<String, String> collectionMetadata = new HashMap<>();
        collectionMetadata.put("mem_maas_version", "1.7.7");
        collectionMetadata.put("mem_maas_deploy_date", "2018-10-04");
        collectionMetadata.put("mem_check_category", "host");
        collectionMetadata.put("product", "osa");
        collectionMetadata.put("osa_version", "14.2.4");
        collectionMetadata.put("mem_env_identifier", "as-c");
        super.addToCollectionMetadata(collectionMetadata);
    }

    private void addFields() {
        super.addFields(
                new Pair<>("used", "bytes"),
                new Pair<>("available", "bytes"),
                new Pair("total", "bytes")
        );
    }
}
