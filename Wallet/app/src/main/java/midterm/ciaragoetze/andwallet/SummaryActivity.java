package midterm.ciaragoetze.andwallet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import midterm.ciaragoetze.andwallet.data.DataManager;

/**
 * Created by ciaragoetze on 10/20/17.
 */

public class SummaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_summary);

        int income = DataManager.getInstance().getIncome();
        int outcome = DataManager.getInstance().getOutcome();
        int balance = income - outcome;

        TextView tvIncome = (TextView) findViewById(R.id.tvIncome);
        tvIncome.setText(getString(R.string.income, income));

        TextView tvOutcome = (TextView) findViewById(R.id.tvOutcome);
        tvOutcome.setText(getString(R.string.outcome, outcome));

        TextView tvBalance = (TextView) findViewById(R.id.tvBalance);
        tvBalance.setText(getString(R.string.balance, balance));

    }

}
