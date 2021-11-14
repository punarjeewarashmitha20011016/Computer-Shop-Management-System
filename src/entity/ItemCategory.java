package entity;

public class ItemCategory {
    private String itemCategory;

    public ItemCategory() {
    }

    public ItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    @Override
    public String toString() {
        return "ItemCategory{" +
                "itemCategory='" + itemCategory + '\'' +
                '}';
    }
}
