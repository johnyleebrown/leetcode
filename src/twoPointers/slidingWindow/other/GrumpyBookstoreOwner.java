package twoPointers.slidingWindow.other;

/**
 * 1052
 *
 * ======
 *
 * Task.
 *
 * Today, the bookstore owner has a store open for customers.length minutes.  Every minute, some number of customers
 * (customers[i]) enter the store, and all those customers leave after the end of that minute.
 *
 * On some minutes, the bookstore owner is grumpy.  If the bookstore owner is grumpy on the i-th minute, grumpy[i] = 1,
 * otherwise grumpy[i] = 0.  When the bookstore owner is grumpy, the customers of that minute are not satisfied,
 * otherwise they are satisfied.
 *
 * The bookstore owner knows a secret technique to keep themselves not grumpy for X minutes straight, but can only use
 * it once.
 *
 * Return the maximum number of customers that can be satisfied throughout the day.
 */
public class GrumpyBookstoreOwner
{
	/**
	 * Pre-counted prefix sums from left to right and from right to left, so when using sliding window we could sum up
	 * the sum on the left and the sum on the right.
	 */
	static class Solution
	{
		public int maxSatisfied(int[] customers, int[] grumpy, int X)
		{
			int n = customers.length;
			int[] prefixFromLeftSide = new int[n];
			for (int i = 0; i < n; i++)
			{
				if (i > 0)
				{
					prefixFromLeftSide[i] = prefixFromLeftSide[i - 1];
				}
				if (grumpy[i] == 0)
				{
					prefixFromLeftSide[i] += customers[i];
				}
			}

			int[] prefixFromRightSide = new int[n];
			for (int i = n - 1; i >= 0; i--)
			{
				if (i < n - 1)
				{
					prefixFromRightSide[i] = prefixFromRightSide[i + 1];
				}
				if (grumpy[i] == 0)
				{
					prefixFromRightSide[i] += customers[i];
				}
			}

			int r = 0;
			int sum = 0;

			for (; r < X - 1; r++)
			{
				sum += customers[r];
			}

			int l = 0;
			int result = 0;

			for (; r < n; r++)
			{
				sum += customers[r];

				int left = l > 0 ? prefixFromLeftSide[l - 1] : 0;
				int right = r < n - 1 ? prefixFromRightSide[r + 1] : 0;
				result = Math.max(result, sum + left + right);

				sum -= customers[l];
				l++;
			}

			return result;
		}
	}
}
