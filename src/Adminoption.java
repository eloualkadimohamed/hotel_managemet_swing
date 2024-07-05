
import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Mustapha
 */
public class Adminoption extends  JFrame{
     JTabbedPane tab;
    ShowEmployeeList  a11;
    AddEmployee a22 ;
   DeleteEmployee a33;
   SearchEmployee a44 ;
   ReservationList a55;
   ChambreList a66 ;
     ClientList  a77;
     public  Adminoption(){
        
         show_Coption();
    }
    public void show_Coption(){
         a11 = new ShowEmployeeList();
          a22 = new AddEmployee();
           a33 = new   DeleteEmployee();
            a44 = new    SearchEmployee();
             a55 = new    ReservationList();
              a66 = new   ChambreList();
             a77 = new    ClientList();
         this.setTitle("Hotel");
        this.setVisible(true);
        this.setSize(500,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
         tab =new JTabbedPane(); 
        
          tab.addTab("   Reservation List  ", a55);
           tab.addTab("  chambre  list  ", a66);
          tab.addTab("  client list  ", a77);
           tab.addTab("Employee list", a11);
         tab.addTab("Add employee", a22);
         tab.addTab("delete Employe", a33);
         tab.addTab("search employe", a44);
        add(tab);
    }
}
