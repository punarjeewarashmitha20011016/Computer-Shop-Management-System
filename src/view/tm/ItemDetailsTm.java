package view.tm;

public class ItemDetailsTm {
    private String itemCode;
    private String itemDescription;
    private String itemBrand;
    private String itemCategory;
    private Double itemRam;
    private Double itemStorage;
    private int itemQtyOnHand;
    private Double itemDiscount;
    private Double itemPrice;

    public ItemDetailsTm() {
    }

    public ItemDetailsTm(String itemCode, String itemDescription, String itemBrand, String itemCategory, double itemRam, double itemStorage, int itemQtyOnHand, Double itemDiscount, Double itemPrice) {
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.itemBrand = itemBrand;
        this.itemCategory = itemCategory;
        this.itemRam=itemRam;
        this.itemStorage=itemStorage;
        this.itemQtyOnHand = itemQtyOnHand;
        this.itemDiscount = itemDiscount;
        this.itemPrice = itemPrice;
    }



    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemBrand() {
        return itemBrand;
    }

    public void setItemBrand(String itemBrand) {
        this.itemBrand = itemBrand;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public Double getItemRam() {
        return itemRam;
    }

    public void setItemRam(Double itemRam) {
        this.itemRam = itemRam;
    }

    public Double getItemStorage() {
        return itemStorage;
    }

    public void setItemStorage(Double itemStorage) {
        this.itemStorage = itemStorage;
    }

    public int getItemQtyOnHand() {
        return itemQtyOnHand;
    }

    public void setItemQtyOnHand(int itemQtyOnHand) {
        this.itemQtyOnHand = itemQtyOnHand;
    }

    public Double getItemDiscount() {
        return itemDiscount;
    }

    public void setItemDiscount(Double itemDiscount) {
        this.itemDiscount = itemDiscount;
    }

    public Double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Double itemPrice) {
        this.itemPrice = itemPrice;
    }

    @Override
    public String toString() {
        return "ItemDetails{" +
                "itemCode='" + itemCode + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemBrand='" + itemBrand + '\'' +
                ", itemCategory='" + itemCategory + '\'' +
                ", itemRam=" + itemRam +
                ", itemStorage=" + itemStorage +
                ", itemQtyOnHand=" + itemQtyOnHand +
                ", itemDiscount=" + itemDiscount +
                ", itemPrice=" + itemPrice +
                '}';
    }
}
