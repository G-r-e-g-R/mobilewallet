package com.nttdata.mobilewallet.infraestructure.config;

import com.nttdata.mobilewallet.application.MobileWalletRepository;
import com.nttdata.mobilewallet.infraestructure.repository.MobileWalletCrudRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * SPRINGCONFIGURATION.
 * Define la configuración para MobileWalletRepository.
 */
@Configuration
public class SpringConfiguration {
    /**
     * Obtenemos el Crud Repository.
     * @return MobileWalletRepository
     */
    @Bean
    public MobileWalletRepository walletRepository(){
        return new MobileWalletCrudRepository();
    }
}
