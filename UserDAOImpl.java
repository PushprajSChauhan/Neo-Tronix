/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.neotronix.dao.impl;

import in.neotronix.dao.UserDAO;
import in.neotronix.pojo.UserPojo;
import in.neotronix.utility.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author ASUS
 */
public class UserDAOImpl implements UserDAO{
    
    @Override
    public boolean isRegistered(String emailId){
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        boolean flag=false;
        try{
            ps=conn.prepareStatement("select 1 from users where emailId=?");
            ps.setString(1, emailId);
            rs=ps.executeQuery();
            flag=rs.next();
        }
        catch(SQLException ex){
            System.out.println("Error in isRegistered method "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return flag;
    }   
    
    @Override
    public String registerUser(UserPojo user){
        String status="registration failed";
        boolean isUserRegistered=isRegistered(user.getUseremail());
//        The control will go inside this if body when user would already be registered in DB
        if(isUserRegistered){
            status="Email already in use. Try again";
            return status;
        }
        
//        Yaha aye mtlb user DB mei nahi hai and usse add krne keliye ab DB se communicate krna padega
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement("insert into users values(?,?,?,?,?,?)");
            ps.setString(1,user.getUseremail());
            ps.setString(2,user.getUsername());
            ps.setString(3,user.getMobile());
            ps.setString(4,user.getAddress());
            ps.setInt(5,user.getPincode());
            ps.setString(6,user.getPassword());
            
            int count=ps.executeUpdate();
            if(count==1){
                status="Registration successfull";
//                Registration email
            }
        }
        catch(SQLException ex){
            System.out.println("Error in registerUser method "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }
    
    @Override
    public String isValidCredentials(String emailId,String password){
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        String status="Login denied! Invalid username or password";
        try{
            ps=conn.prepareStatement("select 1 from users where emailId=? and password=?");
            ps.setString(1, emailId);
            ps.setString(2, password);
            rs=ps.executeQuery();
            if(rs.next()){
                status="Login Successfull";
            }
        }
        catch(SQLException ex){
            status="Error"+ex.getMessage();
            System.out.println("Error in isValidCredentials method "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return status;
    }
    
    @Override
    public UserPojo getUserDetails(String emailId){
        UserPojo user=null;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("select * from users where useremail=?");
            ps.setString(1, emailId);
            rs=ps.executeQuery();
            if(rs.next()){
                user=new UserPojo();
                user.setUseremail(rs.getString("useremail"));
                user.setUsername(rs.getString("username"));
                user.setAddress(rs.getString("address"));
                user.setMobile(rs.getString("mobile"));
                user.setPincode(rs.getInt("pincode"));
                user.setPassword(rs.getString("password"));
            }
        }
        catch(SQLException ex){
            System.out.println("Error in getUserDetails method "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return user;
    }
    
    @Override
    public String getUserFirstName(String emailId){
        String fName=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn=DBUtil.provideConnection();
        try{
            ps=conn.prepareStatement("select username from users where useremail=?");
            ps.setString(1, emailId);
            rs=ps.executeQuery();
            if(rs.next()){
                String fullName=rs.getString(1);
                fName=fullName.split(" ")[0];
            }
        }
        catch(SQLException ex){
            System.out.println("Error in getUserFirstName "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return fName;
    }
    
    @Override
    public String getUserAddr(String emailId){
        String address=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection conn=DBUtil.provideConnection();
        try{
            ps=conn.prepareStatement("select address from users where useremail=?");
            ps.setString(1, emailId);
            rs=ps.executeQuery();
            if(rs.next()){
                address=rs.getString(1);
            }
        }
        catch(SQLException ex){
            System.out.println("Error in getUserAddr "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return address;
    }
}
