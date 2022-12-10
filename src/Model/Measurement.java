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
public class Measurement {
    
    private int Id;
    private String Name;
    
    private int IdHandDetail;
    private double Value;

    public Measurement(int Id, String Name) {
        this.Id = Id;
        this.Name = Name;
    }

    public Measurement(int Id, int IdHandDetail, double Value) {
        this.Id = Id;
        this.IdHandDetail = IdHandDetail;
        this.Value = Value;
    }

    public Measurement(int Id, String Name, int IdHandDetail, double Value) {
        this.Id = Id;
        this.Name = Name;
        this.IdHandDetail = IdHandDetail;
        this.Value = Value;
    }

    public Measurement() {
    }

    public int getIdHandDetail() {
        return IdHandDetail;
    }

    public void setIdHandDetail(int IdHandDetail) {
        this.IdHandDetail = IdHandDetail;
    }

    public double getValue() {
        return Value;
    }

    public void setValue(double Value) {
        this.Value = Value;
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
