/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.neotronix.listener;

import in.neotronix.utility.DBUtil;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author ASUS
 */
public class DBConnectionListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext cont=sce.getServletContext();
        
        String dbUrl=cont.getInitParameter("url");
        String username=cont.getInitParameter("username");
        String password=cont.getInitParameter("password");
        
        DBUtil.openConnection(dbUrl, username, password);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DBUtil.closeConnection();
    }
    
}
