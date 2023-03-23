package views;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Boggle;

/* BoggleGUI.java
 * 
 * Author: Ryan Cabrera
 */

public class BoggleGUI extends Application {

	private Boggle game;
	private GridPane pane;
	private Button newGame = new Button("New Game");
	private Button endGame = new Button("End Game");
	private Label attemptsLabel = new Label("Enter Attempts Below:");
	private Label resultsLabel = new Label("Results:");
	private TextArea diceTrayTA = new TextArea();
	private TextArea guessesTA = new TextArea();
	private TextArea resultsTA = new TextArea();

	@Override
	public void start(Stage stage) {
		stage.setTitle("Boggle");
		makeWindow();

		registerHandlers();

		Scene scene = new Scene(pane, 900, 400);
		stage.setScene(scene);
		;
		stage.show();
	}

	public static void main(String[] args) {
		launch();

	}

	private void makeWindow() {
		Font boardFont = new Font("Courier New", 26);
		Font guessFont = new Font("Courier New", 18);
		Font resultFont = new Font("Courier New", 16);

		diceTrayTA.setMaxWidth(250);
		diceTrayTA.setMouseTransparent(true);
		diceTrayTA.setWrapText(true);
		diceTrayTA.setFont(boardFont);
		guessesTA.setMaxWidth(250);
		guessesTA.setFont(guessFont);
		guessesTA.setWrapText(true);
		guessesTA.setMouseTransparent(true);
		resultsTA.setMaxWidth(350);
		resultsTA.setFont(resultFont);
		resultsTA.setWrapText(true);

		GridPane buttons = new GridPane();
		buttons.setHgap(25);
		buttons.add(newGame, 1, 1);
		buttons.add(endGame, 2, 1);

		pane = new GridPane();
		pane.setHgap(10);
		pane.setVgap(10);
		pane.add(buttons, 1, 1);
		pane.add(attemptsLabel, 2, 1);
		pane.add(diceTrayTA, 1, 2);
		pane.add(resultsLabel, 3, 1);
		pane.add(guessesTA, 2, 2);
		pane.add(resultsTA, 3, 2);
	}

	private void registerHandlers() {
		newGame.setOnAction(new StartGame());
		endGame.setOnAction(new EndGame());

	}

	private class StartGame implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			game = new Boggle();
			guessesTA.setMouseTransparent(false);
			diceTrayTA.setText(game.boardString());
			guessesTA.setText("");
			resultsTA.setText("");
		}
	}
	
	private class EndGame implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent event) {
			guessesTA.setMouseTransparent(true);
			resultsTA.setText("");
			String text = guessesTA.getText();
			System.out.println("TEXT: " + text);
			String[] guesses = text.split("\\s+");
			ArrayList<String> guessesAL = new ArrayList<String>(Arrays.asList(guesses));
			game.playBoggle(guessesAL);
			
			int score = game.getScore();
			
			ArrayList<String> found = game.getFoundWords();
			String foundString = "";
			for (int i = 0; i < found.size(); i++) {
				foundString += found.get(i) + " ";
			}
			
			ArrayList<String> wrong = game.getWrongWords();
			String wrongString = "";
			for (int i = 0; i < wrong.size(); i++) {
				wrongString += wrong.get(i) + " ";
			}
			
			ArrayList<String> possible = game.getPossibleWords();
			String possibleString = "";
			for (int i = 0; i < possible.size(); i++) {
				if (!found.contains(possible.get(i))) {
					possibleString += possible.get(i) + " ";
				}
			}
			
			String resultText = "Your Score: " + score + "\n\n";
			resultText += "Words you found:\n" + foundString + "\n\n";
			resultText += "Incorrect Words:\n" + wrongString + "\n\n";
			resultText += "You could have found " + (possible.size() - found.size());
			resultText += " more words:\n" + possibleString;
			
			resultsTA.setText(resultText);
		}
	}
}
