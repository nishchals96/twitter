/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package twitter;
import java.sql.Connection;
import java.sql.DriverManager;
import static java.sql.JDBCType.NULL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import static jdk.nashorn.internal.parser.TokenType.NOT;
/**
 *
 * @author Nishchhal Sharma
 */
public class Twitter {
    int login(){
        Scanner sc = new Scanner(System.in);
        System.out.println("enter the username");
        String username1 = sc.nextLine();
        System.out.println("enter the password");
        String password1 = sc.nextLine();
        try{
                 Class.forName("oracle.jdbc.driver.OracleDriver");
                 Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","abcd");
                 PreparedStatement stmt = con.prepareStatement("select * from user_twitter_log");
                 ResultSet rs = stmt.executeQuery();
                 if(username1.equals(rs.getString(1)) && password1.equals(rs.getString(2))){
                     System.out.println("login successful come aboard!!");
                 }
                 else
                     System.out.println("login failed, sign up");
                       
                 }
              
               
            catch(Exception e){}
        return(0);
        
    }
    
    int signup(){
        Scanner sc1 = new Scanner(System.in);
        System.out.println("enter the username");
        String username = sc1.nextLine();
        System.out.println("enter the password");
        String password = sc1.nextLine();
        try{
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","abcd");
            PreparedStatement stmt1 = con.prepareStatement("insert into user_twitter_log values (?,?)");
            stmt1.setString(1,username);
            stmt1.setString(2,password);
            int i = stmt1.executeUpdate();
            con.close();
            
            }
            catch(Exception e){}
        return(0);
    }
        
        /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Twitter obj = new Twitter();
        Scanner sc = new Scanner(System.in);
        System.out.println("hello what you want? login or signup?");
        int i = sc.nextInt();
        if(i == 1){
           obj.login();
        }
        if(i==2){
            obj.signup();
            
        }
        
          
            
   
        
    }
    
}
