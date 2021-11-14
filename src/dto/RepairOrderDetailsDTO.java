package dto;

public class RepairOrderDetailsDTO {
    private String orderId;
    private int repairCount;
    private String repairItemDescription;
    private String repairType;
    private String repairPart;
    private String repairStartDate;
    private String repairFinishedDate;
    private double repairItemPrice;

    public RepairOrderDetailsDTO() {
    }

    public RepairOrderDetailsDTO(String orderId, int repairCount, String repairItemDescription, String repairType, String repairPart, String repairStartDate, String repairFinishedDate, double repairItemPrice) {
        this.orderId = orderId;
        this.repairCount = repairCount;
        this.repairItemDescription = repairItemDescription;
        this.repairType = repairType;
        this.repairPart = repairPart;
        this.repairStartDate = repairStartDate;
        this.repairFinishedDate = repairFinishedDate;
        this.repairItemPrice = repairItemPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getRepairCount() {
        return repairCount;
    }

    public void setRepairCount(int repairCount) {
        this.repairCount = repairCount;
    }

    public String getRepairItemDescription() {
        return repairItemDescription;
    }

    public void setRepairItemDescription(String repairItemDescription) {
        this.repairItemDescription = repairItemDescription;
    }

    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    public String getRepairPart() {
        return repairPart;
    }

    public void setRepairPart(String repairPart) {
        this.repairPart = repairPart;
    }

    public String getRepairStartDate() {
        return repairStartDate;
    }

    public void setRepairStartDate(String repairStartDate) {
        this.repairStartDate = repairStartDate;
    }

    public String getRepairFinishedDate() {
        return repairFinishedDate;
    }

    public void setRepairFinishedDate(String repairFinishedDate) {
        this.repairFinishedDate = repairFinishedDate;
    }

    public double getRepairItemPrice() {
        return repairItemPrice;
    }

    public void setRepairItemPrice(double repairItemPrice) {
        this.repairItemPrice = repairItemPrice;
    }

    @Override
    public String toString() {
        return "RepairOrderDetails{" +
                "orderId='" + orderId + '\'' +
                ", repairCount=" + repairCount +
                ", repairItemDescription='" + repairItemDescription + '\'' +
                ", repairType='" + repairType + '\'' +
                ", repairPart='" + repairPart + '\'' +
                ", repairStartDate='" + repairStartDate + '\'' +
                ", repairFinishedDate='" + repairFinishedDate + '\'' +
                ", repairItemPrice=" + repairItemPrice +
                '}';
    }
}
