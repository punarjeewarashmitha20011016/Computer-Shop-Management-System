package view.tm;

public class RepairServicesPartsTm {
    private String itemCode;
    private String itemDescription;
    private int itemQty;
    private double itemUnitPrice;

    public RepairServicesPartsTm() {
    }

    public RepairServicesPartsTm(String itemCode, String itemDescription, int itemQty, double itemUnitPrice) {
        this.itemCode = itemCode;
        this.itemDescription = itemDescription;
        this.itemQty = itemQty;
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

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public double getItemUnitPrice() {
        return itemUnitPrice;
    }

    public void setItemUnitPrice(double itemUnitPrice) {
        this.itemUnitPrice = itemUnitPrice;
    }

    @Override
    public String toString() {
        return "RepairServicesParts{" +
                "itemCode='" + itemCode + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", itemQty=" + itemQty +
                ", itemUnitPrice=" + itemUnitPrice +
                '}';
    }
}
