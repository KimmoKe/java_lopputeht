package main;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// Account username, used for greeting
		String userName = "";
		// User input
		Scanner userInputReader = new Scanner(System.in);
		int userAction = -1;
		// Is user finished?
		boolean isUserFinished = false;
		
		// User interface loop
		do {
			// Ask user what to do
			// User can login, create an account or quit
			userAction = UserInputHandler.checkUserAction(userInputReader);
			
			// User has made a successful selection	
			switch (userAction) {
				case 1:
					// User is trying to log in
					userName = UserInputHandler.userLogin(userInputReader);
					if (userName.isEmpty()) {
						System.out.println("Sisäänkirjautuminen ei onnistunut");
						System.out.println("");
					} else {
						// Login was successful, show message and end
						System.out.println("Tervetuloa takaisin, " + userName + "!");
						System.out.println("");
						//isUserFinished = true;
					}			
					// Debug print
					//System.out.println("Syötit: " + userAction);				
					break;
				case 2:
					// User wants to create an account
					userName = UserInputHandler.createNewAccount(userInputReader);
					if (userName.isEmpty()) {
						System.out.println("Tilin luonti epäonnistui.");
						System.out.println("");
					} else {
						// Account creation successful, show message and end
						System.out.println("Tervetuloa, " + userName + "!");
						System.out.println("");
						//isUserFinished = true;
					}			
					// Debug print
					//System.out.println("Syötit: " + userAction);				
					break;
				case 3:
					// User is finished
					System.out.println("Ohjelma lopetetaan.");
					isUserFinished = true;
					// Debug print
					//System.out.println("Syötit: " + userAction);	
					break;
				default:
					// We shouldn't end up here
					System.out.println("Jokin meni pieleen. Ohjelma lopetetaan.");
					isUserFinished = true;
			}
		} while (!isUserFinished);
		// Close scanner
		userInputReader.close();
	}
}
