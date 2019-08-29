package com.rackspacecloud.metrics.testdatagenerator.generators.measurements;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class Cpu extends Measurement {
    public Cpu() {
        super("cpu",
                "cpu" + "-" + "collection-label", "");
        setCollectionMetadata();
        addFields();
    }

    private void setCollectionMetadata() {
        Map<String, String> collectionMetadata = new HashMap<>();
        collectionMetadata.put("cpu_maas_version", "1.7.7");
        collectionMetadata.put("cpu_maas_deploy_date", "2018-10-04");
        collectionMetadata.put("cpu_check_category", "host");
        collectionMetadata.put("product", "osa");
        collectionMetadata.put("osa_version", "14.2.4");
        collectionMetadata.put("cpu_env_identifier", "as-c");
        super.addToCollectionMetadata(collectionMetadata);
    }

    private void addFields() {
        super.addFields(
                new Pair<>("percentage_user_usage", ""),
                new Pair<>("percentage_system_usage", ""),
                new Pair("percentage_idle", "")
        );
    }
}
