import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class chambre {
    private  Connection connection;
      sec  secu = new sec();
    public void createChambreTable() {
       
        Statement statement = null;
        try {
            // Establish connection
           connection=secu.connect1();
                connection.setAutoCommit(false);
            statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS chambre " +
                         "(numch INTEGER NOT NULL, " +
                         "prix INTEGER NOT NULL, " +
                         "type VARCHAR(255), " +
                         "availability VARCHAR(255), " +
                         "PRIMARY KEY (numch))";
            statement.executeUpdate(sql);
            System.out.println("Chambre table created.");

            // Insert chambre data
            String Ajoutchambre = "INSERT IGNORE INTO chambre (numch, prix, type, availability) " +
                                  "VALUES (1, 200, 'petite', 'available'), " +
                                  "(2, 200, 'petite', 'available'), " +
                                  "(3, 300, 'grand', 'available'), " +
                                  "(4, 300, 'grand', 'available'), " +
                                  "(5, 300, 'grand', 'available'), " +
                                  "(6, 200, 'petite', 'available'), " +
                                  "(7, 300, 'grand', 'available'), " +
                                  "(8, 300, 'grand', 'available'), " +
                                  "(9, 300, 'grand', 'available'), " +
                                  "(10, 200, 'petite', 'available'), " +
                                  "(11, 300, 'grand', 'available'), " +
                                  "(12, 300, 'grand', 'available'), " +
                                  "(13, 300, 'grand', 'available'), " +
                                  "(14, 200, 'petite', 'available'), " +
                                  "(15, 300, 'grand', 'available'), " +
                                  "(16, 300, 'grand', 'available'), " +
                                  "(17, 200, 'petite', 'available'), " +
                                  "(18, 300, 'grand', 'available'), " +
                                  "(19, 300, 'grand', 'available'), " +
                                  "(20, 300, 'grand', 'available')";
            statement.executeUpdate(Ajoutchambre);
            System.out.println("Chambre data inserted.");

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
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
    //chambre mod disponibilite
     public void modifierDisponibilite(int numchambre) {
        try {
            // Start a transaction
             connection=secu.connect1();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE chambre SET availability = ? WHERE numch = ?"
            );
            preparedStatement.setString(1, "reserved");
            preparedStatement.setInt(2, numchambre);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Disponibilité de la chambre " + numchambre + " modifiée à 'reserved'.");
                // Commit the transaction
                connection.commit();
            } else {
                System.out.println("La chambre avec le numéro " + numchambre + " n'existe pas.");
            }
        } catch (SQLException e) {
            try {
                // Rollback the transaction in case of failure
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Error rolling back transaction: " + ex.getMessage());
            }
            e.printStackTrace();
        } finally {
            try {
                // Restore auto-commit mode
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                System.out.println("Error restoring auto-commit: " + ex.getMessage());
            }
        }
    }
     /// modifier to availabilite
      public void modifierDisponibilite1(int numchambre) {
        try {
            // Start a transaction
             connection=secu.connect1();
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE chambre SET availability = ? WHERE numch = ?"
            );
            preparedStatement.setString(1, "available");
            preparedStatement.setInt(2, numchambre);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Disponibilité de la chambre " + numchambre + " modifiée à available.");
                // Commit the transaction
                connection.commit();
            } else {
                System.out.println("La chambre avec le numéro " + numchambre + " n'existe pas.");
            }
        } catch (SQLException e) {
            try {
                // Rollback the transaction in case of failure
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("Error rolling back transaction: " + ex.getMessage());
            }
            e.printStackTrace();
        } finally {
            try {
                // Restore auto-commit mode
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                System.out.println("Error restoring auto-commit: " + ex.getMessage());
            }
        }
    }
//////  list chambre
      public void displayChambres() {
        try {
             connection=secu.connect1();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM chambre"
            );

            ResultSet resultSet = preparedStatement.executeQuery();

            // Print table header
            System.out.println("+------+--------+-------+-------------+");
            System.out.println("| Num  |  Prix  | Type  | Availability|");
            System.out.println("+------+--------+-------+-------------+");

            // Iterate through the result set and print the contents
            while (resultSet.next()) {
                int numch = resultSet.getInt("numch");
                int prix = resultSet.getInt("prix");
                String type = resultSet.getString("type");
                String availability = resultSet.getString("availability");

                // Print row data
                System.out.printf("| %-4d | %-6d | %-5s | %-11s |\n", numch, prix, type, availability);
            }

            // Print bottom border
            System.out.println("+------+--------+-------+-------------+");

            // Close resources
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
      /// Availability
          public int checkAvailability(int numchambre) {
        int availabilityCode = -1; // Default: chambre doesn't exist
        try {
             connection=secu.connect1();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT availability FROM chambre WHERE numch = ?"
            );
            preparedStatement.setInt(1, numchambre);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String availability = resultSet.getString("availability");
                if (availability.equalsIgnoreCase("available")) {
                    availabilityCode = 1; // chambre is available
                } else if (availability.equalsIgnoreCase("reserved")) {
                    availabilityCode = 0; // chambre is reserved
                }
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availabilityCode;
    }
          //chambre available
           public void displayChambreavailable() {
       
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // Establish connection
                connection=secu.connect1();
                connection.setAutoCommit(false);

            // Prepare SQL statement
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM chambre WHERE availability = 'available'"
            );

            // Execute the query
            resultSet = preparedStatement.executeQuery();

            // Print table header
            System.out.println("+------+--------+-------+-------------+");
            System.out.println("| Num  |  Prix  | Type  | Availability|");
            System.out.println("+------+--------+-------+-------------+");

            // Iterate through the result set and print the contents
            while (resultSet.next()) {
                int numch = resultSet.getInt("numch");
                int prix = resultSet.getInt("prix");
                String type = resultSet.getString("type");
                String availability = resultSet.getString("availability");

                // Print row data
                System.out.printf("| %-4d | %-6d | %-5s | %-11s |\n", numch, prix, type, availability );
            }

            // Print bottom border
            System.out.println("+------+--------+-------+-------------+");

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
            // Close resources
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



