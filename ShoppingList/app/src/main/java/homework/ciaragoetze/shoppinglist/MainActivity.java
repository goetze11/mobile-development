package homework.ciaragoetze.shoppinglist;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Toast;

import homework.ciaragoetze.shoppinglist.adapter.ShoppingAdapter;
import homework.ciaragoetze.shoppinglist.data.Item;
import homework.ciaragoetze.shoppinglist.touch.ItemTouchHelperCallback;


public class MainActivity extends AppCompatActivity {

    public static final String EDIT_ITEM_ID = "EDIT_ITEM_ID";
    public static final String ADD_ITEM_ID = "ADD_ITEM_ID";
    public static final int REQUEST_CODE_EDIT = 1001;
    public static final int REQUEST_CODE_ADD = 1002;
    private ShoppingAdapter adapter;
    private int positionToEdit = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fabAdd = (FloatingActionButton) findViewById(R.id.fab);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddActivity();
            }
        });

        FloatingActionButton fabDelete = (FloatingActionButton) findViewById(R.id.fabDelete);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.deleteAll();
            }
        });

        ((ItemApplication)getApplication()).openRealm();

        RecyclerView recyclerViewItem = (RecyclerView) findViewById(R.id.recyclerItem);
        adapter = new ShoppingAdapter(this,((ItemApplication)getApplication()).getRealmItem());

        recyclerViewItem.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewItem.setHasFixedSize(true);

        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerViewItem);

        recyclerViewItem.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        ((ItemApplication)getApplication()).closeRealm();
        super.onDestroy();
    }

    private void openAddActivity() {
        Intent intentAdd = new Intent();
        intentAdd.setClass(MainActivity.this, AddItemActivity.class);
        startActivityForResult(intentAdd, REQUEST_CODE_ADD);
    }

    public void openEditActivity(int adapterPosition, String itemID){
        positionToEdit = adapterPosition;
        Intent intentEdit = new Intent(this, EditItemActivity.class);
        intentEdit.putExtra(EDIT_ITEM_ID, itemID);
        startActivityForResult(intentEdit, REQUEST_CODE_EDIT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK){
            String itemIDThatWasEdited = data.getStringExtra(EDIT_ITEM_ID);
            adapter.updateItem(itemIDThatWasEdited, positionToEdit);
        }else if (requestCode == REQUEST_CODE_ADD && resultCode == RESULT_OK) {
            String itemIDThatWasAdded = data.getStringExtra(ADD_ITEM_ID);
            adapter.addItem(itemIDThatWasAdded);
        }
    }
}