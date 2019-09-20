package com.rackspacecloud.metrics.testdatagenerator.serializers;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.Encoder;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@Slf4j
public class AvroSerializer<T extends SpecificRecordBase> implements Serializer<T> {

    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String topicName, T data) {
        if(data == null) return null;
        log.debug("Data is [{}]", data);

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Schema schema = data.getSchema();
            Encoder encoder = EncoderFactory.get().jsonEncoder(schema, outputStream);
            DatumWriter<GenericRecord> datumWriter = new SpecificDatumWriter<>(schema);

            datumWriter.write(data, encoder);
            encoder.flush();
            outputStream.close();

            byte[] result = outputStream.toByteArray();

            return result;
        }
        catch (Exception e){
            String errorMessage = String.format("Serialization failed for topic [%s] with exception message: [%s]",
                    topicName, e.getMessage());
            log.error("{} Data in question is [{}]", errorMessage, data);
            throw new SerializationException(errorMessage, e);
        }
    }

    @Override
    public void close() {

    }
}
