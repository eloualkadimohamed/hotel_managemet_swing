
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

/**
 *
 * @author Mustapha
 */
public class ClientList extends javax.swing.JPanel {
 private  Connection connection;
   ResultSet resultSet ;
      sec  secu = new sec();
    /**
     * Creates new form ClientList
     */
    public ClientList() {
        initComponents();
        show_table();
    }
   public  void show_table(){
        try {
               
      connection=secu.connect1();
         
         
         PreparedStatement    preparedStatement = connection.prepareStatement("SELECT * FROM client");
            resultSet = preparedStatement.executeQuery();
            DefaultTableModel ct = (DefaultTableModel)ctable.getModel();
            Object[] row =new Object[4];
            while (resultSet.next()) {
                row[0]=resultSet.getString("cinC");
                row[1]  = resultSet.getString("nom");
               row[2]  = resultSet.getString("email");
              row[3] = resultSet.getInt("chambreId");
              ct.addRow(row);
            }
            
            
            } catch (SQLException ex) {
                 System.out.println(ex.getMessage());
            }
  }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        ctable = new javax.swing.JTable();

        ctable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "cin", "name", "email", "num ch"
            }
        ));
        jScrollPane2.setViewportView(ctable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable ctable;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
