/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.neotronix.dao.impl;

import in.neotronix.dao.ProductDAO;
import in.neotronix.pojo.ProductPojo;
import in.neotronix.utility.DBUtil;
import in.neotronix.utility.IDUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class ProductDAOImpl implements ProductDAO{
    
    @Override
    public String addProduct(ProductPojo product){
        String status="Product was not added";
        if(product.getProdId()==null){
            product.setProdId(IDUtil.generateProdId());
        }
        
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement("insert into products values(?,?,?,?,?,?,?,?)");
            ps.setString(1,product.getProdId());
            ps.setString(2,product.getProdName());
            ps.setString(3,product.getProdType());
            ps.setString(4,product.getProdInfo());
            ps.setDouble(5,product.getProdPrice());
            ps.setInt(6,product.getProdQuantity());
            ps.setBlob(7, product.getProdImage());
            ps.setString(8, "Y");
            
            int count=ps.executeUpdate();
            if(count==1){
                status="Product added successfully with ID "+product.getProdId();
            }
        }
        catch(SQLException ex){
            System.out.println("Error in addProduct method "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }
    
    @Override
    public String updateProduct(ProductPojo prevProduct, ProductPojo updatedProduct){
        String status="Product updation failed";
        if(prevProduct.getProdId().equals(updatedProduct.getProdId())==false){
            status="Product ID does not match with the product to be updated. Updation failed";
            return status;
        }
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement("update produts set pname=?, ptype=?, pinfo=?, pprice=?, pquantity=?, image=? where pid=?");
            ps.setString(1, updatedProduct.getProdName());
            ps.setString(2, updatedProduct.getProdType());
            ps.setString(3, updatedProduct.getProdInfo());
            ps.setDouble(4, updatedProduct.getProdPrice());
            ps.setInt(5, updatedProduct.getProdQuantity());
            ps.setBlob(6, updatedProduct.getProdImage());
            ps.setString(7, updatedProduct.getProdId());
//            ID koisi bhi ProductPojo ki le skte hain 

            int count=ps.executeUpdate();
            if(count==1){
                status="Product updated successfully";
            }
        }
        catch(SQLException ex){
            status="Error "+ex.getMessage();
            System.out.println("Error in updateProduct method "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }
    
    @Override
    public String updateProductPrice(String prodId, double updatedPrice){
        String status="Product price updation failed";
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement("update produts set pprice=? where pid=?");
            ps.setDouble(1, updatedPrice);
            ps.setString(2, prodId);

            int count=ps.executeUpdate();
            if(count==1){
                status="Product price updated successfully";
            }
        }
        catch(SQLException ex){
            status="Error "+ex.getMessage();
            System.out.println("Error in updateProductPrice method "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }
    
    @Override
    public List<ProductPojo> getAllProducts(){
        List<ProductPojo> productList=new ArrayList<>();
        Connection conn=DBUtil.provideConnection();
        Statement st=null;
        ResultSet rs=null;
        try{
            st=conn.createStatement();
            rs=st.executeQuery("select * from products where available ='Y'");
            while(rs.next()){
                ProductPojo product=new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdQuantity(rs.getInt("quantity"));
                product.setProdImage(rs.getAsciiStream("image"));
//                getAsciiStream will encode image in Base64 format and send to InputStream
//                we can also use the getBinaryStream method which is also a child of InputStream method

                productList.add(product);
            }
        }
        catch(SQLException ex){
            System.out.println("Error in getAllProducts method "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(st);
        return productList;
    } 
    
    @Override
    public List<ProductPojo> getAllProductsByType(String pType){
        List<ProductPojo> productList=new ArrayList<>();
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        pType=pType.toLowerCase();
        try{
            ps=conn.prepareStatement("select * from products where lower(ptype) like ? AND available='Y'");
            ps.setString(1,"%"+pType+"%");
            rs=ps.executeQuery();
            while(rs.next()){
                ProductPojo product=new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdQuantity(rs.getInt("quantity"));
                product.setProdImage(rs.getAsciiStream("image"));
//                getAsciiStream will encode image in Base64 format and send to InputStream
//                we can also use the getBinaryStream method which is also a child of InputStream method

                productList.add(product);
            }
        }
        catch(SQLException ex){
            System.out.println("Error in getAllProductsByType method "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return productList;
    }
    
    @Override
    public List<ProductPojo> searchAllProducts(String searchTerm){
        List<ProductPojo> productList=new ArrayList<>();
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        searchTerm=searchTerm.toLowerCase();
        try{
            ps=conn.prepareStatement("select * from products where lower(ptype) like ? OR lower(pname) like ? OR lower(pinfo) like ? AND available='Y'");
            ps.setString(1,"%"+searchTerm+"%");
            ps.setString(2,"%"+searchTerm+"%");
            ps.setString(3,"%"+searchTerm+"%");
            rs=ps.executeQuery();
            while(rs.next()){
                ProductPojo product=new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdQuantity(rs.getInt("quantity"));
                product.setProdImage(rs.getAsciiStream("image"));

                productList.add(product);
            }
        }
        catch(SQLException ex){
            System.out.println("Error in searchAllProducts method "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return productList;
    }
    
    @Override
    public ProductPojo getProductDetails(String prodId){
        ProductPojo product=null;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("select * from products where pid=? AND available='Y'");
            ps.setString(1, prodId);
            rs=ps.executeQuery();
            if(rs.next()){
                product=new ProductPojo();
                product.setProdId(rs.getString("pid"));
                product.setProdName(rs.getString("pname"));
                product.setProdPrice(rs.getDouble("pprice"));
                product.setProdType(rs.getString("ptype"));
                product.setProdInfo(rs.getString("pinfo"));
                product.setProdQuantity(rs.getInt("quantity"));
                product.setProdImage(rs.getAsciiStream("image"));
            }
        }
        catch(SQLException ex){
            System.out.println("Error in getProductDetails method "+ex);
            ex.printStackTrace();
        }
        
       DBUtil.closeStatement(ps);
       DBUtil.closeResultSet(rs);
       return product;
    }
    
    @Override
    public int getProductQuantity(String prodId){
        int quantity=0;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("select pquantity from products where pid=?");
            ps.setString(1,prodId);
            rs=ps.executeQuery();
            if(rs.next()){
                quantity=rs.getInt(1);
            }
        }
        catch(SQLException ex){
            System.out.println("Error in getProductQuantity "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return quantity;
    }
    
    @Override
    public String updateProductWithoutImage(String prevProdId, ProductPojo updatedProduct){
        String status="Updation failed";
        int prevQuantity=0;
        
        if(!prevProdId.equals(updatedProduct.getProdId())){
            status="Product ID's do not match. Updation failed";
            return status;
        }
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            prevQuantity=getProductQuantity(prevProdId);
            
            ps=conn.prepareStatement("update produts set pname=?, ptype=?, pinfo=?, pprice=?, pquantity=? where pid=?");
            ps.setString(1, updatedProduct.getProdName());
            ps.setString(2, updatedProduct.getProdType());
            ps.setString(3, updatedProduct.getProdInfo());
            ps.setDouble(4, updatedProduct.getProdPrice());
            ps.setInt(5, updatedProduct.getProdQuantity());
            ps.setBlob(6, updatedProduct.getProdImage());

            int count=ps.executeUpdate();
            if(count==1 && prevQuantity<updatedProduct.getProdQuantity()){
                status="Product updated successfully and Mail sent";
//                Code for sending mail which demanded the product
            }
            else if(count==1){
                status="Product updated successfully";
            }
        }
        catch(SQLException ex){
            status="Error "+ex.getMessage();
            System.out.println("Error in updateProductWithoutImage method "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }
    
    @Override
    public double getProductPrice(String prodId){
        double price=0.0;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=conn.prepareStatement("select pprice from products where pid=?");
            ps.setString(1,prodId);
            rs=ps.executeQuery();
            if(rs.next()){
                price=rs.getDouble(1);
            }
        }
        catch(SQLException ex){
            System.out.println("Error in getProductPrice "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return price;
    }
    
    @Override
    public boolean sellNProducts(String prodId, int n){
        boolean result=false;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        try{
            ps=conn.prepareStatement("update produts set pquantity=(pquantity-?) where pid=?");
            ps.setInt(1, n);
            ps.setString(2, prodId);

            int count=ps.executeUpdate();
            if(count==1){
                result=true;
            }
        }
        catch(SQLException ex){
            System.out.println("Error in sellNProducts method "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return result;
    }
    
    @Override
    public List<String> getAllProductsType(){
        List<String> productTypeList=new ArrayList<>();
        Connection conn=DBUtil.provideConnection();
        Statement st=null;
        ResultSet rs=null;
        try{
            st=conn.createStatement();
            rs=st.executeQuery("select distinct ptype from products");
            while(rs.next()){
                productTypeList.add(rs.getString(1));
            }
        }
        catch(SQLException ex){
            System.out.println("Error in getAllProductsType method "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(st);
        return productTypeList;
    }
    
    @Override
    public byte[] getImage(String prodId){
        byte [] arr=null;
        Connection conn=DBUtil.provideConnection();
        PreparedStatement ps=null;
        ResultSet rs=null;
        
        try{
            ps=conn.prepareStatement("select image from products where pid=?");
            ps.setString(1,prodId);
            rs=ps.executeQuery();
            if(rs.next()){
                arr=rs.getBytes(1);
            }
        }
        catch(SQLException ex){
            System.out.println("Error in getImage method "+ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        
        return arr;
    }
    
    @Override
    public String removeProduct(String prodId){
        
    }
}
