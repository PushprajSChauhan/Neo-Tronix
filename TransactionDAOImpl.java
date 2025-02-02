/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.neotronix.dao.impl;

import in.neotronix.dao.TransactionDAO;
import in.neotronix.utility.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class TransactionDAOImpl implements TransactionDAO{

    @Override
    public String getUserId(String transactionId) {
        String userId="";
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("select useremail from transactions where transid=?");
            ps.setString(1, transactionId);
            rs=ps.executeQuery();
            if(rs.next()){
                userId=rs.getString(1);
            }
        }
        catch(SQLException ex){
            System.out.println("Error in getUserId "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return userId;
    }
    
    public double getTotalAmount(String username) {
        double totalAmount = 0.0;
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs=null;
        try {
            ps = conn.prepareStatement("select amount from transactions where useremail = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                totalAmount = rs.getDouble(1);
            }
        }catch (SQLException ex) {
            System.out.println("Error in getTotalCartAmount "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return totalAmount;
    }
}
