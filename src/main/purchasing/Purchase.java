package main.purchasing;

import java.math.BigDecimal;
import java.sql.Date;

public class Purchase {

    private int salespersonID;
    private String firstName;
    private String lastName;
    private String middleName;
    private String address;
    private String zip;
    private String emailAddress;
    private Date dateOfSale;
    private BigDecimal price;
    private BigDecimal tradeInValue;
    private String VIN;

    private String socialSecurityNumber;
    private Date dateOfLoan;
    private BigDecimal principal;
    private int loanLength;
    private Date dateOfLastPayment;
    private BigDecimal monthlyPayment;

    public int getSalespersonID() {
        return salespersonID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getAddress() {
        return address;
    }

    public String getZip() {
        return zip;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Date getDateOfSale() {
        return dateOfSale;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getTradeInValue() {
        return tradeInValue;
    }

    public String getVIN() {
        return VIN;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public Date getDateOfLoan() {
        return dateOfLoan;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public int getLoanLength() {
        return loanLength;
    }

    public Date getDateOfLastPayment() {
        return dateOfLastPayment;
    }

    public BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }

    public boolean hasLoan() {
        return dateOfLoan != null;
    }

    public void setSalespersonID(int salespersonID) {
        this.salespersonID = salespersonID;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setDateOfSale(Date dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setTradeInValue(BigDecimal tradeInValue) {
        this.tradeInValue = tradeInValue;
    }

    public void setVIN(String VIN) {
        this.VIN = VIN;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public void setDateOfLoan(Date dateOfLoan) {
        this.dateOfLoan = dateOfLoan;
    }

    public void setPrincipal(BigDecimal principal) {
        this.principal = principal;
    }

    public void setLoanLength(int loanLength) {
        this.loanLength = loanLength;
    }

    public void setDateOfLastPayment(Date dateOfLastPayment) {
        this.dateOfLastPayment = dateOfLastPayment;
    }

    public void setMonthlyPayment(BigDecimal monthlyPayment) {
        this.monthlyPayment = monthlyPayment;
    }
}
