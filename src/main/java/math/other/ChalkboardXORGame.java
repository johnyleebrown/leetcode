package math.other;

/**
 * 810
 *
 * We are given non-negative integers nums[i] which are written on a chalkboard.
 * Alice and Bob take turns erasing exactly one number from the chalkboard, with
 * Alice starting first.  If erasing a number causes the bitwise XOR of all the
 * elements of the chalkboard to become 0, then that player loses.  (Also, we'll
 * say the bitwise XOR of one element is that element itself, and the bitwise XOR
 * of no elements is 0.)
 * Also, if any player starts their turn with the bitwise XOR of all the elements
 * of the chalkboard equal to 0, then that player wins.
 * Return True if and only if Alice wins the game, assuming both players play optimally.
 */
public class ChalkboardXORGame {
    /**
     * Time complexity: O(n)
     * Space complexity: O(1)
     */
    public boolean xorGame(int[] nums) {
        int res = 0;
        for (int num : nums) res ^= num;
        return res == 0 || nums.length % 2 == 0;
    }
}
