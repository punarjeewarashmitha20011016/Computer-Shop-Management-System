package dto;

public class RepairIdDTO {
    public String repairId;

    public RepairIdDTO() {
    }

    public RepairIdDTO(String repairId) {
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
