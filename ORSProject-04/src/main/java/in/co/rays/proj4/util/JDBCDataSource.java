package in.co.rays.proj4.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * JDBCDataSource manages database connections using the C3P0 connection pool.
 * 
 * It reads database configuration from system.properties, initializes a
 * centralized pool, and provides utility methods to obtain and close
 * connections safely.
 * 
 * This class follows the Singleton pattern.
 */
public final class JDBCDataSource {

    /** Singleton instance of JDBCDataSource */
    private static JDBCDataSource jds = null;

    /** C3P0 Connection Pool DataSource */
    private ComboPooledDataSource cpds = null;

    /** Reads database configuration from bundle */
    private static ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.proj4.bundle.system");

    /**
     * Private constructor initializes C3P0 pool using values from properties file.
     */
    private JDBCDataSource() {
        try {
            cpds = new ComboPooledDataSource();
            cpds.setDriverClass(rb.getString("driver"));

            String env = System.getProperty("env");

            if ("docker".equals(env)) {
                cpds.setJdbcUrl(rb.getString("url.docker"));
            } else {
                cpds.setJdbcUrl(rb.getString("url.local"));
            }

           
            
            cpds.setUser(rb.getString("username"));
            cpds.setPassword(rb.getString("password"));
            cpds.setInitialPoolSize(Integer.parseInt(rb.getString("initialpoolsize")));
            cpds.setAcquireIncrement(Integer.parseInt(rb.getString("acquireincrement")));
            cpds.setMaxPoolSize(Integer.parseInt(rb.getString("maxpoolsize")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the singleton instance of JDBCDataSource.
     *
     * @return JDBCDataSource instance
     */
    public static JDBCDataSource getInstance() {
        if (jds == null) {
            jds = new JDBCDataSource();
        }
        return jds;
    }

    /**
     * Provides a database connection from the C3P0 pool.
     *
     * @return Connection object, or null if connection fails
     */
    public static Connection getConnection() {
        try {
            return getInstance().cpds.getConnection();
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Closes the connection, statement, and resultset safely.
     *
     * @param conn the connection to close
     * @param stmt the statement to close
     * @param rs   the resultset to close
     */
    public static void closeConnection(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Overloaded method to close only connection and statement.
     */
    public static void closeConnection(Connection conn, Statement stmt) {
        closeConnection(conn, stmt, null);
    }

    /**
     * Overloaded method to close only connection.
     */
    public static void closeConnection(Connection conn) {
        closeConnection(conn, null);
    }
}
