package idh.hotseatgames.games.hangman;

import java.util.Arrays;
import java.util.Random;

import idh.hotseatgames.IGame;
import idh.hotseatgames.utils.ResourceReader;
import idh.hotseatgames.utils.UserInput;

public class Hangman implements IGame {

	private static int MAX_NUMBER_OF_GUESSES = 10;
	
	@Override
	public int startRound(String playerName) {
		/**
		 * Generate random word from text file
		 */
		String word;
		int points = 10;
		UserInput input = UserInput.instance();
		Random rnd = new Random();
		String[] wordPool = ResourceReader.readResource(
				"words.txt",
				getClass())
				.toUpperCase().trim().split("\\P{L}+");
		word = wordPool[rnd.nextInt(wordPool.length)];
		char[] wordChars = word.toCharArray();
		
		/**
		 * Create the empty/_ word to fill later
		 */
		char[] playerGuess = new char[word.length()];
		
		for (int i = 0; i < playerGuess.length; i++) {
			playerGuess[i] = '_';
		}
		
		boolean wordIsGuessed = false;
		int tries = 0;
		
		/**
		 * Game Loop start
		 */
		while (!wordIsGuessed && tries != MAX_NUMBER_OF_GUESSES) {
			System.out.print("Current guesses: ");
			System.out.println(playerGuess);
			System.out.printf("You have %d tries left.\n", MAX_NUMBER_OF_GUESSES-tries);
			char guess = input.prompt("Enter your guess: ").toUpperCase().charAt(0);
			tries++;

			/**
			 * Check if guessed character is in word
			 * If so replace the the _ in the empty word by guessed character
			 */
			for (int i = 0; i < word.length(); i++) {
				if (wordChars[i] == guess) {
					playerGuess[i] = guess;
				}
			}
			
			/**
			 * Check if the word is fully guessed
			 * If so change the boolean wordIsGuessed to break the while loop
			 */
			if (isTheWordGuessed(playerGuess)) {
				wordIsGuessed = true;
				System.out.println("Congratulations. You guessed the word");
			}
			
		}
		
		System.out.println("You ran out of tries. The correct word was: ");
		System.out.println(word);
		
		return points - tries;
	}

	@Override
	public String getInstructions() {
		return ResourceReader.readResource("instructions.txt", getClass());
	}

	@Override
	public String getIntroBanner() {
		return ResourceReader.readResource("intro_banner.ascii", getClass());
	}
	
	private boolean isTheWordGuessed(char[] array) {
		return !Arrays.toString(array).contains("_");
	}

}
