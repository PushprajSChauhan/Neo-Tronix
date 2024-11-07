/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.neotronix.dao.impl;

import in.neotronix.dao.DemandDAO;
import in.neotronix.pojo.DemandPojo;
import in.neotronix.utility.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class DemandDAOImpl implements DemandDAO{
    
    @Override
    public boolean addProduct(DemandPojo demandPojo){
        boolean status=false;
        String updateSQL="update userdemand set quantity=quantity+? where useremail=? AND prodid=?";
        String insertSQL="insert into userdemand values(?,?,?)";
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps1=null,ps2=null;
        try{
            ps1=conn.prepareStatement(updateSQL);
            ps1.setInt(1, demandPojo.getDemandQuantity());
            ps1.setString(2,demandPojo.getUseremail());
            ps1.setString(3, demandPojo.getProdId());
            int count=ps1.executeUpdate();
            if(count==0){
//                Yaha aye hain matlb DB mei user ne iss product ki demand nahi kari hai phle se toh nayi demand row add krenge
                ps2=conn.prepareStatement(insertSQL);
                ps2.setString(1,demandPojo.getUseremail());
                ps2.setString(2,demandPojo.getProdId());
                ps2.setInt(3,demandPojo.getDemandQuantity());
                ps2.executeUpdate();
            }
            status=true;
        }
        catch(SQLException ex){
            System.out.println("Error in addProduct "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps1);
        DBUtil.closeStatement(ps2);
        return status;
    }

    @Override
    public boolean removeProduct(String userId, String prodId) {
        
    }

    @Override
    public List<DemandPojo> haveDemanded(String prodId) {
        
    }
}
