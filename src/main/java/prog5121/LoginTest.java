/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prog5121;

/**
 * UNIT TESTS FOR LOGIN CLASS
 * Tests all validation methods and registration/login functionality
 * @author Chumisa Haya
 */
public class LoginTest {
    
    private Login login = new Login();
    
    /**
     * TEST: Username correctly formatted
     * Test Data: "kyl_1" should return true
     */
    public void testCheckUserNameCorrectlyFormatted() {
        System.out.println("Testing username 'kyl_1' (should pass):");
        boolean result = login.checkUserName("kyl_1");
        System.out.println("Result: " + result + " | Expected: true");
        System.out.println("Test " + (result ? "PASSED" : "FAILED"));
        System.out.println("=".repeat(40));
    }
    
    /**
     * TEST: Username incorrectly formatted
     * Test Data: "kyle!!!!!!!" should return false
     */
    public void testCheckUserNameIncorrectlyFormatted() {
        System.out.println("Testing username 'kyle!!!!!!!' (should fail):");
        boolean result = login.checkUserName("kyle!!!!!!!");
        System.out.println("Result: " + result + " | Expected: false");
        System.out.println("Test " + (!result ? "PASSED" : "FAILED"));
        System.out.println("=".repeat(40));
    }
    
    /**
     * TEST: Password meets complexity requirements
     * Test Data: "Ch&&sec@ke99!" should return true
     */
    public void testCheckPasswordComplexityValid() {
        System.out.println("Testing password 'Ch&&sec@ke99!' (should pass):");
        boolean result = login.checkPasswordComplexity("Ch&&sec@ke99!");
        System.out.println("Result: " + result + " | Expected: true");
        System.out.println("Test " + (result ? "PASSED" : "FAILED"));
        System.out.println("=".repeat(40));
    }
    
    /**
     * TEST: Password does not meet complexity requirements
     * Test Data: "password" should return false
     */
    public void testCheckPasswordComplexityInvalid() {
        System.out.println("Testing password 'password' (should fail):");
        boolean result = login.checkPasswordComplexity("password");
        System.out.println("Result: " + result + " | Expected: false");  // FIXED THIS LINE
        System.out.println("Test " + (!result ? "PASSED" : "FAILED"));
        System.out.println("=".repeat(40));
    }
    
    /**
     * TEST: Cell phone number correctly formatted
     * Test Data: "+27838968976" should return true
     */
    public void testCheckCellPhoneNumberValid() {
        System.out.println("Testing cell number '+27838968976' (should pass):");
        boolean result = login.checkCellPhoneNumber("+27838968976");
        System.out.println("Result: " + result + " | Expected: true");
        System.out.println("Test " + (result ? "PASSED" : "FAILED"));
        System.out.println("=".repeat(40));
    }
    
    /**
     * TEST: Cell phone number incorrectly formatted
     * Test Data: "08966553" should return false
     */
    public void testCheckCellPhoneNumberInvalid() {
        System.out.println("Testing cell number '08966553' (should fail):");
        boolean result = login.checkCellPhoneNumber("08966553");
        System.out.println("Result: " + result + " | Expected: false");
        System.out.println("Test " + (!result ? "PASSED" : "FAILED"));
        System.out.println("=".repeat(40));
    }
    
    /**
     * TEST: Complete registration with valid data
     */
    public void testRegisterUserValidCredentials() {
        System.out.println("Testing registration with valid credentials:");
        String result = login.registerUser("kyl_1", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        System.out.println("Registration Result: " + result);
        boolean success = result.contains("successfully");
        System.out.println("Test " + (success ? "PASSED" : "FAILED"));
        System.out.println("=".repeat(40));
    }
    
    /**
     * TEST: Registration with invalid username
     */
    public void testRegisterUserInvalidUsername() {
        System.out.println("Testing registration with invalid username:");
        String result = login.registerUser("kyle!!!!!!!", "Ch&&sec@ke99!", "+27838968976", "Kyle", "Smith");
        System.out.println("Registration Result: ." + result);
        boolean shouldFail = result.contains("Username is not correctly formatted");
        System.out.println("Test " + (shouldFail ? "PASSED" : "FAILED"));
        System.out.println("=".repeat(40));
    }
    
    /**
     * TEST: Registration with invalid password
     */
    public void testRegisterUserInvalidPassword() {
        System.out.println("Testing registration with invalid password:");
        String result = login.registerUser("kyl_1", "password", "+27838968976", "Kyle", "Smith");
        System.out.println("Registration Result: " + result);
        boolean shouldFail = result.contains("Password is not correctly formatted");
        System.out.println("Test " + (shouldFail ? "PASSED" : "FAILED"));
        System.out.println("=".repeat(40));
    }
    
    /**
     * TEST: Registration with invalid cell phone
     */
    public void testRegisterUserInvalidCellPhone() {
        System.out.println("Testing registration with invalid cell phone:");
        String result = login.registerUser("kyl_1", "Ch&&sec@ke99!", "08966553", "Kyle", "Smith");
        System.out.println("Registration Result: " + result);
        boolean shouldFail = result.contains("Cell phone number incorrectly formatted");
        System.out.println("Test " + (shouldFail ? "PASSED" : "FAILED"));
        System.out.println("=".repeat(40));
    }
    
    /**
     * RUN ALL LOGIN TESTS
     */
    public void runAllLoginTests() {
        System.out.println("\n" + "=".repeat(60));
        System.out.println("🧪 RUNNING ALL LOGIN UNIT TESTS");
        System.out.println("=".repeat(60));
        
        testCheckUserNameCorrectlyFormatted();
        testCheckUserNameIncorrectlyFormatted();
        testCheckPasswordComplexityValid();
        testCheckPasswordComplexityInvalid();
        testCheckCellPhoneNumberValid();
        testCheckCellPhoneNumberInvalid();
        testRegisterUserValidCredentials();
        testRegisterUserInvalidUsername();
        testRegisterUserInvalidPassword();
        testRegisterUserInvalidCellPhone();
        
        System.out.println("=".repeat(60));
        System.out.println("LOGIN TESTS COMPLETED");
        System.out.println("=".repeat(60));
    }
}