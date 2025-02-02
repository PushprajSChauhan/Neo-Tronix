/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.neotronix.servlet;

import in.neotronix.dao.impl.ProductDAOImpl;
import in.neotronix.pojo.ProductPojo;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 *
 * @author ASUS
 */
//this annotation will help us to work with images and files sent or uploaded by JSP/HTML, if not used the servlet will not be able to access the image
@MultipartConfig(maxFileSize = 161777215)
public class AddProductServlet extends HttpServlet {

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
        RequestDispatcher rd = null;

        String status = "Product registration failed";
        String prodName = request.getParameter("name");
        String prodType = request.getParameter("type");
        String prodInfo = request.getParameter("info");
        double prodPrice = 0.0;
        int prodQuantity = 0;
        String price = request.getParameter("price");
        if (price != null) {
            try {
                prodPrice = Double.parseDouble(price);
            } catch (NumberFormatException ex) {
                status = "Invalid unit price!";
                request.setAttribute("message", status);
                rd = request.getRequestDispatcher("addProduct.jsp");
                rd.forward(request, response);
                return;
            }
        } else {
            status = "Price cannot be left blank!";
            request.setAttribute("message", status);
            rd = request.getRequestDispatcher("addProduct.jsp");
            rd.forward(request, response);
            return;
        }

        String quantity = request.getParameter("quantity");
        if (quantity != null) {
            try {
                prodQuantity = Integer.parseInt(quantity);
            } catch (NumberFormatException ex) {
                status = "Invalid quantity!";
                request.setAttribute("message", status);
                rd = request.getRequestDispatcher("addProduct.jsp");
                rd.forward(request, response);
                return;
            }
        } else {
            status = "Quantity cannot be left blank!";
            request.setAttribute("message", status);
            rd = request.getRequestDispatcher("addProduct.jsp");
            rd.forward(request, response);
            return;
        }

//            yaha aake DAO se baat krke product ko DB mei add krna hai
//            file fetch krenge request object se in the form of parts
        Part part = request.getPart("image");
        if (part == null || part.getSize() == 0) {
            System.out.println("No file uploaded or file is empty.");
            request.setAttribute("message", "File upload failed. Please try again.");
            rd = request.getRequestDispatcher("addProduct.jsp");
            rd.forward(request, response);
            return;
        }
        InputStream img = part.getInputStream();
        ProductDAOImpl prodDao = new ProductDAOImpl();
        ProductPojo product = new ProductPojo(null, prodName, prodType, prodInfo, prodPrice, prodQuantity, img);
        status = prodDao.addProduct(product);

        request.setAttribute("message", status);
        rd = request.getRequestDispatcher("addProduct.jsp");
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
