package pollub.ism.lab04;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private Button[][] buttons = new Button[3][3];
    private boolean playerXTurn = true;
    private int roundCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this::onClick);
            }
        }
    }
    public void onClick(View view) {
        if (!((Button) view).getText().toString().equals("")) {
            return;
        }
        if (playerXTurn) {
            ((Button) view).setText("X");
        } else {
            ((Button) view).setText("O");
        }
        roundCounter++;
        if (checkWin()) {
            if (playerXTurn) {
                player1Wins();
            } else {
                player2Wins();
            }
        } else if (roundCounter == 9) {
            draw();
        } else {
            playerXTurn = !playerXTurn;
        }
    }
    private boolean checkWin() {
        String[][] field = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }
        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }
        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }
    private void player1Wins() {
        Toast.makeText(this, "Gracz X wygrał!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }
    private void player2Wins() {
        Toast.makeText(this, "Gracz O wygrał!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }
    private void draw() {
        Toast.makeText(this, "Remis!", Toast.LENGTH_SHORT).show();
        resetBoard();
    }
    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCounter = 0;
        playerXTurn = true;
    }
    private void resetGame() {
        resetBoard();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCounter", roundCounter);
        outState.putBoolean("playerXTurn", playerXTurn);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCounter = savedInstanceState.getInt("roundCounter");
        playerXTurn = savedInstanceState.getBoolean("playerXTurn");
    }
}