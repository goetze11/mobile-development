package midterm.ciaragoetze.andwallet.data;

/**
 * Created by ciaragoetze on 10/20/17.
 */

public class DataManager {
    private DataManager() {}

    private static DataManager instance = null;
    private int income;
    private int outcome;

    public static DataManager getInstance() {
        if (instance==null) {
            instance = new DataManager();
        }
        return instance;
    }

    public int getIncome() {
        return income;
    }

    public int getOutcome() {
        return outcome;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public void setOutcome(int outcome) {
        this.outcome = outcome;
    }
}
