package views;
import java.util.Scanner;

import model.Boggle;

import java.util.ArrayList;

/* BoggleConsole.java
 * 
 * Author: Ryan Cabrera
 */
public class BoggleConsole {

	public static void main(String[] args) {
		Boggle game = new Boggle();

		ArrayList<String> guesses = new ArrayList<>();
		game.printBoard();
		System.out.println("Enter words or ZZ to quit:");
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String input = scanner.nextLine();

			if (input.equals("ZZ")) {
				break;
			}

			String[] words = input.split(" ");
			for (String word : words) {
				if (!guesses.contains(word.toLowerCase()))
					guesses.add(word.toLowerCase());
			}
		}
		game.playBoggle(guesses);
	}

}
