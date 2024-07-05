/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 import java.sql.Connection;
 import java.sql.DriverManager;
 import java.sql.SQLException;
/**
 *
 * @author Mustapha
 */
public class sec {
      
     private final  String user="root";
     private  final String password ="Contemporain2003$";
     private  final String url="jdbc:mysql://localhost:3306";
      private  final String url1="jdbc:mysql://localhost:3306/hotel_management";
    
     public Connection connect() throws SQLException
     {
         Connection con = DriverManager.getConnection(url,user,password);
         return con;
         
     }
     


    
     public Connection connect1() throws SQLException
     {
         Connection con = DriverManager.getConnection(url1,user,password);
         return con;
         
     }
     
}
