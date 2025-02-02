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
public class ShippedItemServlet extends HttpServlet {

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

        OrderDAOImpl orderDao = new OrderDAOImpl();
        orders = orderDao.getAllOrders();  // Assuming this method gets all orders
        List<OrderDetailsPojo> orderDetailsList = new ArrayList<>();

        String orderid = request.getParameter("orderid");
        String prodid = request.getParameter("prodid");
        String userid = request.getParameter("userid");
//        double amount = Double.parseDouble(request.getParameter("amount"));

//        if (orderid == null || prodid == null || userid == null) {
//            request.setAttribute("orders", orders);
//            request.setAttribute("userId", userId);
//            request.setAttribute("userAddress", userAddress);
//            request.setAttribute("shipmentStatus","No products shipped till now");
//            RequestDispatcher rd = request.getRequestDispatcher("shippedItems.jsp");
//            rd.forward(request, response);
//            return;
//        }
        TransactionDAOImpl tranDao = new TransactionDAOImpl();
        UserDAOImpl userDao = new UserDAOImpl();

//        String userEmail = tranDao.getUserId(orderid);  // Fetching user email by transactionId
//        String userAddr = userDao.getUserAddr(userEmail);

        for (OrderPojo order : orders) {
            String transId = order.getOrderId();
            String userEmail = tranDao.getUserId(transId);  // Fetch user email by transaction ID
            String userAddr = userDao.getUserAddr(userEmail);

            userId.put(transId, userEmail);
            userAddress.put(userEmail, userAddr);
        }
//
//        userId.put(orderid, userEmail);
//        userAddress.put(userEmail, userAddr);

        String status = orderDao.shipNow(orderid, prodid);
        System.out.println("ship now executed" + status);

        if (status.equals("Order has been shipped successfully")) {
            System.out.println("Shipment notification sent to: " + userid);
        }

        request.setAttribute("shipmentStatus", status);
        request.setAttribute("orders", orders);
        request.setAttribute("userId", userId);
        request.setAttribute("userAddress", userAddress);

        RequestDispatcher rd = request.getRequestDispatcher("shippedItems.jsp");
        rd.forward(request, response);
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
