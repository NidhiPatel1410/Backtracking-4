// In this problem, using backtracking to place the buildings at all possible arrangements, for each arrangement computing farthest
// distance by doing bfs and updating our ans if smaller found. 

// Time Complexity : O(HW P n) HW total permutations on n buildings - exponential
// Space Complexity : O(HW) - grid, queue
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no
public class OptimalBuilPlacement {
    public static class OptimalBP {
        // Declare minDistance variable to capture our answer
        int minDistance;

        // Method for finding min distance
        int findMinDist(int H, int W, int n) {
            // Declare our grid on which we will be placing buildings
            int[][] grid = new int[H][W];
            // Initialize min distance to the max value
            minDistance = Integer.MAX_VALUE;
            // Iterate over grid and mark all -1 initially
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    grid[i][j] = -1;
                }
            }
            // Now first do backtracking and try all possible arrangements of the buildings
            backtrack(grid, 0, 0, n, H, W);
            // Return ans
            return minDistance;
        }

        // BFS method
        private void bfs(int[][] grid, int H, int W) {
            // Take queue for bfs
            Queue<int[]> q = new LinkedList<>();
            // visited matrix for the cells visited
            boolean[][] visited = new boolean[H][W];
            // Now for starting bfs from wherever the buildings are placed, put their
            // location in queue
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    if (grid[i][j] == 0) {
                        // So add the indexes of building locations in queue
                        q.add(new int[] { i, j });
                        visited[i][j] = true;
                    }
                }
            }
            // keep distance variable that will serve as level
            int distance = 0;
            // Dirs array
            int[][] dirs = new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
            // Start bfs
            while (!q.isEmpty()) {
                // Take size, because all the neighbours at same level will have same distance
                int size = q.size();
                // Loop
                for (int i = 0; i < size; i++) {
                    // Poll the current
                    int[] curr = q.poll();
                    // Look at the neighbours
                    for (int[] dir : dirs) {
                        // Compute the neighbour row and col
                        int nr = curr[0] + dir[0];
                        int nc = curr[1] + dir[1];
                        // Check if it is valid and not visited
                        if (nr >= 0 && nr < H && nc >= 0 && nc < W && !visited[nr][nc]) {
                            // Add them in queue
                            q.add(new int[] { nr, nc });
                            // And mark visited
                            visited[nr][nc] = true;
                        }
                    }
                }
                // After each level increment distance
                distance++;
            }
            // After end of bfs, check if the max distance is less than the global
            // minDistance, then update our ans to this smaller value i.e. we are getting
            // smaller value with this arrangement
            minDistance = Math.min(minDistance, distance - 1);

        }

        // Backtracking method
        private void backtrack(int[][] grid, int row, int col, int n, int H, int W) {
            // Base case
            // When we are out of buildings, that means we have placed all n buildings at
            // one possible arrangement
            if (n == 0) {
                // Now check with this arrangement what is the minDistance, so for that call BFS
                bfs(grid, H, W);
                // Then return to try the next possible arrangement
                return;
            }
            // Another base case when we are out of cells on current row, move to next row
            // and reset column to zero
            if (col == W) {
                row = row + 1;
                col = 0;
            }
            // Logic
            // In this method we will simply place n buildings at all possible locations
            // starting from first cell
            for (int i = row; i < H; i++) {
                for (int j = col; j < W; j++) {
                    // Action - Place the building so mark it as 0
                    grid[i][j] = 0;
                    // Recurse - Call this method recursively and place the building at next column
                    // same row, do this till we are out of buildings or out of cells on each row
                    backtrack(grid, i, j + 1, n - 1, H, W);
                    // Backtrack - Once it completes, go back, remove the building placed
                    grid[i][j] = -1;
                }
            }
        }
    }

    public static void main(String[] args) {
        OptimalBP b = new OptimalBP();
        System.out.println(b.findMinDist(2, 2, 4));
    }
}

// Code which gives the building positions: This is same code which small
// changes to return the grid of where the buildings are placed (cells with
// value 0)
// "static void main" must be defined in a public class.
public class Main {
    public static class OptimalBP {
        int minDistance;
        int[][] result;

        int[][] findMinDist(int H, int W, int n) {
            int[][] grid = new int[H][W];
            result = new int[H][W];
            minDistance = Integer.MAX_VALUE;
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    grid[i][j] = -1;
                }
            }
            backtrack(grid, 0, 0, n, H, W);
            System.out.println(minDistance);
            return result;
        }

        private void bfs(int[][] grid, int H, int W) {
            Queue<int[]> q = new LinkedList<>();
            boolean[][] visited = new boolean[H][W];

            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    if (grid[i][j] == 0) {
                        q.add(new int[] { i, j });
                        visited[i][j] = true;
                    }
                }
            }
            int distance = 0;
            int[][] dirs = new int[][] { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };
            while (!q.isEmpty()) {
                int size = q.size();
                for (int i = 0; i < size; i++) {
                    int[] curr = q.poll();
                    for (int[] dir : dirs) {
                        int nr = curr[0] + dir[0];
                        int nc = curr[1] + dir[1];
                        if (nr >= 0 && nr < H && nc >= 0 && nc < W && !visited[nr][nc]) {
                            q.add(new int[] { nr, nc });
                            visited[nr][nc] = true;
                        }
                    }
                }
                distance++;
            }
            // Whenever you are getting a smaller distance from any arrangement, capture
            // that arrangement in result
            if (distance - 1 < minDistance) {
                for (int i = 0; i < H; i++) {
                    for (int j = 0; j < W; j++) {
                        result[i][j] = grid[i][j];
                    }
                }
            }
            minDistance = Math.min(minDistance, distance - 1);

        }

        private void backtrack(int[][] grid, int row, int col, int n, int H, int W) {
            // Base case
            if (n == 0) {
                bfs(grid, H, W);
                return;
            }
            if (col == W) {
                row = row + 1;
                col = 0;
            }
            // Logic
            for (int i = row; i < H; i++) {
                for (int j = col; j < W; j++) {
                    // Action
                    grid[i][j] = 0;
                    // Recurse
                    backtrack(grid, i, j + 1, n - 1, H, W);
                    // Backtrack
                    grid[i][j] = -1;
                }
                col = 0;
            }
        }
    }

    public static void main(String[] args) {
        OptimalBP b = new OptimalBP();
        int[][] result = b.findMinDist(4, 4, 3);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(result[i][j] + " ");
            }
            System.out.println();
        }

    }
}