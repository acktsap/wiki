package jdbc.basic;

import org.h2.jdbcx.JdbcConnectionPool;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConnectionPoolingUsingDataSource {
    private static final String URL = "jdbc:h2:mem:testdb";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    public static void main(String[] args) throws Exception {
        // creating datasource instance
        JdbcConnectionPool dataSource = JdbcConnectionPool.create(URL, USER, PASSWORD);
        dataSource.setMaxConnections(1);
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
        Connection connection1 = registered.getConnection();
        long start = System.currentTimeMillis();
        System.out.println("connection1: " + connection1);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(() -> {
            try {
                Thread.sleep(2000L);
                connection1.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // takes 2 seconds since pool size is 1
        Connection connection2 = registered.getConnection();
        System.out.println("connection2: " + connection2);
        System.out.println("Elasped: " + Duration.of(System.currentTimeMillis() - start, ChronoUnit.MILLIS));
        connection2.close();

        executorService.shutdown();
    }
}
