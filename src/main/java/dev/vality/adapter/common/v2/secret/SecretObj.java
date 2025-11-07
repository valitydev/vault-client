package dev.vality.adapter.common.v2.secret;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.Map;

/**
 * Объект с секретами для сохранения в vault
 * path - путь, по которому в vault будут храниться секреты, передданые в объекте. Хранится в options платежа.
 * value - значение ключа
 * Например,
 * SecretObj{
 * 'tinkoff-merchant-882347345',
 * {'secret-password','PASSWORD'}
 * }
 */

@Data
@ToString
@AllArgsConstructor
public class SecretObj {
    private String path;
    private Map<String, String> values;
}
