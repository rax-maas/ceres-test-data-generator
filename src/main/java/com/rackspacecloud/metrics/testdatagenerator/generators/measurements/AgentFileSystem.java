package com.rackspacecloud.metrics.testdatagenerator.generators.measurements;

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
                Map.entry("filesystem.total", "KILOBYTES"),
                Map.entry("filesystem.free", "KILOBYTES"),
                Map.entry("filesystem.free_files", "free_files"),
                Map.entry("filesystem.avail", "KILOBYTES"),
                Map.entry("filesystem.files", "files"),
                Map.entry("filesystem.used", "KILOBYTES")
        );
    }
}
