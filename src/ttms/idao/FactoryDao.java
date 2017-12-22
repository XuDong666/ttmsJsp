package ttms.idao;

import ttms.dao.UserDao;
import ttms.dao.EmployeeDao;
/**
 * Created by xudong on 17-12-15.
 */
public class FactoryDao {
    public static User createUserDao(){
        return new UserDao();
    }
    public static Employee createEmployeeDao(){
        return new EmployeeDao();
    }
}
