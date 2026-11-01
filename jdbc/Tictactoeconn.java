package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class Tictactoeconn {

    public static Connection getConnection() throws Exception {

        Class.forName("com.mysql.cj.jdbc.Driver");

        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/jdbc_db",
                "root",
                "S@12"
        );

        return conn;
    }
}