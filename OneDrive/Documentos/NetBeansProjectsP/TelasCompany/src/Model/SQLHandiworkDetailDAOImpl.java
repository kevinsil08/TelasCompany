/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.HandiworkDetailDAO;
import FileConnect.FileConnect;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kevin
 */
public class SQLHandiworkDetailDAOImpl implements HandiworkDetailDAO{
    
    private  Connection connection;
    private static final List<HandiworkDetail> EMPTY = new ArrayList<>();

    @Override
    public int InsertHandiworkDetail(HandiworkDetail HandiworkDetail) {
        try {
            CallableStatement statement = connection.prepareCall("{call pr_add_handiwork_detail (?,?,?,?,?,?,?,?,?)}");
            statement.setInt(1, HandiworkDetail.getTypeItemId());
            statement.setInt(2, HandiworkDetail.getHandiworkId());
            statement.setString(3, HandiworkDetail.getEntryDate());
            statement.setString(4, HandiworkDetail.getDetail());
            statement.setString(5, HandiworkDetail.getAddDetail());
            statement.setDouble(6, HandiworkDetail.getCost());
            statement.setString(7, HandiworkDetail.getDeliveryDeadline());
            boolean state = false;
            if(!HandiworkDetail.getPayStatus().equals("f")){
                state = true;
            }
            statement.setBoolean(8, state);
            statement.setString(9, HandiworkDetail.getState());
            statement.execute();
            return HandiworkDetail.getId();
        } catch (SQLException e) {
            System.err.println(e);
        } 
        return -1;
    }
    
    @Override
    public List<HandiworkDetail> ListItemsForTable(int IdHandiwork) {
        List<HandiworkDetail> ListItems = new ArrayList<>();
        ResultSet rs = null;
        
        try {
            CallableStatement statement = connection.prepareCall("{call pr_list_item_for_handiwork(?)}");
            statement.setInt(1, IdHandiwork);
            rs = statement.executeQuery();
            while (rs.next()) {
                HandiworkDetail HdnDetail = new HandiworkDetail();
                
                HdnDetail.setId((rs.getInt("hde_id")));
                HdnDetail.setNameItem(((rs.getString("typ_name"))));
                HdnDetail.setDeliveryDeadline((rs.getString("hde_delivery_deadline")));
                HdnDetail.setPayment((rs.getDouble("Abonado")));
                HdnDetail.setCost((rs.getDouble("hde_cost")));
                HdnDetail.setPayStatus((rs.getString("hde_pay_status")));
                HdnDetail.setState((rs.getString("hde_state")));
                ListItems.add(HdnDetail);
            }
            return ListItems;
        } catch (SQLException e) {
            System.err.println(e);
        } 
        return EMPTY;
    }

    @Override
    public boolean UpdateHandiworkDetail(HandiworkDetail HandiworkDetail) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean DeleteHandiworkDetail(HandiworkDetail HandiworkDetail) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HandiworkDetail> findProjectByProperty(HandiworkDetailSearchType searchType, Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HandiworkDetail> findAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
