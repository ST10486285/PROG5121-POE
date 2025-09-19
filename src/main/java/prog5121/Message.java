/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prog5121;

import java.util.Scanner;
import javax.swing.JOptionPane;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * The MESSAGE CLASS - Handles message creation, validation, and management.
 * This class contains methods for message validation, hash creation, message operations, and JSON storage.
 * @author Chumisa Haya
 */
public class Message {
    // Message properties
    private String messageID;
    private String messageHash;
    private String recipient;
    private String messageText;
    private String status; // "Sent", "Stored", "Disregarded"
    private static int totalMessages = 0;
    private static int sentMessagesCount = 0;
    
    // Default constructor
    public Message() {
        this.messageID = "";
        this.messageHash = "";
        this.recipient = "";
        this.messageText = "";
        this.status = "";
    }
    
    /**
     * MESSAGE VALIDATION METHOD
     * Checks if message meets the 250 character limit requirement
     * 
     * @param message - the message text to validate
     * @return String - validation result message
     */
    public String checkMessageLength(String message) {
        System.out.println("Validating message length...");
        System.out.println("Message length: " + message.length() + " characters");
        
        if (message.length() <= 250) {
            String successMsg = "Message ready to send.";
            System.out.println("Message validation: SUCCESS - " + successMsg);
            return successMsg;
        } else {
            int excessChars = message.length() - 250;
            String errorMsg = "Message exceeds 250 characters by " + excessChars + ", please reduce size.";
            System.out.println("Message validation: FAILED - " + errorMsg);
            return errorMsg;
        }
    }
    
    /**
     * RECIPIENT VALIDATION METHOD
     * Reuses the cell phone validation from Login class
     * 
     * @param recipient - the recipient's cell phone number
     * @param loginSystem - reference to Login system for validation
     * @return String - validation result message
     */
    public String checkRecipientCell(String recipient, Login loginSystem) {
        System.out.println("Validating recipient cell number: " + recipient);
        
        if (loginSystem.checkCellPhoneNumber(recipient)) {
            String successMsg = "Cell phone number successfully captured.";
            System.out.println("Recipient validation: SUCCESS - " + successMsg);
            return successMsg;
        } else {
            String errorMsg = "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
            System.out.println("Recipient validation: FAILED - " + errorMsg);
            return errorMsg;
        }
    }
    
    /**
     * MESSAGE ID GENERATION METHOD
     * Creates a unique 10-digit message ID
     * 
     * @return String - generated message ID
     */
    public String generateMessageID() {
        // Generate random 10-digit number
        long randomID = (long) (Math.random() * 9000000000L) + 1000000000L;
        this.messageID = String.valueOf(randomID);
        System.out.println("Generated Message ID: " + this.messageID);
        return this.messageID;
    }
    
    /**
     * MESSAGE HASH CREATION METHOD
     * Creates a hash containing: first 2 digits of message ID + message number + first & last words
     * 
     * @param messageNum - the sequence number of the message
     * @return String - generated message hash in uppercase
     */
    public String createMessageHash(int messageNum) {
        if (this.messageID.isEmpty() || this.messageText.isEmpty()) {
            System.out.println("Cannot create hash - missing message ID or text");
            return "";
        }
        
        // Get first 2 digits of message ID
        String firstTwoDigits = this.messageID.substring(0, 2);
        
        // Get first and last words from message
        String[] words = this.messageText.trim().split("\\s+");
        String firstWord = words.length > 0 ? words[0] : "";
        String lastWord = words.length > 1 ? words[words.length - 1] : firstWord;
        
        // Create hash format: 00:0:FIRSTLAST
        this.messageHash = firstTwoDigits + ":" + messageNum + ":" + firstWord + lastWord;
        this.messageHash = this.messageHash.toUpperCase();
        
        System.out.println("Generated Message Hash: " + this.messageHash);
        return this.messageHash;
    }
    
    /**
     * MESSAGE SENDING METHOD
     * Handles the send/store/disregard options for messages
     * 
     * @return String - operation result message
     */
    public String sendMessage() {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n📨 MESSAGE OPTIONS:");
        System.out.println("1. Send Message");
        System.out.println("2. Store Message");
        System.out.println("3. Disregard Message");
        System.out.print("Choose an option (1-3): ");
        
        int choice;
        try {
            choice = scanner.nextInt();
        } catch (Exception e) {
            choice = 0;
        }
        
        switch (choice) {
            case 1:
                this.status = "Sent";
                sentMessagesCount++;
                totalMessages++;
                System.out.println("Message status: SENT");
                return "Message successfully sent.";
                
            case 2:
                this.status = "Stored";
                totalMessages++;
                System.out.println("Message status: STORED");
                return "Message successfully stored.";
                
            case 3:
                this.status = "Disregarded";
                System.out.println("Message status: DISREGARDED");
                return "Press 0 to delete message.";
                
            default:
                this.status = "Disregarded";
                System.out.println("Invalid choice. Message disregarded.");
                return "Press 0 to delete message.";
        }
    }
    
