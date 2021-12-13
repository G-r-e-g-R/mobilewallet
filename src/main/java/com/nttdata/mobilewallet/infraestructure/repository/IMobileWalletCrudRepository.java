package com.nttdata.mobilewallet.infraestructure.repository;

import com.nttdata.mobilewallet.domain.CustomerWallet;
import com.nttdata.mobilewallet.infraestructure.model.dao.CustomerWalletDao;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * IMOBILEWALLETCRUDREPOSITORY.
 * Define las operaciones (CRUD) del Cliente (Customer) el
 * cual extiende del Reactive CRUD.
 */
public interface IMobileWalletCrudRepository
        extends ReactiveCrudRepository<CustomerWalletDao, String> {
    /**
     * Busqueda de un Cliente por numero de telefono.
     * @param numberPhone numero de telefono.
     * @return Mono<CustomerWallet>.
     */
    Mono<CustomerWallet> findByNumberPhone(String numberPhone);
}
