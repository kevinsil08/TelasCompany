/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author kevin
 */
public class HandiworkPayment {
    
    private int Id;
    private String Date;
    private double Amount;

    public HandiworkPayment() {
    }

    public HandiworkPayment(int Id, String Date, double Amount) {
        this.Id = Id;
        this.Date = Date;
        this.Amount = Amount;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double Amount) {
        this.Amount = Amount;
    }
    
}
