package ttms.dao;

/**
 * Created by xudong on 17-12-19.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;

import ttms.idao.Employee;
import ttms.model.EmployeeModel;
import ttms.util.ConnectionManager;

public class EmployeeDao implements Employee {

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
    public EmployeeModel getEmployeeByEmp_no(String emp_no) {
        EmployeeModel info = null;
        Connection conn = ConnectionManager.getInstance().getConnection();
        PreparedStatement psmt = null;
        ResultSet rlt = null;
        try {
            psmt=conn.prepareStatement("select * from employee where emp_no=?");
            psmt.setString(1,emp_no);
            rlt=psmt.executeQuery();
            if(rlt.next()){
                info=new EmployeeModel();
                info.setEmp_id(rlt.getInt("emp_id"));
                info.setEmp_addr(rlt.getString("emp_addr"));
                info.setEmp_email(rlt.getString("emp_email"));
                info.setEmp_name(rlt.getString("emp_name"));
                info.setEmp_no(rlt.getString("emp_no"));
                info.setEmp_tel_num(rlt.getString("emp_tel_num"));
                return info;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            ConnectionManager.close(rlt,psmt,conn);
            return info;
        }
    }
    @SuppressWarnings("finally")
    public ArrayList<EmployeeModel> getAllEmploye(){
        ArrayList<EmployeeModel> list=new ArrayList<EmployeeModel>();
        Connection coon=ConnectionManager.getInstance().getConnection();
        EmployeeModel info=null;
        PreparedStatement psmt=null;
        ResultSet rlt=null;
        try{
            psmt=coon.prepareStatement("SELECT * FROM TTMS.employee");
            rlt=psmt.executeQuery();
            while (rlt.next())
            {
                info = new EmployeeModel();

                info.setEmp_id(rlt.getInt("emp_id"));
                info.setEmp_no(rlt.getString("emp_no"));
                info.setEmp_name(rlt.getString("emp_name"));
                info.setEmp_tel_num(rlt.getString("emp_tel_num"));
                info.setEmp_addr(rlt.getString("emp_addr"));
                info.setEmp_email(rlt.getString("emp_email"));
                // 加入列表
                list.add(info);
            }
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            ConnectionManager.close(rlt,psmt,coon);
            return list;
        }

    }


    @SuppressWarnings("finally")
    public ArrayList<EmployeeModel> findEmployeeByPage(int cPage, String emp_name)
    {
        currentPage = cPage;
        ArrayList<EmployeeModel> list = new ArrayList<EmployeeModel>();

        // 若未指定查找某人，则默认全查
        if (null == emp_name || emp_name.equals("null"))
        {
            emp_name = "";
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            // 获取记录总数
            String sql1 = "select count(emp_no) as AllRecord from employee where emp_name like ?";
            conn = ConnectionManager.getInstance().getConnection();
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, "%" + emp_name + "%");
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
            String sql2 = "select * from employee where emp_name like ? limit ?,?";
            pstmt = conn.prepareStatement(sql2);
            pstmt.setString(1, "%" + emp_name + "%");
            pstmt.setInt(2, PAGE_SIZE * (currentPage - 1));
            pstmt.setInt(3, PAGE_SIZE);
            rs = pstmt.executeQuery();
//            EmployeeModel employee = null;
            while (rs.next())
            {
                EmployeeModel  employee= new EmployeeModel();
                employee.setEmp_id(rs.getInt("emp_id"));
                employee.setEmp_no(rs.getString("emp_no"));
                employee.setEmp_name(rs.getString("emp_name"));
                employee.setEmp_tel_num(rs.getString("emp_tel_num"));
                employee.setEmp_addr(rs.getString("emp_addr"));
                employee.setEmp_email(rs.getString("emp_email"));

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

    /**
     * 存储用户信息
     *
     * @return 成功与否boolean
     */
    @SuppressWarnings("finally")
    public boolean insert(EmployeeModel employee)
    {
        boolean result = false;
        if (employee == null)
            return result;

        // 获取Connection
        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        try
        {
            String sql = "insert into employee (emp_no, emp_name, emp_tel_num, emp_addr, emp_email)values(?,?,?,?,?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, employee.getEmp_no());
            pstmt.setString(2, employee.getEmp_name());
            pstmt.setString(3, employee.getEmp_tel_num());
            pstmt.setString(4, employee.getEmp_addr());
            pstmt.setString(5, employee.getEmp_email());

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

    /**
     * 删除用户(通过employeeId)
     *
     * @return 成功与否boolean
     */
    @SuppressWarnings("finally")
    public boolean delete(int employeeId)
    {
        boolean result = false;
        if (employeeId == 0)
            return result;

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        try
        {
            // 删除子某个用户
            String sql = "delete from employee where emp_id=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, employeeId);
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

    /**
     * 修改用户信息
     *
     * @return 成功与否boolean
     */
    @SuppressWarnings("finally")
    public boolean update(EmployeeModel employee)
    {
        boolean result = false;
        if (employee == null)
            return result;

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        try
        {
            String sql = "update employee set emp_no=?,emp_name=?,emp_tel_num=?,emp_addr=?,emp_email=? where emp_id=?";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, employee.getEmp_no());
            pstmt.setString(2, employee.getEmp_name());
            pstmt.setString(3, employee.getEmp_tel_num());
            pstmt.setString(4, employee.getEmp_addr());
            pstmt.setString(5, employee.getEmp_email());
            pstmt.setInt(6, employee.getEmp_id());

            pstmt.executeUpdate();
            result = true;
            return result;
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
//
    @SuppressWarnings("finally")
    public ArrayList<EmployeeModel> findEmployeeByName(String employeeName)
    {
        if (employeeName == null || employeeName.equals(""))
            return null;

        ArrayList<EmployeeModel> list = new ArrayList<EmployeeModel>();

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            String sql1 = "select count(emp_no) as AllRecord from employee where emp_name like ?";
            con = ConnectionManager.getInstance().getConnection();
            pstmt = con.prepareStatement(sql1);
            pstmt.setString(1, "%" + employeeName + "%");
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
            pstmt = con.prepareStatement("select * from employee where emp_name like ?");
            pstmt.setString(1, "%" + employeeName + "%");// 拼接模糊查询串
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                // 如果有值的话再实例化
                EmployeeModel info = new EmployeeModel();
                info.setEmp_id(rs.getInt("emp_id"));
                info.setEmp_no(rs.getString("emp_no"));
                info.setEmp_name(rs.getString("emp_name"));
                info.setEmp_tel_num(rs.getString("emp_tel_num"));
                info.setEmp_addr(rs.getString("emp_addr"));
                info.setEmp_email(rs.getString("emp_email"));
                // 加入列表
                list.add(info);
            }

            return list;

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
    public EmployeeModel findEmployeeByName1(String employeeName)
    {
        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        EmployeeModel info = new EmployeeModel();
        try
        {

            pstmt = con.prepareStatement("select * from employee where emp_name=?");
            pstmt.setString(1, employeeName);// 拼接模糊查询串
            rs = pstmt.executeQuery();

            while (rs.next())
            {
                // 如果有值的话再实例化

                info.setEmp_id(rs.getInt("emp_id"));
                info.setEmp_no(rs.getString("emp_no"));
                info.setEmp_name(rs.getString("emp_name"));
                info.setEmp_tel_num(rs.getString("emp_tel_num"));
                info.setEmp_addr(rs.getString("emp_addr"));
                info.setEmp_email(rs.getString("emp_email"));
                // 加入列表
            }
            return info;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectionManager.close(rs, pstmt, con);
            return info;
        }
    }
    /**
     * 根据employee_id获取用户信息(一般用于数据内部关联操作)
     *
     * @return 用户
     */
    @SuppressWarnings("finally")
    public EmployeeModel findEmployeeById(int employeeId)
    {
        EmployeeModel info = null;
        if (employeeId == 0)
            return info;

        Connection con = ConnectionManager.getInstance().getConnection();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try
        {
            // 获取所有用户数据
            pstmt = con.prepareStatement("select * from employee where emp_id=?");
            pstmt.setInt(1, employeeId);
            rs = pstmt.executeQuery();
            if (rs.next())
            {
                // 如果有值的话再实例化
                info = new EmployeeModel();
                info.setEmp_id(employeeId);
                info.setEmp_no(rs.getString("emp_no"));
                info.setEmp_name(rs.getString("emp_name"));
                info.setEmp_tel_num(rs.getString("emp_tel_num"));
                info.setEmp_addr(rs.getString("emp_addr"));
                info.setEmp_email(rs.getString("emp_email"));
            }
            return info;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        finally
        {
            ConnectionManager.close(rs, pstmt, con);
            return info;
        }
    }

}
