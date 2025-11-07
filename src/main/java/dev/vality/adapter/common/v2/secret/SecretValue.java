package dev.vality.adapter.common.v2.secret;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
public class SecretValue {
    @ToString.Exclude
    private String value;
}
