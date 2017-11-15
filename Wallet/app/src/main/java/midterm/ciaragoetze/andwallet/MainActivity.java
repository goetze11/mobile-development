package midterm.ciaragoetze.andwallet;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import midterm.ciaragoetze.andwallet.data.DataManager;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tgName)
    ToggleButton tgName;

    @BindView(R.id.etAmount)
    EditText etAmount;

    @BindView(R.id.etName)
    EditText etName;

    @BindView(R.id.layoutContent)
    LinearLayout layoutContent;

    private int income = 0;
    private int outcome = 0;
    private int balance = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnSave)
    public void addRow(Button btnSave) {
        if (etName.getText().toString().matches("") || etAmount.getText().toString().matches("")){
            Snackbar.make(layoutContent, "Please input a transaction", Snackbar.LENGTH_LONG).show();
            return;
        }else {

            final View displayRow = getLayoutInflater().inflate(R.layout.saved_row, null, false);

            TextView tvName = (TextView) displayRow.findViewById(R.id.tvName);
            tvName.setText(etName.getText().toString());

            TextView tvAmount = (TextView) displayRow.findViewById(R.id.tvAmount);
            tvAmount.setText(etAmount.getText().toString());

            ImageView ivType = displayRow.findViewById(R.id.ivType);
            if (tgName.isChecked()) {
                ivType.setImageResource(R.drawable.ic_action_arrow_bottom);
                outcome = outcome + Integer.parseInt
                        (etAmount.getText().toString().replaceAll("[\\D]", ""));
            } else {
                ivType.setImageResource(R.drawable.ic_action_arrow_top);
                income = income + Integer.parseInt
                        (etAmount.getText().toString().replaceAll("[\\D]", ""));
            }

            layoutContent.addView(displayRow, 0);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_balance:
                balance = income - outcome;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage(getString(R.string.balance_message, balance)).setTitle(R.string.balance_title);
                builder.setNegativeButton(R.string.remove_alert,
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
                break;

            case R.id.action_delete:
                income = 0;
                outcome = 0;
                layoutContent.removeAllViews();
                break;

            case R.id.action_summary:
                Intent intentDetails = new Intent();
                intentDetails.setClass(MainActivity.this, SummaryActivity.class);
                //intentDetails.putExtra("total_income",income);
                //intentDetails.putExtra("total_outcome",outcome);
                DataManager.getInstance().setIncome(income);
                DataManager.getInstance().setOutcome(outcome);
                startActivity(intentDetails);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}













