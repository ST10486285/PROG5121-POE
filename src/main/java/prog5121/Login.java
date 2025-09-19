/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prog5121;

/**
 * @author Chumisa Haya.
 * ChatApp APPLICATION - REGISTRATION AND LOGIN.
 * This class handles user registration, validation, and authentication.
 * It contains methods for username, password, and cell phone number validation/Confirmation.
 */

public class Login {
    // User information storage, these strings will store the registered user's information.
    private String username;
    private String password;
    private String cellPhoneNumber;
    private String firstName;
    private String lastName;
    
    // Default constructor - initializes empty user data
    public Login() {
        this.username = "";
        this.password = "";
        this.cellPhoneNumber = "";
        this.firstName = "";
        this.lastName = "";
        System.out.println("Login system initialized - ready for user registration");
    }
    
    /**
     * USERNAME VALIDATION METHOD
     * Checks if username meets the requirements:
     * 1. Must contain an underscore (_)
     * 2. Must be no more than 5 characters long
     * 
     * @param username - the username to validate
     * @return boolean - true if valid, false if invalid
     */
    public boolean checkUserName(String username) {
        System.out.println("Validating username: " + username);
        
        // Check if username contains underscore
        boolean hasUnderscore = username.contains("_");
        System.out.println("Has underscore: " + hasUnderscore);
        
        // Check if username length is 5 characters or less
        boolean validLength = username.length() <= 5;
        System.out.println("Valid length (<=5 chars): " + validLength);
        
        // Return true only if both conditions are met
        boolean isValid = hasUnderscore && validLength;
        System.out.println("Username validation result: " + isValid);
        
        return isValid;
    }
    
    /**
     * PASSWORD COMPLEXITY VALIDATION METHOD
     * Checks if password meets complexity requirements:
     * 1. At least 8 characters long
     * 2. Contains at least one capital letter
     * 3. Contains at least one number
     * 4. Contains at least one special character

     * boolean will return true if valid, false if invalid
     */
    public boolean checkPasswordComplexity(String password) {
        System.out.println("Validating password complexity...");
        
        // Check 1: Minimum length of 8 characters
        boolean hasMinLength = password.length() >= 8;
        System.out.println("Minimum 8 characters: " + hasMinLength);
        
        // Initialize flags for complexity requirements
        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;
        
        // Check each character in the password
        for (int i = 0; i < password.length(); i++) {
            char currentChar = password.charAt(i);
            
            // Check for capital letters
            if (Character.isUpperCase(currentChar)) {
                hasCapital = true;
            }
            // Check for numbers
            else if (Character.isDigit(currentChar)) {
                hasNumber = true;
            }
            // Check for special characters (not letters or numbers)
            else if (!Character.isLetterOrDigit(currentChar)) {
                hasSpecial = true;
            }
        }
        
        System.out.println("Contains capital letter: " + hasCapital);
        System.out.println("Contains number: " + hasNumber);
        System.out.println("Contains special character: " + hasSpecial);
        
        // All requirements must be met
        boolean isValid = hasMinLength && hasCapital && hasNumber && hasSpecial;
        System.out.println("Password validation result: " + isValid);
        
        return isValid;
    }
    
    /**
     * CELL PHONE NUMBER VALIDATION METHOD
     * Validates South African cell phone number format:
     * 1. Must start with international code +27
     * 2. Must be exactly 12 characters long (+27 + 9 digits = 12 chars)
     * 3. Must contain only numbers after +27
     * 
     * This method uses basic string validation instead of regex
     * 
     * @param cellPhoneNumber - the phone number to validate
     * @return boolean - true if valid, false if invalid
     */
    public boolean checkCellPhoneNumber(String cellPhoneNumber) {
        System.out.println("Validating cell phone number: " + cellPhoneNumber);
        
        // Check 1: Must start with +27
        boolean startsWithPlus27 = cellPhoneNumber.startsWith("+27");
        System.out.println("Starts with +27: " + startsWithPlus27);
        
        // Check 2: Total length must be 12 characters (+27 + 9 digits)
        boolean validLength = cellPhoneNumber.length() == 12;
        System.out.println("Valid length (12 chars): " + validLength);
        
        // Check 3: All characters after +27 must be digits
        boolean allDigitsAfterCode = true;
        if (startsWithPlus27 && validLength) {
            // Get the part after +27 (should be 9 digits)
            String numberPart = cellPhoneNumber.substring(3);
            for (int i = 0; i < numberPart.length(); i++) {
                if (!Character.isDigit(numberPart.charAt(i))) {
                    allDigitsAfterCode = false;
                    break;
                }
            }
        } else {
            allDigitsAfterCode = false;
        }
        System.out.println("All digits after +27: " + allDigitsAfterCode);
        
        // All conditions must be true
        boolean isValid = startsWithPlus27 && validLength && allDigitsAfterCode;
        System.out.println("Cell phone validation result: " + isValid);
        
        return isValid;
    }
    
