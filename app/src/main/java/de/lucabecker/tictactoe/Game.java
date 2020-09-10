package de.lucabecker.tictactoe;

class Game {
    public static final int EMPTY = -1;
    public static final int CROSS = 0;
    public static final int CIRCLE = 1;
    public static final int DRAW = 2;
    private boolean mIsCrossTurn;
    private int[][] mBoard;
    private int roundCount;
    private int player1Score;
    private int player2Score;

    /**
     * Class Constructor sets up the gameBoard with {Game.EMPTY} and sets important variables
     */
    public Game() {
        mBoard = new int[3][3];
        resetGame();
        player1Score = 0;
        player2Score = 0;
    }

    /**
     * Returns the Number of Games Player1 has won
     *
     * @return Score of Player1
     */
    public int getPlayer1Score() {
        return player1Score;
    }

    /**
     * Returns the Number of Games Player2 has won
     *
     * @return Score of Player2
     */
    public int getPlayer2Score() {
        return player2Score;
    }

    /**
     * adds 1 to the current Score of the Player passed to the method
     *
     * @param player
     */
    public void addScore(int player) {
        switch (player) {
            case CROSS:
                player1Score++;
                break;
            case CIRCLE:
                player2Score++;
                break;
            default:
                break;
        }
    }

    /**
     * Checks whether or not it is the turn of the Player with the Cross
     *
     * @return Boolean if Cross or not
     */
    public boolean isCrossTurn() {
        return mIsCrossTurn;
    }


    /**
     * Changes the value of whose turn it is
     */
    public void nextPlayer() {
        mIsCrossTurn = !mIsCrossTurn;
    }

    /**
     * Checks for a winner of the Game
     * {@code -1 = Nobody won}
     * {@code 0 = Cross won}
     * {@code 1 = Circle won}
     * {@code 2 = Draw}
     *
     * @return Number for Winner
     */
    public int checkWin() {

        for (int i = 0; i < 3; i++) {
            //Horizontal check
            if (mBoard[i][0] == mBoard[i][1]
                    && mBoard[i][0] == mBoard[i][2]
                    && mBoard[i][0] != EMPTY) {

                return mBoard[i][0];
            }

            //Vertical check
            if (mBoard[0][i] == mBoard[1][i]
                    && mBoard[0][i] == mBoard[2][i]
                    && mBoard[0][i] != EMPTY) {

                return mBoard[0][i];
            }

            //Diagonal check
            if (mBoard[0][0] == mBoard[1][1]
                    && mBoard[0][0] == mBoard[2][2]
                    && mBoard[0][0] != EMPTY) {
                return mBoard[0][0];
            } else if (mBoard[2][0] == mBoard[1][1]
                    && mBoard[2][0] == mBoard[0][2]
                    && mBoard[2][0] != EMPTY) {
                return mBoard[2][0];
            }
        }

        if (roundCount == 9) {
            return DRAW;
        } else {
            return EMPTY;
        }
    }

    /**
     * @param row
     * @param column
     * @param tile
     */
    public void makeMove(int row, int column, int tile) {
        mBoard[row][column] = tile;
        roundCount++;
    }

    public void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                mBoard[i][j] = EMPTY;
            }
        }

        mIsCrossTurn = true;
        roundCount = 0;
    }

    public void resetScores() {
        player1Score = 0;
        player2Score = 0;
    }
}
