package dev.vality.secret;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * Объект с секретами
 * secretes - карта с ключами и соответсвующими им занчениями секретов.
 * version - версия хранилища секретов
 * Например,
 * VersionedSecret{
 * {'secret-password','PASSWORD'},
 * 42
 * }
 */

@Data
@ToString
@AllArgsConstructor
public class VersionedSecret {

    private Map<String, SecretValue> secretes;
    private Integer version;
}
