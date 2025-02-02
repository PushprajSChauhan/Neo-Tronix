/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.neotronix.dao.impl;

import in.neotronix.dao.CartDAO;
import in.neotronix.pojo.CartPojo;
import in.neotronix.pojo.DemandPojo;
import in.neotronix.utility.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class CartDAOImpl implements CartDAO {

    @Override
    public String addProductToCart(CartPojo cart) {
        String status = "Failed to add product into cart";
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("select * from usercart where prodid=? AND useremail=?");
            ps.setString(1, cart.getProdId());
            ps.setString(2, cart.getUseremail());
            rs = ps.executeQuery();
            if (rs.next()) {
//                yeh hamesha true hoga bcos hamare cart mei minimum ek product hamesha rahega jiski quantity ham ab increase kar rahe hain
                ProductDAOImpl prodDao = new ProductDAOImpl();
                int stockQuantity = prodDao.getProductQuantity(cart.getProdId());
//                yaha nayi quantity calculate krenge by adding quantity of product in cart and quantity of product increased using add button
                int increasedQuantity = cart.getQuantity() + rs.getInt("quantity");
                if (stockQuantity < increasedQuantity) {
//                    yaha pe jitne product available hain utne cart mei add krenge and bache hue product quantity ko demand mei daal denge
                    cart.setQuantity(stockQuantity);
                    updateProductInCart(cart);
                    status = "Only " + stockQuantity + " amount of items are available in stock, so we are adding them in your cart.";
                    DemandPojo demandPojo = new DemandPojo();
                    demandPojo.setProdId(cart.getProdId());
                    demandPojo.setUseremail(cart.getUseremail());
                    demandPojo.setDemandQuantity(increasedQuantity - stockQuantity);

                    DemandDAOImpl demandDao = new DemandDAOImpl();
                    boolean result = demandDao.addProduct(demandPojo);
                    if (result) {
                        status += " We will mail you when " + (increasedQuantity - stockQuantity) + " quantity of product becomes available";
                    }
                } else {
                    cart.setQuantity(increasedQuantity);
                    status = updateProductInCart(cart);
                }
            }
        } catch (SQLException ex) {
            status = "Product addition in cart failed due to exception";
            System.out.println("Error in addProductToCart " + ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return status;
    }

    @Override
    public String updateProductInCart(CartPojo cart) {
        String status = "Failed to add product in cart";
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        int ans = 0;
        try {
            ps1 = conn.prepareStatement("select * from usercart where prodid=? AND useremail=?");
            ps1.setString(1, cart.getProdId());
            ps1.setString(2, cart.getUseremail());
            rs = ps1.executeQuery();
            if (rs.next()) {
//                product is present in cart so we need to update it according to the quantity modified
                int quantity = cart.getQuantity();
                if (quantity > 0) {
//                    here we need to modify the required quantity needed by user
                    ps2 = conn.prepareStatement("update usercart set quantity=? where useremail=? AND prodid=?");
                    ps2.setInt(1, cart.getQuantity());
                    ps2.setString(2, cart.getUseremail());
                    ps2.setString(3, cart.getProdId());
                    ans = ps2.executeUpdate();
                    if (ans > 0) {
                        status = "Product successfully updated in the cart";
                    } else {
                        status = "Could not update the product quantity";
                    }
                } else if (quantity == 0) {
//                    here we need to delete the product from cart
                    ps2 = conn.prepareStatement("delete from usercart where useremail=? AND prodid=?");
                    ps2.setString(1, cart.getUseremail());
                    ps2.setString(2, cart.getProdId());
                    ans = ps2.executeUpdate();
                    if (ans > 0) {
                        status = "Product successfully updated in the cart";
                    } else {
                        status = "Could not remove the product from the cart";
                    }
                }
            } else {
//                product is not present in cart so we need to insert it
                ps2 = conn.prepareStatement("insert into usercart values(?,?,?)");
                ps2.setString(1, cart.getUseremail());
                ps2.setString(2, cart.getProdId());
                ps2.setInt(3, cart.getQuantity());
//                System.out.println(cart);
                ans = ps2.executeUpdate();
                if (ans > 0) {
                    status = "Product successfully added in cart";
                } else {
                    status = "Could not insert the product in the cart";
                }
            }
        } catch (SQLException ex) {
            status = "Updation failed due to exception";
            System.out.println("Error in updateProductInCart " + ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps1);
        DBUtil.closeStatement(ps2);
        return status;
    }

    @Override
    public List<CartPojo> getAllCartItems(String userId) {
        List<CartPojo> itemList = new ArrayList<>();
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("select * from usercart where useremail=?");
            ps.setString(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                CartPojo cart = new CartPojo();
                cart.setUseremail(rs.getString("useremail"));
                cart.setProdId(rs.getString("prodid"));
                cart.setQuantity(rs.getInt("quantity"));
                itemList.add(cart);
            }
        } catch (SQLException ex) {
            System.out.println("Error in getAllCartItems " + ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return itemList;
    }

    @Override
    public int getCartItemCount(String userId, String itemId) {
        if (userId == null || itemId == null) {
            return 0;
        }
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            ps = conn.prepareStatement("select quantity from usercart where useremail=? AND prodid=?");
            ps.setString(1, userId);
            ps.setString(2, itemId);
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error in getCartItemCount " + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return count;
    }

    @Override
    public String removeProductFromCart(String userId, String prodId) {
        String status = "Product removal failed";
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        ResultSet rs = null;
        try {
            ps1 = conn.prepareStatement("select from usercart where prodid=? AND useremail=?");
            ps1.setString(1, prodId);
            ps1.setString(2, userId);
            rs = ps1.executeQuery();
            if (rs.next()) {
//                yeh statement kabhi false nahi hoga bcos user ne product quantity kam krne keliye minus button click kiya hai jo sirf tab hoga jab kam se kam 1 item cart mei hoga
                int prodQuantity = rs.getInt("quantity");
                prodQuantity -= 1;
                if (prodQuantity > 0) {
                    ps2 = conn.prepareStatement("update usercart set quantity=? where useremail=? AND prodid=?");
                    ps2.setInt(1, prodQuantity);
                    ps2.setString(2, userId);
                    ps2.setString(3, prodId);
                    int count = ps2.executeUpdate();
                    if (count > 0) {
                        status = "Product removed successfully from the cart";
                    }
                } else {
                    ps2 = conn.prepareStatement("delete from usercart where useremail=? AND prodid=?");
                    ps2.setString(1, userId);
                    ps2.setString(2, prodId);
                    int count = ps2.executeUpdate();
                    if (count > 0) {
                        status = "Product removed from cart";
                    }
                }
            }
        } catch (SQLException ex) {
            status = "Removal failed due to exception";
            System.out.println("Error in removeProductFromCart " + ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps1);
        DBUtil.closeStatement(ps2);
        return status;
    }

    @Override
    public boolean removeAProduct(String userId, String prodId) {
//        yeh method tab call hoga jab user cart wale items ko buy krlega using payment toh saare products ko cart mei se hata denge
//        idhar ek baar mei ek hi product remove hoga but servlet ke andar loop rhega jo har product ko sequantially hatate jayega cart mei se
        boolean flag = false;
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("delete from usercart where useremail=? AND prodid=?");
            ps.setString(1, userId);
            ps.setString(2, prodId);
            int count = ps.executeUpdate();
            if (count > 0) {
                flag = true;
            }
        } catch (SQLException ex) {
            System.out.println("Error in removeAProduct " + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return flag;
    }
}
