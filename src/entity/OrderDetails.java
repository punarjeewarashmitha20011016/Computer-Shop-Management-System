package entity;

public class OrderDetails {
    private String orderId;
    private String itemCode;
    private String itemDescription;
    private String itemBrand;
    private String itemCategory;
    private double itemRam;
    private double itemStorage;
    private int qtyOnHand;
    private double itemDiscount;
    private double itemCost;

    public OrderDetails() {
    }

    public OrderDetails(String orderId, String itemCode, String itemDescription, String itemBrand, String itemCategory, double itemRam, double itemStorage, int qtyOnHand, double itemDiscount, double itemCost) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.itemBrand = itemBrand;
        this.itemCategory = itemCategory;
        this.itemRam = itemRam;
        this.itemStorage = itemStorage;
        this.qtyOnHand = qtyOnHand;
        this.itemDiscount = itemDiscount;
        this.itemCost = itemCost;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getItemDiscount() {
        return itemDiscount;
    }

    public void setItemDiscount(double itemDiscount) {
        this.itemDiscount = itemDiscount;
    }

    public double getItemCost() {
        return itemCost;
    }

    public void setItemCost(double itemCost) {
        this.itemCost = itemCost;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderId='" + orderId + '\'' +
                ", itemCode='" + itemCode + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemBrand='" + itemBrand + '\'' +
                ", itemCategory='" + itemCategory + '\'' +
                ", itemRam=" + itemRam +
                ", itemStorage=" + itemStorage +
                ", qtyOnHand=" + qtyOnHand +
                ", itemDiscount=" + itemDiscount +
                ", itemCost=" + itemCost +
                '}';
    }
}
