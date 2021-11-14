package entity;

public class RepairServicesTypes {
    private String serviceType;
    private double serviceCost;

    public RepairServicesTypes() {
    }

    public RepairServicesTypes(String serviceType, double serviceCost) {
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
