package com.rackspacecloud.metrics.testdatagenerator.generators;

import com.rackspace.monplat.protocol.AccountType;
import com.rackspace.monplat.protocol.ExternalMetric;
import com.rackspace.monplat.protocol.MonitoringSystem;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class MaasDataGenerator {

    public static ExternalMetric getValidMetric(
            String accountType, String account,
            String deviceId, String timestamp, boolean wantIValues){

        ExternalMetric metric = new ExternalMetric();

        metric.setAccount(account);
        metric.setAccountType(Enum.valueOf(AccountType.class, accountType));
        metric.setMonitoringSystem(MonitoringSystem.MAAS);
        metric.setCollectionName("agent.filesystem");
        metric.setCollectionLabel("dummy-collection-label");
        metric.setCollectionTarget("");

        setDevice(deviceId, metric);
        setSystemMetadata(deviceId, metric);
        setCollectionMetadata(metric);

        Map<String, Long> iValues = new HashMap<>();
        if(wantIValues) iValues = getIValues();
        metric.setIvalues(iValues);

        metric.setFvalues(new HashMap<>());
        metric.setSvalues(new HashMap<>());

        setUnits(metric);

        metric.setTimestamp(timestamp);

        return metric;
    }

    private static void setDevice(String deviceId, ExternalMetric metric) {
        metric.setDevice(deviceId);
        metric.setDeviceLabel("dummy-device-label-" + deviceId);
        metric.setDeviceMetadata(new HashMap<>());
    }

    private static void setSystemMetadata(String deviceId, ExternalMetric metric) {
        Map<String, String> systemMetadata = new HashMap<>();
        systemMetadata.put("accountId", "dummy-account-id-" + deviceId);
        systemMetadata.put("entityId", "dummy-entity-id-" + deviceId);
        systemMetadata.put("checkId", "dummy-check-id-" + deviceId);
        systemMetadata.put("monitoringZone", "");
        metric.setSystemMetadata(systemMetadata);
    }

    private static void setCollectionMetadata(ExternalMetric metric) {
        Map<String, String> collectionMetadata = new HashMap<>();
        collectionMetadata.put("rpc_maas_version", "1.7.7");
        collectionMetadata.put("rpc_maas_deploy_date", "2018-10-04");
        collectionMetadata.put("rpc_check_category", "host");
        collectionMetadata.put("product", "osa");
        collectionMetadata.put("osa_version", "14.2.4");
        collectionMetadata.put("rpc_env_identifier", "as-c");
        metric.setCollectionMetadata(collectionMetadata);
    }

    private static Map<String, Long> getIValues() {
        Map<String, Long> iValues = new HashMap<>();
        iValues.put("filesystem.total", getNextLongValue());
        iValues.put("filesystem.free", getNextLongValue());
        iValues.put("filesystem.free_files", getNextLongValue());
        iValues.put("filesystem.avail", getNextLongValue());
        iValues.put("filesystem.files", getNextLongValue());
        iValues.put("filesystem.used", getNextLongValue());
        return iValues;
    }

    private static void setUnits(ExternalMetric metric) {
        Map<String, String> units = new HashMap<>();
        units.put("filesystem.free_files", "free_files");
        units.put("filesystem.files", "files");
        units.put("filesystem.total", "KILOBYTES");
        units.put("filesystem.free", "KILOBYTES");
        units.put("filesystem.avail", "KILOBYTES");
        units.put("filesystem.used", "KILOBYTES");
        metric.setUnits(units);
    }

    private static long getNextLongValue() {
        return ThreadLocalRandom.current().nextLong(1000L, 50_000L);
    }
}
