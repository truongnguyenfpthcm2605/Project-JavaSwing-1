
package fastfood.sql;

import java.sql.*;

/**
 *
 * @author truong
 */
public class JDBCHelper {

    public static Connection getConnection() throws SQLException {
        String connection = "jdbc:sqlserver://localhost:1433;databaseName=FASTFOOD;encrypt=true;trustServerCertificate=true";
        Connection com = DriverManager.getConnection(connection, "sa", "12345");
        return com;
    }

    // lay PrepareStatement
    public static PreparedStatement getstmt(String sql, Object... args) throws SQLException {
        Connection com = getConnection();
        PreparedStatement psmt;
        if (sql.trim().startsWith("{")) {
            psmt = com.prepareCall(sql);
        } else {
            psmt = com.prepareStatement(sql);
        }
        // gan gia tri vao PrepareStatement
        for (int i = 0; i < args.length; i++) {
            psmt.setObject(i + 1, args[i]);
        }
        return psmt;
    }
     // lay ResultSet Query
    public static ResultSet query(String sql, Object... args) throws SQLException {
        PreparedStatement psmt = getstmt(sql, args);
        return psmt.executeQuery();
    }

    // lay gia tri duy nhat 1 gia tri
    public static Object value(String sql, Object... args) {
        try {
            ResultSet rs = query(sql, args);
            if (rs.next()) {
                return rs.getObject(1);
            }
            rs.getStatement().getConnection().close();
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // update
    public static int update(String sql, Object... args) {
        try {
            PreparedStatement psmt = getstmt(sql, args);
            try {
                return psmt.executeUpdate();
            } finally {
                psmt.getConnection().close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    
   
}
