/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.neotronix.dao;

import in.neotronix.pojo.UserPojo;

/**
 *
 * @author ASUS
 */
public interface UserDAO {
    String registerUser(UserPojo user);
    boolean isRegistered(String emailId);
    String isValidCredentials(String emailId, String password);
    UserPojo getUserDetails(String emailId);
    String getUserFirstName(String emailId);
    String getUserAddr(String emailId);
}
