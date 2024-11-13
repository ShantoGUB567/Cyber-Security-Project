package cyber.security;

public class CyberSecurity {

    
    public static void main(String[] args) {
        String plaintext = "Hello, AutoKey123! @#$";
        String key = "MyKey";

        // Encrypt
        String encryptedText = AutoKeyCipher.encrypt(plaintext, key);
        System.out.println("Encrypted Text: " + encryptedText);

        // Decrypt
        String decryptedText = AutoKeyCipher.decrypt(encryptedText, key);
        System.out.println("Decrypted Text: " + decryptedText);
    }
    
}
