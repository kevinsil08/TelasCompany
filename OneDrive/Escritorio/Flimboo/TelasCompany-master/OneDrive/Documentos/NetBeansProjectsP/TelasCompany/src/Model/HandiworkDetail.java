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
public class HandiworkDetail {
    
    private int Id;
    private String nameItem;
    private int TypeItemId;
    private int HandiworkId;
    private String EntryDate;
    private String Detail;
    private String AddDetail;
    private double Payment;
    private double Cost;
    private String DeliveryDeadline;
    private String PayStatus;
    private String State;

    public HandiworkDetail() {
    }

    public HandiworkDetail(int Id, int TypeItemId, int HandiworkId, String EntryDate, String Detail, String AddDetail, double Cost, String DeliveryDeadline, String PayStatus, String State) {
        this.Id = Id;
        this.TypeItemId = TypeItemId;
        this.HandiworkId = HandiworkId;
        this.EntryDate = EntryDate;
        this.Detail = Detail;
        this.AddDetail = AddDetail;
        this.Cost = Cost;
        this.DeliveryDeadline = DeliveryDeadline;
        this.PayStatus = PayStatus;
        this.State = State;
    }

    public void setPayment(double Payment) {
        this.Payment = Payment;
    }

    public double getPayment() {
        return Payment;
    }

    
    public void setNameItem(String nameItem) {
        this.nameItem = nameItem;
    }

    public String getNameItem() {
        return nameItem;
    }

    
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getTypeItemId() {
        return TypeItemId;
    }

    public void setTypeItemId(int TypeItemId) {
        this.TypeItemId = TypeItemId;
    }

    public int getHandiworkId() {
        return HandiworkId;
    }

    public void setHandiworkId(int HandiworkId) {
        this.HandiworkId = HandiworkId;
    }

    public String getEntryDate() {
        return EntryDate;
    }

    public void setEntryDate(String EntryDate) {
        this.EntryDate = EntryDate;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String Detail) {
        this.Detail = Detail;
    }

    public String getAddDetail() {
        return AddDetail;
    }

    public void setAddDetail(String AddDetail) {
        this.AddDetail = AddDetail;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double Cost) {
        this.Cost = Cost;
    }

    public String getDeliveryDeadline() {
        return DeliveryDeadline;
    }

    public void setDeliveryDeadline(String DeliveryDeadline) {
        this.DeliveryDeadline = DeliveryDeadline;
    }

    public String getPayStatus() {
        return PayStatus;
    }

    public void setPayStatus(String PayStatus) {
        this.PayStatus = PayStatus;
    }

    public String getState() {
        return State;
    }

    public void setState(String State) {
        this.State = State;
    }
    
    
    
}
