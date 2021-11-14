package dto;

public class RepairServicesTypesDTO {
    private String serviceType;
    private double serviceCost;

    public RepairServicesTypesDTO() {
    }

    public RepairServicesTypesDTO(String serviceType, double serviceCost) {
        this.serviceType = serviceType;
        this.serviceCost = serviceCost;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public double getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(double serviceCost) {
        this.serviceCost = serviceCost;
    }

    @Override
    public String toString() {
        return "RepairServicesTypes{" +
                "serviceType='" + serviceType + '\'' +
                ", serviceCost=" + serviceCost +
                '}';
    }
}
