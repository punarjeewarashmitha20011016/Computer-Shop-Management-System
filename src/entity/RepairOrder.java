package entity;

public class RepairOrder {
    private String orderId;
    private String repairId;
    private String customerId;
    private String orderDate;
    private String orderTime;
    private double orderDiscount;
    private double orderCost;

    public RepairOrder() {
    }


    public RepairOrder(String orderId, String repairId, String customerId, String orderDate, String orderTime, double orderDiscount, double orderCost) {
        this.orderId = orderId;
        this.repairId = repairId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderDiscount = orderDiscount;
        this.orderCost = orderCost;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getRepairId() {
        return repairId;
    }

    public void setRepairId(String repairId) {
        this.repairId = repairId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public double getOrderDiscount() {
        return orderDiscount;
    }

    public void setOrderDiscount(double orderDiscount) {
        this.orderDiscount = orderDiscount;
    }

    public double getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(double orderCost) {
        this.orderCost = orderCost;
    }

    @Override
    public String toString() {
        return "RepairOrder{" +
                "orderId='" + orderId + '\'' +
                ", repairId='" + repairId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", orderDiscount=" + orderDiscount +
                ", orderCost=" + orderCost +
                '}';
    }
}
