package dao;

import dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        if (daoFactory == null) {
            daoFactory = new DAOFactory();
        }
        return daoFactory;
    }

    public SuperDAO getDAOTypes(DAOTypes types) {
        switch (types) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:return new ItemDAOImpl();
            case ITEMBRAND:return new ItemBrandDAOImpl();
            case ITEMCATEGORY:return new ItemCategoryDAOImpl();
            case ADMIN:return new AdminDAOImpl();
            case CASHIER:return new CashierDAOImpl();
            case NORMALORDER:return new NormalOrderDAOImpl();
            case NORMALORDERDETAILS:return new NormalOrderDetailsDAOImpl();
            case REPAIRSERVICESPARTS:return new RepairServicesPartsDAOImpl();
            case REPAIRSERVICESTYPE:return new RepairServicesTypesDAOImpl();
            case REPAIRSINPROGRESS:return new RepairsInProgressDAOImpl();
            case REPAIRDETAILS:return new RepairDetailsDAOImpl();
            case GENERATEREPAIRID:return new RepairIdDAOImpl();
            case REPAIRSFINISHED:return new RepairsFinishedDAOImpl();
            case REPAIRSFINISHEDETAILS:return new RepairsFinishedDetailsDAOImpl();
            case REPAIRORDER:return new RepairOrderDAOImpl();
            case REPAIRORDERDETAILS:return new RepairOrderDetailsDAOImpl();
            case INCOME:return new IncomeDAOImpl();
            case RETURNS:return new ReturnsDAOImpl();
            default:
                return null;
        }
    }

    public enum DAOTypes {
        CUSTOMER,ITEM,ITEMBRAND,ITEMCATEGORY,ADMIN,CASHIER,NORMALORDER,NORMALORDERDETAILS,REPAIRORDER,
        REPAIRORDERDETAILS,INCOME,REPAIRSERVICESPARTS,REPAIRSERVICESTYPE,REPAIRSINPROGRESS,REPAIRDETAILS,GENERATEREPAIRID,
        REPAIRSFINISHED,REPAIRSFINISHEDETAILS,RETURNS
    }
}
