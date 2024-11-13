package cyber.security;


public class AutoKeyCipher {
    private static final int ASCII_RANGE = 256; // Full range for basic ASCII characters

    // Encrypt using AutoKey Cipher with extended character set
    public static String encrypt(String plaintext, String key) {
        StringBuilder extendedKey = new StringBuilder(key);

        // Extend the key using the plaintext
        for (int i = 0; i < plaintext.length() - key.length(); i++) {
            extendedKey.append(plaintext.charAt(i));
        }

        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            int plainChar = plaintext.charAt(i);
            int keyChar = extendedKey.charAt(i);
            int cipherChar = (plainChar + keyChar) % ASCII_RANGE;
            ciphertext.append((char) cipherChar);
        }

        return ciphertext.toString();
    }

    // Decrypt using AutoKey Cipher with extended character set
    public static String decrypt(String ciphertext, String key) {
        StringBuilder extendedKey = new StringBuilder(key);
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i++) {
            int cipherChar = ciphertext.charAt(i);
            int keyChar = extendedKey.charAt(i);
            int plainChar = (cipherChar - keyChar + ASCII_RANGE) % ASCII_RANGE;
            plaintext.append((char) plainChar);
            extendedKey.append((char) plainChar);  // Extend the key with the decrypted character
        }

        return plaintext.toString();
    }
}
