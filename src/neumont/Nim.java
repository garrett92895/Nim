package neumont;

import java.util.Iterator;
import java.util.Scanner;

public class Nim
{
	static Scanner scan;
	static Game game;
	static String row, amount, input;
	static int computerGames, PlayerOneWins, PlayerTwoWins,
	PlayerOneComputerWins, PlayerTwoComputerWins;
	static boolean debugComputers, printComputerWins;

	public static void main(String[] args)
	{
		scan = new Scanner(System.in);
		input = "Start";
		game = new Game();
		computerGames = 0;
		PlayerOneWins = 0;
		PlayerTwoWins = 0;
		PlayerOneComputerWins = 0;
		PlayerTwoComputerWins = 0;
		debugComputers = false;
		printComputerWins = false;
		
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
				while(stateIterator.hasNext())
				{
					s = stateIterator.next();
					System.out.println(s.getOne() + " " + s.getTwo() + " " + s.getThree() + " " + s.getValue());
				}
			}
			else if ("wins".equalsIgnoreCase(input))
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

		game.getPlayerOne().setComputer(!("player".equalsIgnoreCase(input) || "p".equalsIgnoreCase(input)));

		System.out.println("Will player two be a player or a computer?");
		input = scan.nextLine();

		game.getPlayerTwo().setComputer(!("player".equalsIgnoreCase(input) || "p".equalsIgnoreCase(input)));
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
			if ("1".equals(row) && Game.getCurrentState().getOne() > 0 || "2".equals(row) && Game.getCurrentState().getTwo() > 0 || 
					"3".equals(row) && Game.getCurrentState().getThree() > 0)
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

	
	public static Scanner getScan() {
		return scan;
	}

	public static void setScan(Scanner scan) {
		Nim.scan = scan;
	}

	public static Game getGame() {
		return game;
	}

	public static void setGame(Game game) {
		Nim.game = game;
	}

	public static String getRow() {
		return row;
	}

	public static void setRow(String row) {
		Nim.row = row;
	}

	public static String getAmount() {
		return amount;
	}

	public static void setAmount(String amount) {
		Nim.amount = amount;
	}

	public static String getInput() {
		return input;
	}

	public static void setInput(String input) {
		Nim.input = input;
	}

	public static int getComputerGames() {
		return computerGames;
	}

	public static void setComputerGames(int computerGames) {
		Nim.computerGames = computerGames;
	}

	public static int getPlayerOneWins() {
		return PlayerOneWins;
	}

	public static void setPlayerOneWins(int playerOneWins) {
		PlayerOneWins = playerOneWins;
	}

	public static int getPlayerTwoWins() {
		return PlayerTwoWins;
	}

	public static void setPlayerTwoWins(int playerTwoWins) {
		PlayerTwoWins = playerTwoWins;
	}

	public static int getPlayerOneComputerWins() {
		return PlayerOneComputerWins;
	}

	public static void setPlayerOneComputerWins(int playerOneComputerWins) {
		PlayerOneComputerWins = playerOneComputerWins;
	}

	public static int getPlayerTwoComputerWins() {
		return PlayerTwoComputerWins;
	}

	public static void setPlayerTwoComputerWins(int playerTwoComputerWins) {
		PlayerTwoComputerWins = playerTwoComputerWins;
	}

	public static boolean isDebugComputers() {
		return debugComputers;
	}

	public static void setDebugComputers(boolean debugComputers) {
		Nim.debugComputers = debugComputers;
	}

	public static boolean isPrintComputerWins() {
		return printComputerWins;
	}

	public static void setPrintComputerWins(boolean printComputerWins) {
		Nim.printComputerWins = printComputerWins;
	}
}
