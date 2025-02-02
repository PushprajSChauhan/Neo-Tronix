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
import javax.servlet.http.HttpSession;

/**
 *
 * @author ASUS
 */
public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            String username=request.getParameter("username");
            String password=request.getParameter("password");
            String usertype=request.getParameter("usertype");
            String status="Login denied! Invalid userId or password";
            
            if(usertype.equalsIgnoreCase("admin")){
                if(username.equals("admin@gmail.com") && password.equals("admin")){
                    RequestDispatcher rd=request.getRequestDispatcher("./AdminViewServlet");
                    HttpSession session=request.getSession();
                    session.setAttribute("username", username);
                    session.setAttribute("password", password);
                    session.setAttribute("usertype", usertype);
                    
                    rd.forward(request, response);
                }
                else{
                    RequestDispatcher rd=request.getRequestDispatcher("login.jsp?message="+status);
                    rd.include(request, response);
                }
            }
            else if(usertype.equalsIgnoreCase("customer")){
                UserDAOImpl userDao=new UserDAOImpl();
                status=userDao.isValidCredentials(username, password);
                if(status.equalsIgnoreCase("Login Successfull")){
                    UserPojo userPojo=userDao.getUserDetails(username);
                    HttpSession session=request.getSession();
                    session.setAttribute("userdata", userPojo);
                    session.setAttribute("username", username);
                    session.setAttribute("password", password);
                    session.setAttribute("usertype", usertype);
                    
                    RequestDispatcher rd=request.getRequestDispatcher("./UserHomeServlet");
                    rd.forward(request, response);
                }
                else{
                    RequestDispatcher rd=request.getRequestDispatcher("login.jsp?message="+status);
                    rd.include(request, response);
                }
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
