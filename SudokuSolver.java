import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SudokuSolver {

    private List<Set<Integer>> chunks;
    private List<Set<Integer>> rows;
    private List<Set<Integer>> columns;
    private boolean valid;

    public SudokuSolver() {
        initializeDataStructures();
    }

    private void initializeDataStructures() {
        chunks = new ArrayList<>();
        rows = new ArrayList<>();
        columns = new ArrayList<>();
        valid = false;

        for (int i = 0; i < 9; i++) {
            chunks.add(new HashSet<>());
            rows.add(new HashSet<>());
            columns.add(new HashSet<>());
        }
    }

    public void solveSudoku(char[][] board) {
        initializeDataStructures();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    int value = Character.getNumericValue(board[i][j]);
                    addToSets(value, i, j);
                }
            }
        }

        backtrack(board, 0, 0);
    }

    private void addToSets(int num, int x, int y) {
        int chunkIndex = x / 3 * 3 + y / 3;
        chunks.get(chunkIndex).add(num);
        rows.get(x).add(num);
        columns.get(y).add(num);
    }

    private void removeFromSets(int num, int x, int y) {
        int chunkIndex = x / 3 * 3 + y / 3;
        chunks.get(chunkIndex).remove(num);
        rows.get(x).remove(num);
        columns.get(y).remove(num);
    }

    private void backtrack(char[][] board, int x, int y) {
        if (x == 9 && y == 0) {
            valid = true;
            return;
        }

        int nextX = y != 8 ? x : x + 1;
        int nextY = (y + 1) % 9;

        if (board[x][y] != '.') {
            backtrack(board, nextX, nextY);
        } else {
            for (int num = 1; num <= 9; num++) {
                if (isValidPlacement(num, x, y)) {
                    addToSets(num, x, y);
                    board[x][y] = (char) (num + '0');

                    backtrack(board, nextX, nextY);

                    if (valid) {
                        return;
                    }

                    board[x][y] = '.';
                    removeFromSets(num, x, y);
                }
            }
        }
    }

    private boolean isValidPlacement(int num, int x, int y) {
        int chunkIndex = x / 3 * 3 + y / 3;
        return !chunks.get(chunkIndex).contains(num)
                && !rows.get(x).contains(num)
                && !columns.get(y).contains(num);
    }
}
