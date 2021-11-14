package bo;

import bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {
    }

    public static BOFactory getBoFactory() {
        if (boFactory == null) {
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public SuperBO getBoTypes(BOTypes types) {
        switch (types) {
            case CUSTOMER:
                return new ManageCustomerBOImpl();
            case ITEM:return new ManageItemsBOImpl();
            case ADMIN:return new AdminBOImpl();
            case CASHIER:return new CashierBOImpl();
            case NORMALPLACEORDER:return new NormalPlaceOrderBOImpl();
            case REPAIRPLACEORDER:return new RepairPlaceOrderBOImpl();
            case RETURNS:return new ReturnsBOImpl();
            case VIEWORDERDETAILS:return new ViewOrderDetailsBOImpl();
            case ADDREPAIRS:return new AddRepairsBOImpl();
            case REPAIRSINPROGRESS:return new RepairsInProgressBOImpl();
            case REPAIRSFINISHED:return new RepairsFinishedBOImpl();
            case REPAIRORDERDETAILS:return new RepairOrderDetailsBOImpl();
            case REPAIRITEMS:return new RepairItemsBOImpl();
            case REPAIRSFINISHEDDETAILS:return new RepairsFinishedDetailsBOImpl();
            case NORMALORDERDETAILS:return new NormalOrderDetailsBOImpl();
            case ADDREPAIRSERVICESANDPARTS:return new AddRepairServicesAndPartsBOImpl();
            case MANAGECASHIER:return new ManageCashierBOImpl();
            case MANAGEADMIN:return new ManageAdminBOImpl();
            case VIEWINCOME:return new ViewIncomeBOImpl();
            case STOCK:return new StockBOImpl();
            case LOGIN:return new LoginBOImpl();
            default:
                return null;
        }
    }

    public enum BOTypes {
        CUSTOMER,ITEM,ADMIN,CASHIER,MANAGECASHIER,MANAGEADMIN,NORMALPLACEORDER,REPAIRPLACEORDER,RETURNS,VIEWORDERDETAILS,ADDREPAIRS,
        REPAIRSINPROGRESS,REPAIRSFINISHED,REPAIRORDERDETAILS,REPAIRITEMS,REPAIRSFINISHEDDETAILS,NORMALORDERDETAILS,
        ADDREPAIRSERVICESANDPARTS,VIEWINCOME,STOCK,LOGIN
    }
}
