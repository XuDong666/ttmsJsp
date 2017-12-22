package ttms.idao;

import ttms.model.UserModel;
import java.util.ArrayList;

public interface User {
    public UserModel getUserByNameAndPwd(String userName,String password);
    public ArrayList<UserModel> getAllUser();
    public boolean delete(String emp_no);

    // 改
    public boolean update(UserModel user);
    public boolean insert(UserModel user);
    // 根据用户名查(一般用于和界面交互)
//    public ArrayList<EmployeeModel> findEmployeeByName(String employeeName);
//
//    // 根据用户id查(一般用于数据内部关联操作)
//    public EmployeeModel findEmployeeById(int employeeId);
}
