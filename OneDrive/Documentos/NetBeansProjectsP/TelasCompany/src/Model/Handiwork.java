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

    public Handiwork() {
    }

    public Handiwork(int handiWorkID, int customerID, String entryDate, double totalCost, int numberGarments, boolean state) {
        this.handiWorkID = handiWorkID;
        this.customerID = customerID;
        this.entryDate = entryDate;
        this.totalCost = totalCost;
        this.numberGarments = numberGarments;
        this.state = state;
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
    


            
}
