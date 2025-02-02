/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.neotronix.servlet;

import in.neotronix.dao.impl.CartDAOImpl;
import in.neotronix.dao.impl.ProductDAOImpl;
import in.neotronix.pojo.CartPojo;
import in.neotronix.pojo.ProductPojo;
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
public class CartDetailsServlet extends HttpServlet {

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

//        separate code for increasing or decreasing the quantity of products in cart using + and -
        String addStr = request.getParameter("add");
        if (addStr != null) {
            int add = Integer.parseInt(addStr);
            String uid = request.getParameter("uid");
            String pid = request.getParameter("pid");
            int availQty = Integer.parseInt(request.getParameter("avail"));
            int cartQty = Integer.parseInt(request.getParameter("qty"));
            CartDAOImpl cartDao = new CartDAOImpl();
            if (add == 1) {
//                increment product quantity in cart
                cartQty += 1;
                CartPojo cart = new CartPojo();
                cart.setProdId(pid);
                cart.setQuantity(1);
                cart.setUseremail(uid);
                cartDao.addProductToCart(cart);
            } else if (add == 0) {
//                remove product from cart
                cartQty -= 1;
                CartPojo cart = new CartPojo();
                cart.setProdId(pid);
                cart.setQuantity(cartQty);
                cart.setUseremail(uid);
                cartDao.updateProductInCart(cart);
            }
        }

        CartDAOImpl cartDao = new CartDAOImpl();
        ProductDAOImpl prodDao = new ProductDAOImpl();
        List<CartPojo> cartItems = new ArrayList<>();
        cartItems = cartDao.getAllCartItems(username);

        Map<String, Object> map = new HashMap<>();
        for (CartPojo cart : cartItems) {
            String prodId = cart.getProdId();
            ProductPojo product = prodDao.getProductDetails(prodId);
            map.put(prodId, product);
        }

        RequestDispatcher rd = request.getRequestDispatcher("cartDetails.jsp");
        request.setAttribute("username", username);
        request.setAttribute("cartItems", cartItems);
        request.setAttribute("map", map);

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
