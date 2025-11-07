package dev.vality.secret;

import dev.vality.secret.exception.HexDecodeException;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.HmacUtils;

class HmacSigner {
    public String sign(String data, String hexSecret, SecretRef secretRef, HmacAlgorithms hmacAlgorithm)
            throws HexDecodeException {
        try {
            byte[] key = Hex.decodeHex(hexSecret);
            return new HmacUtils(hmacAlgorithm.getName(), key).hmacHex(data);
        } catch (DecoderException e) {
            throw new HexDecodeException(secretRef.toString());
        }
    }
}
