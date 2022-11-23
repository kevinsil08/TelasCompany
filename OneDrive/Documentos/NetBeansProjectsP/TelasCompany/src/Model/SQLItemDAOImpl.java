/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.ItemDAO;
import FileConnect.FileConnect;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kevin
 */
public class SQLItemDAOImpl implements ItemDAO{
    
    private  Connection connection;
    private static final List<Item> EMPTY = new ArrayList<>();

    @Override
    public int InsertItem(Item typeItem) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean UpdateItem(Item typeItem) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean DeleteItem(Item typeItem) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public List<Item> ListItems() {
        List<Item> ListItems = new ArrayList<>();
        ResultSet rs = null;
        CallableStatement statement = null;
        
        try {
            statement = connection.prepareCall("{call pr_list_items()}");
            rs = statement.executeQuery();
            while (rs.next()) {
                Item item = new Item();
                item.setName(((rs.getString("typ_name"))));
                item.setId((rs.getInt("typ_id")));
                
                ListItems.add(item);
            }
            return ListItems;
        } catch (SQLException e) {
            System.err.println(e);
        } 
        return EMPTY;
    }
    
    

    @Override
    public void setup() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void connect() throws Exception {
        FileConnect fileConnect = new FileConnect();
        connection = fileConnect.getConnection();
    }

    @Override
    public void close() throws Exception {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    

    
    
}
