package homework.ciaragoetze.shoppinglist.touch;

/**
 * Created by ciaragoetze on 11/5/17.
 */

public interface ItemTouchAdapter {

    void onItemDismiss(int position);

    void onItemMove(int fromPosition, int toPosition);
}
