package com.rackspacecloud.metrics.testdatagenerator.generators;

import com.rackspace.monplat.protocol.AccountType;
import com.rackspace.monplat.protocol.ExternalMetric;
import com.rackspace.monplat.protocol.MonitoringSystem;

import java.util.*;

public class DataGenerator {
    private Account[] accounts;

    public DataGenerator(int numberOfAccounts, String accountType, MonitoringSystem monitoringSystem) {
        if(numberOfAccounts < 1) throw new IllegalArgumentException("You should have at least one account.");

        accounts = new Account[numberOfAccounts];

        for(int i = 0; i < numberOfAccounts; i++) {
            accounts[i] = new Account(
                    accountType,
                    monitoringSystem.name() + "-account-name-" + i,
                    monitoringSystem,
                    5
            );
        }
    }

    public List<ExternalMetric> getValidMetric(String timestamp, boolean wantIValues){
        List<ExternalMetric> metricsList = new ArrayList<>();

        Arrays.stream(accounts).forEach( account -> {
            account.getDevices().forEach( device -> {
                device.getMeasurements().forEach(measurement -> {
                    ExternalMetric metric = new ExternalMetric();
                    metric.setAccount(account.getAccountName());
                    metric.setAccountType(Enum.valueOf(AccountType.class, account.getAccountType()));
                    metric.setMonitoringSystem(account.getMonitoringSystem());
                    metric.setCollectionName(measurement.getCollectionName());
                    metric.setCollectionLabel(measurement.getCollectionLabel());
                    metric.setCollectionTarget(measurement.getCollectionTarget());

                    metric.setDevice(device.getDeviceId());
                    metric.setDeviceLabel("dummy-device-label-" + device.getDeviceId());
                    metric.setDeviceMetadata(new HashMap<>());
                    setSystemMetadata(device.getDeviceId(), metric);
                    metric.setCollectionMetadata(measurement.getCollectionMetadata());

                    Map<String, Long> iValues = new HashMap<>();
                    if(wantIValues) iValues = measurement.getIValues();
                    metric.setIvalues(iValues);

                    metric.setUnits(measurement.getUnits());

                    metric.setFvalues(new HashMap<>());
                    metric.setSvalues(new HashMap<>());

                    metric.setTimestamp(timestamp);

                    metricsList.add(metric);
                });
            });
        });

        return metricsList;
    }

    private static void setSystemMetadata(String deviceId, ExternalMetric metric) {
        Map<String, String> systemMetadata = new HashMap<>();
        systemMetadata.put("accountId", "dummy-account-id-" + deviceId);
        systemMetadata.put("entityId", "dummy-entity-id-" + deviceId);
        systemMetadata.put("checkId", "dummy-check-id-" + deviceId);
        systemMetadata.put("monitoringZone", "");
        metric.setSystemMetadata(systemMetadata);
    }
}
