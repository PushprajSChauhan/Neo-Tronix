/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.neotronix.servlet;

import in.neotronix.dao.impl.ProductDAOImpl;
import in.neotronix.pojo.ProductPojo;
import java.io.IOException;
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
public class UpdateProductByIdServlet extends HttpServlet {

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

        String prodId = request.getParameter("prodid");
        if (prodId.isEmpty()) {
            RequestDispatcher rd = request.getRequestDispatcher("updateProduct.jsp");
            request.setAttribute("product", "Please input a valid product ID");
            rd.forward(request, response);
            return;
        }

        ProductDAOImpl prodDao = new ProductDAOImpl();
        ProductPojo product = prodDao.getProductDetails(prodId);
        if (product == null) {
            RequestDispatcher rd = request.getRequestDispatcher("updateProductById.jsp");
            request.setAttribute("message", "No product of given ID found in DB");
            rd.forward(request, response);
            return;
        }

        RequestDispatcher rd = request.getRequestDispatcher("updateProduct.jsp");
        request.setAttribute("product", product);
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
