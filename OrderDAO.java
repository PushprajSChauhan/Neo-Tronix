/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.neotronix.dao;

import in.neotronix.pojo.OrderDetailsPojo;
import in.neotronix.pojo.OrderPojo;
import in.neotronix.pojo.TransactionPojo;
import java.util.List;

/**
 *
 * @author ASUS
 */
public interface OrderDAO {
    public boolean addOrder(OrderPojo order);
//    yeh method kisi bhi user ke transaction/purchase kiye gaye individual items ka record add krega
    
    public boolean addTransaction(TransactionPojo transaction);
//    yeh method transaction table mei ek user ka poora bill add krega
    
    public List<OrderPojo> getAllOrders();
    
    public List<OrderDetailsPojo> getAllOrderDetails(String userEmail);
    
    public String shipNow(String orderId, String prodId);
    
    public String paymentSuccess(String username, double paidAmount);
    
    public int getSoldQuantity(String prodId);
}
