package com.rackspacecloud.metrics.testdatagenerator;

import com.rackspace.monplat.protocol.ExternalMetric;
import com.rackspacecloud.metrics.testdatagenerator.generators.MaasDataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class Sender {
    private static final Logger LOGGER = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private KafkaTemplate<String, ExternalMetric> kafkaTemplate;

    @Value("${topic}")
    private String topic;

    @Value("${number.of.times.to.send.data}")
    private int numberOfTimesToSendData;

    private int numberOfTimesAlreadySentData;

    private void send(ExternalMetric payload) {
        LOGGER.info("START: Sending payload [{}]", payload);
        kafkaTemplate.send(topic, payload);
        LOGGER.info("FINISH: Processing");
    }

    @Scheduled(cron = "*/5 * * * * *") // Run every 5 seconds
    public void sendBatchesOfMessages() {

        if(numberOfTimesAlreadySentData >= numberOfTimesToSendData) return;

        // We have following accounts and their types in the system
        Map<String, String> accountAndTheirTypes = new HashMap<>();
        accountAndTheirTypes.put("1667601", "CORE");
        accountAndTheirTypes.put("1234", "CORE");
        accountAndTheirTypes.put("abcd1234", "CORE");

        String timestamp = Instant.now().toString();

        int numberOfDevices = 5; // This is used to create deviceIDs by combining this with account

        /**
         * This loop assumes that every account has 5 (numberOfDevices) devices.
         */
        for(Map.Entry<String, String> entry : accountAndTheirTypes.entrySet()) {
            String account = entry.getKey();
            String accountType = entry.getValue();

            for (int i = 0; i < numberOfDevices; i++) {
                send(MaasDataGenerator.getValidMetric(
                        accountType, account, account + i, timestamp, true));
            }
        }

        numberOfTimesAlreadySentData++;
    }
}