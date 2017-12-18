package ttms.idao;

import ttms.dao.UserDao;

/**
 * Created by xudong on 17-12-15.
 */
public class FactoryDao {
    public static User createUserDao(){
        return new UserDao();
    }
}
