package idh.hotseatgames.games.würfel;

import idh.hotseatgames.IGame;
import idh.hotseatgames.utils.Delay;
import idh.hotseatgames.utils.ResourceReader;
import idh.hotseatgames.utils.StringUtils;
import idh.hotseatgames.utils.UserInput;

	public class WürfelSpiel implements IGame {
		
		private UserInput input;
		würfel würfel = new würfel();

		private int roundPlayer(String playerName) {

			int points = 0; //Storage for gained points
			
			System.out.println("____________________________________");
			StringUtils.printLineBreaks(2);
			System.out.println(playerName + " starts. Dealer rolls the dice: ");
			Delay.now(1500);
			StringUtils.printLineBreaks(3);

			points = würfel.drawWürfel(5); //rolls the dice and adds values to 'points'
			
			// As long as the points are under 200 the player decides weather he rolls or not 
			while (points < 200) {
				int drawOrNot = (int) input.prompt(
						playerName + ", you got " + points + " points.\n" 
						+ "Do you want to roll another dice?\n"
						+ "[1: yes, 0: no]\n > ", 0, 1);
						
				
				if (drawOrNot == 1) {
					Delay.now(1000);
					StringUtils.printLineBreaks(1);
					points += würfel.drawWürfel(1);
					StringUtils.printLineBreaks(1);
				} else {
					würfel.getWÜrfelBack(); //Delete rolled dice
					return points;
				}
			}
			
			würfel.getWÜrfelBack();
			return points; 

		}
		
		private int roundDealer( ) {
			
			int points = 0;
			
			System.out.println("____________________________________");
			StringUtils.printLineBreaks(2);
			System.out.println("The Dealer plays:");
			Delay.now(1500);
			StringUtils.printLineBreaks(3);
			
			points += würfel.drawWürfel(5); //Draws 2 cards
			
			//has to roll more dice while he got less than 140 points 
			while (points < 140) {
				System.out.println("The Dealer has to roll another dice.\n");
				Delay.now(1000);
				points += würfel.drawWürfel(1);
			}
			
			würfel.getWÜrfelBack();
			return points; 
		}
		
			
			
			@Override
			public String getInstructions() {
				return ResourceReader.readResource("instructions.txt", getClass());
			}

			@Override
			public String getIntroBanner() {
				return ResourceReader.readResource("intro_banner.ascii", getClass());
			}
			
			
			
			@Override
			public int startRound(String playerName) {
				
				input = UserInput.instance();

				//Simulation of the players round
				int pointsPlayer = roundPlayer(playerName); 
				System.out.println(playerName + " scored " + pointsPlayer + " points.");
				
				//If the player scored more than 200 points, he loses instantly 
				if (pointsPlayer >200)  {
					System.out.println("That's over 200! ");
					return 0; 
		
			} else {
				
				//Simulation of the dealers round 
				int pointsDealer = roundDealer();
				System.out.println("The Dealer scored " + pointsDealer + " points.");
				
				//Conditions to determine the outcome of the round
				
				if (pointsPlayer == 200 && pointsDealer != 200) {
					System.out.println("Only " + playerName + " scored 200 points and wins the round!");
					return 2; 
				}
				if (pointsPlayer == 200 && pointsDealer == 200) {
					System.out.println("Both scored 200 points. It's a draw!");
					return 1; 
				}
				if (pointsPlayer != 200 && pointsDealer == 200) {
					System.out.println("Only the dealer scored 200 points and wins the round!");
					return 0; 
				}
				if (pointsDealer < 200 && pointsPlayer < 200 && pointsDealer > pointsPlayer) {
					System.out.println("The dealer is closer to 200 points and wins the round!");
					return 0; 
				}
				if (pointsDealer < 200 && pointsPlayer < 200 && pointsDealer < pointsPlayer) {
					System.out.println(playerName + " is closer to 200 points and wins the round!");
					return 2; 
				} else {
					System.out.println("Both got " + pointsPlayer + ". It's a draw!");
					return 1;
				}
			}
			}
	}
				
				
