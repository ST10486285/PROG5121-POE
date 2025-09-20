/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package prog5121;

/**
 * @author Chumisa Haya
 * ChatApp's MAIN APPLICATION - COMPLETE VERSION
 * This Main Class provides a console interface for user registration, login, messaging,
 * and complete message management with JSON storage and reporting features.
 */

/**
 * References: 
 * - OpenAI. (2023). ChatGPT (September 25 version) [Large language model].
 *   https://chat.openai.com/chat
 * - JSON.org. (2023). JSON Java Library (Version 20231013) [Software library].
 *   https://github.com/stleary/JSON-java
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ChatApp {
    private static final Login loginSystem = new Login();
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Message> allMessages = new ArrayList<>();
    private static final List<Message> sentMessages = new ArrayList<>();
    private static final List<Message> storedMessages = new ArrayList<>();
    private static final List<Message> disregardedMessages = new ArrayList<>();
    private static final List<String> messageHashes = new ArrayList<>();
    private static final List<String> messageIDs = new ArrayList<>();
    
    public static void main(String[] args) {
        System.out.println("✨ WELCOME TO ChatApp ✨");
        System.out.println("=".repeat(40));
        
        // Load messages from previous session if available
        loadMessagesFromJSON();
        
        boolean running = true;
        while (running) {
            displayMainMenu();
            
            int choice = getMenuChoice();
            
            switch (choice) {
                case 1:
                    registerNewUser();
                    break;
                case 2:
                    loginExistingUser();
                    break;
                case 3:
                    runDemoTests();
                    break;
                case 4:
                    showUserInfo();
                    break;
                case 5:
                    sendMessages();
                    break;
                case 6:
                    runMessageTests();
                    break;
                case 7:
                    showMessageStatistics();
                    break;
                case 8:
                    manageMessages();
                    break;
                case 9:
                    generateReports();
                    break;
                case 10:
                    loadTestData();
                    break;
                case 11:
                    runUnitTests();
                    break;
                case 12:
                    // Save messages before exiting
                    saveMessagesToJSON();
                    running = false;
                    System.out.println("Thank you for using QuickChat! Goodbye! 👋");
                    break;
                default:
                    System.out.println("❌ Invalid option. Please choose 1-12.");
            }
            
            System.out.println("\n" + "=".repeat(40));
        }
    }
    
    /**
     * Displays the enhanced main menu options
     */
    private static void displayMainMenu() {
        System.out.println(" MAIN MENU:");
        System.out.println("1. Register New User");
        System.out.println("2. Login");
        System.out.println("3. Run Demo Tests");
        System.out.println("4. Show Registered User Info");
        System.out.println("5. Send Messages");
        System.out.println("6. Message Tests");
        System.out.println("7. Message Statistics");
        System.out.println("8. Manage Messages");
        System.out.println("9. Generate Reports");
        System.out.println("10. Load Test Data");
        System.out.println("11. Run Unit Tests");
        System.out.println("12. Exit");
        System.out.print("Choose an option (1-12): ");
    }
    
    /**
     * Gets and validates menu choice from user
     */
    private static int getMenuChoice() {
        try {
            return scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine(); // Clear invalid input
            return -1; // Return invalid choice
        }
    }
    
    /**
     * Handles new user registration process
     */
    private static void registerNewUser() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("👤 NEW USER REGISTRATION");
        System.out.println("=".repeat(40));
        
        scanner.nextLine(); // Clear buffer
        
        // Get user information
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        
        System.out.print("Enter Username (must contain _ and be ≤5 chars): ");
        String username = scanner.nextLine();
        
        System.out.print("Enter Password (min 8 chars, capital, number, special char): ");
        String password = scanner.nextLine();
        
        System.out.print("Enter SA Cell Phone (e.g., +27831234567): ");
        String cellPhone = scanner.nextLine();
        
        // Attempt registration
        String result = loginSystem.registerUser(username, password, cellPhone, firstName, lastName);
        System.out.println("\n📝 REGISTRATION RESULT:");
        System.out.println(result);
    }
    
    /**
     * Handles user login process
     */
    private static void loginExistingUser() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("🔐 USER LOGIN");
        System.out.println("=".repeat(40));
        
        scanner.nextLine(); // Clear buffer
        
        // Get login credentials
        System.out.print("Enter Username: ");
        String username = scanner.nextLine();
        
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        
        // Attempt login
        boolean loginResult = loginSystem.loginUser(username, password);
        String statusMessage = loginSystem.returnLoginStatus(loginResult);
        
        System.out.println("\n🔑 LOGIN RESULT:");
        System.out.println(statusMessage);
    }
    
    /**
     * Runs demonstration tests showing validation in action
     */
    private static void runDemoTests() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("🧪 DEMONSTRATION TESTS");
        System.out.println("=".repeat(40));
        
        loginSystem.runManualTests();
    }
    
    /**
     * Shows currently registered user information
     */
    private static void showUserInfo() {
        System.out.println("\n" + "=".repeat(40));
        System.out.println("📊 REGISTERED USER INFORMATION");
        System.out.println("=".repeat(40));
        
        String username = loginSystem.getStoredUsername();
        String password = loginSystem.getStoredPassword();
        String cellPhone = loginSystem.getStoredCellPhone();
        String firstName = loginSystem.getStoredFirstName();
        String lastName = loginSystem.getStoredLastName();
        
        if (username.isEmpty()) {
            System.out.println("No user registered yet. Please register first.");
            return;
        }
        
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Username: " + username);
        System.out.println("Password: " + "*".repeat(password.length()));
        System.out.println("Cell Phone: " + cellPhone);
        
        // Show password requirements status
        System.out.println("\n🔒 PASSWORD COMPLEXITY CHECK:");
        System.out.println("Length >= 8: " + (password.length() >= 8));
        System.out.println("Has capital: " + containsCapital(password));
        System.out.println("Has number: " + containsNumber(password));
        System.out.println("Has special char: " + containsSpecialChar(password));
    }
    
    /**
     * MESSAGE SENDING METHOD
     * Handles the message sending workflow without Scanner conflicts
     */
    private static void sendMessages() {
        if (loginSystem.getStoredUsername().isEmpty()) {
            System.out.println("❌ Please register and login first!");
            return;
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("📨 MESSAGE SENDING SYSTEM");
        System.out.println("=".repeat(50));
        
        // Create a separate scanner for message input to avoid conflicts
        Scanner messageScanner = new Scanner(System.in);
        
        System.out.print("How many messages do you want to send? ");
        
        int numMessages;
        try {
            numMessages = messageScanner.nextInt();
            messageScanner.nextLine(); // Clear buffer
        } catch (Exception e) {
            System.out.println("❌ Invalid number. Please enter a valid integer.");
            messageScanner.close();
            return;
        }
        
        int successfulMessages = 0;
        
        for (int i = 1; i <= numMessages; i++) {
            System.out.println("\n" + "=".repeat(40));
            System.out.println("CREATING MESSAGE #" + i);
            System.out.println("=".repeat(40));
            
            Message message = new Message();
            boolean success = createMessageManual(message, i, messageScanner);
            
            if (success) {
                // Add to appropriate arrays
                allMessages.add(message);
                messageIDs.add(message.getMessageID());
                messageHashes.add(message.getMessageHash());
                
                // Add to status-specific arrays
                if ("Sent".equals(message.getStatus())) {
                    sentMessages.add(message);
                } else if ("Stored".equals(message.getStatus())) {
                    storedMessages.add(message);
                } else {
                    disregardedMessages.add(message);
                }
                
                successfulMessages++;
                System.out.println("✅ Message #" + i + " processed successfully!");
            } else {
                System.out.println("❌ Message #" + i + " failed. Skipping...");
            }
        }
        
        // Save messages after sending
        saveMessagesToJSON();
        
        System.out.println("\n📊 MESSAGING SUMMARY:");
        System.out.println("Total attempted: " + numMessages);
        System.out.println("Successful: " + successfulMessages);
        System.out.println("Total sent messages: " + sentMessages.size());
        System.out.println("Total all messages: " + allMessages.size());
        
        messageScanner.close();
    }
    
    /**
     * MANUAL MESSAGE CREATION - Handles input without using Message.createMessage()
     */
    private static boolean createMessageManual(Message message, int messageNum, Scanner messageScanner) {
        // Get recipient number
        System.out.print("Enter recipient's cell number (+27 format): ");
        String recipientInput = messageScanner.nextLine();
        
        String recipientValidation = message.checkRecipientCell(recipientInput, loginSystem);
        if (!recipientValidation.contains("successfully")) {
            System.out.println("❌ " + recipientValidation);
            return false;
        }
        message.setRecipient(recipientInput);
        
        // Get message text
        System.out.print("Enter your message (max 250 chars): ");
        String messageInput = messageScanner.nextLine();
        
        String messageValidation = message.checkMessageLength(messageInput);
        if (!messageValidation.contains("ready to send")) {
            System.out.println("❌ " + messageValidation);
            return false;
        }
        message.setMessageText(messageInput);
        
        // Generate message ID and hash
        message.generateMessageID();
        message.createMessageHash(messageNum);
        
        // Handle send/store/disregard
        System.out.println("\n📨 MESSAGE OPTIONS:");
        System.out.println("1. Send Message");
        System.out.println("2. Store Message");
        System.out.println("3. Disregard Message");
        System.out.print("Choose an option (1-3): ");
        
        int choice;
        try {
            choice = messageScanner.nextInt();
            messageScanner.nextLine(); // Clear buffer
        } catch (Exception e) {
            choice = 0;
        }
        
        switch (choice) {
            case 1:
                message.setStatus("Sent");
                System.out.println("Message status: SENT");
                break;
            case 2:
                message.setStatus("Stored");
                System.out.println("Message status: STORED");
                break;
            case 3:
                message.setStatus("Disregarded");
                System.out.println("Message status: DISREGARDED");
                break;
            default:
                message.setStatus("Disregarded");
                System.out.println("Invalid choice. Message disregarded.");
        }
        
        // Display details
        message.displayMessageDetails();
        
        return true;
    }
    
    /**
     * MESSAGE TESTING METHOD
     * Runs automated message tests
     */
    private static void runMessageTests() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🧪 MESSAGE FUNCTIONALITY TESTS");
        System.out.println("=".repeat(50));
        
        Message testMessage = new Message();
        testMessage.runMessageTests(loginSystem);
    }
    
    /**
     * SHOWS MESSAGE STATISTICS
     * Displays current message statistics
     */
    private static void showMessageStatistics() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("📈 MESSAGE STATISTICS");
        System.out.println("=".repeat(50));
        
        System.out.println("Total messages processed: " + allMessages.size());
        System.out.println("Sent messages: " + sentMessages.size());
        System.out.println("Stored messages: " + storedMessages.size());
        System.out.println("Disregarded messages: " + disregardedMessages.size());
        
        if (!allMessages.isEmpty()) {
            System.out.println("\n📋 RECENT MESSAGES:");
            for (int i = Math.max(0, allMessages.size() - 5); i < allMessages.size(); i++) {
                Message msg = allMessages.get(i);
                String preview = msg.getMessageText().length() > 20 ? 
                    msg.getMessageText().substring(0, 20) + "..." : msg.getMessageText();
                System.out.println((i + 1) + ". To: " + msg.getRecipient() + " - " + preview);
            }
        }
    }
    
    /**
     * MESSAGE MANAGEMENT MENU
     * Handles advanced message operations
     */
    private static void manageMessages() {
        if (allMessages.isEmpty()) {
            System.out.println("❌ No messages available. Please send messages first.");
            return;
        }
        
        boolean managing = true;
        while (managing) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("📋 MESSAGE MANAGEMENT");
            System.out.println("=".repeat(50));
            System.out.println("1. Display All Messages");
            System.out.println("2. Search by Message ID");
            System.out.println("3. Search by Recipient");
            System.out.println("4. Delete Message by Hash");
            System.out.println("5. Show Longest Message");
            System.out.println("6. Back to Main Menu");
            System.out.print("Choose an option (1-6): ");
            
            int choice = getMenuChoice();
            scanner.nextLine(); // Clear buffer
            
            switch (choice) {
                case 1:
                    displayAllMessages();
                    break;
                case 2:
                    searchByMessageID();
                    break;
                case 3:
                    searchByRecipient();
                    break;
                case 4:
                    deleteByMessageHash();
                    break;
                case 5:
                    showLongestMessage();
                    break;
                case 6:
                    managing = false;
                    break;
                default:
                    System.out.println("❌ Invalid option.");
            }
        }
    }
    
    /**
     * DISPLAYS ALL MESSAGES
     */
    private static void displayAllMessages() {
        System.out.println("\n📨 ALL MESSAGES (" + allMessages.size() + "):");
        System.out.println("=".repeat(80));
        for (int i = 0; i < allMessages.size(); i++) {
            Message msg = allMessages.get(i);
            String preview = msg.getMessageText().length() > 30 ? 
                msg.getMessageText().substring(0, 30) + "..." : msg.getMessageText();
            System.out.printf("%2d. To: %-15s | Status: %-12s | %s\n", 
                (i + 1), msg.getRecipient(), msg.getStatus(), preview);
        }
    }
    
    /**
     * SEARCHES MESSAGES BY ID
     */
    private static void searchByMessageID() {
        System.out.print("Enter Message ID to search: ");
        String searchID = scanner.nextLine();
        
        boolean found = false;
        for (Message msg : allMessages) {
            if (msg.getMessageID().equals(searchID)) {
                System.out.println("✅ Found message:");
                System.out.println("ID: " + msg.getMessageID());
                System.out.println("Hash: " + msg.getMessageHash());
                System.out.println("Recipient: " + msg.getRecipient());
                System.out.println("Message: " + msg.getMessageText());
                System.out.println("Status: " + msg.getStatus());
                found = true;
                break;
            }
        }
        
        if (!found) {
            System.out.println("❌ No message found with ID: " + searchID);
        }
    }
    
    /**
 * LOADS TEST DATA FROM ASSIGNMENT SPECIFICATION
 * Populates arrays with the required test messages
 */
