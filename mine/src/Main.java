import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

/*
 * '*' = mine, anything else = empty square.
 */
void main() {
    File mytest = new File("testingmin");

    try (Scanner scan = new Scanner(mytest)) {
        int field = 1;

        // Each field starts with a dimensions line: rows columns
        while (scan.hasNextInt()) {
            int x = scan.nextInt();
            int y = scan.nextInt();
            if (x == 0 && y == 0) {
                break;
            }
            scan.nextLine();

            int[][] grid = new int[x][y];
            int[][] mines = new int[x * y][2];
            int mineCount = 0;

            for (int r = 0; r < x; r++) {
                String line = scan.hasNextLine() ? scan.nextLine() : "";
                for (int c = 0; c < y; c++) {
                    if (c < line.length() && line.charAt(c) == '*') {
                        grid[r][c] = -1;
                        mines[mineCount][0] = r;
                        mines[mineCount][1] = c;
                        mineCount++;
                    } else {
                        grid[r][c] = 0;
                    }
                }
            }

            int[] size = {x, y};
            countAdjacent(size, grid, mines, mineCount);

            if (field > 1) {
                System.out.println();
            }
            System.out.println("Field #" + field + ":");
            printGrid(grid);
            field++;
        }
    } catch (FileNotFoundException e) {
        System.out.println("Could not find file: " + mytest.getAbsolutePath());
    }
}
        /*
         * Go to each mine location and add 1 to every adjacent square,
         * Skipping squares off the edge of the grid and squares that are mines (the ones that are -1).
         */
        void countAdjacent(int[] size, int[][] grid, int[][] mines, int mineCount) {
            int rows = size[0];
            int cols = size[1];

            for (int m = 0; m < mineCount; m++) {
                int r = mines[m][0];
                int c = mines[m][1];

                for (int dr = -1; dr <= 1; dr++) {
                    for (int dc = -1; dc <= 1; dc++) {
                        int nr = r + dr;
                        int nc = c + dc;

                        if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) {
                            continue;
                        }
                        if (grid[nr][nc] == -1) {
                            continue;
                        }
                        grid[nr][nc]++;
                    }
                }
            }
        }

        // Print mines as '*' and everything else as its count
        void printGrid(int[][] grid) {
            for (int[] row : grid) {
                StringBuilder sb = new StringBuilder();
                for (int value : row) {
                    sb.append(value == -1 ? '*' : (char) ('0' + value));
                }
                System.out.println(sb);
            }
        }