    /**
     * MESSAGE DISPLAY METHOD
     * Shows full message details using JOptionPane
     */
    public void displayMessageDetails() {
        String details = "📋 MESSAGE DETAILS:\n" +
                       "Message ID: " + this.messageID + "\n" +
                       "Message Hash: " + this.messageHash + "\n" +
                       "Recipient: " + this.recipient + "\n" +
                       "Message: " + this.messageText + "\n" +
                       "Status: " + this.status + "\n" +
                       "Total Messages: " + totalMessages;
        
        JOptionPane.showMessageDialog(null, details, "Message Information", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("\n" + details);
    }
    
    /**
     * COMPLETE MESSAGE CREATION PROCESS
     * Handles the entire message creation flow
     * 
     * @param loginSystem - reference to Login system for recipient validation
     * @param messageNum - the sequence number of this message
     * @return boolean - true if message was created successfully
     */
    public boolean createMessage(Login loginSystem, int messageNum) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("CREATING MESSAGE #" + messageNum);
        System.out.println("=".repeat(50));
        
        // Get recipient number
        System.out.print("Enter recipient's cell number (+27 format): ");
        String recipientInput = scanner.nextLine();
        
        String recipientValidation = checkRecipientCell(recipientInput, loginSystem);
        if (!recipientValidation.contains("successfully")) {
            System.out.println("❌ " + recipientValidation);
            return false;
        }
        this.recipient = recipientInput;
        
        // Get message text
        System.out.print("Enter your message (max 250 chars): ");
        String messageInput = scanner.nextLine();
        
        String messageValidation = checkMessageLength(messageInput);
        if (!messageValidation.contains("ready to send")) {
            System.out.println("❌ " + messageValidation);
            return false;
        }
        this.messageText = messageInput;
        
        // Generate message ID and hash
        generateMessageID();
        createMessageHash(messageNum);
        
        // Handle send/store/disregard
        String sendResult = sendMessage();
        System.out.println("📩 " + sendResult);
        
        // Display details
        displayMessageDetails();
        
        return true;
    }
    
    /**
     * STORES MESSAGES TO JSON FILE
     * Uses AI-assisted JSON implementation for message storage
     * 
     * @param messages - list of messages to store
     * @param filename - the JSON file name
     */
    public static void storeMessagesToJSON(List<Message> messages, String filename) {
        try {
            JSONArray jsonArray = new JSONArray();
            
            for (Message message : messages) {
                JSONObject jsonMessage = new JSONObject();
                jsonMessage.put("messageID", message.getMessageID());
                jsonMessage.put("messageHash", message.getMessageHash());
                jsonMessage.put("recipient", message.getRecipient());
                jsonMessage.put("messageText", message.getMessageText());
                jsonMessage.put("status", message.getStatus());
                
                jsonArray.put(jsonMessage);
            }
            
            try (FileWriter file = new FileWriter(filename)) {
                file.write(jsonArray.toString(4)); // 4 spaces for indentation
                System.out.println("✅ Messages successfully stored in " + filename);
            }
            
        } catch (IOException e) {
            System.out.println("❌ Error storing messages to JSON: " + e.getMessage());
        }
    }
    
    /**
     * LOADS MESSAGES FROM JSON FILE
     * Reads messages from JSON file back into application
     * 
     * @param filename - the JSON file name
     * @return List<Message> - list of loaded messages
     */
    public static List<Message> loadMessagesFromJSON(String filename) {
        List<Message> loadedMessages = new ArrayList<>();
        
        try {
            String content = new String(Files.readAllBytes(Paths.get(filename)));
            JSONArray jsonArray = new JSONArray(content);
            
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonMessage = jsonArray.getJSONObject(i);
                Message message = new Message();
                
                message.setMessageID(jsonMessage.getString("messageID"));
                message.setMessageHash(jsonMessage.getString("messageHash"));
                message.setRecipient(jsonMessage.getString("recipient"));
                message.setMessageText(jsonMessage.getString("messageText"));
                message.setStatus(jsonMessage.getString("status"));
                
                loadedMessages.add(message);
                
                // Update static counters based on loaded messages
                totalMessages++;
                if ("Sent".equals(message.getStatus())) {
                    sentMessagesCount++;
                }
            }
            
            System.out.println("✅ Loaded " + loadedMessages.size() + " messages from " + filename);
            
        } catch (IOException e) {
            System.out.println("❌ Error loading messages from JSON: " + e.getMessage());
        }
        
