import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ExportEmpoyees {
      private  Connection connection;
      sec  secu = new sec();

    public void exportEmployees() {
       
        Statement statement = null;
        ResultSet resultSet = null;
        BufferedWriter writer = null;

        try {
          connection=secu.connect1();
                connection.setAutoCommit(false);
             statement = connection.createStatement();

            // Executing SQL query
            String sqlQuery = "SELECT * FROM Employee";
            resultSet = statement.executeQuery(sqlQuery);

            // Writing data to file
            writer = new BufferedWriter(new FileWriter("Employees.txt"));
            while (resultSet.next()) {
                // Adjust the format according to your needs
                writer.write(resultSet.getString("cinE") + ", " + resultSet.getString("nomE")
                        + ", " + resultSet.getString("emailE") + "\n");
            }
            System.out.println("Data exported to file successfully.");

            connection.commit(); // Commit transaction

        } catch (SQLException | IOException e) {
            try {
                if (connection != null) {
                    connection.rollback(); // Rollback transaction if an exception occurs
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            // Closing resources
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
                if (writer != null) writer.close();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }
}
