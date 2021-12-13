package com.nttdata.mobilewallet.domain;

import lombok.Data;
import java.time.LocalDateTime;
/**
 * CUSTOMERWALLET.
 * La clase contendrá  información del cliente para la billetera movil.
 *
 */
@Data
public class CustomerWallet {
    /**
     * Identificador del registro.
     */
    private String id;
    /**
     * Tipo de documento.
     */
    private DocumentType typeDocument;
    /**
     * Numero de documento.
     */
    private String numberDocument;
    /**
     * Numero de telefono.
     */
    private String numberPhone;
    /**
     * IMEI del telefono.
     */
    private String imeiPhone;
    /**
     * Correo electronico.
     */
    private String email;
    /**
     * Saldo.
     */
    private Double balance;
    /**
     * Fecha de creacion.
     */
    private LocalDateTime operationDate;
}
