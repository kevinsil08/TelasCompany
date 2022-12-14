/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DAO.PlanchadoDAO;
import FileConnect.FileConnect;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author natha
 */
public class SQLPlanchadoImpl implements PlanchadoDAO{
    
    private Connection connection;
    private static final List<Planchado> EMPTY = new ArrayList<>();
    
    @Override
    public int insertPlanchado(Planchado planchado) {
        CallableStatement statement = null;
        ResultSet rs;
        try {
            statement = connection.prepareCall("{call pr_insert_planchado(?,?)}");
            statement.setString(1, planchado.getDescription());
            statement.setDouble(2, planchado.getCost());
            rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("LAST_INSERT_ID()");
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return -1;
    }
    
    @Override
    public List listPlanchados() {
        List<Planchado> listPlanchados = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        try {
            CallableStatement callableStatement = connection.prepareCall("{call pr_list_planchado()}");
            rs = callableStatement.executeQuery();
            while (rs.next()) {
                Planchado planchado = new Planchado();
                planchado.setPlanchadoID(rs.getInt("pla_id"));
                planchado.setDescription(rs.getString("pla_description"));
                planchado.setCost(rs.getDouble("pla_cost"));
                listPlanchados.add(planchado);
            }
            return listPlanchados;

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

    @Override
    public int insertPlanchadoToDetail(Planchado planchado) {
        CallableStatement statement = null;
        ResultSet rs;
        try {
            statement = connection.prepareCall("{call pr_add_planchado_to_han_detail(?,?)}");
            statement.setInt(1, planchado.getHanDetailID());
            statement.setInt(2, planchado.getPlanchadoID());
            rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("LAST_INSERT_ID()");
            }
        } catch (SQLException e) {
            System.err.println(e);
        }
        return -1;
    }

    @Override
    public List listPlanchadosByHandId(int hanDetailID) {
        List<Planchado> listPlanchados = new ArrayList<>();
        PreparedStatement ps;
        CallableStatement statement = null;
        ResultSet rs;
        try {
            statement = connection.prepareCall("{call pr_list_planchado_by_han_id(?)}");
            statement.setInt(1, hanDetailID);
            rs = statement.executeQuery();
            while (rs.next()) {
                Planchado planchado = new Planchado();
                planchado.setPlanchadoID(rs.getInt("pla_id"));
                planchado.setDescription(rs.getString("pla_description"));
                planchado.setCost(rs.getDouble("pla_cost"));
                listPlanchados.add(planchado);
            }
            return listPlanchados;

        } catch (SQLException e) {
            System.err.println(e);
        }
        return EMPTY;
    }

    @Override
    public int deletePlanchadosOnDetail(int hanDetailID) {
        CallableStatement statement = null;
        ResultSet rs;
        try {
            statement = connection.prepareCall("{call pr_delete_planchados_from_han_detail(?)}");
            statement.setInt(1, hanDetailID);
            rs = statement.executeQuery();
            return 0;
        } catch (SQLException e) {
            System.err.println(e);
        }
        return -1;
    }
    
}
