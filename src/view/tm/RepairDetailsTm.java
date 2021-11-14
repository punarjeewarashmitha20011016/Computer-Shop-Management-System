package view.tm;

public class RepairDetailsTm {
    private String repairId;
    private int repairCount;
    private String repairItemDescription;
    private String repairType;
    private String repairPart;
    private double repairCost;

    public RepairDetailsTm() {
    }

    public RepairDetailsTm(String repairId, int repairCount, String repairItemDescription, String repairType, String repairPart, double repairCost) {
        this.repairId = repairId;
        this.repairCount=repairCount;
        this.repairItemDescription = repairItemDescription;
        this.repairType = repairType;
        this.repairPart = repairPart;
        this.repairCost = repairCost;
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

    public double getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(double repairCost) {
        this.repairCost = repairCost;
    }

    @Override
    public String toString() {
        return "RepairDetails{" +
                "repairId='" + repairId + '\'' +
                ", repairCount=" + repairCount +
                ", repairItemDescription='" + repairItemDescription + '\'' +
                ", repairType='" + repairType + '\'' +
                ", repairPart='" + repairPart + '\'' +
                ", repairCost=" + repairCost +
                '}';
    }
}
