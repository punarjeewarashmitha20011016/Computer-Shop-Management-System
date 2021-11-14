package view.tm;

public class ItemTm {
    private String itemCode;
    private String itemDescription;
    private String itemBrand;
    private String itemCategory;
    private double itemRam;
    private double itemStorage;
    private int itemQty;
    private double itemBuyingPrice;
    private double itemUnitPrice;

    public ItemTm() {
    }
    public ItemTm(String itemCode, String itemDescription, int itemQty, double itemUnitPrice){
        this.itemCode=itemCode;
        this.itemDescription=itemDescription;
        this.itemQty=itemQty;
        this.itemUnitPrice=itemUnitPrice;
    }

    public ItemTm(String itemCode, String itemDescription, String itemBrand, String itemCategory, double itemRam, double itemStorage, int itemQty, double itemBuyingPrice, double itemUnitPrice) {
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.itemBrand = itemBrand;
        this.itemCategory = itemCategory;
        this.itemRam = itemRam;
        this.itemStorage = itemStorage;
        this.itemQty = itemQty;
        this.itemBuyingPrice = itemBuyingPrice;
        this.itemUnitPrice = itemUnitPrice;
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

    public double getItemRam() {
        return itemRam;
    }

    public void setItemRam(double itemRam) {
        this.itemRam = itemRam;
    }

    public double getItemStorage() {
        return itemStorage;
    }

    public void setItemStorage(double itemStorage) {
        this.itemStorage = itemStorage;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public double getItemBuyingPrice() {
        return itemBuyingPrice;
    }

    public void setItemBuyingPrice(double itemBuyingPrice) {
        this.itemBuyingPrice = itemBuyingPrice;
    }

    public double getItemUnitPrice() {
        return itemUnitPrice;
    }

    public void setItemUnitPrice(double itemUnitPrice) {
        this.itemUnitPrice = itemUnitPrice;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemCode='" + itemCode + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemBrand='" + itemBrand + '\'' +
                ", itemCategory='" + itemCategory + '\'' +
                ", itemRam=" + itemRam +
                ", itemStorage=" + itemStorage +
                ", itemQty=" + itemQty +
                ", itemBuyingPrice=" + itemBuyingPrice +
                ", itemUnitPrice=" + itemUnitPrice +
                '}';
    }
}
