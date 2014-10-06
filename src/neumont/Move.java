package neumont;

public class Move
{
	private State state;
	private boolean PlayerOneTurn;
	
	public Move(State state, boolean PlayerOne)
	{
		this.state = state;
		PlayerOneTurn = PlayerOne;
	}
	
	public State getState()
	{
		return state;
	}

	public void setState(State endState)
	{
		this.state = endState;
	}

	public boolean isPlayerOneTurn()
	{
		return PlayerOneTurn;
	}

	public void setPlayerOneTurn(boolean playerOneTurn)
	{
		PlayerOneTurn = playerOneTurn;
	}
}