private static void loadTestData() {
    System.out.println("\n" + "=".repeat(60));
    System.out.println("📋 LOADING TEST DATA FROM ASSIGNMENT");
    System.out.println("=".repeat(60));
    
    // Clear existing data to start fresh
    allMessages.clear();
    sentMessages.clear();
    storedMessages.clear();
    disregardedMessages.clear();
    messageHashes.clear();
    messageIDs.clear();
    
    // Test Data Message 1: +27834557896 | "Did you get the cake?" | Sent
    Message msg1 = new Message();
    msg1.setMessageID("1000000001");
    msg1.setMessageHash("10:1:DIDCAKE");
    msg1.setRecipient("+27834557896");
    msg1.setMessageText("Did you get the cake?");
    msg1.setStatus("Sent");
    addTestMessageToArrays(msg1);
    
    // Test Data Message 2: +27838884567 | Long message | Stored
    Message msg2 = new Message();
    msg2.setMessageID("1000000002");
    msg2.setMessageHash("10:2:WHERETIME");
    msg2.setRecipient("+27838884567");
    msg2.setMessageText("Where are you? You are late! I have asked you to be on time.");
    msg2.setStatus("Stored");
    addTestMessageToArrays(msg2);
    
    // Test Data Message 3: +27834484567 | "Yohoooo, I am at your gate." | Disregarded
    Message msg3 = new Message();
    msg3.setMessageID("1000000003");
    msg3.setMessageHash("10:3:YOHOOGATE");
    msg3.setRecipient("+27834484567");
    msg3.setMessageText("Yohoooo, I am at your gate.");
    msg3.setStatus("Disregarded");
    addTestMessageToArrays(msg3);
    
    // Test Data Message 4: 0838884567 | "It is dinner time!" | Sent (invalid format)
    Message msg4 = new Message();
    msg4.setMessageID("1000000004");
    msg4.setMessageHash("10:4:ITTIME");
    msg4.setRecipient("0838884567");
    msg4.setMessageText("It is dinner time!");
    msg4.setStatus("Sent");
    addTestMessageToArrays(msg4);
    
    // Test Data Message 5: +27838884567 | "Ok, I am leaving without you." | Stored
    Message msg5 = new Message();
    msg5.setMessageID("1000000005");
    msg5.setMessageHash("10:5:OKYOU");
    msg5.setRecipient("+27838884567");
    msg5.setMessageText("Ok, I am leaving without you.");
    msg5.setStatus("Stored");
    addTestMessageToArrays(msg5);
    
    System.out.println("✅ Test data loaded successfully!");
    System.out.println("Total messages: " + allMessages.size());
    System.out.println("Sent: " + sentMessages.size() + " | Stored: " + storedMessages.size() + 
                      " | Disregarded: " + disregardedMessages.size());
}

