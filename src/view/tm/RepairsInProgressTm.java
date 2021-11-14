package view.tm;

public class RepairsInProgressTm {
    private String customerId;
    private String repairId;
    private String repairItemDescription;
    private String repairStartDate;
    private String repairEstimatedFinishingDate;
    private double repairCost;

    public RepairsInProgressTm() {
    }
    public RepairsInProgressTm(String customerId, String repairId, String repairItemDescription, String repairStartDate, String repairEstimatedFinishingDate, double repairCost) {
        this.customerId = customerId;
        this.repairId = repairId;
        this.repairItemDescription = repairItemDescription;
        this.repairStartDate = repairStartDate;
        this.repairEstimatedFinishingDate = repairEstimatedFinishingDate;
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

    public String getRepairItemDescription() {
        return repairItemDescription;
    }

    public void setRepairItemDescription(String repairItemDescription) {
        this.repairItemDescription = repairItemDescription;
    }

    public String getRepairStartDate() {
        return repairStartDate;
    }

    public void setRepairStartDate(String repairStartDate) {
        this.repairStartDate = repairStartDate;
    }

    public String getRepairEstimatedFinishingDate() {
        return repairEstimatedFinishingDate;
    }

    public void setRepairEstimatedFinishingDate(String repairEstimatedFinishingDate) {
        this.repairEstimatedFinishingDate = repairEstimatedFinishingDate;
    }

    public double getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(double repairCost) {
        this.repairCost = repairCost;
    }


    @Override
    public String toString() {
        return "RepairsInProgress{" +
                "customerId='" + customerId + '\'' +
                ", repairId='" + repairId + '\'' +
                ", repairItemDescription='" + repairItemDescription + '\'' +
                ", repairStartDate='" + repairStartDate + '\'' +
                ", repairEstimatedFinishingDate='" + repairEstimatedFinishingDate + '\'' +
                ", repairCost=" + repairCost +
                '}';
    }
}
