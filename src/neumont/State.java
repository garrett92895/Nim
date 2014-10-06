package neumont;

public class State
{
	public static final int ONE = 3;
	public static final int TWO = 5;
	public static final int THREE = 7;
	private int one;
	private int two;
	private int three;
	private double total;
	private int valueCount;

	public State(int one, int two, int three) throws Exception
	{
		setOne(one);
		setTwo(two);
		setThree(three);
		addValue(0);
	}

	public State(int one, int two, int three, double value) throws Exception
	{
		setOne(one);
		setTwo(two);
		setThree(three);
		addValue(value);
	}

	public int getOne()
	{
		return one;
	}

	public void setOne(int one) throws Exception
	{
		if (one < 0 || one > ONE)
		{
			throw new Exception();
		}
		this.one = one;
	}

	public int getTwo()
	{
		return two;
	}

	public void setTwo(int two) throws Exception
	{
		if (two < 0 || two > TWO)
		{
			throw new Exception();
		}
		this.two = two;
	}

	public int getThree()
	{
		return three;
	}

	public void setThree(int three) throws Exception
	{
		if (three < 0 || three > THREE)
		{
			throw new Exception();
		}
		this.three = three;
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
