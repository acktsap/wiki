package jdbc.basic;

import org.h2.jdbcx.JdbcDataSource;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Hashtable;

public class ConnectionUsingDataSource {
    private static final String URL = "jdbc:h2:mem:testdb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static final String DATABASE_DESCRIPTION = "Test database";

    public static void main(String[] args) throws Exception {
        // creating datasource instance
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL(URL);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);
        dataSource.setDescription(DATABASE_DESCRIPTION);
        System.out.println("datasource: " + dataSource);

        // register datasource using JNDI api
        Hashtable<String, String> table = new Hashtable<>();
        table.put("java.naming.factory.initial", "org.osjava.sj.SimpleContextFactory");
        Context context = new InitialContext(table);
        context.bind("jdbc/testdb", dataSource);
        System.out.println("binded datasource using JNDI api");

        // get registered datasource using JNDI api
        DataSource registered = (DataSource) context.lookup("jdbc/testdb");
        System.out.println("registered: " + registered);

        // get connection using datasource
        Connection connection = registered.getConnection();
        System.out.println("connection: " + connection);

        // close connection
        connection.close();
    }
}
