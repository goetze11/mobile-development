package homework.ciaragoetze.weatherinfo.touch;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import homework.ciaragoetze.weatherinfo.adapter.CityAdapter;

/**
 * Created by ciaragoetze on 11/15/17.
 */

public class CityTouchHelperCallback extends ItemTouchHelper.Callback{

    private CityAdapter cityAdapter;

    public CityTouchHelperCallback(CityAdapter cityAdapter) {
        this.cityAdapter = cityAdapter;
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
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        cityAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        cityAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

}
