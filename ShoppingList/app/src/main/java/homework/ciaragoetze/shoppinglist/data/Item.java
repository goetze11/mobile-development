package homework.ciaragoetze.shoppinglist.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ciaragoetze on 11/5/17.
 */

public class Item extends RealmObject {

    @PrimaryKey
    private String itemID;
    private String itemTitle;
    private String category;
    private String description;
    private String price;
    private boolean isBought;

    public Item(){

    }

    public Item(String itemTitle, String description, String price, boolean isBought, String category) {
        this.itemTitle = itemTitle;
        this.description = description;
        this.price = price;
        this.isBought = isBought;
        this.category = category;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public String getPrice() {return price;}

    public void setPrice(String price) {this.price = price;}

    public boolean isBought() {return isBought;}

    public void setBought(boolean bought) {isBought = bought;}

    public String getItemID() {return itemID;}

    public String getCategory() {return category;}

    public void setCategory(String category) {this.category = category;}
}
