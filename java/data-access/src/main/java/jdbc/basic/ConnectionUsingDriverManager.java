package jdbc.basic;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUsingDriverManager {
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String URL = "jdbc:h2:mem:testdb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static void main(String[] args) throws Exception {
        Class.forName(JDBC_DRIVER);

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Connected to database: " + connection);

        connection.close();
    }
}
