package neumont;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Game
{
	private static State currentState;
	private static ArrayList<Move> moves = new ArrayList<Move>();
	private Player playerOne;
	private Player playerTwo;
	
	private boolean playerOneTurn = true;
	
	public Game()
	{
		try
		{currentState = new State(State.ONE, State.TWO, State.THREE);}
		catch (Exception e)
		{e.printStackTrace();}
		
		moves.clear();
		
		playerOne = new Player();
		playerTwo = new Player();
		
		RandomPlayer();
	}
	
	public Game(Player one, Player two)
	{
		try
		{currentState = new State(State.ONE, State.TWO, State.THREE);}
		catch (Exception e)
		{e.printStackTrace();}
		
		moves.clear();
		
		playerOne = one;
		playerTwo = two;
		
		RandomPlayer();
	}

	public static State getCurrentState()
	{
		return currentState;
	}

	public static void setCurrentState(State currentState)
	{
		Game.currentState = currentState;
	}

	public static Iterator<Move> getMoves()
	{
		return moves.iterator();
	}
	
	public static int getNumOfMoves()
	{
		return moves.size();
	}
	
	public Player getPlayerOne()
	{
		return playerOne;
	}

	public void setPlayerOne(Player playerOne)
	{
		this.playerOne = playerOne;
	}

	public Player getPlayerTwo()
	{
		return playerTwo;
	}

	public void setPlayerTwo(Player playerTwo)
	{
		this.playerTwo = playerTwo;
	}
	
	public void RandomPlayer()
	{
		Random random = new Random();
		playerOneTurn = random.nextInt(2) == 0;
	}
	
	public void switchTurn()
	{
		playerOneTurn = !playerOneTurn;
	}
	
	public Player getCurrentPlayer()
	{
		return playerOneTurn ? playerOne : playerTwo;
	}
	
	public String getPlayerTurn()
	{
		return (playerOneTurn? "Player One" : "Player Two");
	}
	
	public boolean isPlayerOneTurn()
	{
		return playerOneTurn;
	}
	
	public boolean isPlayerTwoTurn()
	{
		return !playerOneTurn;
	}
	
	public boolean isPlayerComputer()
	{
		return playerOneTurn ? getPlayerOne().isComputer() : getPlayerTwo().isComputer();
	}
	
	public boolean gameEnded()
	{
		return (currentState.getOne() == 0 && currentState.getTwo() == 0 && currentState.getThree() == 0);
	}
	
	public int getRowAmount(String row)
	{
		switch (row)
		{
			case "1":
				return getCurrentState().getOne();
			case "2":
				return getCurrentState().getTwo();
			case "3":
				return getCurrentState().getThree();
		}
		return 0;
	}
	
	public void take(String row, String amount)
	{
		try
		{
			int a = Integer.parseInt(amount);
			moves.add(new Move(new State(currentState.getOne(), currentState.getTwo(), currentState.getThree()), playerOneTurn));
			switch (row)
			{
				case "1":
					getCurrentState().setOne((getCurrentState().getOne() - a));
					break;
				case "2":
					getCurrentState().setTwo((getCurrentState().getTwo() - a));
					break;
				case "3":
					getCurrentState().setThree((getCurrentState().getThree() - a));
					break;
			}
		}
		catch(Exception e){}
	}
}
