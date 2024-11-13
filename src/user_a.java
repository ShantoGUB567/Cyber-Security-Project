import cyber.security.AutoKeyCipher;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
/*package cyber.security;*/

public class user_a {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 3000);
        System.out.println("Server Connected");
        
        DataOutputStream output = new DataOutputStream(s.getOutputStream());
        DataInputStream input = new DataInputStream(s.getInputStream());
        
        Scanner scn = new Scanner(System.in);
        String str = "";
        
        String key = "MyKey";  // Default key for encryption/decryption
        
        while (!str.equals("Bye")) {
            // Taking input from client
            str = scn.nextLine();

            // Encrypting the input before sending it to the server
            String encryptedMessage = AutoKeyCipher.encrypt(str, key);

            // Send encrypted message to the server
            output.writeUTF(encryptedMessage);
            
            // Receive encrypted message from the server and decrypt it
            String receivedEncryptedMessage = input.readUTF();
            String decryptedMessage = AutoKeyCipher.decrypt(receivedEncryptedMessage, key);

            // Print the decrypted message from the server
            System.out.println("Server says: " + decryptedMessage);
        }
        
        input.close();
        output.close();
        s.close();
    }
}
