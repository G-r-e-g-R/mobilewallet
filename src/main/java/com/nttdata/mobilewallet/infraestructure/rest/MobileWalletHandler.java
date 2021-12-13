package com.nttdata.mobilewallet.infraestructure.rest;

import com.nttdata.mobilewallet.application.MobileWalletOperations;
import com.nttdata.mobilewallet.domain.CustomerWallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
/**
 * MOBILEWALLETHANDLER.
 * Define los metodos de las operaciones del cliente de monedero movil.
 */
@Slf4j
@Component
public class MobileWalletHandler {
    /**
     * Operaciones.
     */
    @Autowired
    private MobileWalletOperations mobileWalletOperations;
    /**
     * Validador.
     */
    @Autowired
    private Validator validator;

    /**
     * Obtenemos todos los clientes con monedero movil.
     * @param serverRequest request.
     * @return Mono<ServerResponse>.
     */
    public Mono<ServerResponse> getall(ServerRequest serverRequest) {
        log.info("[getall] Inicio");
        return ServerResponse.ok().contentType(APPLICATION_JSON)
                .body(mobileWalletOperations.findAll(), CustomerWallet.class)
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    /**
     * Obtenemos un cliente con monedero movil.
     * @param serverRequest request.
     * @return Mono<ServerResponse>.
     */
    public Mono<ServerResponse> getOne(ServerRequest serverRequest) {
        log.info("[getOne] Inicio");
        return mobileWalletOperations.findById(serverRequest.pathVariable("id"))
                .flatMap(wallet -> ServerResponse
                        .ok().contentType(APPLICATION_JSON)
                        .body(fromValue(wallet))
                        .switchIfEmpty(ServerResponse.notFound().build())
                );
    }

    /**
     * Registramos un cliente con monedero movil.
     * @param serverRequest request.
     * @return Mono<ServerResponse>.
     */
    public Mono<ServerResponse> save(ServerRequest serverRequest) {
        log.info("[save] Inicio");
        Mono<CustomerWallet> walletMono = serverRequest.bodyToMono(CustomerWallet.class);

        return walletMono.flatMap(wallet -> {
            Errors errors = new BeanPropertyBindingResult(wallet, CustomerWallet.class.getName());
            validator.validate(wallet, errors);

            if (errors.hasErrors()) {
                return Flux.fromIterable(errors.getFieldErrors())
                        .map(fieldError -> "El campo " + fieldError.getField() + " " + fieldError.getDefaultMessage())
                        .collectList()
                        .flatMap(listString -> ServerResponse.badRequest().body(fromValue(listString)));
            } else {
                return mobileWalletOperations.save(wallet)
                        .flatMap(walletdb -> ServerResponse
                                .created(URI.create("/api/wallet/".concat(walletdb.getId())))
                                .contentType(APPLICATION_JSON)
                                .body(fromValue(walletdb)));
            }
        });

    }

    /**
     * Actualizamos un cliente con monedero movil.
     * @param serverRequest request.
     * @return Mono<ServerResponse>.
     */
    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        log.info("[update] Inicio");
        Mono<CustomerWallet> walletMono = serverRequest.bodyToMono(CustomerWallet.class);
        String id = serverRequest.pathVariable("id");
        return walletMono.flatMap(wallet -> mobileWalletOperations.update(id, wallet))
                .flatMap(walletCreated -> ServerResponse.created(URI.create("/api/wallet/".concat(walletCreated.getId())))
                        .contentType(APPLICATION_JSON)
                        .body(fromValue(walletCreated))
                ).switchIfEmpty(ServerResponse.notFound().build());
    }

    /**
     * Eliminamos un cliente con monedero movil.
     * @param serverRequest request.
     * @return Mono<ServerResponse>
     */
    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        log.info("[update] delete");
        String id = serverRequest.pathVariable("id");
        return mobileWalletOperations.findById(id)
                .flatMap(wallet -> mobileWalletOperations.delete(id).then(ServerResponse.noContent().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
