package de.lucabecker.tictactoe;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final ArrayList<ImageView> mViews = new ArrayList<>();
    private TextView mPlayer1Score;
    private TextView mPlayer2Score;
    private TextView mPlayer2TV;
    private Game mGame;
    private Switch mAI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViews();
        initBoard();
    }

    /**
     * Initiates the TicTacToe Board and updates the Player scores
     *
     * @see Game
     */
    private void initBoard() {
        mGame = new Game();
        resetGame();
        updateScore();
    }


    /**
     * Initiates all the Views in the Layout and sets their onCLickListeners and adds all Views of the TicTacToe GameBoard to a field
     */
    private void initViews() {
        ImageView a1 = findViewById(R.id.a1);
        a1.setOnClickListener(this);
        mViews.add(a1);
        ImageView a2 = findViewById(R.id.a2);
        a2.setOnClickListener(this);
        mViews.add(a2);
        ImageView a3 = findViewById(R.id.a3);
        a3.setOnClickListener(this);
        mViews.add(a3);

        ImageView b1 = findViewById(R.id.b1);
        b1.setOnClickListener(this);
        mViews.add(b1);
        ImageView b2 = findViewById(R.id.b2);
        b2.setOnClickListener(this);
        mViews.add(b2);
        ImageView b3 = findViewById(R.id.b3);
        b3.setOnClickListener(this);
        mViews.add(b3);

        ImageView c1 = findViewById(R.id.c1);
        c1.setOnClickListener(this);
        mViews.add(c1);
        ImageView c2 = findViewById(R.id.c2);
        c2.setOnClickListener(this);
        mViews.add(c2);
        ImageView c3 = findViewById(R.id.c3);
        c3.setOnClickListener(this);
        mViews.add(c3);

        mPlayer1Score = findViewById(R.id.player1Score);
        mPlayer2Score = findViewById(R.id.player2Score);

        mPlayer2TV = findViewById(R.id.player2TV);

        Button resetGameButton = findViewById(R.id.resetGameBtn);
        resetGameButton.setOnClickListener(this);

        mAI = findViewById(R.id.switchAI);
        mAI.setOnCheckedChangeListener((CompoundButton.OnCheckedChangeListener) (compoundButton, b) -> {
            resetGame();
            updateScore();
            if (b) {
                mPlayer2TV.setText("AI:");
            } else {
                mPlayer2TV.setText("Player 2:");
            }
        });
    }

    /**
     * Updates the Score of the TicTacToe Players
     *
     * @see Game
     */
    private void updateScore() {
        mPlayer1Score.setText(String.valueOf(mGame.getPlayer1Score()));
        mPlayer2Score.setText(String.valueOf(mGame.getPlayer2Score()));
    }

    /**
     * @param v The Button on which you clicked
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.resetGameBtn) {
            resetGame();
        } else {
            ImageView view = (ImageView) v;
            //Checks whether or not the Tile is empty
            if (Objects.equals(view.getDrawable().getConstantState(), Objects.requireNonNull(getDrawable(R.drawable.tile_empty)).getConstantState())) {
                //Check whose turn it is
                if (mGame.isCrossTurn()) {
                    makeMove(view, R.drawable.tile_cross, Game.CROSS);

                    if (mAI.isChecked()) {
                        Move move = mGame.getAIMove();
                        int row = move.getRow();
                        int col = move.getCol();

                        if (row != -1 && col != -1) {
                            makeMove(mViews.get((3 * row) + col), R.drawable.tile_circle, Game.CIRCLE);
                        }
                    }
                } else {
                    makeMove(view, R.drawable.tile_circle, Game.CIRCLE);
                }

                view.setEnabled(false);

               /*Check if a player has won the Game
                if so, make a Toast and reset the Game, then update the User scores and at last set every tile in the GameBoard to an empty tile
                */
                switch (mGame.checkWin()) {
                    case Game.CROSS:
                        Toast.makeText(this, "Cross won", Toast.LENGTH_SHORT).show();
                        mGame.addScore(Game.CROSS);
                        restartGame();
                        break;
                    case Game.CIRCLE:
                        Toast.makeText(this, "Circle won", Toast.LENGTH_SHORT).show();
                        mGame.addScore(Game.CIRCLE);
                        restartGame();
                        break;
                    case Game.DRAW:
                        Toast.makeText(this, "Draw", Toast.LENGTH_SHORT).show();
                        restartGame();
                        break;
                    default:
                        if (!mAI.isChecked()) {
                            mGame.nextPlayer();
                        }
                        break;
                }
            }
        }
    }


    private void resetGame() {
        for (ImageView view : mViews) {
            view.setImageDrawable(getDrawable(R.drawable.tile_empty));
            view.setEnabled(true);
        }
        mGame.resetGame();
        mGame.resetScores();
        updateScore();
    }

    private void restartGame() {
        updateScore();
        (new Handler()).postDelayed(this::resetGame, 1000);
    }

    private void makeMove(ImageView view, int rId, int player) {
        //Fill the tile with the players drawable
        view.setImageDrawable(getDrawable(rId));

        //Insert CROSS or CIRCLE value into the 2D GameBoard
        String[] position = view.getTag().toString().split(",");
        if (position[0] != null && position[1] != null) {
            int row = Integer.parseInt(position[0]);
            int column = Integer.parseInt(position[1]);
            mGame.makeMove(row, column, player);
        }
    }

}
