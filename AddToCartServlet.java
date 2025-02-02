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
public class AddToCartServlet extends HttpServlet {

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

        String userId = username;
        String prodId = request.getParameter("pid");
        int prodQuantity = Integer.parseInt(request.getParameter("pqty"));
        String action = request.getParameter("action");
        
        CartDAOImpl cartDao = new CartDAOImpl();
        ProductDAOImpl prodDao = new ProductDAOImpl();
        ProductPojo product = prodDao.getProductDetails(prodId);
        
        int availQuantity = prodDao.getProductQuantity(prodId);
        int cartQuantity = cartDao.getCartItemCount(userId, prodId);
        prodQuantity += cartQuantity;
        
        if (action != null) {
//            logic for buy now
            cartDao.updateProductInCart(new CartPojo(userId, prodId, prodQuantity));
            RequestDispatcher rd = request.getRequestDispatcher("./CartDetailsServlet");
            rd.forward(request, response);
        } else if (prodQuantity == cartQuantity) {
//            logic for remove from cart
            String status = cartDao.removeProductFromCart(userId, prodId); //yaha pe data mei changes hue hain isliye session mei bhi updated data ko set krna pdega toh firse session object mei saara data put krenge
            List<ProductPojo> products=prodDao.getAllProducts();
            Map<String, Integer> map=new HashMap();
            for(ProductPojo prod:products){
                int quantity=cartDao.getCartItemCount(username, prod.getProdId());
                map.put(prod.getProdId(),quantity);
            }
            session.setAttribute("products",products);
            session.setAttribute("map", map);
            
            RequestDispatcher rd = request.getRequestDispatcher("userHome.jsp");
            request.setAttribute("message", status);
            rd.forward(request, response);
        } else if (availQuantity < prodQuantity) {
//            logic for demand
            String status = null;
            if (availQuantity == 0) {
                status = "Product out of stock!";
            } else {
                cartDao.updateProductInCart(new CartPojo(userId, prodId, availQuantity));
                status = "Only " + availQuantity + " number of " + product.getProdName() + " are available. So we are adding only " + availQuantity + " products in your cart";
            }
            DemandDAOImpl demandDao = new DemandDAOImpl();
            DemandPojo demand = new DemandPojo(userId, prodId, (prodQuantity - availQuantity));
            boolean flag = demandDao.addProduct(demand);
            if (flag) {
                status += "<br/>We will notify you when " + product.getProdName() + " will be available in our stock";
            }
            RequestDispatcher rd = request.getRequestDispatcher("./CartDetailsServlet");
            request.setAttribute("message", status);
            rd.forward(request, response);
        } 
        else {
//            add to cart
            String status=cartDao.updateProductInCart(new CartPojo(userId, prodId, prodQuantity));
            List<ProductPojo> products=prodDao.getAllProducts();
            Map<String, Integer> map=new HashMap();
            for(ProductPojo prod:products){
                int quantity=cartDao.getCartItemCount(username, prod.getProdId());
                map.put(prod.getProdId(),quantity);
            }
            session.setAttribute("products",products);
            session.setAttribute("map", map);
            RequestDispatcher rd = request.getRequestDispatcher("userHome.jsp");
//            pw.println("<script>document.getElementById('message').innerHTML='" + status + "'</script>"); //yaha JS se bhi ham message ko apne JSP page mei inject krwa skte hain
//            rd.include(request, response);
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