/**
 * HELPER METHOD: Adds a test message to all appropriate arrays
 */
private static void addTestMessageToArrays(Message message) {
    allMessages.add(message);
    messageIDs.add(message.getMessageID());
    messageHashes.add(message.getMessageHash());
    
    switch (message.getStatus()) {
        case "Sent":
            sentMessages.add(message);
            break;
        case "Stored":
            storedMessages.add(message);
            break;
        case "Disregarded":
            disregardedMessages.add(message);
            break;
    }
}
    
    
    /**
     * SEARCHES MESSAGES BY RECIPIENT
     */
    private static void searchByRecipient() {
        System.out.print("Enter recipient number to search: ");
        String recipient = scanner.nextLine();
        
        List<Message> foundMessages = new ArrayList<>();
        for (Message msg : allMessages) {
            if (msg.getRecipient().equals(recipient)) {
                foundMessages.add(msg);
            }
        }
        
        if (foundMessages.isEmpty()) {
            System.out.println("❌ No messages found for recipient: " + recipient);
        } else {
            System.out.println("✅ Found " + foundMessages.size() + " messages for " + recipient + ":");
            for (Message msg : foundMessages) {
                System.out.println("• " + msg.getMessageText() + " (" + msg.getStatus() + ")");
            }
        }
    }
    
    /**
     * DELETES MESSAGE BY HASH
     */
    private static void deleteByMessageHash() {
        System.out.print("Enter Message Hash to delete: ");
        String hashToDelete = scanner.nextLine();
        
        Message messageToDelete = null;
        for (Message msg : allMessages) {
            if (msg.getMessageHash().equalsIgnoreCase(hashToDelete)) {
                messageToDelete = msg;
                break;
            }
        }
        
        if (messageToDelete != null) {
            allMessages.remove(messageToDelete);
            messageIDs.remove(messageToDelete.getMessageID());
            messageHashes.remove(messageToDelete.getMessageHash());
            
            // Remove from status-specific arrays
            if ("Sent".equals(messageToDelete.getStatus())) {
                sentMessages.remove(messageToDelete);
            } else if ("Stored".equals(messageToDelete.getStatus())) {
                storedMessages.remove(messageToDelete);
            } else {
                disregardedMessages.remove(messageToDelete);
            }
            
            System.out.println("✅ Message '" + messageToDelete.getMessageText() + "' successfully deleted.");
            saveMessagesToJSON(); // Save changes
        } else {
            System.out.println("❌ No message found with hash: " + hashToDelete);
        }
    }
    
    /**
     * SHOWS LONGEST MESSAGE
     */
    private static void showLongestMessage() {
        if (allMessages.isEmpty()) {
            System.out.println("❌ No messages available.");
            return;
        }
        
        Message longestMessage = allMessages.get(0);
        for (Message msg : allMessages) {
            if (msg.getMessageText().length() > longestMessage.getMessageText().length()) {
                longestMessage = msg;
            }
        }
        
        System.out.println("📏 LONGEST MESSAGE (" + longestMessage.getMessageText().length() + " chars):");
        System.out.println("To: " + longestMessage.getRecipient());
        System.out.println("Message: " + longestMessage.getMessageText());
        System.out.println("Status: " + longestMessage.getStatus());
        System.out.println("Hash: " + longestMessage.getMessageHash());
    }
    
    /**
     * GENERATES REPORTS
     */
    private static void generateReports() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("📊 COMPREHENSIVE REPORTS");
        System.out.println("=".repeat(50));
        
        System.out.println("1. Message Statistics Report");
        System.out.println("2. Save Messages to JSON");
        System.out.println("3. Load Messages from JSON");
        System.out.print("Choose report option (1-3): ");
        
        int choice = getMenuChoice();
        scanner.nextLine(); // Clear buffer
        
        switch (choice) {
            case 1:
                generateStatisticsReport();
                break;
            case 2:
                saveMessagesToJSON();
                break;
            case 3:
                loadMessagesFromJSON();
                break;
            default:
                System.out.println("❌ Invalid option.");
        }
    }
    
    /**
     * GENERATES STATISTICS REPORT
     */
    private static void generateStatisticsReport() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("📈 COMPREHENSIVE MESSAGE REPORT");
        System.out.println("=".repeat(60));
        
        System.out.println("Total Messages: " + allMessages.size());
        System.out.println("Sent Messages: " + sentMessages.size());
        System.out.println("Stored Messages: " + storedMessages.size());
        System.out.println("Disregarded Messages: " + disregardedMessages.size());
        
        System.out.println("\n🔍 MESSAGE HASHES:");
        for (int i = 0; i < Math.min(messageHashes.size(), 10); i++) {
            System.out.println("• " + messageHashes.get(i));
        }
        if (messageHashes.size() > 10) {
            System.out.println("... and " + (messageHashes.size() - 10) + " more");
        }
        
        System.out.println("\n💬 MESSAGE LENGTH ANALYSIS:");
        int totalChars = 0;
        int shortest = allMessages.isEmpty() ? 0 : Integer.MAX_VALUE;
        int longest = 0;
        
        for (Message msg : allMessages) {
            int length = msg.getMessageText().length();
            totalChars += length;
            shortest = Math.min(shortest, length);
            longest = Math.max(longest, length);
        }
        
        double avgLength = allMessages.isEmpty() ? 0 : (double) totalChars / allMessages.size();
        System.out.println("Shortest message: " + shortest + " characters");
        System.out.println("Longest message: " + longest + " characters");
        System.out.println("Average message length: " + String.format("%.1f", avgLength) + " characters");
    }
    
    /**
     * SAVES MESSAGES TO JSON FILE
     */
    private static void saveMessagesToJSON() {
        Message.storeMessagesToJSON(allMessages, "messages.json");
        System.out.println("✅ All messages saved to messages.json");
    }
    
    /**
     * LOADS MESSAGES FROM JSON FILE
     */
    private static void loadMessagesFromJSON() {
        List<Message> loadedMessages = Message.loadMessagesFromJSON("messages.json");
        if (!loadedMessages.isEmpty()) {
            // Clear existing messages
            allMessages.clear();
            sentMessages.clear();
            storedMessages.clear();
            disregardedMessages.clear();
            messageHashes.clear();
            messageIDs.clear();
            
            // Add loaded messages
            allMessages.addAll(loadedMessages);
            
            // Populate status-specific arrays
            for (Message msg : loadedMessages) {
                messageIDs.add(msg.getMessageID());
                messageHashes.add(msg.getMessageHash());
                
                if ("Sent".equals(msg.getStatus())) {
                    sentMessages.add(msg);
                } else if ("Stored".equals(msg.getStatus())) {
                    storedMessages.add(msg);
                } else {
                    disregardedMessages.add(msg);
                }
            }
            
            System.out.println("✅ Messages loaded successfully into application");
        }
    }
    
    /**
 * RUNS ALL UNIT TESTS
 */
private static void runUnitTests() {
    System.out.println("\n" + "=".repeat(60));
    System.out.println("🔬 RUNNING COMPREHENSIVE UNIT TESTS");
    System.out.println("=".repeat(60));
    
    // Run Login tests
    LoginTest loginTest = new LoginTest();
    loginTest.runAllLoginTests();
    
    // Run Message tests
    MessageTest messageTest = new MessageTest();
    messageTest.runAllMessageTests();
    
    System.out.println("=".repeat(60));
    System.out.println("ALL UNIT TESTS COMPLETED");
    System.out.println("=".repeat(60));
}
    
    // Helper methods for password analysis
    private static boolean containsCapital(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isUpperCase(c)) return true;
        }
        return false;
    }
    
    private static boolean containsNumber(String str) {
        for (char c : str.toCharArray()) {
            if (Character.isDigit(c)) return true;
        }
        return false;
    }
    
    private static boolean containsSpecialChar(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) return true;
        }
        return false;
    }
}