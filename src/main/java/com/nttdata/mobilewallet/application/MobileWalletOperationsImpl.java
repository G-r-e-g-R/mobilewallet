package com.nttdata.mobilewallet.application;

import com.nttdata.mobilewallet.domain.CustomerWallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * MOBILEWALLETOPERATIONSIMPL.
 * Implementa las operaciones (CRUD) del cliente con monedero movil.
 */
@Slf4j
@Service
public class MobileWalletOperationsImpl
        implements MobileWalletOperations{
    @Autowired
    private MobileWalletRepository repository;

    @Override
    public
    Flux<CustomerWallet>
    findAll() {
        return repository.findAll();
    }

    @Override
    public
    Mono<CustomerWallet>
    findById(String id) {
        return repository.findById(id);
    }

    @Override
    public
    Mono<CustomerWallet>
    save(CustomerWallet customerWallet) {
        return repository.save(customerWallet);
    }

    @Override
    public
    Mono<CustomerWallet>
    update(String id, CustomerWallet customerWallet) {
        return repository.update(id, customerWallet);
    }

    @Override
    public
    Mono<Void>
    delete(String id) {
        return repository.delete(id);
    }

    @Override
    public
    Mono<CustomerWallet>
    findByNumberPhone(String numberPhone) {
        return repository.findByNumberPhone(numberPhone);
    }
}
