/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author natha
 */
public class Handiwork {
    private int handiWorkID;
    private int customerID;
    private String entryDate;
    private double totalCost;
    private int numberGarments;
    private boolean state;
    private double leftPayment;
    private String payStatus; 
    
    private String ciCustomer;
    private String namesCustomer;
    private String stateString;

    public Handiwork() {
    }

    public Handiwork(int handiWorkID, int customerID, String entryDate, double totalCost, int numberGarments, boolean state , double leftPayment , String payStatus) {
        this.handiWorkID = handiWorkID;
        this.customerID = customerID;
        this.entryDate = entryDate;
        this.totalCost = totalCost;
        this.numberGarments = numberGarments;
        this.state = state;
        this.leftPayment = leftPayment;
        this.payStatus = payStatus;
    }

    public Handiwork(int handiWorkID, int customerID, String entryDate, double totalCost,  String ciCustomer, String namesCustomer , String stateString , double leftPayment , String payStatus) {
        this.handiWorkID = handiWorkID;
        this.customerID = customerID;
        this.entryDate = entryDate;
        this.totalCost = totalCost;
        this.numberGarments = numberGarments;
        this.state = state;
        this.ciCustomer = ciCustomer;
        this.namesCustomer = namesCustomer;
        this.stateString = stateString;
        this.leftPayment = leftPayment;
        this.payStatus = payStatus;
    }
    
    public String getStateString() {
        return stateString;
    }

    public void setStateString(String stateString) {
        this.stateString = stateString;
    }
    
    public String getCiCustomer() {
        return ciCustomer;
    }

    public void setCiCustomer(String ciCustomer) {
        this.ciCustomer = ciCustomer;
    }

    public String getNamesCustomer() {
        return namesCustomer;
    }

    public void setNamesCustomer(String namesCustomer) {
        this.namesCustomer = namesCustomer;
    }
    
    

    public int getHandiWorkID() {
        return handiWorkID;
    }

    public void setHandiWorkID(int handiWorkID) {
        this.handiWorkID = handiWorkID;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public int getNumberGarments() {
        return numberGarments;
    }

    public void setNumberGarments(int numberGarments) {
        this.numberGarments = numberGarments;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public double getLeftPayment() {
        return leftPayment;
    }

    public void setLeftPayment(double leftPayment) {
        this.leftPayment = leftPayment;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }
    


            
}
