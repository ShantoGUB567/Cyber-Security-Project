import cyber.security.AutoKeyCipher;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
/*package cyber.security;*/

public class user_b {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(3000);
        System.out.println("Waiting for Client");
        
        Socket s = ss.accept();
        System.out.println("Client request is accepted.");
        
        DataInputStream input = new DataInputStream(s.getInputStream());
        DataOutputStream output = new DataOutputStream(s.getOutputStream());
        
        Scanner scn = new Scanner(System.in);
        String str = "";
        
        String key = "MyKey";  // Default key for encryption/decryption
        
        while (!str.equals("Bye")) {
            // Receive encrypted message from the client
            String receivedEncryptedMessage = input.readUTF();
            
            // Decrypt the received message
            String decryptedMessage = AutoKeyCipher.decrypt(receivedEncryptedMessage, key);
            System.out.println("Client says: " + decryptedMessage);
            
            // Get server's response and encrypt it before sending back to the client
            str = scn.nextLine();
            String encryptedResponse = AutoKeyCipher.encrypt(str, key);
            output.writeUTF(encryptedResponse);
        }
        
        input.close();
        output.close();
        s.close();
    }
}
