package ttms.dao;

/**
 * Created by xudong on 17-12-15.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

import ttms.idao.User;
import ttms.model.EmployeeModel;
import ttms.model.UserModel;
import ttms.util.ConnectionManager;

public class UserDao implements User {
    public static final int PAGE_SIZE = 6;
    private int allCount;
    private int allPageCount;
    private int currentPage;

    public int getAllCount()
    {
        return allCount;
    }

    public int getAllPageCount()
    {
        return allPageCount;
    }

    public int getCurrentPage()
    {
        return currentPage;
    }


    @SuppressWarnings("finally")
    public UserModel getUserByNameAndPwd(String userName, String password) {
        UserModel info = null;
        if (userName == "" || password == "") {
            return info;
        }
        Connection conn = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rls = null;
        try {
            pstmt = conn.prepareStatement("select * from user where emp_no=? and emp_pass =?");
            pstmt.setString(1,userName);
            pstmt.setString(2,password);
            rls=pstmt.executeQuery();
            if(rls.next()){
                info=new UserModel();
                info.setEmp_no(rls.getString("emp_no"));
                info.setEmp_pass(rls.getString("emp_pass"));
                info.setHead_path(rls.getString("head_path"));
                info.setType(rls.getInt("type"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            ConnectionManager.close(rls,pstmt,conn);
            return info;
        }
    }

    public ArrayList<UserModel> findUserByPage(int cPage)
    {
        currentPage = cPage;
        ArrayList<UserModel> list = new ArrayList<UserModel>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            // 获取记录总数
            String sql1 = "select count(emp_no) as AllRecord from user";
            conn = ConnectionManager.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql1);
            rs = pstmt.executeQuery();
            if (rs.next())
                allCount = rs.getInt("AllRecord");
            rs.close();
            pstmt.close();
            // 记算总页数
            allPageCount = (allCount + PAGE_SIZE - 1) / PAGE_SIZE;

            // 如果当前页数大于总页数，则赋值为总页数
            if (allPageCount > 0 && currentPage > allPageCount)
                currentPage = allPageCount;

            // 获取第currentPage页数据
            String sql2 = "select * from user limit ?,?";
            pstmt = conn.prepareStatement(sql2);
            pstmt.setInt(1, PAGE_SIZE * (currentPage - 1));
            pstmt.setInt(2, PAGE_SIZE);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                UserModel  employee= new UserModel();
                employee.setEmp_no(rs.getString("emp_no"));
                employee.setEmp_pass(rs.getString("emp_pass"));
                employee.setType(rs.getInt("type"));
                employee.setHead_path(rs.getString("head_path"));
                // 将该用户信息插入列表
                list.add(employee);
            }

            return list;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectionManager.close(rs, pstmt, conn);
            return list;
        }
    }

    @SuppressWarnings("finally")
    public ArrayList<UserModel> findUserByNo(String userNo)
    {
        ArrayList<UserModel> list = new ArrayList<UserModel>();
        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            String sql1 = "select count(emp_no) as AllRecord from user where emp_no=?";
            con = ConnectionManager.getInstance().getConnection();
            pstmt = con.prepareStatement(sql1);
            pstmt.setString(1, userNo);
            rs = pstmt.executeQuery();
            if (rs.next())
                allCount = rs.getInt("AllRecord");
            rs.close();
            pstmt.close();
            // 记算总页数
            allPageCount = (allCount + PAGE_SIZE - 1) / PAGE_SIZE;

            // 如果当前页数大于总页数，则赋值为总页数
            if (allPageCount > 0 && currentPage > allPageCount)
                currentPage = allPageCount;


            // 获取所有用户数据:模糊查询
            pstmt = con.prepareStatement("select * from user where emp_no=?");
            pstmt.setString(1,userNo);// 拼接模糊查询串
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                // 如果有值的话再实例化
                UserModel info = new UserModel();
                info.setEmp_no(rs.getString("emp_no"));
                info.setEmp_pass(rs.getString("emp_pass"));
                info.setType(rs.getInt("type"));
                info.setHead_path(rs.getString("head_path"));
                // 将该用户信息插入列表
                // 加入列表
                list.add(info);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectionManager.close(rs, pstmt, con);
            return list;
        }
    }

    @Override
    public ArrayList<UserModel> getAllUser() {
        return null;
    }

    @Override
    public boolean delete(String emp_no) {

        boolean result = false;
        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        try
        {
            // 删除子某个用户
            String sql = "delete from user where emp_no=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, emp_no);
            pstmt.executeUpdate();
            ConnectionManager.close(null, pstmt, con);

            result = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 关闭连接
            ConnectionManager.close(null, pstmt, con);
            return result;
        }
    }

    @Override
    public boolean update(UserModel user) {
        return false;
    }

    @Override
    public boolean insert(UserModel user) {
        boolean result = false;

        // 获取Connection
        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        try
        {
            String sql = "insert into user(emp_no, emp_pass,type,head_path) values(?,?,?,'')";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, user.getEmp_no());
            pstmt.setString(2, user.getEmp_pass());
            pstmt.setInt(3, user.getType());
            pstmt.executeUpdate();
            result = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            // 关闭连接
            ConnectionManager.close(null, pstmt, con);
            return result;
        }
    }

}
