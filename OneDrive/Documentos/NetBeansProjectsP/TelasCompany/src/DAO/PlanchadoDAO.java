/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Planchado;
import java.util.List;

/**
 *
 * @author natha
 */
public interface PlanchadoDAO extends DAO{
    public int insertPlanchado(Planchado planchado);
    public List listPlanchados();
    public int insertPlanchadoToDetail(Planchado planchado);
    public List listPlanchadosByHandId(int hanDetailID);
    public int deletePlanchadosOnDetail(int hanDetailID);
}
