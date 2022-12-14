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
            
            CallableStatement statement = connection.prepareCall("{call pr_add_handiwork_detail (?,?,?,?,?,?,?,?,?,?)}");
            statement.setInt(1, HandiworkDetail.getTypeItemId());
            statement.setInt(2, HandiworkDetail.getHandiworkId());
            statement.setString(3, HandiworkDetail.getEntryDate());
            statement.setString(4, HandiworkDetail.getDetail());
            statement.setString(5, HandiworkDetail.getAddDetail());
            statement.setDouble(6, HandiworkDetail.getCost());
            statement.setString(7, HandiworkDetail.getDeliveryDeadline());
            statement.setBoolean(8, false);
            statement.setString(9, HandiworkDetail.getState());
            statement.setInt(10, HandiworkDetail.getSubItemsQty());
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                return rs.getInt("last_insert_id()");
            }
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
                HdnDetail.setDetail(((rs.getString("hde_detail"))));
                HdnDetail.setAddDetail(((rs.getString("hde_add_det"))));
                HdnDetail.setNameItem(((rs.getString("typ_name"))));
                HdnDetail.setDeliveryDeadline((rs.getString("hde_delivery_deadline")));
                HdnDetail.setPayment((rs.getDouble("Abonado")));
                HdnDetail.setCost((rs.getDouble("hde_cost")));
                String StatePay = "No pagado";
                if(!"0".equals(rs.getString("hde_pay_status"))){
                    StatePay = "Pagado";
                }
                HdnDetail.setPayStatus(StatePay);
                
                String StateItem = "Pendiente";
                if(!"p".equals(rs.getString("hde_state"))){
                    StateItem = "Finalizado";
                }
                
                HdnDetail.setState(StateItem);
                
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
        CallableStatement statement = null;
        try {
            statement = connection.prepareCall("{call pr_update_handiwork_detail( ?, ?, ?, ?, ?, ?, ?,?)}");
            statement.setString(1, HandiworkDetail.getDetail());
            statement.setString(2, HandiworkDetail.getAddDetail());
            statement.setDouble(3, HandiworkDetail.getCost());
            statement.setString(4, HandiworkDetail.getDeliveryDeadline());
            
            boolean paystatus = false;
            if(!HandiworkDetail.getPayStatus().equals("No pagado")){
                paystatus = true;
            }
            statement.setBoolean(5, paystatus);
            
            String stateItem = "p";
            if(! HandiworkDetail.getPayStatus().equals("Pendiente")){
                stateItem = "f";
            }
            
            statement.setString(6, stateItem);
            statement.setInt(7, HandiworkDetail.getId());
            statement.setInt(8, HandiworkDetail.getSubItemsQty());
            statement.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
        } 
        return false;
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
