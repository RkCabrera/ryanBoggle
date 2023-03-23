package model;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/* Boggle.java
 * 
 * Author: Ryan Cabrera
 */
public class Boggle {
	private ArrayList<String> guesses;
	private ArrayList<String> boggleWords;
	private DiceTray theBoard;
	private int score;
	private ArrayList<String> possibleWords;
	private ArrayList<String> foundWords;
	private ArrayList<String> wrongWords;

	public Boggle() {
		boggleWords = new ArrayList<>();
		File file = new File("BoggleWords.txt");
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				boggleWords.add(scanner.nextLine());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}

		score = 0;
		theBoard = new DiceTray();
		possibleWords = new ArrayList<>();
		foundWords = new ArrayList<>();
		wrongWords = new ArrayList<>();
	}

	public Boggle(char[][] board) {
		boggleWords = new ArrayList<>();
		File file = new File("BoggleWords.txt");
		try {
			Scanner scanner = new Scanner(file);
			while (scanner.hasNextLine()) {
				boggleWords.add(scanner.nextLine());
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}

		score = 0;
		theBoard = new DiceTray(board);
		possibleWords = new ArrayList<>();
		foundWords = new ArrayList<>();
		wrongWords = new ArrayList<>();
	}

	public void printBoard() {
		theBoard.printBoard();
	}
	
	public String boardString() {
		return theBoard.getBoardString();
	}

	// start sequence of play.
	public void playBoggle(ArrayList<String> guesses) {
		this.guesses = guesses;
		getPossibleWords(boggleWords);
		searchGuesses(possibleWords);
		wrongGuesses(possibleWords);
		getScore(foundWords);
		endGame(foundWords, possibleWords, wrongWords, score);
	}

	// creates list of all possible words in given dicetray
	private void getPossibleWords(ArrayList<String> boggleWords) {
		for (int i = 0; i < boggleWords.size(); i++) {
			if (theBoard.found(boggleWords.get(i))) {
				possibleWords.add(boggleWords.get(i));
			}
		}
	}

	// iterate through guesses. tally up score.
	private void searchGuesses(ArrayList<String> possibleWords) {
		for (int i = 0; i < guesses.size(); i++) {
			if (possibleWords.contains(guesses.get(i).toLowerCase())) {
				foundWords.add(guesses.get(i));
			}
		}
	}

	private void wrongGuesses(ArrayList<String> possibleWords) {
		for (int i = 0; i < guesses.size(); i++) {
			if (!possibleWords.contains(guesses.get(i).toLowerCase())) {
				wrongWords.add(guesses.get(i));
			}
		}
	}

	// iterate through found words and tally score
	private void getScore(ArrayList<String> foundWords) {
		ArrayList<String> counted = new ArrayList<>();
		for (int i = 0; i < foundWords.size(); i++) {
			if (!counted.contains(foundWords.get(i))) {
				int size = foundWords.get(i).length();

				if (size == 3 || size == 4) {
					score += 1;
				} else if (size == 5) {
					score += 2;
				} else if (size == 6) {
					score += 3;
				} else if (size == 7) {
					score += 5;
				} else if (size >= 8) {
					score += 11;
				}
			}
		}
	}

	public void endGame(ArrayList<String> found, ArrayList<String> possible, ArrayList<String> wrong, int score) {
		System.out.println("Congratulations!\nScore: " + score);

		System.out.println("Words you found:\n---------------");
		for (int i = 0; i < found.size(); i++) {
			System.out.print(found.get(i) + " ");
			if (i % 10 == 0 && i != 0) {
				System.out.println();
			}
		}

		System.out.println("\n\nIncorrect words:\n---------------");
		for (int i = 0; i < wrong.size(); i++) {
			System.out.print(wrong.get(i) + " ");
			if (i % 10 == 0 && i != 0) {
				System.out.println();
			}
		}

		int difference = possible.size() - found.size();
		System.out.println("\n\nYou could have found " + difference + " more words.");
		System.out.println("The computer found all of your words plus these:");
		System.out.println("---------------------------------------------");
		for (int i = 0; i < possible.size(); i++) {
			if (!found.contains(possible.get(i))) {
				System.out.print(possible.get(i) + " ");
			}
			if (i % 10 == 0 && i != 0) {
				System.out.println();
			}
		}
		System.out.println();
	}

	public int getScore() {
		return score;
	}

	public ArrayList<String> getFoundWords() {
		return foundWords;
	}

	public ArrayList<String> getWrongWords() {
		return wrongWords;
	}

	public ArrayList<String> getPossibleWords() {
		return possibleWords;
	}

}
