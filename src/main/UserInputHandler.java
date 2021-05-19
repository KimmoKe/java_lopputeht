package main;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;

public class UserInputHandler {
	
	// When user chooses what to do
	public static int checkUserAction(Scanner userInputReader) {
		// What user wants to do
		int userAction = -1;
		// Allowed actions
		ArrayList<Integer> allowedActions = new ArrayList<Integer>(
				Arrays.asList(1,2,3));
		
		// Until input is satisfactory
		while (userAction == -1) {
			// User interface
			System.out.println("Valitse toiminto");
			System.out.printf("1) Kirjaudu sisään\n2) Luo tili\n3) Lopeta\n");
			System.out.printf("Toiminto: ");
			
			// Check if user input was an integer
			try {
				userAction = Integer.parseInt(userInputReader.nextLine());
			} catch (NumberFormatException e) {
				// User input was not an integer, send an error message.
				System.out.println("Käytä vain numeroita.");
				System.out.println("");
			}
			
			// Check if chosen action is available, send an error message if not available
			if (!allowedActions.contains(userAction)) {
				// Action unavailable
				userAction = -1;
				System.out.println("Virheellinen valinta. Yritä uudelleen.");
				System.out.println("");
			}
		}
		
		return userAction;
	}
	
	// When user wants to login
	public static String userLogin(Scanner userInputReader) {
		// User inputs
		String userName = "";
		String password = "";
		// Value to see what happened during login
		int loginSuccess = 0;
		
		// Get inputs from user
		System.out.printf("Syöta käyttäjänimi: ");
		userName = userInputReader.nextLine();
		System.out.printf("Syötä salasana: ");
		password = userInputReader.nextLine();
		
		// Attempt login
		loginSuccess = FileIOHandling.verifyUser(userName, password);
		// Check results
		if (loginSuccess == 1) {
			// Login was successful
			//System.out.println("Hurraa!");
		} else if (loginSuccess == 0) {
			// Username or password related error
			System.out.println("Käyttäjätunnus tai salasana väärin.");
			userName = "";
		} else if (loginSuccess == -999) {
			// Database empty, shouldn't happen
			System.out.println("Tietokanta tyhjä.");
			userName = "";
		} else if (loginSuccess == -1) {
			// Database read error
			System.out.println("Tietokannan luku ei onnistu.");
			userName = "";
		} else {
			// Something else went wrong
			System.out.println("Jotain meni pieleen.");
			userName = "";
		}
		// No need to have password stored anymore
		password = "";
		
		return userName;
	}
	
	// When user want to create a new account
	public static String createNewAccount (Scanner userInputReader) {
		// User inputs
		String userName = "";
		String password = "";
		// Value to see what during account creation
		int accountCreation = 0;
		
		// Get user inputs
		System.out.printf("Syöta käyttäjänimi: ");
		userName = userInputReader.nextLine();
		System.out.printf("Syötä salasana: ");
		password = userInputReader.nextLine();
		
		// Attempt to create account
		accountCreation = FileIOHandling.registerUser(userName, password);
		// Check results
		if (accountCreation == 1) {
			// Creation successful
			//System.out.println("Hurraa!");
		} else if (accountCreation == 0) {
			// Could not create new account
			System.out.println("Tietojen tallennus epäonnistui.");
			userName = "";
		} else if (accountCreation == -2) {
			// Username in use
			System.out.println("Valitsemasi käyttäjänimi ei käy.");
			userName = "";
		} else {
			// Something else went wrong
			System.out.println("Jotain meni pieleen.");
			userName = "";
		}
		// No need to have password stored anymore
		password = "";
		
		return userName;
	}
}
