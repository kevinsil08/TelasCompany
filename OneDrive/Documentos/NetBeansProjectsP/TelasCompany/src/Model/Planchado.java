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
public class Planchado {
    private int planchadoID;
    private String description;
    private Double cost;
    private int hanDetailID;

    public Planchado(int planchadoID, String description, Double cost, int hanDetailID) {
        this.planchadoID = planchadoID;
        this.description = description;
        this.cost = cost;
        this.hanDetailID = hanDetailID;
    }

    public Planchado() {
    }

    public Planchado(Planchado planchadoSeleccion) {
        this.planchadoID = planchadoSeleccion.getPlanchadoID();
        this.description = planchadoSeleccion.getDescription();
        this.cost = planchadoSeleccion.getCost();
        this.hanDetailID = planchadoSeleccion.getHanDetailID();
    }

    public int getPlanchadoID() {
        return planchadoID;
    }

    public void setPlanchadoID(int planchadoID) {
        this.planchadoID = planchadoID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public int getHanDetailID() {
        return hanDetailID;
    }

    public void setHanDetailID(int hanDetailID) {
        this.hanDetailID = hanDetailID;
    }

    @Override
    public String toString() {
        return   description  + "\t/$" + cost.toString();
    }
    
}
