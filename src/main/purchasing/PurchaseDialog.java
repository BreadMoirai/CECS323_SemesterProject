package main.purchasing;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

public class PurchaseDialog extends Dialog<Purchase> {

    private final Purchase purchase = new Purchase();

    public PurchaseDialog() {
        setTitle("Purchase");
        setHeaderText("EMPLOYEE CREDENTIALS");
        getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
        getDialogPane().getStyleClass().add("text-input-dialog");
        resizableProperty().setValue(true);
        getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        setEmployeeFields();
        setResultConverter(dialogButton -> null);
        getDialogPane().setMaxHeight(Double.MAX_VALUE);
        getDialogPane().setMinHeight(400);
        getDialogPane().setMinWidth(400);
    }

    private void setEmployeeFields() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        TextField employeeId = new TextField();
        final TextFormatter<Integer> value = new TextFormatter<>(new IntegerStringConverter());
        employeeId.textFormatterProperty().set(value);

        grid.add(new Label("Employee Id"), 0, 0);
        grid.add(employeeId, 1, 0);

        final Button next = new Button("Next");
        next.setOnAction(event -> {
            purchase.setSalespersonID(value.getValue());
            setupCustomerFields();
        });
        grid.add(next, 2, 1);
        Platform.runLater(employeeId::requestFocus);

        getDialogPane().setContent(grid);
    }

    private void setupCustomerFields() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        TextField firstName = new TextField();
        TextField lastName = new TextField();
        TextField middleName = new TextField();
        TextField address = new TextField();
        TextField zip = new TextField();
        TextField emailAddress = new TextField();

        grid.add(new Label("First Name"), 0, 0);
        grid.add(new Label("Last Name"), 0, 1);
        grid.add(new Label("Middle Name"), 0, 2);
        grid.add(new Label("Address"), 0, 3);
        grid.add(new Label("Zip"), 0, 4);
        grid.add(new Label("Email"), 0, 5);
        grid.add(firstName, 1, 0);
        grid.add(lastName, 1, 1);
        grid.add(middleName, 1, 2);
        grid.add(address, 1, 3);
        grid.add(zip, 1, 4);
        grid.add(emailAddress, 1, 5);

        final Button next = new Button("Next");
        next.setOnAction(event -> {
            purchase.setFirstName(firstName.getText());
            purchase.setLastName(lastName.getText());
            purchase.setMiddleName(middleName.getText());
            purchase.setAddress(address.getText());
            purchase.setZip(zip.getText());
            purchase.setEmailAddress(emailAddress.getText());
            setupPriceFields();
        });
        grid.add(next, 2, 6);
        Platform.runLater(firstName::requestFocus);

        getDialogPane().setContent(grid);
    }

    private void setupPriceFields() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        DatePicker dateOfSale = new DatePicker();
        TextField price = new TextField();
        final TextFormatter<Double> priceValue = new TextFormatter<>(new DoubleStringConverter());
        price.textFormatterProperty().set(priceValue);
        TextField tradeInValue = new TextField();
        final TextFormatter<Double> tradeInValueValue = new TextFormatter<>(new DoubleStringConverter());
        tradeInValue.textFormatterProperty().set(tradeInValueValue);
        TextField VIN = new TextField();

        grid.add(new Label("Date Of Sale"), 0, 0);
        grid.add(new Label("Price"), 0, 1);
        grid.add(new Label("Trade In Value"), 0, 2);
        grid.add(new Label("VIN"), 0, 3);
        grid.add(dateOfSale, 1, 0);
        grid.add(price, 1, 1);
        grid.add(tradeInValue, 1, 2);
        grid.add(VIN, 1, 3);

        final Button next = new Button("Next");
        next.setOnAction(event -> {
            purchase.setDateOfSale(Date.valueOf(dateOfSale.getValue()));
            purchase.setPrice(BigDecimal.valueOf(priceValue.getValue()));
            purchase.setTradeInValue(BigDecimal.valueOf(tradeInValueValue.getValue()));
            purchase.setVIN(VIN.getText());
            setupLoanFields();
        });
        grid.add(next, 2, 4);
        Platform.runLater(dateOfSale::requestFocus);
        getDialogPane().setContent(grid);
    }

    private void setupLoanFields() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        TextField ssn = new TextField();
        DatePicker dateOfLoan = new DatePicker();
        TextField principal = new TextField();
        final TextFormatter<Double> principalValue = new TextFormatter<>(new DoubleStringConverter());
        principal.textFormatterProperty().set(principalValue);
        TextField loanLength = new TextField();
        final TextFormatter<Integer> loanLengthValue = new TextFormatter<>(new IntegerStringConverter());
        loanLength.textFormatterProperty().set(loanLengthValue);
        final Label monthlyPayment = new Label();
        final Label lastPayment = new Label();
        loanLengthValue.valueProperty().addListener((observable, oldValue, newValue) -> {
            final LocalDate date = dateOfLoan.getValue();
            if (date == null) return;
            final Integer ll = newValue;
            if (ll == null) return;
            final Date lastDate = Date.valueOf(
                    LocalDate.of(date.getYear(), date.getMonth().plus(1), 1).plusYears(ll));
            final double monthlyPaymentValue = principalValue.getValue() / (ll * 12);
            monthlyPayment.setText(
                    String.format("$%.2f", monthlyPaymentValue));
            lastPayment.setText(lastDate.toString());
        });

        grid.add(new Label("SocialSecurityNumber"), 0, 0);
        grid.add(new Label("Date Of Loan"), 0, 1);
        grid.add(new Label("Principle"), 0, 2);
        grid.add(new Label("Loan Years"), 0, 3);
        grid.add(new Label("Monthly Payment"), 0, 4);
        grid.add(new Label("Last Payment Date"), 0, 5);
        grid.add(ssn, 1, 0);
        grid.add(dateOfLoan, 1, 1);
        grid.add(principal, 1, 2);
        grid.add(loanLength, 1, 3);
        grid.add(monthlyPayment, 1, 4);
        grid.add(lastPayment, 1, 5);

        Platform.runLater(ssn::requestFocus);
        getDialogPane().setContent(grid);

        getDialogPane().getButtonTypes().add(ButtonType.FINISH);

        setResultConverter(buttonType -> {
            purchase.setSocialSecurityNumber(ssn.getText());
            final LocalDate date = dateOfLoan.getValue();
            purchase.setDateOfLoan(Date.valueOf(date));
            purchase.setPrincipal(BigDecimal.valueOf(principalValue.getValue()));
            final Integer value = loanLengthValue.getValue();
            purchase.setLoanLength(value);
            final Date lastDate = Date.valueOf(
                    LocalDate.of(date.getYear(), date.getMonth().plus(1), 1).plusYears(loanLengthValue.getValue()));
            final double monthlyPaymentValue = principalValue.getValue() / (loanLengthValue.getValue() * 12);
            purchase.setMonthlyPayment(BigDecimal.valueOf(monthlyPaymentValue));
            purchase.setDateOfLastPayment(lastDate);
            return purchase;
        });
    }
}
