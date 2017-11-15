package homework.ciaragoetze.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.UUID;

import homework.ciaragoetze.shoppinglist.data.Item;

/**
 * Created by ciaragoetze on 11/6/17.
 */

public class AddItemActivity extends AppCompatActivity {

    private EditText etName;
    private EditText etDescription;
    private EditText etPrice;
    private CheckBox cbBought;
    private Spinner spCategory;

    private Item itemToAdd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_item_dialog);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        etName = (EditText)findViewById(R.id.etName);
        etPrice = (EditText)findViewById(R.id.etPrice);
        etDescription = (EditText)findViewById(R.id.etDescription);
        cbBought = (CheckBox)findViewById(R.id.cbBought);
        spCategory = (Spinner) findViewById(R.id.spCategory);


        final ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.category_array, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(spinnerAdapter);


        Button btnAdd = (Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ItemApplication)getApplication()).getRealmItem().beginTransaction();
                itemToAdd = ((ItemApplication)getApplication()).getRealmItem()
                        .createObject(Item.class, UUID.randomUUID().toString());

                itemToAdd.setItemTitle(etName.getText().toString());
                itemToAdd.setDescription(etDescription.getText().toString());
                itemToAdd.setPrice(etPrice.getText().toString());
                itemToAdd.setBought(cbBought.isChecked());
                String category = spCategory.getSelectedItem().toString();
                itemToAdd.setCategory(category);

                ((ItemApplication)getApplication()).getRealmItem().commitTransaction();

                Intent intentResult = new Intent();
                intentResult.putExtra(MainActivity.ADD_ITEM_ID, itemToAdd.getItemID());
                setResult(RESULT_OK, intentResult);
                finish();
            }
        });

        Button btnCancel = (Button)findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
