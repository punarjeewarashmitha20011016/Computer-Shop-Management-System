package entity;

public class RepairID {
    public String repairId;

    public RepairID() {
    }

    public RepairID(String repairId) {
        this.repairId = repairId;
    }

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    @Override
    public String toString() {
        return "RepairID{" +
                "repairId='" + repairId + '\'' +
                '}';
    }
}
