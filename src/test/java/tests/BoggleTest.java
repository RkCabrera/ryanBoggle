package tests;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import model.Boggle;

/* BoggleTest.java
 * 
 * Author: Ryan Cabrera
 */

class BoggleTest {

	@Before
	public void setup() {
		guesses.add("ABS");
		guesses.add("ains");
		guesses.add("dets");
	}

	private char[][] longWord = {

			{ 'A', 'B', 'S', 'E' },

			{ 'I', 'M', 'T', 'N' },

			{ 'N', 'D', 'E', 'D' },

			{ 'S', 'S', 'E', 'N' } };

	Boggle game = new Boggle(longWord);
	ArrayList<String> guesses = new ArrayList<>();

	@Test
	void testScore() {
		guesses.add("AIM");
		guesses.add("sent");
		guesses.add("SEND");

		game.playBoggle(guesses);
		assertEquals(game.getScore(), 3);
	}

	@Test
	void testWordOutputs() {
		ArrayList<String> correct = new ArrayList<>();
		ArrayList<String> wrong = new ArrayList<>();

		guesses.add("aim");
		guesses.add("sent");
		guesses.add("send");
		guesses.add("tense");
		guesses.add("absentmindedness");
		guesses.add("notaword");
		guesses.add("notawordeither");

		correct.add("aim");
		correct.add("sent");
		correct.add("send");
		correct.add("tense");
		correct.add("absentmindedness");

		wrong.add("notaword");
		wrong.add("notawordeither");

		game.playBoggle(guesses);

		assertEquals(game.getFoundWords(), correct);
		assertEquals(game.getWrongWords(), wrong);
	}

}
