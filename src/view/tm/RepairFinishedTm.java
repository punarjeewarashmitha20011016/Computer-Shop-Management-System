package view.tm;

import java.util.ArrayList;

public class RepairFinishedTm {
    private String customerId;
    private String repairId;
    private String repairItemDescription;
    private String repairStartDate;
    private String repairFinishedDate;
    private double repairCost;
    private ArrayList<RepairListDetailsTm> listDetailsArrayList;

    public RepairFinishedTm() {
    }

    public RepairFinishedTm(String customerId, String repairId, String repairItemDescription, String repairStartDate, String repairFinishedDate, double repairCost) {
        this.customerId = customerId;
        this.repairId = repairId;
        this.repairItemDescription = repairItemDescription;
        this.repairStartDate = repairStartDate;
        this.repairFinishedDate = repairFinishedDate;
        this.repairCost = repairCost;
    }

    public RepairFinishedTm(String customerId, String repairId, String repairItemDescription, String repairStartDate, String repairFinishedDate, double repairCost, ArrayList<RepairListDetailsTm> listDetailsArrayList) {
        this.customerId = customerId;
        this.repairId = repairId;
        this.repairItemDescription = repairItemDescription;
        this.repairStartDate = repairStartDate;
        this.repairFinishedDate = repairFinishedDate;
        this.repairCost = repairCost;
        this.listDetailsArrayList = listDetailsArrayList;
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

    public String getRepairFinishedDate() {
        return repairFinishedDate;
    }

    public void setRepairFinishedDate(String repairFinishedDate) {
        this.repairFinishedDate = repairFinishedDate;
    }

    public double getRepairCost() {
        return repairCost;
    }

    public void setRepairCost(double repairCost) {
        this.repairCost = repairCost;
    }

    public ArrayList<RepairListDetailsTm> getListDetailsArrayList() {
        return listDetailsArrayList;
    }

    public void setListDetailsArrayList(ArrayList<RepairListDetailsTm> listDetailsArrayList) {
        this.listDetailsArrayList = listDetailsArrayList;
    }

    @Override
    public String toString() {
        return "RepairFinished{" +
                "customerId='" + customerId + '\'' +
                ", repairId='" + repairId + '\'' +
                ", repairItemDescription='" + repairItemDescription + '\'' +
                ", repairStartDate='" + repairStartDate + '\'' +
                ", repairFinishedDate='" + repairFinishedDate + '\'' +
                ", repairCost=" + repairCost +
                ", listDetailsArrayList=" + listDetailsArrayList +
                '}';
    }
}
