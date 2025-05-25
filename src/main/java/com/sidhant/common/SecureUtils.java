package com.sidhant.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.UUID;

@Component
public class SecureUtils {
    private Logger logger = LoggerFactory.getLogger(SecureUtils.class);

    @Value("secureKey")
    private String secureKey;

    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";

    private SecretKeySpec secretKey;

    public String decode(String data, String secureKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        setKey(secureKey);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        UUID uuid = UUID.fromString(data);
        byte[] bytes = getBytesFromUUID(uuid);
        return new String(cipher.doFinal(bytes));
    }

    private static byte[] getBytesFromUUID(UUID uuid) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
        byteBuffer.putLong(uuid.getMostSignificantBits());
        byteBuffer.putLong(uuid.getLeastSignificantBits());
        return byteBuffer.array();

    }

    private void setKey(String myKey) {
        MessageDigest sha = null;

        byte[] Key = myKey.getBytes(StandardCharsets.UTF_8);
        try {
            sha = MessageDigest.getInstance("SHA-1");
            Key = sha.digest(Key);
            Key = Arrays.copyOf(Key, 16);
            secretKey = new SecretKeySpec(Key, "AES");
        } catch (NoSuchAlgorithmException e) {
            logger.error("Execption", e);
        }

    }

    public String encode(String id, String secureKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        setKey(secureKey);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] bytes = cipher.doFinal(id.getBytes(StandardCharsets.UTF_8));
        return getUUIDFromBytes(bytes).toString();
    }

    private static UUID getUUIDFromBytes(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        Long high = byteBuffer.getLong();
        Long low = byteBuffer.getLong();
        return new UUID(high, low);
    }

    public String getSecretSerialization() {
        return secureKey;
    }
}
