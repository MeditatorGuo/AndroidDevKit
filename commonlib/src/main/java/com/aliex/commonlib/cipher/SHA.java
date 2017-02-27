package com.aliex.commonlib.cipher;

import java.security.MessageDigest;

/**
 * @Description: SHA加密
 */
public class SHA {
    public static byte[] encrypt(byte[] data) throws Exception {
        MessageDigest sha = MessageDigest.getInstance(CipherType.SHA.getType());
        sha.update(data);
        return sha.digest();
    }
}
