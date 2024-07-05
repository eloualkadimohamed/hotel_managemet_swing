
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
public class Coption extends JFrame {
   
    JTabbedPane tab;
    ReserveCh  a1;
    Managereservation a2 ;
    AnnulerReservation a3;
  //  ClientSignin ClientSignin =new ClientSignin();
  //  public String cin = ClientSignin.getcin();
   // cin = ClientSignin.getcin();
    public Coption(){
        
         show_Coption();
        // System.out.println(cin);
    }
    public void show_Coption(){
         a1 = new ReserveCh();
          a2 = new  Managereservation();
           a3 = new  AnnulerReservation();
           
         this.setTitle("Hotel");
        this.setVisible(true);
        this.setSize(500,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
         tab =new JTabbedPane(); 
        tab.addTab("Reserve chambre", a1);
         tab.addTab("chambre reserved", a2);
          tab.addTab("annuler reservation", a3);
          
        add(tab);
    }
           
}
