

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 10417 on 2017/5/31.
 */
public class JdbcCRUDByPreparedStatement {
    private static Connection conn = null;
    private static PreparedStatement st = null;
    private static ResultSet rs = null;

//    public void insert(String strSql) {
//        try {
//            //获取一个数据库连接
//            conn = JdbcUtils.getConnection();
//            //要执行的SQL命令，SQL中的参数使用?作为占位符
//            //String strSql = "insert into users(name,sex) values(?,?)";
//            //通过conn对象获取负责执行SQL命令的prepareStatement对象
//            st = conn.prepareStatement(strSql);
//            //执行插入操作，executeUpdate方法返回成功的条数
//            strSql="insert into student VALUES ('strings[0]','strings[1]','strings[2]') ";
//            int num = st.executeUpdate(strSql);
//            if (num > 0) {
//                System.out.println("插入成功！！");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            //SQL执行完成之后释放相关资源
//            JdbcUtils.release(conn, st, rs);
//        }
//    }

    public void sInsert(String[] strings){
        try{
            conn=JdbcUtils.getConnection();
            //String sql="insert into student values( 'strings[0]','strings[1])','strings[2]')";
            String sqlstr="insert into student VALUES (?,?,?)";
            System.out.println(sqlstr);
            st = conn.prepareStatement(sqlstr);
            st.setString(1,strings[0]);
            st.setString(2,strings[1]);
            st.setString(3,strings[2]);
            int num = st.executeUpdate();
            if (num > 0) {
                System.out.println("插入成功！！");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            JdbcUtils.release(conn,st,rs);
        }
    }

    public void nsInsert(String[] nstrings){
        try{
            conn=JdbcUtils.getConnection();
            String nsql="insert into newstudent values(?,?,?,?)";

            st = conn.prepareStatement(nsql);
            st.setString(1,nstrings[0]);
            st.setString(2,nstrings[1]);
            st.setString(3,nstrings[2]);
            st.setString(4,nstrings[3]);
            int num = st.executeUpdate();
            if (num > 0) {
                System.out.println("插入成功！！");
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            JdbcUtils.release(conn,st,rs);
        }
    }

//    public ResultSet search(){
//        try{
//            String strsql="select * from student";
//            conn=JdbcUtils.getConnection();
//            st = conn.prepareStatement(strsql);
//            rs=st.executeQuery(strsql);
//            System.out.println("学号"+"\t"+"姓名"+"\t"+"文件");
//            while(rs.next()){
//                System.out.print(rs.getString(1)+"\t\t");
//                System.out.print(rs.getString(2)+"\t\t");
//                System.out.print(rs.getString(3)+"\n");
//            }
//        }catch(Exception e){
//            e.printStackTrace();
//        }finally {
//            JdbcUtils.release(conn, st, rs);
//        }
//        return rs;
//    }

    public List<Student> nsearch(){
        List<Student> list = new ArrayList<>();
        try{
            String nstrsql="select * from newstudent";
            conn=JdbcUtils.getConnection();
            st = conn.prepareStatement(nstrsql);

            rs=st.executeQuery(nstrsql);
            while (rs.next()) {
                list.add(new Student(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtils.release(conn, st, rs);
        }
        return list;
    }


//    public void delete(String strSql) {
//        try {
//            conn = JdbcUtils.getConnection();
//            st = conn.prepareStatement(strSql);
//            int num = st.executeUpdate();
//            if (num > 0) {
//                System.out.println("删除成功！！");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            JdbcUtils.release(conn, st, rs);
//        }
//    }
//
//    public void update(String strSql) {
//        try {
//            conn = JdbcUtils.getConnection();
//            st = conn.prepareStatement(strSql);
//            int num = st.executeUpdate();
//            if (num > 0) {
//                System.out.println("更新成功！！");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        } finally {
//            JdbcUtils.release(conn, st, rs);
//        }
//    }
//
//    public void find(String strSql) {
//        try {
//            conn = JdbcUtils.getConnection();
//            st = conn.prepareStatement(strSql);
//            rs = st.executeQuery();
//            while (rs.next()) {
//                System.out.print(rs.getString("userid") + "        ");
//                System.out.print(rs.getString("name") + "        ");
//                System.out.print(rs.getString("sex") + "        ");
//                System.out.print(rs.getString("Tel") + "        ");
//                System.out.println();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            JdbcUtils.release(conn, st, rs);
//        }
//    }
}
