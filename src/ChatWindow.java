import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ChatWindow extends JFrame {

    private JTextPane userAChatBox;
    private JTextPane userBChatBox;
    private JTextField userAInputField;
    private JTextField userBInputField;
    private JButton sendMessageButtonA;
    private JButton sendMessageButtonB;
    private JTextArea adminPanel;

    public ChatWindow() {
        // Set up JFrame
        setTitle("Chat Application");
        setLayout(new BorderLayout());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create main panel with GridLayout for user chat boxes
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2, 10, 10));

        // Initialize components with JTextPane for formatted chat
        userAChatBox = new JTextPane();
        userAChatBox.setEditable(false);
        userAChatBox.setBackground(new Color(240, 248, 255)); // Light blue background

        userBChatBox = new JTextPane();
        userBChatBox.setEditable(false);
        userBChatBox.setBackground(new Color(240, 248, 255)); // Light blue background

        userAInputField = new JTextField();
        userBInputField = new JTextField();

        sendMessageButtonA = new JButton("Send");
        sendMessageButtonB = new JButton("Send");

        // Set up panels for user A and user B
        JPanel userAPanel = new JPanel(new BorderLayout());
        userAPanel.add(new JLabel("User A"), BorderLayout.NORTH);
        userAPanel.add(new JScrollPane(userAChatBox), BorderLayout.CENTER);
        JPanel userAInputPanel = new JPanel(new BorderLayout());
        userAInputPanel.add(userAInputField, BorderLayout.CENTER);
        userAInputPanel.add(sendMessageButtonA, BorderLayout.EAST);
        userAPanel.add(userAInputPanel, BorderLayout.SOUTH);

        JPanel userBPanel = new JPanel(new BorderLayout());
        userBPanel.add(new JLabel("User B"), BorderLayout.NORTH);
        userBPanel.add(new JScrollPane(userBChatBox), BorderLayout.CENTER);
        JPanel userBInputPanel = new JPanel(new BorderLayout());
        userBInputPanel.add(userBInputField, BorderLayout.CENTER);
        userBInputPanel.add(sendMessageButtonB, BorderLayout.EAST);
        userBPanel.add(userBInputPanel, BorderLayout.SOUTH);

        // Add panels to main panel
        mainPanel.add(userAPanel);
        mainPanel.add(userBPanel);

        // Admin Panel for encrypted message display with fixed height
        adminPanel = new JTextArea(5, 0);
        adminPanel.setEditable(false);
        adminPanel.setBackground(Color.LIGHT_GRAY);
        adminPanel.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JPanel adminPanelContainer = new JPanel(new BorderLayout());
        adminPanelContainer.add(new JLabel("Admin Panel: Encrypted Messages"), BorderLayout.NORTH);
        adminPanelContainer.add(new JScrollPane(adminPanel), BorderLayout.CENTER);

        // Add main panel and admin panel to the frame
        add(mainPanel, BorderLayout.CENTER);
        add(adminPanelContainer, BorderLayout.SOUTH);

        // Action listeners for buttons and enter key events
        addSendAction(sendMessageButtonA, userAInputField, userAChatBox, userBChatBox, "User A", true);
        addSendAction(sendMessageButtonB, userBInputField, userBChatBox, userAChatBox, "User B", false);

        userAInputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessageButtonA.doClick();
                }
            }
        });

        userBInputField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    sendMessageButtonB.doClick();
                }
            }
        });
    }

    private void addSendAction(JButton button, JTextField inputField, JTextPane senderChatBox, JTextPane receiverChatBox, String sender, boolean isRightAligned) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = inputField.getText();
                if (!message.isEmpty()) {
                    // Append formatted message to both sender's and receiver's chat box
                    appendFormattedMessage(senderChatBox, message, sender, isRightAligned);
                    appendFormattedMessage(receiverChatBox, message, sender, !isRightAligned);
                    // Display encrypted message in the Admin Panel
                    adminPanel.append(sender + ": " + encryptMessage(message) + "\n");
                    inputField.setText(""); // Clear input field
                }
            }
        });
    }

    private void appendFormattedMessage(JTextPane chatBox, String message, String sender, boolean isRightAligned) {
        StyledDocument doc = chatBox.getStyledDocument();
        SimpleAttributeSet messageStyle = new SimpleAttributeSet();

        // Set alignment
        StyleConstants.setAlignment(messageStyle, isRightAligned ? StyleConstants.ALIGN_RIGHT : StyleConstants.ALIGN_LEFT);
        // Set background color and corner radius
        StyleConstants.setBackground(messageStyle, sender.equals("User A") ? new Color(229, 228, 226) : new Color(72, 209, 204));
        StyleConstants.setLeftIndent(messageStyle, 5);
        StyleConstants.setRightIndent(messageStyle, 5);
        StyleConstants.setSpaceAbove(messageStyle, 5);
        StyleConstants.setSpaceBelow(messageStyle, 5);

        // Apply style and add message
        try {
            doc.insertString(doc.getLength(), sender + ": " + message + "\n", messageStyle);
            doc.setParagraphAttributes(doc.getLength(), 1, messageStyle, false);
        } catch (BadLocationException ex) {
            ex.printStackTrace();
        }
    }

    // Simple Caesar cipher for encryption (shifts characters by 3 positions)
    private String encryptMessage(String message) {
        StringBuilder encrypted = new StringBuilder();
        for (char c : message.toCharArray()) {
            if (Character.isLetter(c)) {
                char shifted = (char) (c + 3);
                if (Character.isLowerCase(c) && shifted > 'z' || Character.isUpperCase(c) && shifted > 'Z') {
                    shifted -= 26;
                }
                encrypted.append(shifted);
            } else {
                encrypted.append(c); // Keep non-letters unchanged
            }
        }
        return encrypted.toString();
    }

    public static void main(String[] args) {
        // Create and show the chat window
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ChatWindow().setVisible(true);
            }
        });
    }
}
