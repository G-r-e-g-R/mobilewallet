package com.nttdata.mobilewallet.infraestructure.model.dao;

import com.nttdata.mobilewallet.domain.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document("customerwallet")
public class CustomerWalletDao {
    /**
     * Identificador del registro.
     */
    @Id
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
