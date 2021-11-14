package controller;

import bo.BOFactory;
import bo.custom.ManageItemsBO;
import bo.custom.StockBO;
import dto.ItemDTO;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import view.tm.ItemTm;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import util.CommonFunctions;

import java.sql.SQLException;
import java.util.ArrayList;

public class StockFormController {
    public AnchorPane itemStockPane;
    public TableView<ItemTm> tblItemStock;
    public TableColumn tblItemCode;
    public TableColumn tblItemDescription;
    public TableColumn tblItemBrand;
    public TableColumn tblItemCategory;
    public TableColumn tblItemRam;
    public TableColumn tblItemStorage;
    public TableColumn tblItemQty;
    public TableColumn tblItemUnitPrice;
    public TextField txtSearchItems;
    public TableColumn tblItemBuyingPrice;
    public ArrayList<String> itemCodeList = new ArrayList<>();
    public AutoCompletionBinding<String> autoCompletionBinding;
    private StockBO stockBO= (StockBO) BOFactory.getBoFactory().getBoTypes(BOFactory.BOTypes.STOCK);

    public void initialize() {
        tblItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        tblItemDescription.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        tblItemBrand.setCellValueFactory(new PropertyValueFactory<>("itemBrand"));
        tblItemCategory.setCellValueFactory(new PropertyValueFactory<>("itemCategory"));
        tblItemRam.setCellValueFactory(new PropertyValueFactory<>("itemRam"));
        tblItemStorage.setCellValueFactory(new PropertyValueFactory<>("itemStorage"));
        tblItemQty.setCellValueFactory(new PropertyValueFactory<>("itemQty"));
        tblItemBuyingPrice.setCellValueFactory(new PropertyValueFactory<>("itemBuyingPrice"));
        tblItemUnitPrice.setCellValueFactory(new PropertyValueFactory<>("itemUnitPrice"));
        try {
            setDataToItemCodeList();
            autoCompleteSearchField();
            setDataToObList();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        tblItemStock.refresh();
        searchFromTheTable();
    }

    public void setDataToItemCodeList() throws SQLException {
        ArrayList<ItemDTO> all = stockBO.getAllItems();
        for (ItemDTO d : all
        ) {
            itemCodeList.add(d.getItemCode());
        }
    }

    public void autoCompleteSearchField() {
        autoCompletionBinding = TextFields.bindAutoCompletion(txtSearchItems, itemCodeList);
        txtSearchItems.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case SPACE:
                        CommonFunctions.autoCompletionLearnWord(itemCodeList, txtSearchItems.getText().trim(), autoCompletionBinding, txtSearchItems);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void setDataToObList() throws SQLException {
        tblItemStock.getItems().clear();
        ArrayList<ItemDTO> all = stockBO.getAllItems();
        for (ItemDTO d : all
        ) {
            tblItemStock.getItems().add(new ItemTm(d.getItemCode(), d.getItemDescription(), d.getItemBrand(), d.getItemCategory(), d.getItemRam(), d.getItemStorage(), d.getItemQty(), d.getItemBuyingPrice(), d.getItemUnitPrice()));
        }
    }

    public void searchFromTheTable() {
        ObservableList<ItemTm> itemObservableList = tblItemStock.getItems();
        FilteredList<ItemTm> filteredData = new FilteredList<>(itemObservableList, p -> true);

        txtSearchItems.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(person -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (person.getItemCode().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getItemDescription().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getItemBrand().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (person.getItemCategory().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
        SortedList<ItemTm> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tblItemStock.comparatorProperty());
        tblItemStock.setItems(sortedData);
    }
}
