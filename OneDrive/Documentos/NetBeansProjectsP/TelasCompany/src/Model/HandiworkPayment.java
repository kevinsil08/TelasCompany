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
    private String nameItem;
    private double TotalPayment;
    

    public HandiworkPayment() {
    }

    public HandiworkPayment(int Id, String Date, double Amount) {
        this.Id = Id;
        this.Date = Date;
        this.Amount = Amount;
    }

    public HandiworkPayment(int Id, String Date, double Amount, String nameItem) {
        this.Id = Id;
        this.Date = Date;
        this.Amount = Amount;
        this.nameItem = nameItem;
    }

    public HandiworkPayment(int Id, String Date, double Amount, String nameItem, double TotalPayment) {
        this.Id = Id;
        this.Date = Date;
        this.Amount = Amount;
        this.nameItem = nameItem;
        this.TotalPayment = TotalPayment;
    }

    public double getTotalPayment() {
        return TotalPayment;
    }

    public void setTotalPayment(double TotalPayment) {
        this.TotalPayment = TotalPayment;
    }

    public String getNameItem() {
        return nameItem;
    }

    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
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
