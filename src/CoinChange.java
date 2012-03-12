

/**
 * 
 */

/**
 * @author Dan Albert
 *
 */
public class CoinChange
{
	public static int[] changeGreedy(int[] coins, int target) throws Exception
	{
		int[] change = new int[coins.length];
		
		while (target > 0)
		{
			int sel = 0;
			int high = 0;
			for (int i = 0; i < coins.length; i++)
			{
				if ((coins[i] > high) && (coins[i] <= target))
				{
					sel = i;
					high = coins[i];
				}
			}
			
			if (coins[sel] <= 0)
			{
				throw new Exception("No valid solutions");
			}
			
			target -= high;
			change[sel]++;
		}
		
		return change;
	}
	
	public static int[] changeDp(int[] coins, int target) throws Exception
	{
		int[][] change = new int[target + 1][coins.length];
		int[] nCoins = new int[target + 1];
		
		nCoins[0] = 0;
		for (int i = 1; i <= target; i++)
		{
			int[] vals = new int[coins.length];
			for (int j = 0; j < coins.length; j++)
			{
				if (coins[j] <= i)
				{
					vals[j] = nCoins[i - coins[j]] + 1;
				}
				else
				{
					vals[j] = Integer.MAX_VALUE;
				}
			}
			
			nCoins[i] = minimum(vals);
			change[i][minLoc(vals)]++;
		}
		
		return change[target];
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		int[] coins = {1, 10, 25, 50};
		int target = 40;
		
		String coinStr = arrayToString(coins);
		System.out.println("Coins to make " + target + " using " + coinStr + ":");
		
		try
		{
			//System.out.println(arrayToString(changeGreedy(coins, target)));
			System.out.println(arrayToString(changeDp(coins, target)));
			//System.out.println(changeDp(coins, target));
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	
	public static String arrayToString(int[] list)
	{
		String output = "";
		boolean first = true;
		
		for (int i = 0; i < list.length; i++)
		{
			if (!first)
			{
				output += ", ";
			}
			
			output += list[i];
			first = false;
		}
		
		return output;
	}
	
	public static int minimum(int[] list)
	{
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < list.length; i++)
		{
			if (list[i] < min)
			{
				min = list[i];
			}
		}
		
		return min;
	}
	
	public static int minLoc(int[] list)
	{
		int index = 0;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < list.length; i++)
		{
			if (list[i] < min)
			{
				min = list[i];
				index = i;
			}
		}
		
		return index;
	}
}
