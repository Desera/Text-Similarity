/**
 * Created by 10417 on 2017/5/31.
 */
import java.sql.*;

public class JdbcUtils {
    //获取数据库连接驱动
    private static String driver = "com.mysql.jdbc.Driver";
    //获取数据库连接用户名
    private static String username = "root";
    //获取数据库连接密码
    private static String password = "blu927hsulxt";
    //获取数据库连接URL地址
    private static String url = "jdbc:mysql://localhost:3306/jdbc?useUnicode=true&characterEncoding=utf-8&useSSL=false";

    static {
        try {
            //加载数据库驱动
            Class.forName(driver);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * @return Connection数据库连接对象
     * @Description: 获取数据库连接对象
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * @Description: 释放资源，
     * 要释放的资源包括Connection数据库连接对象，负责执行SQL命令的Statement对象，存储查询结果的ResultSet对象
     */
    public static void release(Connection conn, Statement st, ResultSet rs) {
        if (rs != null) {
            try {
                //关闭存储查询结果的ResultSet对象
                rs.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (st != null) {
            try {
                //关闭负责执行SQL命令的Statement对象
                st.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                //关闭Connection数据库连接对象
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
