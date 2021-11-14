package entity;

public class ItemBrand {
    private String itemBrand;

    public ItemBrand() {
    }

    public ItemBrand(String itemBrand) {
        this.itemBrand = itemBrand;
    }

    public String getItemBrand() {
        return itemBrand;
    }

    public void setItemBrand(String itemBrand) {
        this.itemBrand = itemBrand;
    }

    @Override
    public String toString() {
        return "ItemBrand{" +
                "itemBrand='" + itemBrand + '\'' +
                '}';
    }
}
