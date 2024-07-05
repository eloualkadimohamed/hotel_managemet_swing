import java.sql.*;

public class client {

    private  Connection connection;
      sec  secu = new sec();
      
      //create client table
    public void createClientTable() {
      
        try {
            connection=secu.connect1();
                connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS client (" +
                    "cinC VARCHAR(255) NOT NULL, " +
                    "nom VARCHAR(255) NOT NULL, " +
                    "email VARCHAR(255), " +
                    "password VARCHAR(255) NOT NULL, " +
                    "chambreId INTEGER , " +
                    "PRIMARY KEY (cinC), " +
                    "FOREIGN KEY (chambreId) REFERENCES chambre(numch))";
            statement.executeUpdate(sql);
            connection.commit(); // Commit transaction
            System.out.println("Client table created successfully.");
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback transaction if there's an error
                } catch (SQLException ex) {
                    System.out.println("Error rolling back transaction: " + ex.getMessage());
                }
            }
            System.out.println("Error creating client table: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    System.out.println("Error closing connection: " + ex.getMessage());
                }
            }
        }
    }
    //ajout client 
    
    
    public void ajoutclient(String cinC, String nomc, String emailc, int numch, String password) {
      
        PreparedStatement preparedStatement = null;
        try {
            connection=secu.connect1();
                connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO client(cinC, nom, email, chambreId, password) VALUES (?, ?, ?, ?, ?)");
            preparedStatement.setString(1, cinC);
            preparedStatement.setString(2, nomc);
            preparedStatement.setString(3, emailc);
            preparedStatement.setInt(4, numch);
            preparedStatement.setString(5, password);
            preparedStatement.executeUpdate();

            System.out.println("Insertion successful");
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Error during insertion: " + e.getMessage());
         
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Error rolling back transaction: " + ex.getMessage());
            }
        } finally {
           
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
    /// ajout client whitout num chambre
    
    public void ajoutclient1(String cinC, String nomc, String emailc, String password) {
      
        PreparedStatement preparedStatement = null;
        try {
            connection=secu.connect1();
                connection.setAutoCommit(false);

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO client(cinC, nom, email, password) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, cinC);
            preparedStatement.setString(2, nomc);
            preparedStatement.setString(3, emailc);
           // preparedStatement.setInt(4, numch);
            preparedStatement.setString(4, password);
            preparedStatement.executeUpdate();

            System.out.println("Insertion successful");
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Error during insertion: " + e.getMessage());
         
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Error rolling back transaction: " + ex.getMessage());
            }
              } finally {
           
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
       ////client login
    public boolean clientLogin(String cinC, String password) {
        boolean isAuthenticated = false;
        Connection connection = null;
        try {
           connection=secu.connect1();
            connection.setAutoCommit(false); // Start transaction
            String query = "SELECT * FROM client WHERE cinC = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, cinC);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            isAuthenticated = resultSet.next();
            resultSet.close();
            preparedStatement.close();
            connection.commit(); // Commit transaction
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Rollback transaction if there's an error
                } catch (SQLException ex) {
                    System.out.println("Error rolling back transaction: " + ex.getMessage());
                }
            }
            System.out.println("Error during client login: " + e.getMessage());
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    System.out.println("Error closing connection: " + ex.getMessage());
                }
            }
        }
        return isAuthenticated;
    }
    /////client list
     public void displayClients() {
        
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        
        try {
            // Establish connection to the database
            connection=secu.connect1();
                connection.setAutoCommit(false);

            // Create and execute the SELECT query
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM client"
            );
            resultSet = preparedStatement.executeQuery();

            // Print table header
            System.out.println("+----------+---------------+---------------------+------------+");
            System.out.println("| CIN      |       Nom     |          Email      | Chambre ID |");
            System.out.println("+----------+---------------+---------------------+------------+");

            // Iterate through the result set and print the contents
            while (resultSet.next()) {
                String cinC = resultSet.getString("cinC");
                String nom = resultSet.getString("nom");
                String email = resultSet.getString("email");
                int chambreId = resultSet.getInt("chambreId");

                // Print row data
                System.out.printf("| %-8s | %-13s | %-19s | %10d |\n", cinC, nom, email, chambreId);
            }

            // Print bottom border
            System.out.println("+----+---------------------+---------------------+------------+");

            // Commit transaction
            connection.commit();
        } catch (SQLException e) {
            // Rollback transaction in case of any exception
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException ex) {
                System.out.println("Error rolling back transaction: " + ex.getMessage());
            }
            System.out.println("Error: " + e.getMessage());
        } finally {
            // Close resources and restore auto-commit mode
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.setAutoCommit(true);
                    connection.close();
                }
            } catch (SQLException e) {
                System.out.println("Error closing resources: " + e.getMessage());
            }
        }
    }
     ///supremier client 
      public void deleteClient(String cin) {
       
        PreparedStatement preparedStatement = null;
        try {
           connection=secu.connect1();
                connection.setAutoCommit(false);
        
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM client WHERE cinC = ?"
            );

            // Set parameter values
            preparedStatement.setString(1, cin);

            // Execute the query
            int rowsAffected1 = preparedStatement.executeUpdate();

            if (rowsAffected1 > 0) {
                System.out.println("client deleted");
            } else {
                System.out.println("No client found with the provided credentials.");
            }

            // Commit the transaction if all operations succeed
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Error during deletion: " + e.getMessage());
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
    }public void updateChambreId(String cinC, int chambreId) {
    Connection connection = null;
    try {
        // Start a transaction
        connection = secu.connect1();
        connection.setAutoCommit(false);

        PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE client SET chambreId = ? WHERE cinC = ?"
        );
        preparedStatement.setInt(1, chambreId);
        preparedStatement.setString(2, cinC);
        int rowsAffected = preparedStatement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("chambreId for client " + cinC + " updated to " + chambreId + ".");
            // Commit the transaction
            connection.commit();
        } else {
            System.out.println("Client with cinC " + cinC + " not found.");
        }
    } catch (SQLException e) {
        try {
            // Rollback the transaction in case of failure
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException ex) {
            System.out.println("Error rolling back transaction: " + ex.getMessage());
        }
        e.printStackTrace();
    } finally {
        try {
            // Restore auto-commit mode
            if (connection != null) {
                connection.setAutoCommit(true);
            }
        } catch (SQLException ex) {
            System.out.println("Error restoring auto-commit: " + ex.getMessage());
        }
    }
}


}
