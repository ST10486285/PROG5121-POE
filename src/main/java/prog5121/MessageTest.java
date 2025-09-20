/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prog5121;

/**
 * UNIT TESTS FOR MESSAGE CLASS
 * Tests message validation, hash creation, and operations
 * @author Chumisa Haya
 */
public class MessageTest {
    
    private Message message = new Message();
    private Login login = new Login();
    
    /**
     * TEST: Message within 250 character limit
     */
    public void testCheckMessageLengthValid() {
        System.out.println("Testing message within 250 characters:");
        String result = message.checkMessageLength("Hello, this is a test message!");
        System.out.println("Result: " + result);
        boolean success = result.contains("ready to send");
        System.out.println("Test " + (success ? "PASSED" : "FAILED"));
        System.out.println("=".repeat(40));
    }
    
    /**
     * TEST: Message exceeds 250 character limit
     */
    public void testCheckMessageLengthInvalid() {
        System.out.println("Testing message exceeding 250 characters:");
        // Create a long message
        StringBuilder longMessage = new StringBuilder();
        for (int i = 0; i < 300; i++) {
            longMessage.append("A");
        }
        String result = message.checkMessageLength(longMessage.toString());
        System.out.println("Result: " + result);
        boolean shouldFail = result.contains("exceeds 250 characters");
        System.out.println("Test " + (shouldFail ? "PASSED" : "FAILED"));
        System.out.println("=".repeat(40));
    }
    
    /**
     * TEST: Valid recipient cell number
     */
    public void testCheckRecipientCellValid() {
        System.out.println("Testing valid recipient cell number:");
        String result = message.checkRecipientCell("+27831234567", login);
        System.out.println("Result: " + result);
        boolean success = result.contains("successfully captured");
        System.out.println("Test " + (success ? "PASSED" : "FAILED"));
        System.out.println("=".repeat(40));
    }
    
    /**
     * TEST: Invalid recipient cell number
     */
    public void testCheckRecipientCellInvalid() {
        System.out.println("Testing invalid recipient cell number:");
        String result = message.checkRecipientCell("0831234567", login);
        System.out.println("Result: " + result);
        boolean shouldFail = result.contains("incorrectly formatted");
        System.out.println("Test " + (shouldFail ? "PASSED" : "FAILED"));
        System.out.println("=".repeat(40));
    }
    
    /**
     * TEST: Message hash generation
     */
    public void testCreateMessageHash() {
        System.out.println("Testing message hash generation:");
        message.setMessageID("1234567890");
        message.setMessageText("Hello world this is a test");
        String hash = message.createMessageHash(1);
        System.out.println("Generated Hash: " + hash);
        boolean validFormat = hash != null && hash.contains(":") && hash.length() > 5;
        System.out.println("Test " + (validFormat ? "PASSED" : "FAILED"));
        System.out.println("=".repeat(40));
    }
    
    /**
     * TEST: Message ID generation
     */
    public void testGenerateMessageID() {
        System.out.println("Testing message ID generation:");
        String messageID = message.generateMessageID();
        System.out.println("Generated ID: " + messageID);
        boolean validFormat = messageID != null && messageID.length() == 10;
        System.out.println("Test " + (validFormat ? "PASSED" : "FAILED"));
        System.out.println("=".repeat(40));
    }
    
    /**
     * RUN ALL MESSAGE TESTS
     */
    public void runAllMessageTests() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🧪 RUNNING ALL MESSAGE UNIT TESTS");
        System.out.println("=".repeat(60));
        
        testCheckMessageLengthValid();
        testCheckMessageLengthInvalid();
        testCheckRecipientCellValid();
        testCheckRecipientCellInvalid();
        testCreateMessageHash();
        testGenerateMessageID();
        
        System.out.println("=".repeat(60));
        System.out.println("MESSAGE TESTS COMPLETED");
        System.out.println("=".repeat(60));
    }
}