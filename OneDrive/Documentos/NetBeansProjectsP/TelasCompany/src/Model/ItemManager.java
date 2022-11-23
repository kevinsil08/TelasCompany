/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.ItemDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kevin
 */
public class ItemManager {
    
    private ItemDAO itemDAO;
    
    public ItemManager(ItemDAO itemDAO){
        try {
            this.itemDAO = itemDAO;
            itemDAO.connect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void AddHandiworkDetail(String Name) throws Exception{
        Item item = new Item(0,Name);
        itemDAO.InsertItem(item);
    }
    
    public void UpdateHandiworkDetail(Item item){
        itemDAO.UpdateItem(item);
    }
    
    public void DeleteHandiworkDetail(Item item){
        itemDAO.DeleteItem(item);
    }
    
    public List<Item> ListItems(){
        return itemDAO.ListItems();
    }
    
    
    
    public void close(){
        try {
            itemDAO.close();
        } catch (Exception ex) {
            Logger.getLogger(HandiworkDetailManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
