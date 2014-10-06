package neumont;

public class State
{
	public static final int ONE = 3;
	public static final int TWO = 5;
	public static final int THREE = 7;
	
	private int[] rowValues;
	
	private double total = 0;
	private int valueCount = 0;

	public State(int one, int two, int three) throws Exception
	{
		
		rowValues = new int[] {one, two, three};
	}

	public int getOne()
	{
		return rowValues[0];
	}

	public void setOne(int one) throws Exception
	{
		if (one < 0 || one > ONE)
		{
			throw new Exception();
		}
		rowValues[0] = one;
	}

	public int getTwo()
	{
		return rowValues[1];
	}

	public void setTwo(int two) throws Exception
	{
		if (two < 0 || two > TWO)
		{
			throw new Exception();
		}
		rowValues[1] = two;
	}

	public int getThree()
	{
		return rowValues[2];
	}

	public void setThree(int three) throws Exception
	{
		if (three < 0 || three > THREE)
		{
			throw new Exception();
		}
		rowValues[2] = three;
	}

	public double getRawValue()
	{
		if (valueCount == 0)
		{
			return 0;
		}
		return total;
	}

	public double getValue()
	{
		if (valueCount == 0)
		{
			return 0;
		}
		return total / Double.parseDouble(valueCount + "");
	}

	public void addValue(double value) throws Exception
	{
		if (value < -1 || value > 1)
		{
			throw new Exception();
		}
		if ((total < Integer.MAX_VALUE - 10000 && total > Integer.MIN_VALUE + 1000) && total < Integer.MAX_VALUE - 10)
		{
			valueCount++;
			total += value;
		}
	}
}
