package entity;

import dto.ItemDetailsDTO;

import java.util.ArrayList;

public class Order {
    private String orderId;
    private String customerId;
    private String orderDate;
    private String orderTime;
    private double orderCost;
    private ArrayList<ItemDetailsDTO>items = new ArrayList<>();

    public Order() {
    }

    public Order(String orderId, String customerId, String orderDate, String orderTime,double orderCost) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderCost=orderCost;
    }
    public Order(String orderId, String customerId, String orderDate, String orderTime,double orderCost, ArrayList<ItemDetailsDTO> items) {
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

    public ArrayList<ItemDetailsDTO> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemDetailsDTO> items) {
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
