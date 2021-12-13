package com.nttdata.mobilewallet.infraestructure.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.mobilewallet.application.MobileWalletRepository;
import com.nttdata.mobilewallet.domain.CustomerWallet;
import com.nttdata.mobilewallet.infraestructure.model.dao.CustomerWalletDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * MOBILEWALLETCRUDREPOSITORY.
 * Define las operaciones (CRUD) del cliente con monedero movil.
 */
@Slf4j
@Component
public class MobileWalletCrudRepository
        implements MobileWalletRepository {
    /**
     * Objeto de lectura y escritura de JSON.
     */
    @Autowired
    private ObjectMapper objectMapper;
    /**
     * Repositorio CRUD.
     */
    @Autowired
    private IMobileWalletCrudRepository repository;

    /**
     * Busqueda de todos los clientes con monedero movil.
     * @return Flux<CustomerWallet>.
     */
    @Override
    public
    Flux<CustomerWallet>
    findAll() {
        return repository.findAll().map(this::toCustomerWallet);
    }

    /**
     * Busqueda de un cliente con monedero movil por Id.
     * @param id codigo del cliente con monedero movil.
     * @return Mono<CustomerWallet>.
     */
    @Override
    public
    Mono<CustomerWallet>
    findById(String id) {
        return repository.findById(id).map(this::toCustomerWallet);
    }

    /**
     * Registramos un cliente con monedero movil.
     * @param customerWallet cleinte.
     * @return Mono<CustomerWallet>.
     */
    @Override
    public
    Mono<CustomerWallet>
    save(CustomerWallet customerWallet) {
        if (customerWallet.getOperationDate() == null)
            customerWallet.setOperationDate(LocalDateTime.now());
        return repository.save(this.toCustomerWalletDao(customerWallet)).map(this::toCustomerWallet);
    }

    /**
     * Actualizamos un cliente con monedero movil.
     * @param id codigo del cliente con monedero movil.
     * @param customerWallet cliente.
     * @return Mono<CustomerWallet>
     */
    @Override
    public
    Mono<CustomerWallet>
    update(String id, CustomerWallet customerWallet) {
        return repository.findById(id)
                .switchIfEmpty(Mono.empty())
                .flatMap(customerWalletDao -> {
                    customerWallet.setId(customerWalletDao.getId());
                    customerWallet.setOperationDate(customerWalletDao.getOperationDate());
                    return repository.save(this.toCustomerWalletDao(customerWallet)).map(this::toCustomerWallet);
                });
    }

    /**
     * Eliminamos un cliente con monedero movil.
     * @param id codigo del cliente con monedero movil.
     * @return Mono<Void>.
     */
    @Override
    public
    Mono<Void>
    delete(String id) {
        return repository.deleteById(id);
    }

    /**
     * Busqueda de un cliente con monedero movil por numero de telefono.
     * @param numberPhone numero de telefono.
     * @return  Mono<CustomerWallet>.
     */
    @Override
    public
    Mono<CustomerWallet>
    findByNumberPhone(String numberPhone) {
        return repository.findByNumberPhone(numberPhone);
    }

    /**
     * Convertimos CustomerWalletDao a CustomerWallet
     * @param customerWalletDao cliente Dao con monedero movil.
     * @return CustomerWallet
     */
    public
    CustomerWallet
    toCustomerWallet(CustomerWalletDao customerWalletDao) {
        return objectMapper.convertValue(customerWalletDao, CustomerWallet.class);
    }

    /**
     * Convertimos CustomerWallet a CustomerWalletDao
     * @param customerWallet cliente con monedero movil.
     * @return CustomerWalletDao
     */
    public
    CustomerWalletDao
    toCustomerWalletDao(CustomerWallet customerWallet) {
        return objectMapper.convertValue(customerWallet, CustomerWalletDao.class);
    }
}
