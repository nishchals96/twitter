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
package twitter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
/**
 *
 * @author Nishchhal Sharma
 */
public class Twitter {
    int login(){
        
        try{
            Scanner sc = new Scanner(System.in);
        System.out.println("enter the username");
        String username1 = sc.nextLine();
        System.out.println("enter the password");
        String password1 = sc.nextLine();
                 Class.forName("oracle.jdbc.driver.OracleDriver");
                 Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","abcd");
                 String query = "select username,password from user_twitter_main where username = '"+username1+"'";
                 PreparedStatement pst = con.prepareStatement(query);
                 ResultSet rs = pst.executeQuery();
                 if(password1.equals(rs.getString("password"))) {
                     System.out.println("login successful come aboard!!");
                     String query1 = "select username,tweet,tweet_id from twitterfeed,user_twitter_main where twitterfeed.username = user_twitter_main.username and user_twitter_main.username = '"+username1+"'"; 
                     String query2 = "update twitter_session set status = 'active' where username = '"+username1+"'";
                     
    }
             else System.out.println("login failed, sign up");
                       
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
            try (Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","abcd")) {
                PreparedStatement stmt1 = con.prepareStatement("insert into user_twitter_main values (?,?)");
                stmt1.setString(1,username);
                stmt1.setString(2,password);
                int i = stmt1.executeUpdate();
            }
            
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
