/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Model.Customer;
import java.util.List;

/**
 *
 * @author kevin
 */
public interface PersonDAO extends DAO{
    
    public int insertPerson(Customer person);
    public boolean updatePerson(Customer person);
    public boolean deletePerson(Customer person);
    public List searchPerson(Customer person);
    public List ListPersons();
    public List searchByIdNames(int projectId);
    public List getIdNamesPersons();
    
}
