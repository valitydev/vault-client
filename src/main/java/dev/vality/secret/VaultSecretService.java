package dev.vality.secret;

import dev.vality.secret.exception.*;
import lombok.RequiredArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.vault.VaultException;
import org.springframework.vault.core.VaultTemplate;
import org.springframework.vault.support.Versioned;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static dev.vality.secret.exception.SecretAlreadyModifyException.CAS_ERROR_MESSAGE;

@RequiredArgsConstructor
public class VaultSecretService implements SecretService {

    private final VaultTemplate vaultTemplate;

    @Override
    public Map<String, SecretValue> getSecrets(String serviceName, String path) throws SecretPathNotFoundException {
        var map = vaultTemplate.opsForVersionedKeyValue(serviceName).get(path);
        if (map == null || map.getData() == null) {
            throw new SecretPathNotFoundException(path);
        }
        return map.getData().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new SecretValue(e.getValue().toString())));
    }

    @Override
    public VersionedSecret getVersionSecrets(String serviceName, String path) throws SecretPathNotFoundException {
        var map = vaultTemplate.opsForVersionedKeyValue(serviceName).get(path);
        if (map == null || !map.hasData() || CollectionUtils.isEmpty(map.getData()) || isEmptyValues(map)) {
            throw new SecretsNotFoundException("Secrets doesn't exist or empty for path %s".formatted(path));
        }

        Map<String, SecretValue> secretes = map.getData().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> new SecretValue(e.getValue().toString())));
        return new VersionedSecret(secretes, map.getVersion().getVersion());
    }

    private boolean isEmptyValues(Versioned<Map<String, Object>> map) {
        return map.getData().values().stream()
                .noneMatch(o -> StringUtils.hasText(o.toString()));
    }

    @Override
    public SecretValue getSecret(String serviceName, SecretRef secretRef) throws SecretNotFoundException {
        String secret = getSecretString(serviceName, secretRef);
        return new SecretValue(secret);
    }

    @Override
    public String hmac(String serviceName, String data, SecretRef secretRef, HmacAlgorithms hmacAlgorithm)
            throws SecretNotFoundException, HexDecodeException {
        String hexSecret = getSecretString(serviceName, secretRef);
        return new HmacSigner().sign(data, hexSecret, secretRef, hmacAlgorithm);
    }

    @Override
    public String digest(String serviceName, String data, SecretRef secretRef, DigestAlgorithms algorithm)
            throws SecretNotFoundException {
        String secret = getSecretString(serviceName, secretRef);
        return new DigestSigner().sign(data, secret, algorithm);
    }

    @Override
    public void writeSecret(String serviceName, SecretObj secretObj) {
        vaultTemplate.opsForVersionedKeyValue(serviceName).put(secretObj.getPath(), secretObj.getValues());
    }

    @Override
    public Integer writeVersionSecret(String serviceName, SecretObj secretObj) {
        Versioned.Metadata metadata =
                vaultTemplate.opsForVersionedKeyValue(serviceName).put(secretObj.getPath(), secretObj.getValues());
        return metadata.getVersion().getVersion();
    }

    @Override
    public Integer writeWithCas(String serviceName, SecretObj secretObj, Integer version) {
        try {
            var versionedBody = Versioned.create(secretObj.getValues(), Versioned.Version.from(version));
            var metadata = vaultTemplate.opsForVersionedKeyValue(serviceName).put(secretObj.getPath(), versionedBody);
            return metadata.getVersion().getVersion();
        } catch (VaultException e) {
            if (isCasError(e)) {
                throw new SecretAlreadyModifyException(e);
            }
            throw e;
        }
    }

    private static boolean isCasError(VaultException e) {
        return Objects.nonNull(e.getMessage()) && e.getMessage().contains(CAS_ERROR_MESSAGE);
    }

    private String getSecretString(String serviceName, SecretRef secretRef) throws SecretNotFoundException {
        var map = vaultTemplate.opsForVersionedKeyValue(serviceName).get(secretRef.getPath());
        if (map == null || map.getData() == null || map.getData().get(secretRef.getKey()) == null) {
            throw new SecretNotFoundException(secretRef.toString());
        }
        return map.getData().get(secretRef.getKey()).toString();
    }
}
