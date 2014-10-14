package neumont;

public class Player
{
	private boolean computer = false;
	private int winCount = 0;
	private int winAsComputerCount = 0;	
	
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

	public int getWinCount()
	{
		return winCount;
	}

	public void setWinCount(int winCount)
	{
		this.winCount = winCount;
	}

	public int getWinAsComputerCount()
	{
		return winAsComputerCount;
	}

	public void setWinAsComputerCount(int winAsComputerCount)
	{
		this.winAsComputerCount = winAsComputerCount;
	}
	
	public void addWin()
	{
		winCount++;
	}
	
	public void addComputerWin()
	{
		winAsComputerCount++;
	}
}