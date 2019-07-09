package medium.dp;

/**
 * 790
 * We have two types of tiles: a 2x1 domino shape,
 * and an "L" tromino shape. These shapes may be rotated.
 */
public class DominoAndTrominoTiling {
    /**
     * dp[n]=dp[n-1]+dp[n-2] + 2*(dp[n-3]+…+d[0]) = dp[n-1]+dp[n-3]+dp[n-1] = 2*dp[n-1]+dp[n-3]
     * because dp[i - 1] = dp[i-2]+dp[i-3]+2*(dp[i-4]+..+dp[0])
     * Time complexity: O(n)
     * Space complexity: O(n)
     */
    private static final int MOD = 1000000007;

    public int numTilings(int N) {
        if (N < 0) return 0;
        if (N == 0) return 1;
        if (N < 3) return N;

        int[] dp = new int[N + 1];
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i <= N; i++) {
            dp[i] = 2 * dp[i - 1] % MOD + dp[i - 3] % MOD;
            dp[i] %= MOD;
        }

        return dp[N];
    }
}
