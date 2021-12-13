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

import java.util.HashMap;
import java.util.Map;
/**
 * PRODUCERCONFIGURATION.
 * La clase contendrá la información de configuración del productor.
 *
 */
@Slf4j
@Configuration
public class ProducerConfiguration {

    @Value("${custom.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Configuración de variables para la fabrica de productores.
     * @return
     */
    @Bean
    public
    Map<String, Object>
    producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        props.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        props.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        return props;
    }

    /**
     * Fabrica de productores.
     * @return ProducerFactory<String, OperationWallet>
     */
    @Bean
    public
    ProducerFactory<String, OperationWallet>
    producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    /**
     * Plantilla de Kafka para el productor.
     * @return
     */
    @Bean
    public
    KafkaTemplate<String, OperationWallet>
    operationWalletKafkaTemplate() {
        KafkaTemplate<String, OperationWallet>
                kafkaTemplate = new KafkaTemplate<>(producerFactory());
        kafkaTemplate
                .setProducerListener(
                        new ProducerListener<String, OperationWallet>() {
        /**
         * Metodo de ejecución exitosa.
          * @param producerRecord
         * @param recordMetadata
         */
            @Override
            public
            void
            onSuccess(ProducerRecord<String,
                    OperationWallet> producerRecord,
                      RecordMetadata recordMetadata) {
                log.info("Success to publish message: {}  offset:  {}",
                        producerRecord.value(),
                        recordMetadata.offset());
            }
            /**
             * Metodo de ejecución con Error.
             * @param producerRecord
             * @param recordMetadata
             */
            @Override
            public void
            onError(ProducerRecord<String,
                    OperationWallet> producerRecord,
                    RecordMetadata recordMetadata,
                    Exception exception) {
                log.warn("Error to publish message [{}] exception: {}",
                        producerRecord.value(),
                        exception.getMessage());
            }
        });
        return kafkaTemplate;
    }
}
