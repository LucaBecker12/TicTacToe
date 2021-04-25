package de.lucabecker.tictactoe;

public class Minimax {

    /**
     * This method is called by other classes which want to implement the Minimax-Algorithm for TicTacToe.
     * It returns the best possible move according to the algorithm
     *
     * @param board A 2D array of the Tic Tac Toe game
     * @return An object of the class Move containing the best move
     */
    public static Move findBestMove(int[][] board) {
        Move bestMove = new Move();
        int bestVal = 1000;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == Game.EMPTY) {
                    board[i][j] = Game.CIRCLE;

                    int val = minimax(board, true);

                    board[i][j] = Game.EMPTY;

                    if (val < bestVal) {
                        bestMove.setMove(i, j);
                        bestVal = val;
                    }
                }
            }
        }
        return bestMove;
    }

    private static int minimax(int[][] board, boolean isMax) {
        int score = evaluate(board);

        if (Math.abs(score) == 10) {
            return score;
        }

        if (!isMovesLeft(board)) {
            return 0;
        }

        if (isMax) {
            int best = -1000;

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] == Game.EMPTY) {
                        board[i][j] = Game.CROSS;

                        int val = minimax(board, false);
                        best = Math.max(best, val);

                        board[i][j] = Game.EMPTY;
                    }
                }
            }

            return best;
        } else {
            int best = 1000;

            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] == Game.EMPTY) {
                        board[i][j] = Game.CIRCLE;

                        int val = minimax(board, true);
                        best = Math.min(best, val);
                        board[i][j] = Game.EMPTY;
                    }
                }
            }

            return best;
        }

    }

    private static int evaluate(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2]) {
                if (board[i][0] == Game.CROSS) {
                    return +10;
                } else if (board[i][0] == Game.CIRCLE) {
                    return -10;
                }
            }

            if (board[0][i] == board[1][i] && board[0][i] == board[2][i]) {
                if (board[0][i] == Game.CROSS) {
                    return +10;
                } else if (board[0][i] == Game.CIRCLE) {
                    return -10;
                }
            }
        }

        if (board[0][0] == board[1][1] && board[0][0] == board[2][2]) {
            if (board[0][0] == Game.CROSS) {
                return +10;
            } else if (board[0][0] == Game.CIRCLE) {
                return -10;
            }
        }

        if (board[0][2] == board[1][1] && board[0][2] == board[2][0]) {
            if (board[0][2] == Game.CROSS) {
                return +10;
            } else if (board[0][2] == Game.CIRCLE) {
                return -10;
            }
        }

        return 0;
    }

    private static boolean isMovesLeft(int[][] board) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == Game.EMPTY)
                    return true;
        return false;
    }


}