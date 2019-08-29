package com.rackspacecloud.metrics.testdatagenerator.generators;

import com.rackspace.monplat.protocol.MonitoringSystem;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Account {
    private String accountType;
    private String accountName;
    private MonitoringSystem monitoringSystem;

    private List<Device> devices;

    public Account(
            final String accountType,
            final String accountName,
            final MonitoringSystem monitoringSystem,
            final int deviceCount) {
        this.accountType = accountType;
        this.accountName = accountName;
        this.monitoringSystem = monitoringSystem;
        devices = new ArrayList<>();

        for(int i = 0; i < deviceCount; i++) {
            addDevices(new Device("dummy-device-type", "id-" + i));
        }
    }

    private void addDevices(Device device) {
        devices.add(device);
    }
}
