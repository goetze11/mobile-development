package homework.ciaragoetze.shoppinglist.adapter;

/**
 * Created by ciaragoetze on 11/5/17.
 */

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import homework.ciaragoetze.shoppinglist.MainActivity;
import homework.ciaragoetze.shoppinglist.R;
import homework.ciaragoetze.shoppinglist.data.Item;
import homework.ciaragoetze.shoppinglist.touch.ItemTouchAdapter;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder>
        implements ItemTouchAdapter {

    private List<Item> itemList;

    private Context context;
    private Realm realmItem;


    public ShoppingAdapter(Context context, Realm realmItem) {
        this.context = context;
        this.realmItem = realmItem;

        itemList = new ArrayList<Item>();

        RealmResults<Item> itemResult =
                realmItem.where(Item.class).findAll()
                        .sort(context.getString(R.string.item_title), Sort.ASCENDING);

        for (Item item : itemResult) {
            itemList.add(item);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_row, parent, false);
        return new ViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Item itemData = itemList.get(position);

        holder.tvName.setText(itemData.getItemTitle());
        holder.tvPrice.setText(context.getString((R.string.item_price), itemData.getPrice()));
        holder.tvDescription.setText(context.getString((R.string.item_description),
                                                            itemData.getDescription()));
        holder.cbBought.setChecked(itemData.isBought());
        chooseCategoryImage(itemData.getCategory(), holder);

        holder.cbBought.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realmItem.beginTransaction();
                itemData.setBought(true);
                realmItem.commitTransaction();

            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) context).openEditActivity(holder.getAdapterPosition(),
                        itemList.get(holder.getAdapterPosition()).getItemID());
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemDismiss(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public void onItemDismiss(int position) {

        Item itemToDelete = itemList.get(position);
        realmItem.beginTransaction();
        itemToDelete.deleteFromRealm();
        realmItem.commitTransaction();

        itemList.remove(position);
        notifyItemRemoved(position);
    }

    public void deleteAll(){
        realmItem.beginTransaction();
        realmItem.deleteAll();
        realmItem.commitTransaction();

        itemList.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(itemList, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(itemList, i, i - 1);
            }
        }

        notifyItemMoved(fromPosition, toPosition);
    }

    public void addItem(String itemIDThatWasAdded) {
        Item newItem = realmItem.where(Item.class).equalTo(context.getString(R.string.item_ID),
                                                            itemIDThatWasAdded).findFirst();
        itemList.add(0, newItem);
        notifyItemInserted(0);

    }

    public void updateItem(String itemIDThatWasEdited, int positionToEdit) {
        Item item = realmItem.where(Item.class).equalTo(context.getString(R.string.item_ID),
                                                            itemIDThatWasEdited).findFirst();
        itemList.set(positionToEdit, item);
        notifyItemChanged(positionToEdit);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvPrice;
        private TextView tvDescription;
        private CheckBox cbBought;
        private ImageView ivCategory;
        private FloatingActionButton btnEdit;
        private FloatingActionButton btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            cbBought = itemView.findViewById(R.id.cbBought);
            btnEdit = itemView.findViewById(R.id.fabEdit);
            btnDelete = itemView.findViewById(R.id.fabDelete);
            ivCategory = itemView.findViewById(R.id.ivCategory);
        }
    }

    public void chooseCategoryImage(String categoryName, ViewHolder holder) {
        if (categoryName != null) {
            String lowerCategory = categoryName.toLowerCase();
            if (lowerCategory.equals(context.getString(R.string.category_food))) {
                holder.ivCategory.setImageResource(R.mipmap.food);
            } else if (lowerCategory.equals(context.getString(R.string.category_clothing))) {
                holder.ivCategory.setImageResource(R.mipmap.clothing);
            } else if (lowerCategory.equals(R.string.category_electronic)) {
                holder.ivCategory.setImageResource(R.mipmap.electronics);
            } else if (lowerCategory.equals(context.getString(R.string.category_book))) {
                holder.ivCategory.setImageResource(R.mipmap.book);
            } else if (lowerCategory.equals(context.getString(R.string.category_household))) {
                holder.ivCategory.setImageResource(R.mipmap.household);
            }
        }
    }
}
