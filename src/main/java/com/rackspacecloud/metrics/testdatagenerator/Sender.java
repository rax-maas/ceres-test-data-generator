package com.rackspacecloud.metrics.testdatagenerator;

import com.rackspace.monplat.protocol.ExternalMetric;
import com.rackspace.monplat.protocol.MonitoringSystem;
import com.rackspacecloud.metrics.testdatagenerator.generators.DataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.util.List;

public class Sender {
    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private KafkaTemplate<String, ExternalMetric> kafkaTemplate;

    @Value("${topic}")
    private String topic;

    @Value("${number.of.times.to.send.data}")
    private int numberOfTimesToSendData;

    private int numberOfTimesAlreadySentData;

    DataGenerator coreMaasDataGenerator;
    DataGenerator encoreMaasDataGenerator;
    DataGenerator uimDataGenerator;

    public Sender() {
        coreMaasDataGenerator = new DataGenerator(5, "CORE", MonitoringSystem.MAAS);
        encoreMaasDataGenerator = new DataGenerator(10, "ENCORE", MonitoringSystem.MAAS);
        uimDataGenerator = new DataGenerator(15, "RCN", MonitoringSystem.UIM);
    }

    private void send(ExternalMetric payload) {
        LOGGER.info("START: Sending payload [{}]", payload);
        kafkaTemplate.send(topic, payload);
        LOGGER.info("FINISH: Processing");
    }

    @Scheduled(cron = "*/1 * * * * *") // Run every 5 seconds
    public void sendBatchesOfMessages() {

        if(numberOfTimesAlreadySentData >= numberOfTimesToSendData) return;

        String timestamp = Instant.now().toString();
        List<ExternalMetric> metricsList = coreMaasDataGenerator.getValidMetric(timestamp, true);
        metricsList.addAll(encoreMaasDataGenerator.getValidMetric(timestamp, true));
        metricsList.addAll(uimDataGenerator.getValidMetric(timestamp, true));

        metricsList.forEach(metric -> send(metric));

        numberOfTimesAlreadySentData++;
    }
}