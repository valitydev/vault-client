package dev.vality.secret;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DigestAlgorithms {
    MD5("MD5"),
    SHA256("SHA-256");

    private final String name;
}
