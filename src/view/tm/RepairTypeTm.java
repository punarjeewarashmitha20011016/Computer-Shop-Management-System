package view.tm;

public class RepairTypeTm {
    private String repairType;
    private double repairTypeCost;

    public RepairTypeTm() {
    }

    public RepairTypeTm(String repairType) {
        this.repairType=repairType;
    }

    public RepairTypeTm(String repairType, double repairTypeCost) {
        this.repairType = repairType;
        this.repairTypeCost = repairTypeCost;
    }

    public String getRepairType() {
        return repairType;
    }

    public void setRepairType(String repairType) {
        this.repairType = repairType;
    }

    public double getRepairTypeCost() {
        return repairTypeCost;
    }

    public void setRepairTypeCost(double repairTypeCost) {
        this.repairTypeCost = repairTypeCost;
    }

    @Override
    public String toString() {
        return "RepairType{" +
                "repairType='" + repairType + '\'' +
                ", repairTypeCost=" + repairTypeCost +
                '}';
    }
}
