/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.neotronix.servlet;

import in.neotronix.dao.impl.CartDAOImpl;
import in.neotronix.dao.impl.DemandDAOImpl;
import in.neotronix.dao.impl.ProductDAOImpl;
import in.neotronix.pojo.CartPojo;
import in.neotronix.pojo.DemandPojo;
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
public class UpdateCartServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String password = (String) session.getAttribute("password");
        String usertype = (String) session.getAttribute("usertype");

        if (usertype == null || password == null || username == null || !usertype.equalsIgnoreCase("customer")) {
            response.sendRedirect("login.jsp?message=Access Denied! Please login to access the application");
            return;
        }
        
        String prodId=request.getParameter("pid");
        int prodQuantity=Integer.parseInt(request.getParameter("pqty"));
        ProductDAOImpl prodDao=new ProductDAOImpl();
        CartDAOImpl cartDao=new CartDAOImpl();
        ProductPojo product=prodDao.getProductDetails(prodId);
        int availQuantity=product.getProdQuantity();
        if(availQuantity<prodQuantity){
            String status="Only "+ availQuantity +" number of "+ product.getProdName() +" are available in the stock. So we can add only "+ availQuantity +" amount of item in your cart.";
            DemandDAOImpl demandDao = new DemandDAOImpl();
            DemandPojo demand = new DemandPojo(username, prodId, (prodQuantity - availQuantity));
            boolean flag = demandDao.addProduct(demand);
            if (flag) {
                status += "<br/>We will notify you when " + product.getProdName() + " will be available in our stock";
            }
            RequestDispatcher rd = request.getRequestDispatcher("./CartDetailsServlet");
            request.setAttribute("message", status);
            rd.forward(request, response);
        }
        else{
            String status=cartDao.updateProductInCart(new CartPojo(username, prodId, prodQuantity));
            RequestDispatcher rd = request.getRequestDispatcher("./CartDetailsServlet");
            request.setAttribute("message", status);
            rd.forward(request, response);
        }
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
