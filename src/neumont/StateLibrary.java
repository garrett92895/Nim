package neumont;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class StateLibrary
{
	private static ArrayList<State> states = new ArrayList<State>();;

	public static void createAllStates()
	{
		for (int a = 0; a < State.ONE + 1; a++)
		{
			for (int b = 0; b < State.TWO + 1; b++)
			{
				for (int c = 0; c < State.THREE + 1; c++)
				{
					try
					{
						add(new State(a, b, c));
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void add(State s)
	{
		for (State listed : states)
		{
			if (isSame(s, listed))
			{
				return;
			}
		}
		states.add(s);
	}

	public static boolean isSame(State a, State b)
	{
		return (a.getOne() == b.getOne() && a.getTwo() == b.getTwo() && a.getThree() == b.getThree())
				|| (a.getOne() == b.getOne() && a.getTwo() == b.getThree() && a.getThree() == b.getTwo())
				|| (a.getOne() == b.getTwo() && a.getTwo() == b.getOne() && a.getThree() == b.getThree())
				|| (a.getOne() == b.getTwo() && a.getTwo() == b.getThree() && a.getThree() == b.getOne())
				|| (a.getOne() == b.getThree() && a.getTwo() == b.getOne() && a.getThree() == b.getTwo())
				|| (a.getOne() == b.getThree() && a.getTwo() == b.getTwo() && a.getThree() == b.getOne());
	}

	public static Iterator<State> getPossibleStates()
	{
		ArrayList<State> possible = new ArrayList<State>();
		for (State s : states)
		{
			if (isPossibleMove(s))
			{
				possible.add(s);
			}
		}
		return possible.iterator();
	}

	public static boolean isPossibleMove(State state)
	{
		int a1 = state.getOne();
		int b1 = state.getTwo();
		int c1 = state.getThree();
		int a2 = Game.getCurrentState().getOne();
		int b2 = Game.getCurrentState().getTwo();
		int c2 = Game.getCurrentState().getThree();
		return (a1 < a2 && b1 == b2 && c1 == c2) || (a1 == a2 && b1 < b2 && c1 == c2) || (a1 == a2 && b1 == b2 && c1 < c2)
				|| (a1 < a2 && b1 == c2 && c1 == b2) || (a1 == a2 && b1 < c2 && c1 == b2) || (a1 == a2 && b1 == c2 && c1 < b2)
				|| (a1 < b2 && b1 == a2 && c1 == c2) || (a1 == b2 && b1 < a2 && c1 == c2) || (a1 == b2 && b1 == a2 && c1 < c2)
				|| (a1 < b2 && b1 == c2 && c1 == a2) || (a1 == b2 && b1 < c2 && c1 == a2) || (a1 == b2 && b1 == c2 && c1 < a2)
				|| (a1 < c2 && b1 == a2 && c1 == b2) || (a1 == c2 && b1 < a2 && c1 == b2) || (a1 == c2 && b1 == a2 && c1 < b2)
				|| (a1 < c2 && b1 == b2 && c1 == a2) || (a1 == c2 && b1 < b2 && c1 == a2) || (a1 == c2 && b1 == b2 && c1 < a2);
	}

	public static String getBestMove()
	{
		ArrayList<State> best = new ArrayList<State>();
		Iterator<State> possible = getPossibleStates();
		best.add(possible.next());
		while(possible.hasNext())
		{
			State s = possible.next();
			if (s.getValue() < best.get(0).getValue())
			{
				best.clear();
				best.add(s);
			}
			else if (s.getValue() == best.get(0).getValue())
			{
				best.add(s);
			}
		}
		Random random = new Random();
		while (true)
		{
			int choice = random.nextInt(best.size());
			try
			{
				String s = getTo(best.get(choice), Game.getCurrentState());
				if (s != null)
				{
					return s;
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}
	}

	public static String getTo(State to, State from)
	{
		int a = to.getOne();
		int b = to.getTwo();
		int c = to.getThree();
		State[] combinations = new State[6];
		try
		{
			combinations[0] = new State(a, b, c);
		}
		catch (Exception e){}
		try
		{
			combinations[1] = new State(a, c, b);
		}
		catch (Exception e){}
		try
		{
			combinations[2] = new State(b, a, c);
		}
		catch (Exception e){}
		try
		{
			combinations[3] = new State(b, c, a);
		}
		catch (Exception e){}
		try
		{
			combinations[4] = new State(c, a, b);
		}
		catch (Exception e){}
		try
		{
			combinations[5] = new State(c, b, a);
		}
		catch (Exception e){}

		for (State s : combinations)
		{
			if(s != null)
			{
				int a1 = s.getOne();
				int b1 = s.getTwo();
				int c1 = s.getThree();
				int a2 = from.getOne();
				int b2 = from.getTwo();
				int c2 = from.getThree();
				if (a1 < a2 && b1 == b2 && c1 == c2)
				{
					return ("1:" + (a2 - a1));
				}
				else if (a1 == a2 && b1 < b2 && c1 == c2)
				{
					return ("2:" + (b2 - b1));
				}
				else if (a1 == a2 && b1 == b2 && c1 < c2)
				{
					return ("3:" + (c2 - c1));
				}
			}
		}
		return null;
	}

	public static void valueMoves(boolean PlayerOneWin)
	{
		int moves = Game.getNumOfMoves();
		double i = 0;
		Iterator<Move> moveIterator = Game.getMoves();
		while(moveIterator.hasNext())
		{
			Move m = moveIterator.next();
			try
			{
				if (PlayerOneWin)
				{
					getStateWithSameValues(m.getState()).addValue(
							(m.isPlayerOneTurn() ? i / Double.parseDouble(moves + "") : -i / Double.parseDouble(moves + "")));
				}
				else
				{
					getStateWithSameValues(m.getState()).addValue(
							(!m.isPlayerOneTurn() ? i / Double.parseDouble(moves + "") : -i / Double.parseDouble(moves + "")));
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			i++;
		}
	}

	public static State getStateWithSameValues(State s)
	{
		State stateWithSameValues = null;
		for (State st : states)
		{
			if ((s.getOne() == st.getOne() && s.getTwo() == st.getTwo() && s.getThree() == st.getThree())
					|| (s.getOne() == st.getOne() && s.getTwo() == st.getThree() && s.getThree() == st.getTwo())
					|| (s.getOne() == st.getTwo() && s.getTwo() == st.getOne() && s.getThree() == st.getThree())
					|| (s.getOne() == st.getTwo() && s.getTwo() == st.getThree() && s.getThree() == st.getOne())
					|| (s.getOne() == st.getThree() && s.getTwo() == st.getOne() && s.getThree() == st.getTwo())
					|| (s.getOne() == st.getThree() && s.getTwo() == st.getTwo() && s.getThree() == st.getOne()))
			{
				stateWithSameValues = st;
				break;
			}
		}
		return stateWithSameValues;
	}

	public static Iterator<State> getStates()
	{
		return states.iterator();
	}
}