package com.nttdata.mobilewallet.domain;

import lombok.Data;

/**
 * PROFILE.
 * La clase contendrá  información de los perfiles.
 *
 */
@Data
public class Profile {
    /**
     * Identificador del registro.
     */
    private String id;
    /**
     * Tipo de perfil.
     */
    private String type;
}
