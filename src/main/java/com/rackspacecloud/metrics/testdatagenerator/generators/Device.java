package com.rackspacecloud.metrics.testdatagenerator.generators;

import com.rackspacecloud.metrics.testdatagenerator.generators.measurements.AgentFileSystem;
import com.rackspacecloud.metrics.testdatagenerator.generators.measurements.Cpu;
import com.rackspacecloud.metrics.testdatagenerator.generators.measurements.Measurement;
import com.rackspacecloud.metrics.testdatagenerator.generators.measurements.Memory;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class Device {
    private String deviceType;
    private String deviceId;
    private List<Measurement> measurements;

    public Device(final String deviceType, final String deviceId) {
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.measurements = new ArrayList<>();
        measurements.addAll(Arrays.asList(new AgentFileSystem(), new Cpu(), new Memory()));
    }
}
