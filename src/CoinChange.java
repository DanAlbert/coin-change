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
		Integer[][] change = new Integer[target + 1][coins.length];
		int[] nCoins = new int[target + 1];
		
		for (int i = 0; i < coins.length; i++)
		{
			change[0][i] = 0;
		}
		
		for (int i = 1; i <= target; i++)
		{
			for (int j = 0; j < coins.length; j++)
			{
				if (coins[j] <= i)
				{
					int offset = 0;
					Integer lastVal;
					
					do
					{
						lastVal = change[i][j] = change[i - coins[j]][j - offset];
						offset++;
					} while (lastVal == null);
					
					change[i][j] = lastVal + 1;
				}
				else
				{
					change[i][j] = null;
				}
			}
			
			nCoins[i] = minimum(change[i]);
		}
		
		int[] result = new int[coins.length];
		while (target > 0)
		{
			Integer minLoc = minLoc(change[target]);
			result[minLoc]++;
			target -= coins[minLoc];
		}
		
		return result;
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
	
	public static int minimum(Integer[] list)
	{
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < list.length; i++)
		{
			if ((list[i] != null) && (list[i] < min))
			{
				min = list[i];
			}
		}
		
		return min;
	}
	
	public static int minLoc(Integer[] list)
	{
		int index = 0;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < list.length; i++)
		{
			if ((list[i] != null) && (list[i] < min))
			{
				min = list[i];
				index = i;
			}
		}
		
		return index;
	}
}
