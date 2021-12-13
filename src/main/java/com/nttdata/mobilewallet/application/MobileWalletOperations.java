package com.nttdata.mobilewallet.application;

import com.nttdata.mobilewallet.domain.CustomerWallet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
/**
 * MOBILEWALLETOPERATIONS.
 * Define las operaciones (CRUD) del cliente con monedero movil.
 */
public interface MobileWalletOperations {

    /**
     * Busqueda de todos los Clientes con monedero movil.
     * @return
     */
    Flux<CustomerWallet>
    findAll();

    /**
     * Busqueda por Id de un Cliente con monedero movil.
     * @param id
     * @return
     */
    Mono<CustomerWallet>
    findById(String id);

    /**
     * Registro de un un Cliente con monedero movil.
     * @param customerWallet
     * @return
     */
    Mono<CustomerWallet>
    save(CustomerWallet customerWallet);

    /**
     * Actualización de un Cliente con monedero movil.
     * @param id
     * @param customerWallet
     * @return
     */
    Mono<CustomerWallet>
    update(String id, CustomerWallet customerWallet);

    /**
     * Eliminación de un Cliente con monedero movil.
     * @param id
     * @return
     */
    Mono<Void>
    delete(String id);

    /**
     * Busqueda de un Cliente con monedero movil por numero de telefono.
     * @param numberPhone
     * @return
     */
    Mono<CustomerWallet>
    findByNumberPhone(String numberPhone);
}
