class Solution
{
    public List<Set<Integer>> chunks = new ArrayList<>();
    public List<Set<Integer>> rows = new ArrayList<>();
    public List<Set<Integer>> columns = new ArrayList<>();
    public boolean valid = false;

    public void solveSudoku(char[][] board)
    {
        for (int i = 0; i < 9; i++)
        {
            chunks.add(new HashSet<>());
            rows.add(new HashSet<>());
            columns.add(new HashSet<>());
        }
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (board[i][j] != '.')
                {
                    int value = Integer.parseInt(String.valueOf(board[i][j]));
                    chunks.get(i / 3 * 3 + j / 3)
                          .add(value);
                    rows.get(i)
                        .add(value);
                    columns.get(j)
                           .add(value);
                }
            }
        }
        bt(board, 0, 0);
    }

    public void bt(char[][] board, int x, int y)
    {
        if (x == 9 && y == 0)
        {
            valid = true;
            return;
        }
        int nextX = y != 8 ? x : x + 1, nextY = (y + 1) % 9;
        if (board[x][y] != '.')
        {
            bt(board, nextX, nextY);
        }
        else
        {
            for (int num = 1; num <= 9; num++)
            {
                if (chunks.get(x / 3 * 3 + y / 3)
                          .contains(num) || rows.get(x)
                                                .contains(num) || columns.get(y)
                                                                         .contains(num)) continue;
                chunks.get(x / 3 * 3 + y / 3)
                      .add(num);
                rows.get(x)
                    .add(num);
                columns.get(y)
                       .add(num);
                board[x][y] = (char) (num + '0');
                bt(board, nextX, nextY);
                if (valid) return;
                board[x][y] = '.';
                chunks.get(x / 3 * 3 + y / 3)
                      .remove(num);
                rows.get(x)
                    .remove(num);
                columns.get(y)
                       .remove(num);
            }
        }
    }
}

作者：Somnia1337
链接：https://leetcode.cn/problems/sudoku-solver/
来源：力扣（LeetCode）
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。