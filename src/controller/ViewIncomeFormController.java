package controller;

import bo.BOFactory;
import bo.custom.ViewIncomeBO;
import db.DbConnection;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import static util.CommonFunctions.setDate;

public class ViewIncomeFormController {
    public AnchorPane paneToViewReport;
    private ViewIncomeBO incomeBO= (ViewIncomeBO) BOFactory.getBoFactory().getBoTypes(BOFactory.BOTypes.VIEWINCOME);

    public void btnCustomerDetails(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/CustomerDetailsReport.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
            setReportToPane(jasperPrint);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void btnIncomeStatement(ActionEvent actionEvent) {
        try {
            double normalSales = incomeBO.getTotalNormalOrdersIncome();
            double repairSales = incomeBO.getTotalRepairSalesIncome();
            double total = getIncome();
            double itemBuyingPrice = getItemBuyingPrice();
            double returnCost = getReturnCost();
            double expenses = getItemBuyingPrice() + returnCost;
            double profit = total - expenses;
            HashMap map = new HashMap();
            map.put("normalSalesIncome", normalSales);
            map.put("repairSalesIncome", repairSales);
            map.put("totalIncome", total);
            map.put("computersAndItems", itemBuyingPrice);
            map.put("totalExpenses", expenses);
            map.put("profit", profit);
            map.put("returns", returnCost);

            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/IncomeReport.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, new JREmptyDataSource());
            /*JasperViewer.viewReport(jasperPrint,false);*/
            setReportToPane(jasperPrint);
        } catch (SQLException | JRException throwables) {
            throwables.printStackTrace();
        }
    }

    public double getReturnCost() throws SQLException {
        double cost = incomeBO.getSumOfReturnItems();
        return cost;
    }

    public double getIncome() throws SQLException {
        return incomeBO.getTotalIncome();
    }

    public double getItemBuyingPrice() throws SQLException {
        return incomeBO.getSumOfTotalItemBuyingPrices();
    }

    public void btnItemDetails(ActionEvent actionEvent) {
        JasperDesign design = null;
        try {
            design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/ItemDetailsReport.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
            setReportToPane(jasperPrint);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void btnMostSellingItem(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/MostSellingItemReport.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
            setReportToPane(jasperPrint);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void setReportToPane(JasperPrint jasperPrint) {
        SwingNode swingNode = new SwingNode();
        AnchorPane.setTopAnchor(swingNode, 0.0);
        AnchorPane.setBottomAnchor(swingNode, 0.0);
        AnchorPane.setLeftAnchor(swingNode, 0.0);
        AnchorPane.setRightAnchor(swingNode, 0.0);
        JRViewer viewer = new JRViewer(jasperPrint);
        SwingUtilities.invokeLater(() -> swingNode.setContent(viewer));
        paneToViewReport.getChildren().add(swingNode);
    }

    public void btnNormalOrderDetails(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/OrderDetailsReport.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
            setReportToPane(jasperPrint);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void btnRepairOrderDetails(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/RepairOrderDetailsReport.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
            setReportToPane(jasperPrint);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    public void btnReturnDetails(ActionEvent actionEvent) {
        try {
            JasperDesign design = JRXmlLoader.load(this.getClass().getResourceAsStream("/view/reports/Returns.jrxml"));
            JasperReport jasperReport = JasperCompileManager.compileReport(design);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DbConnection.getInstance().getConnection());
            setReportToPane(jasperPrint);
        } catch (JRException e) {
            e.printStackTrace();
        }
    }
}


