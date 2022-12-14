/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Item;
import java.util.List;

/**
 *
 * @author kevin
 */
public interface ItemDAO extends DAO{
    
    public int InsertItem(Item typeItem);
    public boolean UpdateItem(Item typeItem);
    public boolean DeleteItem(Item typeItem);
    public List<Item> ListItems();
    
    
    
}
