package com.nttdata.mobilewallet.infraestructure.producer;

import com.nttdata.mobilewallet.domain.OperationWallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * MOBILEWALLETPRODUCER.
 * La clase envia mensajes usando el KafkaTemplate.
 *
 */
@Slf4j
@Service
public class MobileWalletProducer {
    /**
     * Nombre del topico.
     */
    @Value("${custom.kafka.topic-name-mobile-wallet}")
    private String topicName;
    /**
     * Template.
     */
    @Autowired
    private KafkaTemplate<String, OperationWallet> operationWalletKafkaTemplate;

    /**
     * Productor.
     * @param operationWallet elemento enviado.
     */
    public void producer(OperationWallet operationWallet) {
        operationWalletKafkaTemplate.send(topicName, operationWallet);
    }
}
