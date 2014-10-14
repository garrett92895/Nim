package neumont;

import java.util.Iterator;
import java.util.Scanner;

public class Nim
{
	private static Scanner scan;
	private static Game game;
	private static String row, amount, input;
	private static int computerGames;

	public static void main(String[] args)
	{
		scan = new Scanner(System.in);
		input = "Start";
		game = new Game();
		StateLibrary.createAllStates();
		Menu();
	}

	public static void Menu()
	{
		System.out.println("Welcome to NIM!");
		setup();

		while (!"quit".equalsIgnoreCase(input) && !"0".equalsIgnoreCase(input))
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
			if ("1".equalsIgnoreCase(input) || "setup".equalsIgnoreCase(input))
			{
				setup();
			}
			else if ("2".equalsIgnoreCase(input) || "play".equalsIgnoreCase(input))
			{
				play();
			}
			else if ("values".equalsIgnoreCase(input))
			{
				Iterator<State> stateIterator = StateLibrary.getStates();
				State s = null;
				while (stateIterator.hasNext())
				{
					s = stateIterator.next();
					System.out.println(s.getOne() + " " + s.getTwo() + " " + s.getThree() + " " + s.getValue());
				}
			}
			else if ("wins".equalsIgnoreCase(input))
			{
				System.out.println("Player One has won " + game.getPlayerOne().getWinCount() + " game(s).");
				System.out.println("Player Two has won " + game.getPlayerTwo().getWinCount() + " game(s).");
				System.out.println("Player One as a computer has won " + game.getPlayerOne().getWinAsComputerCount() + " game(s).");
				System.out.println("Player Two as a computer has won " + game.getPlayerTwo().getWinAsComputerCount() + " game(s).");
			}
		}
		System.out.println("Goodbye");
	}

	public static void setup()
	{
		Player playerOne = game.getPlayerOne();
		Player playerTwo = game.getPlayerTwo();
		
		System.out.println("Setting up...");
		System.out.println("Will player one be a player or a computer?");
		String playerOneChoice = scan.nextLine();
		System.out.println("Will player two be a player or a computer?");
		String playerTwoChoice = scan.nextLine();
		boolean playerOneIsComputer = !("player".equalsIgnoreCase(playerOneChoice) || "p".equalsIgnoreCase(playerOneChoice));
		boolean playerTwoIsComputer = !("player".equalsIgnoreCase(playerTwoChoice) || "p".equalsIgnoreCase(playerTwoChoice));
		
		playerOne.setComputer(playerOneIsComputer);
		playerTwo.setComputer(playerTwoIsComputer);
	}

	public static void play()
	{

		System.out.println("Good luck to both players!");
		if (game.bothPlayersAreComputers())
		{
			getComputerGameCount();
		}
		do
		{
			game.reset();
			while (!game.gameEnded())
			{
				if (computerGames <= 0)
				{
					System.out.println("It is " + game.getPlayerTurn() + "'s turn.");
					printGame();
				}
				if (game.isCurrentPlayerComputer())
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
				if (computerGames <= 0)
				{
					System.out.println(game.getPlayerTurn() + " takes " + amount + " from " + row + ".");
				}
				game.switchTurn();
			}
			addWins();
			if (computerGames <= 0)
			{
				System.out.println("Congratulations to " + game.getPlayerTurn() + ", you win!");
			}
			else
			{
				System.out.println("Game finished. " + (computerGames - 1) + " more games to play.");
			}
			StateLibrary.valueMoves(game.isPlayerOneTurn());
			computerGames--;
		}
		while (computerGames > 0);
	}
	
	public static void addWins()
	{
		if (game.isPlayerOneTurn())
		{
			if (game.getPlayerOne().isComputer())
			{
				game.getPlayerOne().addComputerWin();
			}
			else
			{
				game.getPlayerOne().addWin();
			}
		}
		else
		{
			if (game.getPlayerTwo().isComputer())
			{
				game.getPlayerTwo().addComputerWin();
			}
			else
			{
				game.getPlayerTwo().addWin();
			}
		}
	}

	public static void getComputerGameCount()
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
				if ("k".endsWith(input.toLowerCase()))
				{
					input = input.substring(0, input.length() - 1);
					computerGames = Integer.parseInt(input);
					computerGames *= 1000;
				}
				else if ("m".endsWith(input.toLowerCase()))
				{
					input = input.substring(0, input.length() - 1);
					computerGames = Integer.parseInt(input);
					computerGames *= 1000000;
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
	
	public static void printGame()
	{
		System.out.print("1 \t");
		for (int i = 0; i < Game.getCurrentState().getOne(); i++)
		{
			System.out.print(" X ");
		}

		System.out.print("\n2 \t");
		for (int i = 0; i < Game.getCurrentState().getTwo(); i++)
		{
			System.out.print(" X ");
		}

		System.out.print("\n3 \t");
		for (int i = 0; i < Game.getCurrentState().getThree(); i++)
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
			if ("1".equals(row) && Game.getCurrentState().getOne() > 0 || "2".equals(row) && Game.getCurrentState().getTwo() > 0 || "3".equals(row)
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
			if ("row".equalsIgnoreCase(amount))
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
