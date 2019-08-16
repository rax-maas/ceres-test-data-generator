package com.rackspacecloud.metrics.testdatagenerator.generators.measurements;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class AgentFileSystem extends Measurement {
    public AgentFileSystem() {
        super("agent.filesystem",
                "agent.filesystem" + "-" + "collection-label", "");
        setCollectionMetadata();
        addFields();
    }

    private void setCollectionMetadata() {
        Map<String, String> collectionMetadata = new HashMap<>();
        collectionMetadata.put("agent_filesystem_maas_version", "1.7.7");
        collectionMetadata.put("agent_filesystem_maas_deploy_date", "2018-10-04");
        collectionMetadata.put("agent_filesystem_check_category", "host");
        collectionMetadata.put("product", "osa");
        collectionMetadata.put("osa_version", "14.2.4");
        collectionMetadata.put("agent_filesystem_env_identifier", "as-c");
        super.addToCollectionMetadata(collectionMetadata);
    }

    private void addFields() {
        super.addFields(
                new Pair<>("filesystem.total", "KILOBYTES"),
                new Pair<>("filesystem.free", "KILOBYTES"),
                new Pair("filesystem.free_files", "free_files"),
                new Pair<>("filesystem.avail", "KILOBYTES"),
                new Pair<>("filesystem.files", "files"),
                new Pair<>("filesystem.used", "KILOBYTES")
        );
    }
}
