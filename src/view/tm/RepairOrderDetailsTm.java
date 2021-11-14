package view.tm;

public class RepairOrderDetailsTm {
    private String repairOrderId;
    private int repairCount;
    private String repairItemDescription;
    private String repairType;
    private String repairPart;
    private String repairStartDate;
    private String repairFinishedDate;
    private double repairCost;

    public RepairOrderDetailsTm() {
    }

    public RepairOrderDetailsTm(String repairOrderId, int repairCount, String repairItemDescription, String repairType, String repairPart, String repairStartDate, String repairFinishedDate, double repairCost) {
        this.repairOrderId = repairOrderId;
        this.repairCount = repairCount;
        this.repairItemDescription = repairItemDescription;
        this.repairType = repairType;
        this.repairPart = repairPart;
        this.repairStartDate = repairStartDate;
        this.repairFinishedDate = repairFinishedDate;
        this.repairCost = repairCost;
    }

    public String getRepairOrderId() {
        return repairOrderId;
    }

    public void setRepairOrderId(String repairOrderId) {
        this.repairOrderId = repairOrderId;
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

    public double getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(double repairCost) {
        this.repairCost = repairCost;
    }

    @Override
    public String toString() {
        return "RepairOrderDetails{" +
                "repairOrderId='" + repairOrderId + '\'' +
                ", repairCount='" + repairCount + '\'' +
                ", repairItemDescription='" + repairItemDescription + '\'' +
                ", repairType='" + repairType + '\'' +
                ", repairPart='" + repairPart + '\'' +
                ", repairStartDate='" + repairStartDate + '\'' +
                ", repairFinishedDate='" + repairFinishedDate + '\'' +
                ", repairCost=" + repairCost +
                '}';
    }
}
