package entity;

public class Cashier {
    private String cashierId;
    private String cashierName;
    private String cashierNic;
    private String cashierContactNo;
    private String cashierUserName;
    private String cashierPassword;
    private String imageLocation;

    public Cashier() {
    }

    public Cashier(String cashierId, String cashierName, String cashierNic, String cashierContactNo, String cashierUserName, String cashierPassword, String imageLocation) {
        this.cashierId = cashierId;
        this.cashierName = cashierName;
        this.cashierNic = cashierNic;
        this.cashierContactNo = cashierContactNo;
        this.cashierUserName = cashierUserName;
        this.cashierPassword = cashierPassword;
        this.imageLocation = imageLocation;
    }

    public String getCashierId() {
        return cashierId;
    }

    public void setCashierId(String cashierId) {
        this.cashierId = cashierId;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public String getCashierNic() {
        return cashierNic;
    }

    public void setCashierNic(String cashierNic) {
        this.cashierNic = cashierNic;
    }

    public String getCashierContactNo() {
        return cashierContactNo;
    }

    public void setCashierContactNo(String cashierContactNo) {
        this.cashierContactNo = cashierContactNo;
    }

    public String getCashierUserName() {
        return cashierUserName;
    }

    public void setCashierUserName(String cashierUserName) {
        this.cashierUserName = cashierUserName;
    }

    public String getCashierPassword() {
        return cashierPassword;
    }

    public void setCashierPassword(String cashierPassword) {
        this.cashierPassword = cashierPassword;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    @Override
    public String toString() {
        return "Cashier{" +
                "cashierId='" + cashierId + '\'' +
                ", cashierName='" + cashierName + '\'' +
                ", cashierNic='" + cashierNic + '\'' +
                ", cashierContactNo='" + cashierContactNo + '\'' +
                ", cashierUserName='" + cashierUserName + '\'' +
                ", cashierPassword='" + cashierPassword + '\'' +
                ", imageLocation='" + imageLocation + '\'' +
                '}';
    }
}
