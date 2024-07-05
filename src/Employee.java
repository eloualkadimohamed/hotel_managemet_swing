import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Employee {
     private  Connection connection;
      sec  secu = new sec();
    public void createEmployeeTable() {
       
        Statement statement = null;
        try {
            // Establish connection
           connection=secu.connect1();
                connection.setAutoCommit(false);
             statement = connection.createStatement();
            // Create Employee table if not exists
            String createTableSql = "CREATE TABLE IF NOT EXISTS Employee (" +
                        "cinE VARCHAR(255) NOT NULL, " +
                        "nomE VARCHAR(255), " +
                        "emailE VARCHAR(255), " + // Comma moved here
                        "PRIMARY KEY (cinE))"; // Parenthesis moved here

            statement.executeUpdate(createTableSql);
            System.out.println("Employee table created successfully.");

            // Insert Employee data
            String insertDataSql = "INSERT  IGNORE INTO Employee (cinE, nomE, emailE) " +
                                   "VALUES " +
                                   "('RX3451', 'ali', 'ali@gmail.com'), " +
                                   "('RX3657', 'ahmed', 'ahmed@gmail.com'), " +
                                   "('RB44357', 'hassan', 'hassan@gmail.com'), " +
                                   "('AX3657', 'mustapha', 'mustapha@gmail.com')";
            statement.executeUpdate(insertDataSql);
            System.out.println("Employee data inserted successfully.");

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
    //ajout emploiyee
     public void insertEmployee(String cinE, String nom, String email) {
      
        PreparedStatement preparedStatement = null;
        try {
            // Establish connection
            connection=secu.connect1();
                connection.setAutoCommit(false);
            // Prepare insert statement
            String insertQuery = "INSERT  IGNORE INTO Employee (cinE, nomE, emailE) VALUES (?, ?, ?)";
            preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, cinE);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, email);

            // Execute the insert statement
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if insertion was successful
            if (rowsAffected > 0) {
                System.out.println("Employee inserted successfully.");
            } else {
                System.out.println("Failed to insert employee.");
            }

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
     /// list employee
      public void showEmployees() {
       
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // Establish connection
          connection=secu.connect1();
                connection.setAutoCommit(false);

            // Prepare SQL statement
            preparedStatement = connection.prepareStatement(
                "SELECT * FROM Employee"
            );

            // Execute the SQL statement
            resultSet = preparedStatement.executeQuery();

            // Print table header
            System.out.println("+------+--------------+----------------------+");
            System.out.println("| cin  | Nom          | Email                |");
            System.out.println("+------+--------------+----------------------+");

            // Iterate through the result set and print the contents
            while (resultSet.next()) {
                String cin = resultSet.getString("cinE");
                String nom = resultSet.getString("nomE");
                String email = resultSet.getString("emailE");

                // Print row data
                System.out.printf("| %-4s | %-12s | %-20s |\n", cin, nom, email);
            }

            // Print bottom border
            System.out.println("+------+--------------+----------------------+");

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
      
      // search employee
       public void searchEmployee(String nomE) {
       
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            // Establish connection
         connection=secu.connect1();
                connection.setAutoCommit(false);
            // Prepare search statement
            String searchQuery = "SELECT * FROM Employee WHERE nomE = ?";
            preparedStatement = connection.prepareStatement(searchQuery);
            preparedStatement.setString(1, nomE);

            // Execute the search statement
            resultSet = preparedStatement.executeQuery();

            // Check if employee with nomE exists
            if (resultSet.next()) {
                // Employee found, print details
                String nom = resultSet.getString("nomE");
                String email = resultSet.getString("emailE");
                String cin = resultSet.getString("cinE");
                System.out.println("Employee found with nomE " + nomE + ":");
                System.out.println("Nom: " + nom);
                System.out.println("Email: " + email);
                System.out.println("CIN: " + cin);
            } else {
                System.out.println("No employee found with nomE " + nomE + ".");
            }

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
       // Delete employe
        public void deleteEmployee(String cinE) {
       
        PreparedStatement preparedStatement = null;
        try {
            // Establish connection
          connection=secu.connect1();
                connection.setAutoCommit(false);   

            // Prepare delete statement
            String deleteQuery = "DELETE FROM Employee WHERE cinE = ?";
            preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setString(1, cinE);

            // Execute the delete statement
            int rowsAffected = preparedStatement.executeUpdate();

            // Check if deletion was successful
            if (rowsAffected > 0) {
                System.out.println("Employee with cin " + cinE + " deleted successfully.");
            } else {
                System.out.println("No employee found with cin " + cinE + ".");
            }

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
            System.out.println("Error: " + e.getMessage());
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
}
