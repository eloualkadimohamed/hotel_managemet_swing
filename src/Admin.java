import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Admin {
    private sec secu = new sec();
    private  Connection connection;
     public void createAdminTable() {
        try {
            // Disable auto-commit mode to start a transaction
             connection=secu.connect1();
                
            connection.setAutoCommit(false);

            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS admin (" +
                         "numA INT NOT NULL AUTO_INCREMENT, " +
                         "username VARCHAR(255), " +
                         "password VARCHAR(255), " +
                         "PRIMARY KEY (numA))";

            statement.executeUpdate(sql);
            System.out.println("Admin table created successfully.");

            // Insert admin data
            String AjoutAdmin = "INSERT IGNORE INTO admin (numA, username, password) " +
                                "VALUES (1, 'ali', 'ali123'), " +
                                "(2, 'ahmed', 'ahmed123')";

            statement.executeUpdate(AjoutAdmin);
            System.out.println("Admin data inserted successfully.");

            // Commit the transaction if all operations succeed
            connection.commit();
        } catch (SQLException e) {
            // Roll back the transaction if an error occurs
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Error rolling back transaction: " + ex.getMessage());
            }
            System.out.println("Error creating admin table: " + e.getMessage());
        }
    }

    public boolean adminLogin(String username, String password) {
        boolean isAuthenticated = false;
        try {
            Connection connection = secu.connect1(); // Obtain connection
            String query = "SELECT * FROM admin WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                isAuthenticated = true;
            }
            resultSet.close();
            preparedStatement.close();
            connection.close(); // Close connection after use
        } catch (SQLException e) {
            // Handle SQL exception
            e.printStackTrace();
        }
        return isAuthenticated;
    }
}
