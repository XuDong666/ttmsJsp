package ttms.idao;

/**
 * Created by xudong on 17-12-19.
 */

import ttms.model.EmployeeModel;

import java.util.ArrayList;

public interface Employee {
    public EmployeeModel getEmployeeByEmp_no(String emp_no);
    public ArrayList<EmployeeModel> getAllEmploye();
    public boolean insert(EmployeeModel employee);

    // 删
    public boolean delete(int employeeId);

    // 改
    public boolean update(EmployeeModel employee);

    // 根据用户名查(一般用于和界面交互)
    public ArrayList<EmployeeModel> findEmployeeByName(String employeeName);

    // 根据用户id查(一般用于数据内部关联操作)
    public EmployeeModel findEmployeeById(int employeeId);
}
