package dto;

public class ItemCategoryDTO {
    private String itemCategory;

    public ItemCategoryDTO() {
    }

    public ItemCategoryDTO(String itemCategory) {
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
