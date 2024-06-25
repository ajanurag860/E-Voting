
package evoting.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection {
   private static Connection conn=null; 
   static{
       try{
       Class.forName("oracle.jdbc.OracleDriver");
       conn=DriverManager.getConnection("jdbc:oracle:thin:@//Anurag:1521/xe","advjava","mystudents");
           System.out.println("Connection opened");
       }
       catch(ClassNotFoundException cnf){
           cnf.printStackTrace();
       }
       catch(SQLException sqlex){
           sqlex.printStackTrace();
       }
   }
  public static Connection getConnection(){
      return conn;
  } 
 public static void closeConnection(){
     try{
      if(conn!=null){      
     conn.close();
      }
 } 
     catch(SQLException sqlex){
         sqlex.printStackTrace();
     }
 }     
}
