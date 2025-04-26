package br.com.nivlabs.cliniv.util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Utilitário para criptografia
 *
 * @author viniciosarodrigues
 */
public class EncryptUtils {

    private static final String CIPHER_STRATEGY = "AES/GCM/NoPadding";
    private static final String ALGORITHM = "AES";
    private static EncryptUtils instance;

    @Value("${secret-key.property}")
    private String secretKey;

    private static final int GCM_IV_LENGTH = 12;
    private static final int GCM_TAG_LENGTH = 16;

    public static EncryptUtils getInstance() {
        if (instance == null)
            instance = new EncryptUtils();
        return instance;
    }

    /**
     * Método para criptografia fraca de informações, não utilizar para informações sensíveis
     *
     * @param privateString texto sem criptografia
     * @return Texto criptografado
     * @throws NoSuchAlgorithmException           Algoritimo de criptografia não encontrado
     * @throws NoSuchPaddingException             Mecanismo de espaçamento não encontrado
     * @throws InvalidKeyException                Chave inválida
     * @throws InvalidAlgorithmParameterException Parâmetro de algoritimo inválido
     * @throws IllegalBlockSizeException          Tamanho de bloco não permitido
     * @throws BadPaddingException                Espaçamento mal formatado
     */
    public String encrypt(String privateString) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] iv = new byte[GCM_IV_LENGTH];
        (new SecureRandom()).nextBytes(iv);

        Cipher cipher = Cipher.getInstance(CIPHER_STRATEGY);
        GCMParameterSpec ivSpec = new GCMParameterSpec(GCM_TAG_LENGTH * Byte.SIZE, iv);
        SecretKey skey = new SecretKeySpec(this.secretKey.getBytes(), ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, skey, ivSpec);

        byte[] ciphertext = cipher.doFinal(privateString.getBytes(StandardCharsets.UTF_8));
        byte[] encrypted = new byte[iv.length + ciphertext.length];
        System.arraycopy(iv, 0, encrypted, 0, iv.length);
        System.arraycopy(ciphertext, 0, encrypted, iv.length, ciphertext.length);

        return Base64.encodeBase64String(encrypted);
    }

    /**
     * Método para descriptografia fraca de informações
     *
     * @param encrypted Texto criptografado
     * @return Texto descriptografado
     * @throws NoSuchAlgorithmException           Algoritimo de criptografia não encontrado
     * @throws NoSuchPaddingException             Mecanismo de espaçamento não encontrado
     * @throws InvalidKeyException                Chave inválida
     * @throws InvalidAlgorithmParameterException Parâmetro de algoritimo inválido
     * @throws IllegalBlockSizeException          Tamanho de bloco não permitido
     * @throws BadPaddingException                Espaçamento mal formatado
     */
    public String decrypt(String encrypted) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        byte[] decoded = Base64.decodeBase64(encrypted);
        byte[] iv = Arrays.copyOfRange(decoded, 0, GCM_IV_LENGTH);

        Cipher cipher = Cipher.getInstance(CIPHER_STRATEGY);
        GCMParameterSpec ivSpec = new GCMParameterSpec(GCM_TAG_LENGTH * Byte.SIZE, iv);
        SecretKey skey = new SecretKeySpec(this.secretKey.getBytes(), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, skey, ivSpec);

        byte[] ciphertext = cipher.doFinal(decoded, GCM_IV_LENGTH, decoded.length - GCM_IV_LENGTH);

        return new String(ciphertext, StandardCharsets.UTF_8);
    }

}
