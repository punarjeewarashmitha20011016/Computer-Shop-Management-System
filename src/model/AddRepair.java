package model;

public class AddRepair {
    private String customerId;
    private String repairId;
    private int repairCount;
    private String repairItemDescription;
    private String repairItemType;
    private String repairItemPart;
    private double repairCost;

    public AddRepair() {
    }

    public AddRepair(String customerId,String repairId,int repairCount,String repairItemDescription, String repairItemType, String repairItemPart, double repairCost) {
        this.customerId=customerId;
        this.repairId=repairId;
        this.repairCount=repairCount;
        this.repairItemDescription = repairItemDescription;
        this.repairItemType = repairItemType;
        this.repairItemPart = repairItemPart;
        this.repairCost = repairCost;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
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

    public String getRepairItemType() {
        return repairItemType;
    }

    public void setRepairItemType(String repairItemType) {
        this.repairItemType = repairItemType;
    }

    public String getRepairItemPart() {
        return repairItemPart;
    }

    public void setRepairItemPart(String repairItemPart) {
        this.repairItemPart = repairItemPart;
    }

    public double getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(double repairCost) {
        this.repairCost = repairCost;
    }

    @Override
    public String toString() {
        return "AddRepair{" +
                "customerId='" + customerId + '\'' +
                ", repairId='" + repairId + '\'' +
                ", repairCount=" + repairCount +
                ", repairItemDescription='" + repairItemDescription + '\'' +
                ", repairItemType='" + repairItemType + '\'' +
                ", repairItemPart='" + repairItemPart + '\'' +
                ", repairCost=" + repairCost +
                '}';
    }
}