        return loadedMessages;
    }
    
    /**
     * CONVERTS MESSAGE TO JSON OBJECT
     * Helper method for individual message conversion
     * 
     * @return JSONObject - the message as a JSON object
     */
    public JSONObject toJSON() {
        JSONObject jsonMessage = new JSONObject();
        jsonMessage.put("messageID", this.messageID);
        jsonMessage.put("messageHash", this.messageHash);
        jsonMessage.put("recipient", this.recipient);
        jsonMessage.put("messageText", this.messageText);
        jsonMessage.put("status", this.status);
        return jsonMessage;
    }
    
    /**
     * CREATES MESSAGE FROM JSON OBJECT
     * Helper method for converting JSON back to Message object
     * 
     * @param jsonMessage - the JSON object containing message data
     * @return Message - the created Message object
     */
    public static Message fromJSON(JSONObject jsonMessage) {
        Message message = new Message();
        message.setMessageID(jsonMessage.getString("messageID"));
        message.setMessageHash(jsonMessage.getString("messageHash"));
        message.setRecipient(jsonMessage.getString("recipient"));
        message.setMessageText(jsonMessage.getString("messageText"));
        message.setStatus(jsonMessage.getString("status"));
        return message;
    }
    
    /**
     * GETS ALL MESSAGES AS JSON ARRAY
     * Convenience method for converting multiple messages
     * 
     * @param messages - list of messages to convert
     * @return JSONArray - messages as JSON array
     */
    public static JSONArray getAllMessagesAsJSON(List<Message> messages) {
        JSONArray jsonArray = new JSONArray();
        for (Message message : messages) {
            jsonArray.put(message.toJSON());
        }
        return jsonArray;
    }
    
    // Getter methods
    public String getMessageID() { return messageID; }
    public String getMessageHash() { return messageHash; }
    public String getRecipient() { return recipient; }
    public String getMessageText() { return messageText; }
    public String getStatus() { return status; }
    public static int getTotalMessages() { return totalMessages; }
    public static int getSentMessagesCount() { return sentMessagesCount; }
    
    // Setter methods
    public void setMessageID(String messageID) { this.messageID = messageID; }
    public void setMessageHash(String messageHash) { this.messageHash = messageHash; }
    public void setRecipient(String recipient) { this.recipient = recipient; }
    public void setMessageText(String messageText) { this.messageText = messageText; }
    public void setStatus(String status) { this.status = status; }
    
    /**
     * MANUAL TESTING METHOD
     * Tests message functionality with sample data
     */
    public void runMessageTests(Login loginSystem) {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("TESTING MESSAGE FUNCTIONALITY");
        System.out.println("=".repeat(60));
        
        // Test 1: Message length validation
        System.out.println("\n--- TEST 1: MESSAGE LENGTH VALIDATION ---");
        System.out.println("Short message: " + checkMessageLength("Hello there!"));
        System.out.println("Long message: " + checkMessageLength("A".repeat(300)));
        
        // Test 2: Recipient validation
        System.out.println("\n--- TEST 2: RECIPIENT VALIDATION ---");
        System.out.println("Valid recipient: " + checkRecipientCell("+27831234567", loginSystem));
        System.out.println("Invalid recipient: " + checkRecipientCell("0831234567", loginSystem));
        
        // Test 3: Message hash generation
        System.out.println("\n--- TEST 3: MESSAGE HASH GENERATION ---");
        this.messageID = "1234567890";
        this.messageText = "Hello world this is a test";
        System.out.println("Generated hash: " + createMessageHash(1));
        
        // Test 4: JSON conversion
        System.out.println("\n--- TEST 4: JSON CONVERSION ---");
        this.messageID = "9876543210";
        this.messageHash = "98:1:TESTEXAMPLE";
        this.recipient = "+27839876543";
        this.messageText = "This is a test message for JSON";
        this.status = "Sent";
        
        JSONObject jsonMessage = toJSON();
        System.out.println("JSON representation: " + jsonMessage.toString(2));
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("MESSAGE TESTS COMPLETED");
        System.out.println("=".repeat(60));
    }
}