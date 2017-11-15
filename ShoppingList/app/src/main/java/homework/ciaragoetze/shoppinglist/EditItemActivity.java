package homework.ciaragoetze.shoppinglist;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import homework.ciaragoetze.shoppinglist.data.Item;

public class EditItemActivity extends AppCompatActivity {

    public static final int SPINNER_FOOD = 0;
    public static final int SPINNER_CLOTHING = 2;
    public static final int SPINNER_BOOK = 1;
    public static final int SPINNER_ELECTRONIC = 4;
    public static final int SPINNER_HOUSEHOLD = 3;
    private EditText etName;
    private EditText etDescription;
    private EditText etPrice;
    private CheckBox cbBought;
    private Spinner spCategory;

    private Item itemToEdit = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        if (getIntent().hasExtra(MainActivity.EDIT_ITEM_ID)) {
            String itemID = getIntent().getStringExtra(MainActivity.EDIT_ITEM_ID);
            itemToEdit = ((ItemApplication)getApplication()).getRealmItem().where(Item.class).
                    equalTo(getString(R.string.item_ID), itemID).findFirst();
        }

        etName = (EditText)findViewById(R.id.etName);
        etPrice = (EditText)findViewById(R.id.etPrice);
        etDescription = (EditText)findViewById(R.id.etDescription);
        cbBought = (CheckBox)findViewById(R.id.cbBought);
        spCategory = (Spinner)findViewById(R.id.spCategory);

        final ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(spinnerAdapter);

        Button btnAdd = (Button) findViewById(R.id.btnAdd);

        if(itemToEdit != null){
            etName.setText(itemToEdit.getItemTitle());
            etPrice.setText(itemToEdit.getPrice());
            etDescription.setText(itemToEdit.getDescription());
            cbBought.setChecked(itemToEdit.isBought());
            setSpinnerValue(itemToEdit.getCategory());
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ItemApplication)getApplication()).getRealmItem().beginTransaction();
                itemToEdit.setItemTitle(etName.getText().toString());
                itemToEdit.setDescription(etDescription.getText().toString());
                itemToEdit.setPrice(etPrice.getText().toString());
                itemToEdit.setBought(cbBought.isChecked());
                itemToEdit.setCategory(spCategory.getSelectedItem().toString());
                ((ItemApplication)getApplication()).getRealmItem().commitTransaction();

                Intent intentResult = new Intent();
                intentResult.putExtra(MainActivity.EDIT_ITEM_ID, itemToEdit.getItemID());
                setResult(RESULT_OK, intentResult);
                finish();
            }
        });

    }

    public void setSpinnerValue(String categoryName){
        if (categoryName != null) {
            String lowerCategory = categoryName.toLowerCase();
            if (lowerCategory.equals(getString(R.string.category_food))) {
                spCategory.setSelection(SPINNER_FOOD);
            } else if (lowerCategory.equals(getString(R.string.category_clothing))) {
                spCategory.setSelection(SPINNER_CLOTHING);
            } else if (lowerCategory.equals(getString(R.string.category_electronic))) {
                spCategory.setSelection(SPINNER_ELECTRONIC);
            } else if (lowerCategory.equals(getString(R.string.category_book))) {
                spCategory.setSelection(SPINNER_BOOK);
            } else if (lowerCategory.equals(getString(R.string.category_household))) {
                spCategory.setSelection(SPINNER_HOUSEHOLD);
            }
        }
    }
}
