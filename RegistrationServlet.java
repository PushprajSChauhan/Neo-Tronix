/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.neotronix.servlet;

import in.neotronix.dao.impl.UserDAOImpl;
import in.neotronix.pojo.UserPojo;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author ASUS
 */
public class RegistrationServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String useremail = request.getParameter("useremail");
        String address = request.getParameter("address");
        String mobile = request.getParameter("mobile");
        String pincodeStr = request.getParameter("pincode");
        String password = request.getParameter("password");
        String cpassword = request.getParameter("cpassword");

        boolean match = password.equals(cpassword);

        RequestDispatcher rd;

        if (!match) {
            request.setAttribute("password", match);
            rd = request.getRequestDispatcher("register.jsp");
            rd.forward(request, response);
            return;
        }

        UserDAOImpl userDao = new UserDAOImpl();

        UserPojo user = new UserPojo();
        user.setUsername(username);
        user.setUseremail(useremail);
        user.setAddress(address);
        user.setMobile(mobile);
        user.setPincode(Integer.parseInt(pincodeStr));
        user.setPassword(password);

        String status = userDao.registerUser(user);
        request.setAttribute("status", status);  

        rd = request.getRequestDispatcher("register.jsp");
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
