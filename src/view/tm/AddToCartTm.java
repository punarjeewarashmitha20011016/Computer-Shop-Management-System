package view.tm;

public class AddToCartTm {
    private String itemCode;
    private String itemDescription;
    private String itemBrand;
    private String itemCategory;
    private int qty;
    private int qtyOnHand;
    private double discountedPrice;
    private double totalItemCost;

    public AddToCartTm() {
    }

    public AddToCartTm(String itemCode, String itemDescription, String itemBrand, String itemCategory, int qtyOnHand, double discountedPrice, double totalItemCost) {
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.itemBrand = itemBrand;
        this.itemCategory = itemCategory;
        this.qtyOnHand = qtyOnHand;
        this.discountedPrice = discountedPrice;
        this.totalItemCost = totalItemCost;
    }

    public AddToCartTm(String itemCode, String itemDescription, String itemBrand, String itemCategory, int qty, int qtyOnHand, double discountedPrice, double totalItemCost) {
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.itemBrand = itemBrand;
        this.itemCategory = itemCategory;
        this.qty = qty;
        this.qtyOnHand = qtyOnHand;
        this.discountedPrice = discountedPrice;
        this.totalItemCost = totalItemCost;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public double getTotalItemCost() {
        return totalItemCost;
    }

    public void setTotalItemCost(double totalItemCost) {
        this.totalItemCost = totalItemCost;
    }

    @Override
    public String toString() {
        return "AddToCart{" +
                "itemCode='" + itemCode + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemBrand='" + itemBrand + '\'' +
                ", itemCategory='" + itemCategory + '\'' +
                ", qty=" + qty +
                ", qtyOnHand=" + qtyOnHand +
                ", discountedPrice=" + discountedPrice +
                ", totalItemCost=" + totalItemCost +
                '}';
    }
}
