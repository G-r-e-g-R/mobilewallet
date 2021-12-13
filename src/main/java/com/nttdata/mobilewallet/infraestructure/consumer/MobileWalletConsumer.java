package com.nttdata.mobilewallet.infraestructure.consumer;

import com.nttdata.mobilewallet.domain.OperationWallet;
import com.nttdata.mobilewallet.domain.Status;
import com.nttdata.mobilewallet.infraestructure.producer.MobileWalletProducer;
import com.nttdata.mobilewallet.infraestructure.repository.MobileWalletCrudRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
/**
 * MOBILEWALLETCONSUMER.
 * La clase recibe mensajes emitidos por el productor.
 *
 */
@Slf4j
@Service
public class MobileWalletConsumer {
    /**
     * Repositorio.
     */
    @Autowired
    private MobileWalletCrudRepository mobilWalletCrudRepository;
    /**
     * Productor.
     */
    @Autowired
    private MobileWalletProducer mobileWalletProducer;

    /**
     * Configuracion para la comunicacion.
     * @param operationWallet
     */
    @KafkaListener(
            topics = "${custom.kafka.topic-name}",
            groupId = "${custom.kafka.group-id}",
            containerFactory = "walletConcurrentKafkaListenerContainerFactory")

    /**
     * Consumidor.
     */
    public void consumer(OperationWallet operationWallet) {
        log.info("[consumer] Inicio:", operationWallet);
        this.validarBalance(operationWallet);
    }

    /**
     * Validamos los requisitos.
     */
    private void validarBalance(OperationWallet operationWallet) {

        operationWallet.setStatus(Status.RECHAZADO);
        mobilWalletCrudRepository
                .findByNumberPhone(operationWallet.getOriginNumberPhone())
                .filter(a ->
                                a.getBalance() >= operationWallet.getAmount()
                         )
                .map(b -> {b.setBalance(b.getBalance() - operationWallet.getAmount());
                            mobilWalletCrudRepository.save(b)
                                    .flatMap(c -> mobilWalletCrudRepository
                                            .findByNumberPhone(operationWallet.getDestinyNumberPhone()))
                                    .flatMap(d -> {
                                        d.setBalance(d.getBalance() + operationWallet.getAmount());
                                        return mobilWalletCrudRepository.save(d);
                                    });
                            operationWallet.setStatus(Status.EXITOSO);
                            this.mobileWalletProducer.producer(operationWallet);
                            return Mono.just(b);
                        });
    }

}
