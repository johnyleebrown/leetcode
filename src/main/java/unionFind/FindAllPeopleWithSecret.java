package unionFind;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 2092
 */
public class FindAllPeopleWithSecret {

  /**
   * Intuition 1. Pairs=connections=graph. If we want to propagate the value of some
   * property to connected nodes usually it means we have to use a union find ds since
   * it's more efficient than going through all connected nodes each time and update.
   * (When propagating on a range we usually use Segment Tree).
   *
   * Intuition 2. If someone from current time knows the secret, the next times these
   * people can tell the secret to others. Also if someone from current time knows the
   * secret, people who they contacted previously can't know the secret. We reset the
   * connections of people who talked to each other but don't know the secret - these
   * connections shouldn't affect future meetings.
   *
   * Clean solution with treemap.
   *
   * Time complexity O(m*log(m)), where m is number of meetings. Space - O(m)
   */
  public static class Solution1 {

    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {

      // create <time, index> map
      Map<Integer, List<Integer>> timeToIndexes = new TreeMap<>();
      int m = meetings.length;
      for (int i = 0; i < m; i++) {
        timeToIndexes.putIfAbsent(meetings[i][2], new ArrayList<>());
        timeToIndexes.get(meetings[i][2]).add(i);
      }

      UF uf = new UF(n);
      // base
      uf.union(0, firstPerson);

      // for every time we have a pool of people that talk to each other
      // if someone knows a secret proir to this meeting - all pool will too
      // if not - reset unions from this pool
      for (int time : timeToIndexes.keySet()) {
        Set<Integer> pool = new HashSet<>();

        for (int ind : timeToIndexes.get(time)) {
          int[] currentMeeting = meetings[ind];
          uf.union(currentMeeting[0], currentMeeting[1]);
          pool.add(currentMeeting[0]);
          pool.add(currentMeeting[1]);
        }

        // meeting that took place now should't affect future
        // meetings if people don't know the secret
        for (int i : pool) {
          if (!uf.connected(0, i)) uf.reset(i);
        }
      }

      // if the person is conneted to 0 - they know a secret
      List<Integer> ans = new ArrayList<>();
      for (int i = 0; i < n; i++) {
        if (uf.connected(i, 0)) ans.add(i);
      }
      return ans;
    }

    // regular union find
    private static class UF {

      int[] parent, rank;

      public UF(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
          parent[i] = i;
        }
      }

      public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);

        if (rootP == rootQ)
          return;

        if (rank[rootP] < rank[rootQ]) {
          parent[rootP] = rootQ;
        } else {
          parent[rootQ] = rootP;
          rank[rootP]++;
        }
      }

      public int find(int p) {
        while (parent[p] != p) {
          p = parent[parent[p]];
        }
        return p;
      }

      public boolean connected(int p, int q) {
        return find(p) == find(q);
      }

      public void reset(int p) {
        parent[p] = p;
        rank[p] = 0;
      }
    }
  }

  /**
   * Sort array by time instead of using treemap. Then process iteratively.
   */
  public static class Solution2 {

    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {

      Arrays.sort(meetings, ((a, b) -> a[2] - b[2]));

      UF uf = new UF(n);
      uf.union(0, firstPerson); // base

      // for every time we have a pool of people that talk to each other
      // if someone knows a secret proir to this meeting - all pool will too
      // if not - reset unions from this pool
      int i = 0;
      while (i < meetings.length) {
        int curTime = meetings[i][2];
        Set<Integer> pool = new HashSet<>();

        while (i < meetings.length && curTime == meetings[i][2]) {
          int[] currentMeeting = meetings[i];
          uf.union(currentMeeting[0], currentMeeting[1]);
          pool.add(currentMeeting[0]);
          pool.add(currentMeeting[1]);
          i++;
        }

        // meeting that took place now should't affect future
        // meetings if people don't know the secret
        for (int j : pool) {
          if (!uf.connected(0, j)) uf.reset(j);
        }
      }

      // if the person is conneted to 0 - they know a secret
      List<Integer> ans = new ArrayList<>();
      for (int j = 0; j < n; j++) {
        if (uf.connected(j, 0)) ans.add(j);
      }
      return ans;
    }

    // regular union find
    private static class UF {

      int[] parent, rank;

      public UF(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
          parent[i] = i;
        }
      }

      public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);

        if (rootP == rootQ)
          return;

        if (rank[rootP] < rank[rootQ]) {
          parent[rootP] = rootQ;
        } else {
          parent[rootQ] = rootP;
          rank[rootP]++;
        }
      }

      public int find(int p) {
        while (parent[p] != p) {
          p = parent[parent[p]];
        }
        return p;
      }

      public boolean connected(int p, int q) {
        return find(p) == find(q);
      }

      public void reset(int p) {
        parent[p] = p;
        rank[p] = 0;
      }
    }
  }
}
