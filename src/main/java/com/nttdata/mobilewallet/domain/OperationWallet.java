package com.nttdata.mobilewallet.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * OPERATIONWALLET.
 * La clase contendrá  información de las operaciones en la billetera movil.
 *
 */
@Data
public class OperationWallet {
    /**
     * Identificador del registro.
     */
    private String id;
    /**
     * Numero de telefono origen.
     */
    private String originNumberPhone;
    /**
     * Numero de telefono destino.
     */
    private String destinyNumberPhone;
    /**
     * Estado de la operacion.
     */
    private Status status;
    /**
     * Monto de la operacion.
     */
    private Double amount;
    /**
     * Fecha de la operacion.
     */
    private LocalDateTime operationDate;
}
