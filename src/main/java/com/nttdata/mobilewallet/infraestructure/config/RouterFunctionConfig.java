package com.nttdata.mobilewallet.infraestructure.config;

import com.nttdata.mobilewallet.infraestructure.rest.MobileWalletHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
/**
 * ROUTERFUNCTIONCONFIG.
 * Define la configuración para las llamadas.
 */
public class RouterFunctionConfig {
    String uri = "api/wallet";

    /**
     * Ruteador para las llamadas.
     * @param mobileWalletHandler Contontrol de llamadas.
     * @return outerFunction<ServerResponse>.
     */
    @Bean
    public RouterFunction<ServerResponse> routes(MobileWalletHandler mobileWalletHandler) {
        return route(GET(uri), mobileWalletHandler::getall)
                .andRoute(GET(uri), mobileWalletHandler::getall)
                .andRoute(GET(uri.concat("/{id}")), mobileWalletHandler::getOne)
                .andRoute(POST(uri), mobileWalletHandler::save)
                .andRoute(PUT(uri.concat("/{id}")), mobileWalletHandler::update)
                .andRoute(DELETE(uri.concat("/{id}")), mobileWalletHandler::delete);


    }
}
