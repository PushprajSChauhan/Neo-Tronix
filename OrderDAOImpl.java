/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.neotronix.dao.impl;

import in.neotronix.dao.OrderDAO;
import in.neotronix.pojo.CartPojo;
import in.neotronix.pojo.OrderDetailsPojo;
import in.neotronix.pojo.OrderPojo;
import in.neotronix.pojo.TransactionPojo;
import in.neotronix.pojo.UserPojo;
import in.neotronix.utility.DBUtil;
import in.neotronix.utility.IDUtil;
import in.neotronix.utility.MailMessage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;

/**
 *
 * @author ASUS
 */
public class OrderDAOImpl implements OrderDAO {

    @Override
    public boolean addOrder(OrderPojo order) {
        boolean status = false;
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("insert into orders values(?,?,?,?,?)");
            ps.setString(1, order.getOrderId());
            ps.setString(2, order.getProdId());
            ps.setInt(3, order.getQuantity());
            ps.setDouble(4, order.getAmount());
            ps.setInt(5, 0);
//            order ship nahi hoga turant place krte hi isliye uska status 0 rkhenge
            int count = ps.executeUpdate();
            status = count > 0;
        } catch (SQLException ex) {
            System.out.println("Error in addOrder " + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }

    @Override
    public boolean addTransaction(TransactionPojo transaction) {
        boolean status = false;
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("insert into transactions values(?,?,?,?)");
            ps.setString(1, transaction.getTransactionId());
            ps.setString(2, transaction.getUseremail());
            ps.setDate(3, new java.sql.Date(transaction.getTranTime().getTime()));
//            yaha phle util wali Date ko fetch kiya hai, fir usse sql wali Date mei convert kiya hai using getTime() method and a constructor of java.sql.Date
            ps.setDouble(4, transaction.getAmount());

            int count = ps.executeUpdate();
            status = count > 0;
        } catch (SQLException ex) {
            System.out.println("Error in addTransaction " + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }

    @Override
    public List<OrderPojo> getAllOrders() {
        List<OrderPojo> orderList = new ArrayList<>();
        Connection conn = DBUtil.provideConnection();
        Statement st = null;
        ResultSet rs = null;
        try {
            st = conn.createStatement();
            rs = st.executeQuery("select * from orders");
            while (rs.next()) {
                OrderPojo order = new OrderPojo();
                order.setOrderId(rs.getString("orderid"));
                order.setProdId(rs.getString("prodid"));
                order.setQuantity(rs.getInt("quantity"));
                order.setShipped(rs.getInt("shipped"));
                order.setAmount(rs.getDouble("amount"));
                orderList.add(order);
            }
        } catch (SQLException ex) {
            System.out.println("Error in getAllOrders " + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(st);
        DBUtil.closeResultSet(rs);
        return orderList;
    }

    @Override
    public List<OrderDetailsPojo> getAllOrderDetails(String userEmail) {
        List<OrderDetailsPojo> orderDetailsList = new ArrayList<>();
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement("select"
                    + " p.pid as prodid,"
                    + " o.orderid as orderid,"
                    + " o.shipped as shipped,"
                    + " p.image as image,"
                    + " p.pname as pname,"
                    + " o.quantity as quantity,"
                    + " o.amount as amount,"
                    + " t.transtime as time"
                    + " FROM orders o"
                    + " JOIN products p on o.prodid = p.pid"
                    + " JOIN transactions t on o.orderid = t.transid"
                    + " where t.useremail = ?");
            ps.setString(1, userEmail);
            rs = ps.executeQuery();
            while (rs.next()) {
                OrderDetailsPojo orderDetails = new OrderDetailsPojo();
                orderDetails.setOrderId(rs.getString("orderid"));
                orderDetails.setProdImage(rs.getAsciiStream("image"));
                orderDetails.setProdId(rs.getString("prodid"));
                orderDetails.setProdName(rs.getString("pname"));
                orderDetails.setQuantity(rs.getInt("quantity"));
                orderDetails.setAmount(rs.getDouble("amount"));
                orderDetails.setTime(rs.getTimestamp("time"));
                orderDetails.setShipped(rs.getInt("shipped"));
                orderDetailsList.add(orderDetails);
            }
        } catch (SQLException ex) {
            System.out.println("Error in getAllOrderDetails " + ex);
            ex.printStackTrace();
        }
        DBUtil.closeResultSet(rs);
        DBUtil.closeStatement(ps);
        return orderDetailsList;
    }

    @Override
    public String shipNow(String orderId, String prodId) {
        String status = "Failed to ship order";
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("update orders set shipped=1 where orderid=? AND prodid=?");
            ps.setString(1, orderId);
            ps.setString(2, prodId);
            System.out.println(prodId+" "+orderId);
            int count = ps.executeUpdate();
            System.out.println(count);
            if (count > 0) {
                status = "Order has been shipped successfully";
                TransactionDAOImpl tranDao = new TransactionDAOImpl();
                String userId = tranDao.getUserId(orderId);
                UserDAOImpl userDao = new UserDAOImpl();
                MailMessage.shipmentSuccess(userId, userDao.getUserFirstName(userId), userDao.getUserAddr(userId));
            }
        } catch (SQLException | MessagingException ex) {
            System.out.println("Error in shipNow " + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        return status;
    }

    @Override
    public String paymentSuccess(String username, double paidAmount) {
        String status = "Order Placement Failed!";
        CartDAOImpl cartDao = new CartDAOImpl();
        List<CartPojo> cartList = cartDao.getAllCartItems(username);
        if(cartList.isEmpty()){
            return status;
        }
        String transactionId = IDUtil.generateTranId();
        TransactionPojo tranPojo = new TransactionPojo();
        tranPojo.setTransactionId(transactionId);
        tranPojo.setUseremail(username);
        tranPojo.setAmount(paidAmount);
        tranPojo.setTranTime(new java.util.Date());

        boolean result = addTransaction(tranPojo);
        if (result == false) {
            return status;
        }
        boolean ordered = true;
        ProductDAOImpl prodDao = new ProductDAOImpl();
        for (CartPojo cart : cartList) {
            double amount = prodDao.getProductPrice(cart.getProdId()) * cart.getQuantity();
            OrderPojo order = new OrderPojo();
            order.setOrderId(transactionId);
            order.setProdId(cart.getProdId());
            order.setQuantity(cart.getQuantity());
            order.setAmount(amount);
            order.setShipped(0);
            ordered = addOrder(order);
            if (ordered == false) {
                break;
            }
            ordered = cartDao.removeAProduct(cart.getUseremail(), cart.getProdId());
            ordered = prodDao.sellNProducts(cart.getProdId(), cart.getQuantity());
            if (ordered == false) {
                break;
            }
        }

        if (ordered) {
//            yaha aye matlb status update krna h succesfull order place hone keliye
            status = "Order placed successfully";
            UserDAOImpl userDao = new UserDAOImpl();
            UserPojo user = userDao.getUserDetails(username);
            try {
                MailMessage.transactionSuccess(username, user.getUsername(), tranPojo.getTransactionId(), paidAmount);
                System.out.println("Transaction successfull " + transactionId);
            } catch (MessagingException ex) {
                System.out.println("Error in paymentSuccess " + ex);
                ex.printStackTrace();
            }
        } else {
            System.out.println("Transaction failed " + transactionId);
        }
        return status;
    }

    @Override
    public int getSoldQuantity(String prodId) {
        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int quantity = 0;
        try {
            ps = conn.prepareStatement("select SUM(quantity) as quantity from orders where prodid=?");
            ps.setString(1, prodId);
            rs = ps.executeQuery();
            if (rs.next()) {
                quantity = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Error in getSoldQuantity " + ex);
            ex.printStackTrace();
        }
        DBUtil.closeStatement(ps);
        DBUtil.closeResultSet(rs);
        return quantity;
    }
}
