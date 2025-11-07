package dev.vality.adapter.common.v2.secret;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HmacAlgorithms {
    HMAC_MD5("HmacMD5"),
    HMAC_SHA_1("HmacSHA1"),
    HMAC_SHA_256("HmacSHA256"),
    HMAC_SHA_512("HmacSHA512");

    private final String name;
}
