package com.rackspacecloud.metrics.testdatagenerator.config;

import com.rackspace.monplat.protocol.ExternalMetric;
import com.rackspacecloud.metrics.testdatagenerator.Sender;
import com.rackspacecloud.metrics.testdatagenerator.serializers.AvroSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableKafka
public class ProducerConfiguration {
    private Map<String, Object> producerConfig() {
        Map<String, Object> properties = new HashMap<>();

        List<String> servers = new ArrayList<>();
        servers.add("localhost:9092");

        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, AvroSerializer.class);
//        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 100);

        return properties;
    }

    @Bean
    public ProducerFactory<String, ExternalMetric> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, ExternalMetric> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public Sender sender(){
        return new Sender();
    }

//    @Bean
//    public MaasDataGenerator maasDataGenerator() {
//        return new MaasDataGenerator(10, "CORE");
//    }
}
