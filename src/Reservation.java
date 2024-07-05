import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Reservation {
    
  private  Connection connection;
      sec  secu = new sec();
      //create table
    public void createReservationTable() {
        try {
           connection=secu.connect1();
                
            PreparedStatement statement = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS reservation " +
                    "(idr INT AUTO_INCREMENT NOT NULL, " +
                    "date_entree DATE, " +
                    "date_sortie DATE, " +
                    "chambrenum INT NOT NULL, " +
                    "clientcin VARCHAR(255) NOT NULL, " +
                    "PRIMARY KEY (idr), " +
                    "FOREIGN KEY (chambrenum) REFERENCES chambre(numch), " +
                    "FOREIGN KEY (clientcin) REFERENCES client(cinC))"
            );
            statement.executeUpdate();
            System.out.println("Database table 'reservation' created successfully.");
            connection.close();
        } catch (SQLException e) {
            System.out.println("Error creating reservation table: " + e.getMessage());
        }
    }
    // ajout reservation 
    
    public void ajoutreservation(String Dd, String Df, int idrch, String cin) {
       
        PreparedStatement preparedStatement = null;
        try {
                connection=secu.connect1();
                connection.setAutoCommit(false);
        
            preparedStatement = connection.prepareStatement(
                    "INSERT INTO reservation(date_entree, date_sortie, chambrenum, clientcin) VALUES (?, ?, ?, ?)"
            );

            // Set parameter values
            preparedStatement.setString(1, Dd);
            preparedStatement.setString(2, Df);
            preparedStatement.setInt(3, idrch);
            preparedStatement.setString(4, cin);

            // Execute the query
            preparedStatement.executeUpdate();
        
            System.out.println("Reservation successful");
            
            // Commit the transaction if all operations succeed
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Error during reservation: " + e.getMessage());
            // Roll back the transaction if an error occurs
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Error rolling back transaction: " + ex.getMessage());
            }
        } finally {
            // Close resources
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
//// delete reservation
    public void deleteReservation(String cin) {
       
        PreparedStatement preparedStatement = null;
        try {
            // Establish connection
           connection=secu.connect1();
                connection.setAutoCommit(false);


            // Prepare SQL statement for deletion
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM reservation WHERE clientcin = ?"
            );
            preparedStatement.setString(1, cin);

            // Execute the deletion
            int rowsAffected = preparedStatement.executeUpdate();

            // Commit the transaction if all operations succeed
            connection.commit();

            // Close resources
            preparedStatement.close();
            connection.close();

            // Check if any rows were deleted
            if (rowsAffected > 0) {
                System.out.println("Reservation canceled successfully.");
            } else {
                System.out.println("No reservations found for the provided client cin.");
            }
        } catch (SQLException e) {
            // Roll back the transaction if any error occurs
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Error rolling back transaction: " + ex.getMessage());
            }
            System.out.println("Error deleting reservation: " + e.getMessage());
        } finally {
            // Close resources in a finally block to ensure cleanup
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
     public void showreservation() {
      
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // Establish connection
           connection=secu.connect1();
                connection.setAutoCommit(false);

            // Prepare SQL statement
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM reservation"
            );

            // Execute the SQL statement
            resultSet = preparedStatement.executeQuery();

            // Print table header
            System.out.println("+--------+----------------+----------------+-----------+----------+");
            System.out.println("|   ID   |  Date d'entrée |  Date de sortie|Chambrenum |ClientNUM |");
            System.out.println("+--------+----------------+----------------+-----------+----------+");

            // Iterate through the result set and print the contents
            while (resultSet.next()) {
                int id = resultSet.getInt("idr");
                String date_entree = resultSet.getString("date_entree");
                String date_sortie = resultSet.getString("date_sortie");
                int chambreId = resultSet.getInt("chambrenum");
                String clientId = resultSet.getString("clientcin");

                // Print row data
                System.out.printf("| %6d | %-14s | %-14s | %9d | %8s |\n", id, date_entree, date_sortie, chambreId, clientId);
            }

            // Print bottom border
            System.out.println("+--------+----------------+----------------+-----------+----------+");

            // Commit the transaction if all operations succeed
            connection.commit();
        } catch (SQLException e) {
            // Roll back the transaction if any error occurs
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Error rolling back transaction: " + ex.getMessage());
            }
            System.out.println("Error: " + e.getMessage());
        } finally {
            // Close resources in a finally block to ensure cleanup
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
     //chambre reserved
    public void  Showchreserved(String clientCIN) {
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;
    try {
        // Establish connection
        connection = secu.connect1();
        connection.setAutoCommit(false);

        // Prepare SQL statement
        preparedStatement = connection.prepareStatement(
                "SELECT * FROM reservation WHERE clientcin = ?"
        );

        // Set the client CIN parameter
        preparedStatement.setString(1, clientCIN);

        // Execute the SQL statement
        resultSet = preparedStatement.executeQuery();

        // Print table header
        System.out.println("+--------+----------------+----------------+-----------+----------+");
        System.out.println("|   ID   |  Date d'entrée |  Date de sortie|Chambrenum |ClientNUM |");
        System.out.println("+--------+----------------+----------------+-----------+----------+");

        // Iterate through the result set and print the contents
        while (resultSet.next()) {
            int id = resultSet.getInt("idr");
            String date_entree = resultSet.getString("date_entree");
            String date_sortie = resultSet.getString("date_sortie");
            int chambreId = resultSet.getInt("chambrenum");
            String clientId = resultSet.getString("clientcin");

            // Print row data
            System.out.printf("| %6d | %-14s | %-14s | %9d | %8s |\n", id, date_entree, date_sortie, chambreId, clientId);
        }

        // Print bottom border
        System.out.println("+--------+----------------+----------------+-----------+----------+");

        // Commit the transaction if all operations succeed
        connection.commit();
    } catch (SQLException e) {
        // Roll back the transaction if any error occurs
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException ex) {
            System.out.println("Error rolling back transaction: " + ex.getMessage());
        }
        System.out.println("Error: " + e.getMessage());
    } finally {
        // Close resources in a finally block to ensure cleanup
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing resources: " + e.getMessage());
        }
    }
}

}
