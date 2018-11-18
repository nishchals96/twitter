/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 *
 * @author Nishchhal Sharma
 */
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        int success = this.login(username, password); 
        String message;
        if (success == 1) {
           message = "Success";
        }
        else {
           message = "failure";
        }
        
        response.getWriter().write("{ \"message\": \"" + message + " \"}");
        response.getWriter().flush();
        response.getWriter().close();
//        else {
//            this.signup(username, password);
//        }
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

    int login(String username, String password) {  
        int success = 0;

        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","abcd");
            String query = "select username,password from user_twitter_main where username = '"+username+"'";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String receivedPassword = rs.getString("password");
                
                System.out.println("DB passowrd:" + receivedPassword + " requestPassword:" + password);
                
                if(password.equals(receivedPassword)) {
                    System.out.println("login successful");

                    success = 1;
    //                String query1 = "select username,tweet,tweet_id from twitterfeed,user_twitter_main where twitterfeed.username = user_twitter_main.username and user_twitter_main.username = '"+username1+"'"; 
    //                String query2 = "update twitter_session set status = 'active' where username = '"+username1+"'";     
                }
                else System.out.println("login failed");        
                }

        }
        catch(Exception e){
            System.out.println("Exception at login:" + e);
        }
        
        return success;
    }
}


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
tables for datatbase are:-
1. user_twitter_main for login credentials
2. twitterfeed for saving tweets with respective tweet id's
3. twitter_session for session handling and this runs after logging in.
*/
/*
login() for logging into twitter and tweeting. session handling from same function
signup() for sigining up

*/

