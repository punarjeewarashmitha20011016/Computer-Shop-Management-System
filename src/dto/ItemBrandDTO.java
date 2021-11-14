package dto;

public class ItemBrandDTO {
    private String itemBrand;

    public ItemBrandDTO() {
    }

    public ItemBrandDTO(String itemBrand) {
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
