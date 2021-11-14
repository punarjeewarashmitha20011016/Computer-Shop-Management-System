package dto;

public class RepairListDetailsDTO {
    private int repairCount;
    private String repairType;
    private String repairPart;
    private double repairCost;

    public RepairListDetailsDTO() {
    }

    public RepairListDetailsDTO(int repairCount, String repairType, String repairPart, double repairCost) {
        this.repairCount=repairCount;
        this.repairType = repairType;
        this.repairPart = repairPart;
        this.repairCost = repairCost;
    }

    public int getRepairCount() {
        return repairCount;
    }

    public void setRepairCount(int repairCount) {
        this.repairCount = repairCount;
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
        return "RepairListDetails{" +
                "repairCount=" + repairCount +
                ", repairType='" + repairType + '\'' +
                ", repairPart='" + repairPart + '\'' +
                ", repairCost=" + repairCost +
                '}';
    }
}
