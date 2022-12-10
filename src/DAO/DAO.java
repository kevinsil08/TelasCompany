/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

/**
 *
 * @author natha
 */
public interface DAO {
    public void setup() throws Exception;
    public  void connect() throws Exception;
    public void close() throws Exception;
}
