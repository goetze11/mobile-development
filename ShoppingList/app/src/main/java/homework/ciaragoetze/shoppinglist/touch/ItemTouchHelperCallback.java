package homework.ciaragoetze.shoppinglist.touch;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import homework.ciaragoetze.shoppinglist.adapter.ShoppingAdapter;

/**
 * Created by ciaragoetze on 11/5/17.
 */

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback{


    private ShoppingAdapter shoppingAdapter;

    public ItemTouchHelperCallback(ShoppingAdapter shoppingAdapter) {
        this.shoppingAdapter = shoppingAdapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        //int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, 0);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        shoppingAdapter.onItemMove(viewHolder.getAdapterPosition(),
                target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //shoppingAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

}
