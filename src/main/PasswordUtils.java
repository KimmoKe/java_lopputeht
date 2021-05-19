// SOURCE
// https://www.appsdeveloperblog.com/encrypt-user-password-example-java/

package main;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

// Utilities for hashing passwords and verifying if a given password matches the one on record
public class PasswordUtils {
	
	// For generating random numbers. SecureRandom() is cryptographically strong random number generator
	private static final Random RANDOM = new SecureRandom();
	// Alphabet used to generate salt
	private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	// Number of iterations during hashing
	private static final int ITERATIONS = 10000;
	// Length of encryption key
	private static final int KEY_LENGTH = 256;
	
	// Generate salt
	public static String getSalt(int length) {
		// Initialise salt variable
		StringBuilder returnValue = new StringBuilder(length);
		
		// Randomly grab characters from ALPHABET to construct salt
		for (int i = 0; i < length; i++) {
			returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}
		
		return new String(returnValue);
	}
	
	// Generate salted hash
	public static byte[] hash(char[] password, byte[] salt) {
		// Specs of the key for calculating the hash
		PBEKeySpec spec = new PBEKeySpec(password, salt, ITERATIONS, KEY_LENGTH);
		
		// Get rid of cleartext password
		Arrays.fill(password, Character.MIN_VALUE);
		
		try {
			// Generate hash
			SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			return skf.generateSecret(spec).getEncoded();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			// If something goes wrong
			System.out.println("Jokin meni pieleen.");
			return null;
		} finally {
			// Get rid of key specs
			spec.clearPassword();
		}
	}
	
	// Hash and encode password
	public static String generateSecurePassword(String password, String salt) {
		// Return initialisation
		String returnValue = null;
		
		// Generate salted hash of password
		byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
		
		if (securePassword != null) {
			// Encode hash into Base64 String
			returnValue = Base64.getEncoder().encodeToString(securePassword);
		}
		return returnValue;
	}
	
	// Verify user's login
	public static boolean verifyUserPassword(String providedPassword, String securePassword, String salt) {
		// Return initialisation
		boolean returnValue = false;
		
		// Encrypt password used for login
		String newSecurePassword = generateSecurePassword(providedPassword, salt);
		// Check if it matches what is on record
		returnValue = newSecurePassword.equals(securePassword);
		
		return returnValue;
	}
}
