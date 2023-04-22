package idh.hotseatgames.games.würfel;

import java.util.Random;



public class würfel {

	//Arrays für Darstellung und Werte der einzelnen Karten
	private String[] würfel = {"|1|", "|2|", "|3|", "|4|", "|5|", "|6|"};
	private int[] valueOfWürfel = { 10, 20, 30, 40, 50, 60};
	private String drawnWürfel = ""; 
	
	
	/**
	 * Draws random würfel. 
	 * @param quantity of drawn würfel
	 * @return Returns total points of drawn würfel
	 */
	public int drawWürfel(int quantity) {

		int points = 0; 
		Random randomWürfel = new Random();
		
		for (int i = quantity; i > 0; i--) {
			int indexWürfel = randomWürfel.nextInt(6);
			drawnWürfel += würfel[indexWürfel] + "   ";
			points = points + valueOfWürfel[indexWürfel];
		}
		System.out.println(drawnWürfel + "\n");
		
		return points;
	}
	
	/**
	 * Deletes the drawn cards. 
	 */
	public void getWÜrfelBack() {
		drawnWürfel = "";
	}
}
