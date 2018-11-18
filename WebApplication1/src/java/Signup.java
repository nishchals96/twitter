/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import static java.net.Proxy.Type.HTTP;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sun.misc.IOUtils;


/**
 *
 * @author Nishchhal Sharma
 */
public class Signup extends HttpServlet {
    
    public class User {
        String name;
        String username;
        String password;
    };

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
        
        String jsonString = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        User currentUser = new Gson().fromJson(jsonString, User.class);
        System.out.println(currentUser.name);
        int success = this.signup(currentUser.name, currentUser.username, currentUser.password);
        String message;
        if (success == 1){
            message = "success";
            
            RequestDispatcher rd = request.getRequestDispatcher("/Session");
            
            rd.include(request, response);
        }
        else{
            message = "failure";
        }
       response.getWriter().write("{   \"message\" :  \"" + message + "\" }");
       response.getWriter().flush();
       response.getWriter().close();

        
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

    int signup(String name, String username, String password){
        int success = 0;
        try {     
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","abcd");
            PreparedStatement stmt = con.prepareStatement("select * from user_twitter_main where username='"+username+"'");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                success = 0;
                
                return success;   
            }
        
            PreparedStatement stmt1 = con.prepareStatement("insert into user_twitter_main(username, password, name) values (?,?,?)");
            stmt1.setString(1, username);
            stmt1.setString(2, password);
            stmt1.setString(3, name);
            int i = stmt1.executeUpdate();
            success = 1;
        } 
        catch(Exception e){
            System.out.println("Exception at signup:  "+ e);
        }
        
        return success;
        
        
    }

}
