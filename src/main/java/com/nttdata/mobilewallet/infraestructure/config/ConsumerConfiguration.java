package com.nttdata.mobilewallet.infraestructure.config;

import com.nttdata.mobilewallet.domain.OperationWallet;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * CONSUMERCONFIGURATION.
 * La clase contendrá la información de configuración del consumidor.
 *
 */
@Slf4j
@Configuration
public class ConsumerConfiguration {

    @Value("${custom.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ProducerFactory<String, OperationWallet> producerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory(config);
    }

    @Bean
    public
    KafkaTemplate<String, OperationWallet>
    operationWalletKafkaTemplate() {
        KafkaTemplate<String, OperationWallet> kafkaTemplate
                = new KafkaTemplate<>(producerFactory());

        kafkaTemplate.setProducerListener(new ProducerListener<String, OperationWallet>() {
            @Override
            public void onSuccess(ProducerRecord<String, OperationWallet> producerRecord, RecordMetadata recordMetadata) {
                log.info("[operationWalletKafkaTemplate] Exitoso:", producerRecord.value(),
                        recordMetadata.offset());
            }

            @Override
            public void onError(ProducerRecord<String, OperationWallet> producerRecord, RecordMetadata recordMetadata,
                                Exception exception) {
                log.warn("[operationWalletKafkaTemplate] Error:", producerRecord.value(), exception.getMessage());
            }
        });
        return kafkaTemplate;
    }
}
