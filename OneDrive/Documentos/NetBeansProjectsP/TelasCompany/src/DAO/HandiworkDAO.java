/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Handiwork;
import java.util.List;

/**
 *
 * @author natha
 */
public interface HandiworkDAO extends DAO{
    public int insertHandiwork(Handiwork handiwork);
    public boolean updateHandiwork(Handiwork handiwork);
    public boolean deleteHandiwork(Handiwork handiwork);
    public double totalPayments(Handiwork handiwork);
    public boolean setState(Handiwork handiwork);
    public List searchHanById(int idHandiwork);
    public boolean updateNumGarmentsTotalCost(Handiwork handiwork);
    public List<Handiwork> findAll();
}
