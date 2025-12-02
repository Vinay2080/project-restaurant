package org.miniproject.restuarant.config.security;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Service
public class KeyUtils {

    private KeyUtils() {

    }

    public static PrivateKey loadPrivateKey(final String pemPath) throws Exception {
        final String key = readKeyFromResources(pemPath)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

//        This line decodes that Base64 string back into its original binary form
        final byte[] decoded = Base64.getDecoder().decode(key);

        //        The PKCS8EncodedKeySpec (for private keys) and X509EncodedKeySpec (for public keys) constructors expect the key in binary format, not as a Base64 string
        final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
        return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
    }

    public static PublicKey loadPublicKey(final String pemPath) throws Exception {
        final String key = readKeyFromResources(pemPath)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

//        This line decodes that Base64 string back into its original binary form
        final byte[] decoded = Base64.getDecoder().decode(key);

//        The PKCS8EncodedKeySpec (for private keys) and X509EncodedKeySpec (for public keys) constructors expect the key in binary format, not as a Base64 string
        final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
        return KeyFactory.getInstance("RSA").generatePublic(keySpec);
    }


    private static String readKeyFromResources(String pemPath) throws IOException {
        {
            try (final InputStream inputStream = KeyUtils.class.getClassLoader().getResourceAsStream(pemPath)) {
                if (inputStream == null) {
                    throw new IllegalArgumentException("key not found: " + pemPath);
                }
                return new String(inputStream.readAllBytes());
            }
        }
    }


}
