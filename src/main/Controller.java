package main;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import main.purchasing.Purchase;
import main.purchasing.PurchaseDialog;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Optional;

public class Controller {

    public Label tableTitle;
    public TableView<ObservableList> table;

    @FXML
    public void initialize() {
        tableTitle.setFont(new Font(20));
    }

    /**
     * This activates on button press.
     * @param actionEvent
     */
    public void onAction(ActionEvent actionEvent) {
        final Object userData = ((Button) actionEvent.getSource()).getUserData();
        switch ((String) userData) {
            case "1":
                tableTitle.setText(
                        "All prospective customers who visited the dealership but have not made a purchase with your " +
                                "dealership within the past month");

                break;
            case "2": {
                final TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle(
                        "Customers who had not purchased a vehicle of a certain color within the past 3 " +
                                "to 5 year");
                dialog.setHeaderText("WHAT COLOR");
                dialog.setContentText("Please enter a certain color");
                final Optional<String> result = dialog.showAndWait();
                if (!result.isPresent())
                    return;
                final String color = result.get();
                tableTitle.setText(
                        "Customers who had not purchased a [" + color + "] vehicle within the past 3 to 5 year");

                break;
            }
            case "3": {
                final TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle(
                        "Customers who had purchased a vehicle of a certain color within the past 3 " +
                                "to 5 year");
                dialog.setHeaderText("WHAT COLOR");
                dialog.setContentText("Please enter a certain color");
                final Optional<String> result = dialog.showAndWait();
                if (!result.isPresent())
                    return;
                final String color = result.get();
                tableTitle.setText(
                        "Customers who had purchased a [" + color + "] vehicle within the past 3 to 5 year");

                break;
            }
            case "4":
                tableTitle.setText("Frequent customers who make a purchase on average every 2 years or less");

                break;
            case "5":
                tableTitle.setText("Employees with unused vacation time");

                break;
            case "6":
                tableTitle.setText("Pay rates of all technicians possessing certificates");

                break;
            case "7":
                tableTitle.setText("Top three salespeople with the highest number of sales in the past 30 days");

                break;
            case "8":
                tableTitle.setText("Top three salespeople with the highest gross sales in the past 30 days");

                break;
            case "9":
                tableTitle.setText(
                        "Top three salespeople with the most number of repeated sales to the same customers");

                break;
            case "10":
                tableTitle.setText("Top five most popular car models in the past 3 years");

                break;
            case "11":
                tableTitle.setText("All electric cars sold within the last year");

                break;
            case "12":
                tableTitle.setText("All non-fossil fuel cars sold within the last year");

                break;
            case "13":
                tableTitle.setText("The month with the highest number of convertible cars sold");

                break;
            case "14": {
                final Dialog<MakeModel> dialog = new Dialog<>();
                dialog.setTitle("List of cars of a certain make and model");
                dialog.setHeaderText("WHAT MAKE WHAT MODEL");
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
                dialog.getDialogPane().getStyleClass().add("text-input-dialog");

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                TextField make = new TextField();
                TextField model = new TextField();

                grid.add(new Label("Make"), 0, 0);
                grid.add(make, 1, 0);
                grid.add(new Label("Model"), 0, 1);
                grid.add(model, 1, 1);

                dialog.getDialogPane().setContent(grid);
                Platform.runLater(make::requestFocus);

                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == null || dialogButton != ButtonType.OK) return null;
                    if (make.getText().isEmpty() || model.getText().isEmpty()) return null;
                    return new MakeModel(make.getText(), model.getText());
                });

                final Optional<MakeModel> makeModel = dialog.showAndWait();
                if (!makeModel.isPresent()) return;
                final MakeModel result = makeModel.get();
                tableTitle.setText(
                        String.format("Cars with make [%s] and model [%s]", result.getMake(), result.getModel()));

                break;
            }
            case "15": {
                final Dialog<MakeModelColor> dialog = new Dialog<>();
                dialog.setTitle("List of cars of a certain make, model, and color");
                dialog.setHeaderText("WHAT MAKE WHAT MODEL WHAT COLOR");
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
                dialog.getDialogPane().getStyleClass().add("text-input-dialog");

                GridPane grid = new GridPane();
                grid.setHgap(10);
                grid.setVgap(10);
                grid.setPadding(new Insets(20, 150, 10, 10));

                TextField make = new TextField();
                TextField model = new TextField();
                TextField color = new TextField();

                grid.add(new Label("Make"), 0, 0);
                grid.add(make, 1, 0);
                grid.add(new Label("Model"), 0, 1);
                grid.add(model, 1, 1);
                grid.add(new Label("Color"), 0, 2);
                grid.add(color, 1, 2);

                dialog.getDialogPane().setContent(grid);
                Platform.runLater(make::requestFocus);

                dialog.setResultConverter(dialogButton -> {
                    if (dialogButton == null || dialogButton != ButtonType.OK) return null;
                    if (make.getText().isEmpty() || model.getText().isEmpty() || color.getText().isEmpty()) return null;
                    return new MakeModelColor(make.getText(), model.getText(), color.getText());
                });

                final Optional<MakeModelColor> makeModel = dialog.showAndWait();
                if (!makeModel.isPresent()) return;
                final MakeModelColor result = makeModel.get();
                tableTitle.setText(String.format("Cars with make [%s], model [%s], and color [%s]", result.getMake(),
                                                 result.getModel(), result.getColor()));

                break;
            }
            case "purchase": {
                final Optional<Purchase> purchase = new PurchaseDialog().showAndWait();
            }
        }
    }

    private void setTableResults(ResultSet rs) throws SQLException {
        table.getItems().clear();
        table.getColumns().clear();
        final ResultSetMetaData metaData = rs.getMetaData();
        for (int i = 0; i < metaData.getColumnCount(); i++) {
            final int j = i;
            TableColumn<ObservableList, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
            col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(j).toString()));
            table.getColumns().add(col);
        }
        final ObservableList<ObservableList> data = FXCollections.observableArrayList();
        while(rs.next()){
            ObservableList<String> row = FXCollections.observableArrayList();
            for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                row.add(rs.getString(i));
            }
            data.add(row);
        }
        table.setItems(data);
    }

    private static class MakeModel {

        private final String make, model;

        private MakeModel(String make, String model) {
            this.make = make;
            this.model = model;
        }

        public String getMake() {
            return make;
        }

        public String getModel() {
            return model;
        }
    }

    private static class MakeModelColor extends MakeModel {

        private final String color;

        private MakeModelColor(String make, String model, String color) {
            super(make, model);
            this.color = color;
        }

        public String getColor() {
            return color;
        }
    }
}
