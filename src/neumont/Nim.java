package neumont;

import java.util.Iterator;
import java.util.Scanner;

public class Nim
{
	static Scanner scan = new Scanner(System.in);
	static String input = "Start";
	static Game game = new Game();
	static String row;
	static String amount;
	static int computerGames = 0;
	static int PlayerOneWins = 0;
	static int PlayerTwoWins = 0;
	static int PlayerOneComputerWins = 0;
	static int PlayerTwoComputerWins = 0;
	static boolean debugComputers = false;
	static boolean printComputerWins = false;

	public static void main(String[] args)
	{
		StateLibrary.createAllStates();
		Menu();
	}

	public static void Menu()
	{
		System.out.println("Welcome to NIM!");
		setup();

		while (!input.equalsIgnoreCase("quit") && !input.equalsIgnoreCase("0"))
		{
			if (computerGames > 0)
			{
				input = "play";
			}
			else
			{
				System.out.println("Please select an option.");
				System.out.println("0: Quit");
				System.out.println("1: Setup");
				System.out.println("2: Play");
				input = scan.nextLine();
			}
			if (input.equalsIgnoreCase("1") || input.equalsIgnoreCase("setup"))
			{
				setup();
			}
			else if (input.equalsIgnoreCase("2") || input.equalsIgnoreCase("play"))
			{
				play();
			}
			else if (input.equalsIgnoreCase("values"))
			{
				Iterator<State> stateIterator = StateLibrary.getStates();
				State s = null;
				while(stateIterator.hasNext())
				{
					s = stateIterator.next();
					System.out.println(s.getOne() + " " + s.getTwo() + " " + s.getThree() + " " + s.getValue());
				}
			}
			else if (input.equalsIgnoreCase("raw values"))
			{
				Iterator<State> stateIterator = StateLibrary.getStates();
				State s = null;
				while(stateIterator.hasNext())
				{
					s = stateIterator.next();
					System.out.println(s.getOne() + " " + s.getTwo() + " " + s.getThree() + " " + s.getRawValue());
				}
			}
			else if (input.equalsIgnoreCase("wins"))
			{
				System.out.println("Player One has won " + PlayerOneWins + " game(s).");
				System.out.println("Player Two has won " + PlayerTwoWins + " game(s).");
				System.out.println("Player One as a computer has won " + PlayerOneComputerWins + " game(s).");
				System.out.println("Player Two as a computer has won " + PlayerTwoComputerWins + " game(s).");
			}
		}
		System.out.println("Goodbye");
	}

	public static void setup()
	{
		System.out.println("Setting up...");
		System.out.println("Will player one be a player or a computer?");
		input = scan.nextLine();
		if (input.equalsIgnoreCase("player") || input.equalsIgnoreCase("p"))
		{
			game.getPlayerOne().setComputer(false);
		}
		else
		{
			game.getPlayerOne().setComputer(true);
		}

		System.out.println("Will player two be a player or a computer?");
		input = scan.nextLine();
		if (input.equalsIgnoreCase("player") || input.equalsIgnoreCase("p"))
		{
			game.getPlayerTwo().setComputer(false);
		}
		else
		{
			game.getPlayerTwo().setComputer(true);
		}
	}

