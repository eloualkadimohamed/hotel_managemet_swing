import java.sql.*;
import java.util.Scanner;


public class Main {
    static  Connection c ;
    public static void main(String[] args) {
       MainJFrame mjf =new MainJFrame();
       //clientlistJFrame cjf =new clientlistJFrame();
        sec  secu = new sec();
        Scanner scanner = new Scanner(System.in);
        Reservation reservation = new Reservation();
        chambre ch = new chambre();
        client client = new client();
        Admin admin = new Admin();
      
        Employee employe = new Employee();
        ExportEmpoyees export = new ExportEmpoyees();
       
       
     
        
        try {
           
            c= secu.connect();
            Statement statement = c.createStatement();
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS hotel_management");
            
            ch.createChambreTable();
            client.createClientTable();
            employe.createEmployeeTable();
            reservation.createReservationTable();
            admin.createAdminTable();
            export.exportEmployees();
            mjf.setVisible(true);
           
           
                            
                          
                          
                       
             
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            scanner.close();
        }
    }  
    }
