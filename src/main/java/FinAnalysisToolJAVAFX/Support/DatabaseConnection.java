package FinAnalysisToolJAVAFX.Support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseConnection {

    public Connection connection;
    public Statement statement;

    public DatabaseConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/fintable", "root", "Oscar1010!");
            statement = connection.createStatement();
            System.out.println("Connection successful");
        } catch (Exception e) {
            e.printStackTrace();
        };
    }
}
