/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 *
 * @author Nishchhal Sharma
 */
public class Session extends HttpServlet {

    public class Session_Instance {
        int session_id;
        String access_token;
        int user_id;
        int session_state;
        int success;
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, String action)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        
        try {
            String access_token = request.getParameter("access_token"),
                    user_id_string = request.getParameter("user_id");
            int user_id = 0;
            if (user_id_string != null) { user_id = Integer.parseInt(request.getParameter("user_id")); }
            
            System.out.println(access_token + user_id);
            
            Session_Instance returnedSession = new Session_Instance();
            
            if (action.equals("get")) {
                if (access_token == null) returnedSession = this.getSession("", user_id);
                else if (user_id_string == null) returnedSession = this.getSession(access_token, -1);
            }
            else {
                if (user_id_string != null) returnedSession = this.createSession(user_id);
            }
            
            // Now we convert retunedSession object into json.
            String jsonString = new Gson().toJson(returnedSession);
            
            response.getWriter().write(jsonString);
            response.getWriter().flush();
            response.getWriter().close();
        }
        catch(Exception e) {
            System.out.println(e);
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
        processRequest(request, response, "get");
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
        processRequest(request, response, "post");
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

    /*
    Give access_token = "" to this function if you want to query
    using user_id. Other wise, give access_token != "".
    */
    Session_Instance getSession(String access_token, int user_id) {
        // if access_token is given, then query using access token
        // if user id is given, then query using user id. 
        // but dont use them at the same time.
        Session_Instance currentSession = new Session_Instance();
        int found = 0;
        
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","abcd");

            String statement;
            if (access_token.equals("")) {
                statement = "SELECT * from session_user where user_id=" + user_id + ";";
            }
            else {
                statement = "SELECT * from session_user where access_token=" + access_token + ";";
            }

            PreparedStatement query = con.prepareStatement(statement);
            ResultSet rs = query.executeQuery();
            
            while(rs.next()) {
                found = 1;
                
                currentSession.access_token = rs.getString("access_token");
                currentSession.session_id = rs.getInt("session_id");
                currentSession.session_state = rs.getInt("session_state");
                currentSession.user_id = rs.getInt("user_id");
            }
        }
        catch (Exception e) {
            System.out.println("Exception at getSession:" + e);
        }
    
        currentSession.success = found;  
        
        return currentSession;
    }
    
    Session_Instance createSession(int user_id) {
        String access_token = "abcd";//UUID.randomUUID().toString().replace("-", "");

        try {
            System.out.println(access_token);
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","abcd");
            
            String statement = "insert into session_user (user_id, access_token, session_state) values (?, ?, ?);";
            PreparedStatement query = con.prepareStatement(statement);
            query.setString(1, user_id);
            query.setString(2, access_token);
            query.setString(3, session_state);
            int rs = query.executeUpdate();
            
           
        }
        catch (Exception e) {
            System.out.println("Exception at createSession:" + e);
        }
        
        Session_Instance current_session = this.getSession(access_token, -1);
        
        return current_session;
    }
}
