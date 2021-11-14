package view.tm;

import java.util.ArrayList;

public class OrderTm {
    private String orderId;
    private String customerId;
    private String orderDate;
    private String orderTime;
    private double orderCost;
    private ArrayList<ItemDetailsTm>items = new ArrayList<>();

    public OrderTm() {
    }

    public OrderTm(String orderId, String customerId, String orderDate, String orderTime, double orderCost) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderCost=orderCost;
    }
    public OrderTm(String orderId, String customerId, String orderDate, String orderTime, double orderCost, ArrayList<ItemDetailsTm> items) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderCost=orderCost;
        this.items = items;
    }

    public double getOrderCost() {
        return orderCost;
    }

    public void setOrderCost(double orderCost) {
        this.orderCost = orderCost;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public ArrayList<ItemDetailsTm> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemDetailsTm> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", orderCost=" + orderCost +
                ", items=" + items +
                '}';
    }
}
