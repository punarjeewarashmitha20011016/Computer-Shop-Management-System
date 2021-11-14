package entity;

public class Income {
    private String dateAsPerIncome;
    private double normalSalesIncome;
    private double repairSalesIncome;
    private double totalIncome;

    public Income() {
    }

    public Income(String dateAsPerIncome, double normalSalesIncome, double repairSalesIncome, double totalIncome) {
        this.dateAsPerIncome = dateAsPerIncome;
        this.normalSalesIncome = normalSalesIncome;
        this.repairSalesIncome = repairSalesIncome;
        this.totalIncome = totalIncome;
    }

    public String getDateAsPerIncome() {
        return dateAsPerIncome;
    }

    public void setDateAsPerIncome(String dateAsPerIncome) {
        this.dateAsPerIncome = dateAsPerIncome;
    }

    public double getNormalSalesIncome() {
        return normalSalesIncome;
    }

    public void setNormalSalesIncome(double normalSalesIncome) {
        this.normalSalesIncome = normalSalesIncome;
    }

    public double getRepairSalesIncome() {
        return repairSalesIncome;
    }

    public void setRepairSalesIncome(double repairSalesIncome) {
        this.repairSalesIncome = repairSalesIncome;
    }

    public double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(double totalIncome) {
        this.totalIncome = totalIncome;
    }

    @Override
    public String toString() {
        return "Income{" +
                "dateAsPerIncome='" + dateAsPerIncome + '\'' +
                ", normalSalesIncome=" + normalSalesIncome +
                ", repairSalesIncome=" + repairSalesIncome +
                ", totalIncome=" + totalIncome +
                '}';
    }
}
