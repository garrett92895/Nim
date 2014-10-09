package neumont;

public class Player
{
	private boolean computer = false;
	
	public Player()
	{
		
	}
	
	public Player(boolean computer)
	{
		setComputer(computer);
	}

	public boolean isComputer()
	{
		return computer;
	}

	public void setComputer(boolean computer)
	{
		this.computer = computer;
	}
}