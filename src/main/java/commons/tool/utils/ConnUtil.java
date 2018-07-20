package commons.tool.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnUtil {
    public static Connection getMysqlConn(String ip, String port, String dbname, String username, String password) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // jdbc:mysql://localhost:3306/iispace?characterEncoding=utf-8
            String url = "jdbc:mysql://" + ip + ":" + port + "/" + dbname
                    + "?characterEncoding=utf-8&tinyInt1isBit=false&zeroDateTimeBehavior=convertToNull";
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static Connection getMysqlConn(String url, String username, String password) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    public static Connection getMysqlConn(String driver, String url, String username, String password) {
        Connection conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }
}