	public static void play()
	{

		System.out.println("Good luck to both players!");
		if (game.getPlayerOne().isComputer() && game.getPlayerTwo().isComputer())
		{
			System.out.println("How many games would you like the computers to play?");
			input = scan.nextLine();
			try
			{
				computerGames = Integer.parseInt(input);
			}
			catch (Exception e)
			{
				try
				{
					if (input.toLowerCase().endsWith("k"))
					{
						input = input.substring(0, input.length() - 1);
						computerGames = Integer.parseInt(input);
						computerGames *= 1000;
					}
					else if (input.toLowerCase().endsWith("m"))
					{
						input = input.substring(0, input.length() - 1);
						computerGames = Integer.parseInt(input);
						computerGames *= 1000000;
					}
					else if (input.toLowerCase().endsWith("b"))
					{
						input = input.substring(0, input.length() - 1);
						computerGames = Integer.parseInt(input);
						computerGames *= 1000000000;
					}
					else
					{
						System.out.println("Invalid input, defaulting to 1");
						computerGames = 1;
					}
				}
				catch (Exception ex)
				{
					System.out.println("Invalid input, defaulting to 1");
					computerGames = 1;
				}
			}
		}
		do
		{
			game = new Game(game.getPlayerOne(), game.getPlayerTwo());
			while (!game.gameEnded())
			{
				if (computerGames <= 0 || debugComputers)
				{
					System.out.println("It is " + game.getPlayerTurn() + "'s turn.");
					printGame();
				}
				if (game.isPlayerComputer())
				{
					String[] sa = StateLibrary.getBestMove().split(":");
					row = sa[0];
					amount = sa[1];
				}
				else
				{
					row = row();
					amount = amount();
				}
				game.take(row, amount);
				if (computerGames <= 0 || debugComputers)
				{
					System.out.println(game.getPlayerTurn() + " takes " + amount + " from " + row + ".");
				}
				game.switchTurn();
			}
			if (computerGames <= 0)
			{
				System.out.println("Congratulations to " + game.getPlayerTurn() + ", you win!");
				if (game.isPlayerOneTurn())
				{
					if (game.getPlayerOne().isComputer())
					{
						PlayerOneComputerWins++;
					}
					else
					{
						PlayerOneWins++;
					}
				}
				else
				{
					if (game.getPlayerTwo().isComputer())
					{
						PlayerTwoComputerWins++;
					}
					else
					{
						PlayerTwoWins++;
					}
				}
			}
			else
			{
				if (printComputerWins)
				{
					System.out.println("Congratulations to " + game.getPlayerTurn() + ", you win!");
				}
				System.out.println("Game finished. " + (computerGames - 1) + " more games to play.");
				if (game.isPlayerOneTurn())
				{

					PlayerOneComputerWins++;
				}
				else
				{
					PlayerTwoComputerWins++;
				}
			}
			StateLibrary.valueMoves(game.isPlayerOneTurn());
			computerGames--;
		}
		while (computerGames > 0);
	}

	public static void printGame()
	{
		System.out.print("1 \t");
		for (int i = 0; i < game.getCurrentState().getOne(); i++)
		{
			System.out.print(" X ");
		}

		System.out.print("\n2 \t");
		for (int i = 0; i < game.getCurrentState().getTwo(); i++)
		{
			System.out.print(" X ");
		}

		System.out.print("\n3 \t");
		for (int i = 0; i < game.getCurrentState().getThree(); i++)
		{
			System.out.print(" X ");
		}

		System.out.println("\n");
	}

	public static String row()
	{
		while (true)
		{
			System.out.println("Which row do you want to take from?");

			if (Game.getCurrentState().getOne() > 0)
			{
				System.out.println("1");
			}
			if (Game.getCurrentState().getTwo() > 0)
			{
				System.out.println("2");
			}
			if (Game.getCurrentState().getThree() > 0)
			{
				System.out.println("3");
			}
			String row = scan.nextLine();
			if (row.equals("1") && Game.getCurrentState().getOne() > 0 || row.equals("2") && Game.getCurrentState().getTwo() > 0 || row.equals("3")
					&& Game.getCurrentState().getThree() > 0)
			{
				return row;
			}
			System.out.println("Invalid input, please try again.");
		}
	}

	public static String amount()
	{
		while (true)
		{
			System.out.println("How many would you like to take from row " + row + "?");
			System.out.println("Any number from 1 to " + game.getRowAmount(row));
			System.out.println("Type 'row' to reselect a row.");
			String amount = scan.nextLine();

			try
			{
				int amountNum = Integer.parseInt(amount);
				if (amountNum >= 1 && amountNum <= game.getRowAmount(row))
				{
					return amount;
				}
			}
			catch (Exception e)
			{
			}
			if (amount.equalsIgnoreCase("row"))
			{
				row = row();
			}
			else
			{
				System.out.println("Invalid input, please try again.");
			}
		}
	}
}
