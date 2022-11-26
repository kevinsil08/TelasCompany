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
public class Item {
    
    private int Id;
    private String Name;
    
    private String DeliveryDate;
    private double Payment;
    private double TotalCost;
    private boolean StatusPayment;
    private String StatusHandiworkDetail;

    public Item() {
    }

    public Item(int Id, String Name) {
        this.Id = Id;
        this.Name = Name;
    }

    public Item(int Id, String Name, String DeliveryDate, double Payment, double TotalCost, boolean StatusPayment, String StatusHandiworkDetail) {
        this.Id = Id;
        this.Name = Name;
        this.DeliveryDate = DeliveryDate;
        this.Payment = Payment;
        this.TotalCost = TotalCost;
        this.StatusPayment = StatusPayment;
        this.StatusHandiworkDetail = StatusHandiworkDetail;
    }
    
    

    public String getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(String DeliveryDate) {
        this.DeliveryDate = DeliveryDate;
    }

    public double getPayment() {
        return Payment;
    }

    public void setPayment(double Payment) {
        this.Payment = Payment;
    }

    public double getTotalCost() {
        return TotalCost;
    }

    public void setTotalCost(double TotalCost) {
        this.TotalCost = TotalCost;
    }

    public boolean isStatusPayment() {
        return StatusPayment;
    }

    public void setStatusPayment(boolean StatusPayment) {
        this.StatusPayment = StatusPayment;
    }

    public String getStatusHandiworkDetail() {
        return StatusHandiworkDetail;
    }

    public void setStatusHandiworkDetail(String StatusHandiworkDetail) {
        this.StatusHandiworkDetail = StatusHandiworkDetail;
    }
    
    

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }
    
}