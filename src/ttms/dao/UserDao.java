package ttms.dao;

/**
 * Created by xudong on 17-12-15.
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import ttms.idao.User;
import ttms.model.UserModel;
import ttms.util.ConnectionManager;

public class UserDao implements User {

    @SuppressWarnings("finally")
    public UserModel getUserByNameAndPwd(String userName, String password) {
        UserModel info = null;
        if (userName == "" || password == "") {
            System.out.print("************");
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

}
