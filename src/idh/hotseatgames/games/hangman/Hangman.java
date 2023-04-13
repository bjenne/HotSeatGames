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
		
		char[] playerGuess = new char[word.length()];
		
		for (int i = 0; i < playerGuess.length; i++) {
			playerGuess[i] = '_';
		}
		
		boolean wordIsGuessed = false;
		int tries = 0;
		
		while (!wordIsGuessed && tries != MAX_NUMBER_OF_GUESSES) {
			System.out.print("Current guesses: ");
			System.out.println(playerGuess);
			System.out.printf("You have %d tries left.\n", MAX_NUMBER_OF_GUESSES-tries);
			char guess = input.prompt("Enter your guess: ").toUpperCase().charAt(0);
			tries++;

			for (int i = 0; i < word.length(); i++) {
				if (wordChars[i] == guess) {
					playerGuess[i] = guess;
				}
			}
			
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
