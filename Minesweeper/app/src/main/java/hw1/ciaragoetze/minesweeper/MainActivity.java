package hw1.ciaragoetze.minesweeper;

import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    private LinearLayout layoutContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutContent = (LinearLayout) findViewById(R.id.layoutContent);

        MSModel.getInstance().initializeGame();

        final MSView msView = (MSView) findViewById(R.id.msView);
        final ToggleButton tg = (ToggleButton) findViewById(R.id.tgButton);
        tg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tg.isChecked()) {
                    msView.setComponent(true);
                } else {
                    msView.setComponent(false);
                }
            }
        });

        Button clearBtn = (Button) findViewById(R.id.clearBtn);
        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msView.clearBoard();
            }
        });

    }

    public void endGame(boolean gameWon, String endGameMessage) {
        if (!gameWon) {
            if (endGameMessage == MSView.GAME_LOST_BY_FLAG) {
                Snackbar.make(layoutContent, R.string.game_lost_by_flag,
                        Snackbar.LENGTH_LONG).show();
            }else {
                Snackbar.make(layoutContent, R.string.game_lost_by_mine,
                        Snackbar.LENGTH_LONG).show();
            }
        } else {
            Snackbar.make(layoutContent, R.string.game_won,
                    Snackbar.LENGTH_LONG).show();
        }
    }

}