    /**
     * USER REGISTRATION METHOD
     * Validates all user input and registers the user if all validations pass
     * Returns appropriate messages for each validation failure
     * 
     * @param username - desired username
     * @param password - desired password
     * @param cellPhoneNumber - user's cell phone number
     * @param firstName - user's first name
     * @param lastName - user's last name
     * @return String - registration status message
     */
    public String registerUser(String username, String password, String cellPhoneNumber, 
                             String firstName, String lastName) {
        System.out.println("\n=== STARTING USER REGISTRATION ===");
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        
        StringBuilder resultMessage = new StringBuilder();
        
        // Step 1: Validate username
        System.out.println("\n--- Validating Username ---");
        if (!checkUserName(username)) {
            String errorMsg = "Username is not correctly formatted, please ensure that your username contains an underscore and is no more than five characters in length.";
            System.out.println("USERNAME VALIDATION FAILED: " + errorMsg);
            return errorMsg;
        }
        resultMessage.append("Username successfully captured.\n");
        System.out.println("Username validation passed");
        
        // Step 2: Validate password
        System.out.println("\n--- Validating Password ---");
        if (!checkPasswordComplexity(password)) {
            String errorMsg = "Password is not correctly formatted, please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
            System.out.println("PASSWORD VALIDATION FAILED: " + errorMsg);
            return errorMsg;
        }
        resultMessage.append("Password successfully captured.\n");
        System.out.println("Password validation passed");
        
        // Step 3: Validate cell phone number
        System.out.println("\n--- Validating Cell Phone Number ---");
        if (!checkCellPhoneNumber(cellPhoneNumber)) {
            String errorMsg = "Cell phone number incorrectly formatted or does not contain international code.";
            System.out.println("CELL PHONE VALIDATION FAILED: " + errorMsg);
            return errorMsg;
        }
        resultMessage.append("Cell phone number successfully added.\n");
        System.out.println("Cell phone validation passed");
        
        // All validations passed - store user data
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        
        resultMessage.append("User registered successfully!");
        System.out.println("=== USER REGISTRATION COMPLETED SUCCESSFULLY ===");
        
        return resultMessage.toString();
    }
    
    /**
     * USER LOGIN METHOD
     * Compares input credentials with stored registration data
     * 
     * @param inputUsername - username entered at login
     * @param inputPassword - password entered at login
     * @return boolean - true if credentials match, false otherwise
     */
    public boolean loginUser(String inputUsername, String inputPassword) {
        System.out.println("\n=== ATTEMPTING USER LOGIN ===");
        System.out.println("Input username: " + inputUsername);
        System.out.println("Stored username: " + this.username);
        System.out.println("Password match: " + this.password.equals(inputPassword));
        
        // Check if username and password match stored values
        boolean isAuthenticated = this.username.equals(inputUsername) && 
                                this.password.equals(inputPassword);
        
        System.out.println("Login result: " + (isAuthenticated ? "SUCCESS" : "FAILED"));
        return isAuthenticated;
    }
    
    /**
     * LOGIN STATUS MESSAGE METHOD
     * Returns appropriate message based on login success/failure
     * 
     * @param isLoggedIn - result from loginUser method
     * @return String - welcome message or error message
     */
    public String returnLoginStatus(boolean isLoggedIn) {
        if (isLoggedIn) {
            String welcomeMsg = "Welcome " + firstName + "," + lastName + " It is great to see you again.";
            System.out.println("Returning welcome message: " + welcomeMsg);
            return welcomeMsg;
        } else {
            String errorMsg = "Username or password incorrect, please try again.";
            System.out.println("Returning error message: " + errorMsg);
            return errorMsg;
        }
    }
    
    /**
     * MANUAL TESTING METHOD
     * Simulates unit tests by running validation checks with test data
     */
    public void runManualTests() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("RUNNING MANUAL VALIDATION TESTS");
        System.out.println("=".repeat(50));
        
        // Test 1: Username validation
        System.out.println("\n--- TEST 1: USERNAME VALIDATION ---");
        System.out.println("Testing 'syl_1' (should pass): " + checkUserName("syl_1"));
        System.out.println("Testing 'styles!!!!!!!' (should fail): " + checkUserName("styles!!!!!!!"));
        
        // Test 2: Password validation
        System.out.println("\n--- TEST 2: PASSWORD VALIDATION ---");
        System.out.println("Testing 'Ch&&sec@ke99!' (should pass): " + checkPasswordComplexity("Ch&&sec@ke99!"));
        System.out.println("Testing 'password' (should fail): " + checkPasswordComplexity("password"));
        
        // Test 3: Cell phone validation
        System.out.println("\n--- TEST 3: CELL PHONE VALIDATION ---");
        System.out.println("Testing '+27838968976' (should pass): " + checkCellPhoneNumber("+27838968976"));
        System.out.println("Testing '08966553' (should fail): " + checkCellPhoneNumber("08966553"));
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("MANUAL TESTS COMPLETED");
        System.out.println("=".repeat(50));
    }
    
    // Getter methods for testing purposes
    public String getStoredUsername() { return username; }
    public String getStoredPassword() { return password; }
    public String getStoredCellPhone() { return cellPhoneNumber; }
    public String getStoredFirstName() { return firstName; }
    public String getStoredLastName() { return lastName; }
}