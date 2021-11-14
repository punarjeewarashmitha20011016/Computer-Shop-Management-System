package view.tm;

public class CustomerTm {
    private String customerId;
    private String customerName;
    private String customerContactNo;
    private String customerAddress;

    public CustomerTm() {
    }

    public CustomerTm(String customerId, String customerName, String customerContactNo, String customerAddress) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerContactNo = customerContactNo;
        this.customerAddress = customerAddress;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerContactNo() {
        return customerContactNo;
    }

    public void setCustomerContactNo(String customerContactNo) {
        this.customerContactNo = customerContactNo;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerContactNo=" + customerContactNo +
                ", customerAddress='" + customerAddress + '\'' +
                '}';
    }
}
