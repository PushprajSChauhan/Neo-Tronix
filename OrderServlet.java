/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.neotronix.servlet;

import in.neotronix.dao.impl.OrderDAOImpl;
import in.neotronix.pojo.OrderDetailsPojo;
import java.io.IOException;
import java.util.List;
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
public class OrderServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session=request.getSession();
        String username=(String)session.getAttribute("username");
        String password=(String)session.getAttribute("password");
        String usertype=(String)session.getAttribute("usertype");
            
        if(username==null || password==null){
            response.sendRedirect("login.jsp?message=Session expired! Please login again");
        }
        
        String amountParam=request.getParameter("amount");
        OrderDAOImpl orderDao=new OrderDAOImpl();
        if(amountParam!=null && amountParam.isEmpty()==false){
            double amount=Double.parseDouble(amountParam);
            String status=orderDao.paymentSuccess(username, amount);
            if(amount==0.0){
                status="No orders placed yet";
            }
            session.setAttribute("paymentStatus", status);
        }

        List<OrderDetailsPojo> orders=orderDao.getAllOrderDetails(username);
        session.setAttribute("orders", orders);
        RequestDispatcher rd=request.getRequestDispatcher("orderDetails.jsp");
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
