package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringJoiner;

public class FileIOHandling {
	
	// File handle for "database"
	private static final String FILEHANDLE = "storage/database.txt";
	// Delimiter used in concatenation
	private static final String DELIMITER =";";
	
	// Verifying if user is registered
	public static int verifyUser(String uname, String pword) {
		// Return value to indicate if something went wrong
		int returnValue = 0;
		// Values for user validation
		boolean usernameFoundOnFile = false;
		boolean userMatchFound = false;
		// When reading lines from "database"
		String fileLine = null;
		// List for user handling
		ArrayList<String> listOfUsers = new ArrayList<>();
		
		// Get users from "database"
		try (BufferedReader reader = new BufferedReader(new FileReader(FILEHANDLE))) {
			fileLine = reader.readLine();
			while (fileLine != null) {
				listOfUsers.add(fileLine);
				fileLine = reader.readLine();
			}
		} catch (IOException e) {
			System.out.println("'Tietokannan' luku ei onnistunut." + e.getMessage());
			returnValue = -1;
		}
		
		// Try to find username
		if (listOfUsers.size() != 0) {
			for (String userLine: listOfUsers) {
				// Split the user line into parts
				String[] parts = userLine.split(DELIMITER);
				String uname2 = parts[0];
				String pword2 = parts[1];
				String salt = parts[2];
				
				// See if username was found
				if (uname.equals(uname2)) {
					// Username found
					usernameFoundOnFile = true;
				} else {
					continue;
				}
				
				// If username was found, check if given password matches
				if (usernameFoundOnFile) {
					userMatchFound = PasswordUtils.verifyUserPassword(pword, pword2, salt);
					if (userMatchFound) {
						// User found and verified
						returnValue = 1;
						break;
					} else {
						// Username correct but password wrong
						returnValue = 0;
						break;
					}
				}
			}
		} else {
			// No users on file, file is empty for some reason
			returnValue = -999;
		}
		
		return returnValue;
	}
	
	// Registering new user to the "database"
	public static int registerUser(String uname, String pword) {
		// Return value to indicate if something went wrong
		int returnValue = 0;
		// Value to check if username is already in use
		boolean usernameInUse = false;
		// Variables for password securing
		String securedPassword = "";
		String saltForPassword = "";
		int saltLength = 30;
		// For creating a concatenated String for storage
		StringJoiner joiner = new StringJoiner(DELIMITER);
		String joinedString = "";
		// When reading lines from "database"
		String fileLine = null;
		// List for user handling
		ArrayList<String> listOfUsers = new ArrayList<>();
		
		// Get users from "database"
		try (BufferedReader reader = new BufferedReader(new FileReader(FILEHANDLE))) {
			fileLine = reader.readLine();
			while (fileLine != null) {
				listOfUsers.add(fileLine);
				fileLine = reader.readLine();
			}
		} catch (IOException e) {
			System.out.println("'Tietokannan' luku ei onnistunut." + e.getMessage());
			returnValue = -1;
		}
		
		// See if username is already in use
		if (listOfUsers.size() != 0) {
			for (String userLine: listOfUsers) {
				// Split the user line into parts
				String[] parts = userLine.split(DELIMITER);
				String uname2 = parts[0];
				
				// Compare given username with stored
				if (uname.equals(uname2)) {
					usernameInUse = true;
					break;
				} else {
					continue;
				}
			}
		} else {
			// File empty, no users
			usernameInUse = false;
		}
		
		if (usernameInUse) {
			// Username is already in use
			returnValue = -2;
		} else {
			// Add user into "database"
			// Generate salt for securing password
			saltForPassword = PasswordUtils.getSalt(saltLength);
			
			// Secure user password for storage
			securedPassword = PasswordUtils.generateSecurePassword(pword, saltForPassword);
			
			// Concatenate username, secured password and salt for storage
			joiner.add(uname).add(securedPassword).add(saltForPassword);
			joinedString = joiner.toString();
			
			// Store user details
			try (
				FileWriter fw = new FileWriter(FILEHANDLE,true); 
				BufferedWriter bw = new BufferedWriter(fw); 
				PrintWriter out = new PrintWriter(bw)
			) {
				out.println(joinedString);
				returnValue = 1;
			} catch (IOException e) {
				System.out.println("Tietojen tallennus epäonnistui" + e.getMessage());
				returnValue = 0;
			}
		}
		
		return returnValue;
	}
}
