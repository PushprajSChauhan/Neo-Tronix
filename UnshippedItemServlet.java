/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.neotronix.servlet;

import in.neotronix.dao.impl.OrderDAOImpl;
import in.neotronix.dao.impl.TransactionDAOImpl;
import in.neotronix.dao.impl.UserDAOImpl;
import in.neotronix.pojo.OrderDetailsPojo;
import in.neotronix.pojo.OrderPojo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ASUS
 */
public class UnshippedItemServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");
        String usertype = (String) session.getAttribute("usertype");

        if (usertype == null || !usertype.equalsIgnoreCase("admin")) {
            response.sendRedirect("login.jsp?message=Access Denied! Please login as Admin");
        } else if (username == null || password == null) {
            response.sendRedirect("login.jsp?message=Session expired! Please login again");
        }

        List<OrderPojo> orders = new ArrayList<>();
        Map<String, String> userId = new HashMap<>();  // key is transaction ID, value is user ID
        Map<String, String> userAddress = new HashMap<>();  // key is user ID, value is user address

        // Fetch orders with status 'shipped' = 0 (unshipped orders)
        OrderDAOImpl orderDAO = new OrderDAOImpl();
        orders = orderDAO.getAllOrders();  // Assuming this method gets all orders
        List<OrderDetailsPojo> orderDetailsList = new ArrayList<>();

        // Iterate over orders and filter out unshipped ones
        for (OrderPojo order : orders) {
            if (order.getShipped() == 0) {
                OrderDetailsPojo orderDetails = new OrderDetailsPojo();
                orderDetails.setOrderId(order.getOrderId());
                orderDetails.setProdId(order.getProdId());
                orderDetails.setQuantity(order.getQuantity());
                orderDetails.setAmount(order.getAmount());
                orderDetails.setShipped(order.getShipped());

                // Fetch additional details for each order (e.g., product name, image, etc.)
                orderDetailsList = orderDAO.getAllOrderDetails(order.getOrderId());
                
                // Fetch user email from the TransactionPojo (since transactionId and orderId are the same)
                String transactionId = order.getOrderId();  // Using orderId as transactionId
                TransactionDAOImpl transactionDAO = new TransactionDAOImpl();
                String userEmail = transactionDAO.getUserId(transactionId);  // Fetching user email by transactionId
                
                // Fetch user address using the userEmail
                UserDAOImpl userDAO = new UserDAOImpl();
                String userAddr = userDAO.getUserAddr(userEmail);  // Assuming you have a method to fetch address by email
                
                // Populate userId and userAddress maps
                userId.put(order.getOrderId(), userEmail);
                userAddress.put(userEmail, userAddr);
            }
        }

        // Set the request attributes to pass to the JSP
        request.setAttribute("orders", orders);
        request.setAttribute("userId", userId);
        request.setAttribute("userAddress", userAddress);
        
        RequestDispatcher rd=request.getRequestDispatcher("unshippedItems.jsp");
        rd.forward(request,response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
